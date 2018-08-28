package cev.blackFish

import org.apache.shiro.SecurityUtils

class FileController extends BaseController {
    
    def searchService
	def fileService
	def tagService
	
	def list() {
		
		setParams(params)
		
		def fileList = fileService.list(
			params.max.toInteger(),
			params.offset.toInteger(),
			params.titleOnly as boolean,
			params?.type ?: 'PowerPoint',
			params.q,
			params.bool,
			params.sort,
			params.order,
			params.category
		)
		
		return [fileList:fileList, params:params]
	}
		
	/**
	 * Looks up and returns a file object based on its ID.  If the file is not published
	 * and the user is not logged in, the user will be redirected to the login page.  If 
	 * the file does not have a tiles director or height or width, a 404 is issued
	 * @param id
	 * @return file
	 */
	def pano(Long id) {
		
		if(!id) {
			redirect(action:'404', controller:'errors')
			return
		}
			
		File file = fileService.readFile(id)
		
		if(!file) {
			redirect(action:'404', controller:'errors')
			return
		}
		
		if(!file.published && !SecurityUtils.subject.authenticated) {
			
			redirect(action:'login',
				controller:'auth',
				params:["targetUri":"/file/pano/${file.id}"]
			)
			return
		}
		
		if(!file.tilesDirectory || !file.height || !file.width) {
			
			redirect(action:'404', controller:'errors')
			return
		}
		
		[file:file]
	}

    /**
     * Looks up and returns a file object based on its ID.  If the file is not published
	 * and the user is not logged in, the user will be redirected to the login page. 
     * @param id
     * @return file
     */
	def show(Long id) {
	
		if(!id) {
		    redirect(action:'404', controller:'errors')
            return
        }
           
        File file = fileService.readFile(id)
        
        if(!file) {
            redirect(action:"404", controller:"errors")
			return
        }

        renderFile(file)
    }
    
    /**
     * Looks up and returns a file object based on its title.  If the file is not published
	 * and the user is not logged in, the user will be redirected to the login page.
     * @param title
     * @return
     */
	def title(String title) {
        
        if(!title) {
            redirect(action:"404", controller:"errors")
            return
        }
		
		File file = fileService.findByTitle(title)
        
        if(!file) {
            redirect(action:"404", controller:"errors")
            return
        }
		
		renderFile(file)   
    }
	
	/**
	 * Renders a file object, the isIOS return value, the tags collection and parameters
	 * and the return value of setView()  If the file is not published
	 * and the user is not logged in, the user will be redirected to the login page. 
	 * @param file
	 * @return
	 */
	private renderFile(File file) {
		
		if(!file.published && !SecurityUtils.subject.authenticated) {
			redirect(
				action:"login", 
				controller:"auth", 
				params:["targetUri":"/file/${file.urlTitle}"]
			)
			return
		}
		
		def tags = file.tags ? tagService.createFileCloudMap(file.tags) : null

		render(view:setView(file),
			model:[
				file:file,
				iOS:isIOS(),
				tags:tags,
				params:params
			]
		)
	}

    /**
     * Returns a searchResult object based on the parameters passed in
     * @return searchResult
     */
	def search() {
		
        setParams(params)
		
        def searchResult = searchService.objectSearch(params, "file")
		
        params.object = "file"
		
        render(
			view:"/search/index", 
			model:[searchResult:searchResult, params:params, layout:"main"]
		)
    }
	
	/**
	 * Returns a view string for a file object.  Default is 'show'.  If file is a streaming video, view is 
	 * stream
	 * @param file
	 * @return
	 */
	private setView(File file) {
		
		def view = "show"
		
		switch(true) {
			
			case file.stream:
				view = "stream"
				break
			
			default: view = "show"
		}
		return view
	}
}
