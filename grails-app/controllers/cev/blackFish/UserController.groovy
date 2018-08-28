package cev.blackFish

class UserController extends BaseController {
	
	def securityService
	def mailService
	def userAdminService
	def userService
	def exportService
	
	static allowedMethods = [save:'POST', findByEmail:'POST']
	
	def save() {
	 
	 withForm {
		 
		 User userInstance = new User()
		 
		 bindData(userInstance, params, [exclude:['roles', 'confirmed']])
		 
		 userInstance.name = userInstance.firstName + " " + userInstance.lastName
		 
		 userInstance.setUrlTitle(cleanTitle(userInstance.name))
		 
		 userInstance.setNonce(makeNonce())
 
		 if(params.password) securityService.saltAndHashUser(params.password, userInstance)
 
		 if(!userAdminService.save(userInstance)) {
 
			 render(view: "create", model: [userInstance: userInstance])
			 return
		 }
	 
		 def userRole = Role.findByName(config.userRole) // Default User role
		 
		 if(userRole) userInstance.addToRoles(userRole)
		 
		 securityService.addPermission("userAdmin:*:${userInstance.id}", userInstance)
		 
		 try {
			 sendMail {
				 async true
				 to userInstance.email
				 from "mailer@trogdor.ocean.washington.edu"
				 subject "UWPCC Account Confirmation"
				 html g.render(template: "/user/confirmationEmail", model: [userInstance: userInstance])
			 }
			 
			 } catch(Exception e) {
				 log.error("an exception occurred while attempting to send an email because of ${e}")
			 }
			 
			 flash.message = "An email has been sent to your address to confirm your new account"
			 
			 redirect(action: "emailed")
		 }
	 }
	
	/**
	 * Find a user by first and last name; if found, forward to their home page.
	 * @return
	 */
	def home(String url) {

        if(!url) {
			redirect(action:'404', controller:'errors')
            return
        }
		
		User user = userService.findByUrl(url)
		
		if(!user) {
			redirect(action:'404', controller:'errors')
			return
		}

		return [ user:user, params:params ]
    }
	
	def create() {
		
		if(!params.nonce && params.nonce.size() != 64) {
			response.status = 403
			redirect(action:'403', controller:'errors')
			return
		}
		
		[userInstance: new User(params)]
	}

	/**
	 * Looks up a user by their email address.  If a user is found, a nonce is created which is saved
	 * and used to identify the user later.  An email is sent to the user's address with the nonce.
	 * ToDo: The nonce should be expired after a short period of time. 
	 * @param email
	 * @return
	 */
	def findByEmail(String email) {

		withForm {
			
			if(!email) {
				flash.message = "Please enter an email address"
				render(view: "resetPassword")
				return
			}
			
			User userInstance = userService.findByEmail(email)
			
			if(!userInstance) {
				//flash.message = "Your email address could not be found.  Please create a new account."
				render(view: "emailNotFound")
				return
			}
			
			userInstance.setNonce(makeNonce())

			try {
				sendMail {
					async true
					to userInstance.email
					from "mailer@trogdor.ocean.washington.edu"
					subject "UWPCC Password Reset"
					html g.render(template: "/user/passwordResetEmail", model: [userInstance: userInstance])
				}
				
			} catch(Exception e) {
				log.error("an exception occurred while attempting to send an email because of ${e.getCause()}")
			}
			
			userInstance.setConfirmed(false)
			
			if(!userAdminService.save(userInstance)) {
				
				flash.message = "An error has occurred while attempting to reset your password."
				render(view: "/errors/error500")
				return
			}
			
			flash.message = "An email has been sent to your address to with a link to change your password"
			
			redirect(action: "emailed")
		}
	}
	
	def resetPassword() {}
	
	def emailed() {}
	
	/**
	 * Returns a list of people.  If the format parameter is not html, all active people are 
	 * written to a .xls file.  Otherwise, a filtered (by parameters) list is forwarded to the view.
	 * @return
	 */
	def people() {
		
		def userInstanceList = null
		
		if(params?.format && params.format != "html" && params.download == 'all') {
			
			userInstanceList = User.findAllByConfirmed(true, ['sort':'lastName'])
			
			List fields = ["lastName", "firstName", "phone", "email"]
			
			Map labels = ["LastName":"lastName", "FirstName":"firstName", "Phone":"phone", "Email":"email"]
			
			exportList(userInstanceList, fields, labels)
			return
		}
		
		setPeopleParams(params)

		setParams(params)
		
		userInstanceList = userQuery(params)

		[userInstanceList: userInstanceList]
	}
	
	/**
	 * Ajax version of people()
	 * @return
	 */
	def remotePeopleList() {
		
		setPeopleParams(params)

		setParams(params)
		
		def userInstanceList = userQuery(params)
		
		render(
			template: "/user/remotePeopleList", 
			model: [userInstanceList: userInstanceList, params:params]
		)
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	private userQuery(params) {
		
		def userInstanceList = userService.people(
			params.max.toInteger(),
			params.offset.toInteger(),
			params.nameOnly as boolean,
			params.q,
			params.view,
			params.category,
			params.sort,
			params.order,
			params.bool
		)
	}
	
	/**
	 * Default parameters; order by lastname ascending, max 12
	 * @param params
	 * @return
	 */
	private setPeopleParams(params) {
		
		if(!params.sort) params.sort = "lastName"
		if(!params.order) params.order = "asc"
		if(!params.max) params.max = "12"
	}
	
	/**
	 * Accepts a List of people and Maps of fields and labels 
	 * @param personList
	 * @param fields
	 * @param labels
	 * @return
	 */
	private exportList(personList, fields, labels) {
		
		response.contentType = config.grails.mime.types[params.format]
		
		response.setHeader("Content-disposition", "attachment; filename=People.${params.extension}")

		// Formatter closure
		def upperCase = { domain, value ->
			return value.toUpperCase()
		}

		exportService.export(params.format, response.outputStream, personList, fields, labels, [:], [:])
	}
	
	/**
	 * Generates a random 60 character String safe for URL encoding
	 * @return
	 */
	private String makeNonce() {
		
		String nonce = org.apache.commons.lang.RandomStringUtils.random(60, true, true)
		
		return nonce.replaceAll("[^\\d\\w\\.\\-]", "")
	}
	
}
