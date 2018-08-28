package cev.blackFish

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.util.SavedRequest
import org.apache.shiro.web.util.WebUtils

class AuthController extends BaseController {
    
    def shiroSecurityManager
    def cacheService
	def userService
	def userAdminService
	def securityService
    
    static allowedMethods = [signIn:'POST']
	
	def confirm() {
			
		if(!params.nonce) {
			redirect(action:'404', controller:'errors')
			return
		}
		
		User userInstance = userService.findByNonce(params.nonce)
		
		if(!userInstance) {
			flash.message = message(code: "login.failed")
			redirect(action: "login")
			return
		}
		
		if(!userAdminService.confirmUser(userInstance)) {

			flash.message = message(code: "login.failed")
			redirect(action: "login")
			return
		}
		
		def authToken = new UsernameToken(userInstance.username)
		
		try {
			
			SecurityUtils.subject.login(authToken)
			
			if(params.updatePassword) {
				
				flash.message = "Please reset your password"
				
				redirect(action: "updatePasswordForm", controller:"userAdmin", id: userInstance.id)
			}
			else {

				flash.message = "Your account has been activated."
				redirect(action: "edit", controller:"userAdmin", id: userInstance.id)
			}
		}
		catch (AuthenticationException ex) {
 
			flash.message = "Your credentials could not be found"
		
			redirect(action: "login")
		}
	}

    def index() { 
		
		redirect(action: "login", params: params) 
	}

    def login() {

        return [ 
			username: params.username, 
			rememberMe: (params.rememberMe != null), 
			targetUri: params.targetUri 
		]
    }

    def signIn() {

        def authToken = new UsernamePasswordToken(params.username, params.password as String)

        // Support for "remember me"
        if (params.rememberMe) {
            authToken.rememberMe = true
        }
        
        // If a controller redirected to this page, redirect back
        // to it. Otherwise redirect to the root URI.
        def targetUri = params.targetUri ?: "/"
		
		// Only local redirects are allowed
		if(!targetUri.startsWith("/")) targetUri = "/"
        
        // Handle requests saved by Shiro filters.
        def savedRequest = WebUtils.getSavedRequest(request)
        if (savedRequest) {
            targetUri = savedRequest.requestURI - request.contextPath
            if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
        }
        
        try{
            // Perform the actual login. An AuthenticationException
            // will be thrown if the username is unrecognised or the
            // password is incorrect.
            SecurityUtils.subject.login(authToken)

            log.info "Redirecting to '${targetUri}'."
			
			User currentUser = securityService.getUser()

			println "user from service is ${currentUser}"

			redirect(uri: targetUri)
        }
        catch (AuthenticationException ex){
            
            if(maxLoginAttemptsExceeded(request, 7)) {
                render "You have exceeded the maximum number of login attempts and have been locked out"
                return
            }
            
            // Authentication failed, so display the appropriate message
            // on the login page.
            log.info "Authentication failure for user '${params.username}'."
            flash.message = message(code: "login.failed")

            // Keep the username and "remember me" setting so that the
            // user doesn't have to enter them again.
            def m = [ username: params.username ]
            if (params.rememberMe) {
                m["rememberMe"] = true
            }

            // Remember the target URI too.
            if (params.targetUri) {
                m["targetUri"] = params.targetUri
            }

            // Now redirect back to the login page.
            redirect(action: "login", params: m)
        }
    }

    def signOut() {
        // Log the user out of the application.
        SecurityUtils.subject?.logout()

        // For now, redirect back to the home page.
        redirect(uri: "/")
    }

    def unauthorized() {
        render "You do not have permission to access this page."
    }
	
	/**
	 * Simple brute force attack disruptor--limits the number of login attempts by a particular
	 * address
	 * @param request
	 * @return
	 */
	private Boolean maxLoginAttemptsExceeded(request, max) {

		Integer attempts = 1
		
		def address = request.getRemoteAddr() ?: ""
		
		if(!cacheService.remoteAddressExpired(address)) {
			
			attempts = cacheService.getRemoteAddress(address)
			
			if(attempts >= 1 && attempts < max) {

				cacheService.incrementRemoteAddressAttempts(address, attempts)
			}
			
			// At max, return true
			if(attempts == max) {
				log.info "${address} has attempted to login ${attempts} times and has been locked out"
				return true
			}
		}
		else cacheService.setRemoteAddress(address, attempts)
		return false
	}
}
