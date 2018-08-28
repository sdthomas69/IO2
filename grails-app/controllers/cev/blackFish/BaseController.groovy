package cev.blackFish

import grails.util.Holders
import grails.util.Environment
import grails.util.GrailsNameUtils
//import grails.plugins.htmlcleaner.HtmlCleaner

import javax.servlet.ServletContext

class BaseController {
    
    def config = Holders.config
	def htmlService
	def fileService
    
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    /**
     * Sets default values for parameters
     * sort=date, order=desc, offset=0, udate=list, max=default value in config.properties
     * max cannot be larger than 64, less than 0
     * @param params
     * @return
     */
	private setParams(params) {
        
        if(!params.max || !isNumeric(params.max.toString()) || params.max.toInteger() < 0) {
			params.max = config.perPage
        }
		
        //params.max = Math.min(params.max?.toInteger() ?: 0, config.maxPerPage.toInteger())

		if(!params.max) params.max = 50
        
        if(!params.sort) params.sort = "date"
        if(!params.order) params.order = "desc"
        if(!params.offset) params.offset = 0
		if(!params.offset || !isNumeric(params.offset)) params.offset = 0
        if(!params.update) params.update = "list"
    }
    
    /**
     * Helper method for constructing GORM queries
     * @param queryMap
     * @return
     */
	private buildQuery(queryMap) {
        
        def query = {
            queryMap.each { key, value ->
                eq(key, value)
            }
            maxResults(params.max.toInteger() ?: config.perPage?.toString())
            firstResult(params.offset.toInteger() ?: 0)
			setReadOnly true
        }
    }

    /**
     * Returns true or false if the string is a number or not
     * @param str
     * @return
     */
	private Boolean isNumeric(String str) {
        
        return org.apache.commons.lang.math.NumberUtils.isNumber(str)
    }
    
    /**
     * Checks to see if an IOS User-Agent is present in the header
     * @return
     */
	private Boolean isIOS() {
        
        String agent = request.getHeader("User-Agent") ?: ""
        
        return agent.contains("iPhone") || agent.contains("iPad") || agent.contains("iPod") ?: false
    }
    
    /**
     * Checks for the existence of a Quicktime movie in an object's file collection
     * @param object
     * @return
     */
	private Boolean hasVideos(object) {
        
        if(!object.files) return false
        
        def videos = object.files.findAll { file -> file.type == 'Quicktime' }
        
        return (videos.size() > 0)
    }
	
	/**
	 * Check to see if the Grails runtime environment is Development
	 * @return
	 */
	private Boolean isDevelopment() {
		
		return Environment.developmentMode
	}
    
    /**
     * Generate short description string 
     * Strip html tags from a string and make sure it's no bigger than 151 characters
     * @param description
     * @return 
     */
    private addShortDescription(String description) {
        
        return stripTags(description.size() > 150 ? description[0..150] : description)
    }
    
    /**
     * Strip HTML tags
     * @param html
     * @return
     */
	private stripTags(String html) {
        //return html?.replaceAll("\\<.*?>", "")
		return cleanHtml(html, 'none')
    }
	
	/**
	 * Replace spaces with underscores and encode as HTML
	 * @param title
	 * @return
	 */
	private cleanTitle(String title) {
		title = title.replaceAll(' ', '_')
		title = title.encodeAsHTML()
		return title
	}
	
	/**
	 * Replace spaces with underscores
	 * @param title
	 * @return
	 */
	private cleanOldTitle(String title) {
		
		if(title.contains(' ')) {
			title = title.replaceAll(' ', '_')
		}
		return title
	}
	
	/**
	 * Check to see if a domain class's version is greater than the parameter version
	 * @param object
	 * @return
	 */
	private boolean checkVersion(object) {
		
		if(params.version) {
			return object.version > params.version.toLong()
		}
		return false
	}
	
	/**
	 * Helper method to 1) fill the short description with a portion of the description if it doesn't have a 
	 * short description, and 2) set the class's urlTitle field
	 * @param object
	 * @param title
	 * @param shortDescription
	 * @param description
	 * @return
	 */
	private setTextProperties(object, title, shortDescription, description) {
		
		if(!shortDescription && description) object.shortDescription = addShortDescription(object.description)
		
		if(shortDescription) object.shortDescription = stripTags(object.shortDescription)

		if(title) object.setUrlTitle(object.cleanTitle(title))
	}
	
	/**
	 * Sets the optimistic locking failure error message on a domain class and renders it to the edit view
	 * @param object
	 * @return
	 */
	private optimisticLock(object) {
		
		def objectName = GrailsNameUtils.getShortName(object.getClass().getName())
		
		object.errors.rejectValue(
			"version",
			"${objectName}.optimistic.locking.failure",
			"Another user has updated this item while you were editing it. Please save your changes somewhere else, refresh the page to get the latest version and try again."
		)
		render(view:'edit', model:[objectName:object])
	}
	
	/**
	 * 
	 * @param object
	 * @param directory
	 * @return
	 */
	private String writeObject(object, directory) {
		
		String uri = ""
		
		if(config.writeHtmlFiles) {
			 
			def path = getServletContext().getRealPath("")
			 
			if(path) uri = htmlService.writeObjectAsHtml(object, directory, path)
		}
		return uri
	}
	
	/**
	 * Writes all stories, files, and tags out as html files, if specified in config.properties
	 */
	private void writeAllObjects() {
		
		if(config.writeHtmlFiles) {
			
			htmlService.updateObjects()
			
			def path = getServletContext().getRealPath("")
			
			if(path) {

				htmlService.writeAllObjectsAsHtml(path)
			}
		} 
	}
	
	/**
	 * Deletes the specified html file
	 * @param uri
	 */
	private void deleteFile(String uri) {
		
		if(config.writeHtmlFiles && uri) {
		
			String path = getServletContext().getRealPath(uri)
			
			if(path && fileService) {
				fileService.deleteDirectory(path)
			}
		}
	}
	
	private Boolean tense(collection) {
		
		return collection?.size() > 1 ? true : false
	}

	/**
	 * Default not found method for controllers
	 */
	protected void notFound() {
		
		response.status = 404
		redirect(action:"404", controller:"errors")
	}
}

