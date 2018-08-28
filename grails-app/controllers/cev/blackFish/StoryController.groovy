package cev.blackFish

import org.apache.shiro.SecurityUtils
//import groovy.transform.CompileStatic
//import javax.servlet.ServletContext

//import grails.plugin.cache.Cacheable

//@CompileStatic
class StoryController extends BaseController {
    
    def tagService
    def searchService
	def storyService
	//def kmlService

	def hello() {
		render "hello"
	}

	/*@Cacheable('breadCrumb')
	def breadcrumbs(Long id) {
		
		if(!id) {
			notFound()
			return
		}
		
		Story story = storyService.readStory(id)

		if(!story) {
			notFound()
			return
		}
		cache:render([template: "/common/breadcrumbs", model: story])
	}*/
	
	/*def people() {
		
		setParams(params)

		params.category = "Person"
		params.sort = "title"
		params.order = "asc"
		
		def storyList = storyQuery()
		
		[
			storyList: storyList,
			params: params
		]
	}
	
	def news() {
		
		setParams(params)

		params.bool = "news"
		def storyList = storyQuery()
		
		[
			storyList: storyList,
			params: params
		]
	}*/
	
	def list() {
		
		setParams(params)
		
		def storyList = storyQuery()
		
		return [storyList:storyList, params:params]
	}

	def remoteStoryList() {
		
		setParams(params)
		
		def storyList = storyQuery()
		
		render(
			template:'remoteStoryList',
			model: [storyList: storyList, params: params]
		)
	}
	
	/*def map() {
		
		Story story = null
		
		if(params.id && isNumeric(params.id.toString())) {
			story = storyService.readStory(params.id.toInteger())
		}
		
		else if(params.title) {
			story = story = storyService.findByTitle(cleanOldTitle(params.title))
		}

        if(!story || !story.mapped) {
            notFound()
            return
        }
		
		render(view:"map", 
			model: [
				story:story,
				iOS:isIOS()
			]
		)
	}

	def kml(Long id) {
		
		if(!id) {
			notFound()
			return
		}
		
		Story story = storyService.readStory(id)

        if(!story) {
            notFound()
            return
        }

		if(story.stories && story.mapped) {
			
			ServletContext context = getServletContext()
			
			String kmlText = kmlService.componentKmlAsString(story, context)
			
			byte[] byteArray = kmlText.getBytes()
			
			response.setHeader("Cache-control", "no-cache")
			response.setHeader ("Content-disposition", "inline; filename=${story}.kml")
			response.setContentType ("application/vnd.google-earth.kml+xml")
			response.outputStream << byteArray
		}
		else render ""
	}*/

    def show(Long id) {
	
		if(!id) {
			notFound()
            return
        }
 
        Story story = storyService.readStory(id)

        if(!story) {
			notFound()
            return
        }
        
        renderStory(story) 
    }
    
    def title(String title) {
        
        if(!title) {
			notFound()
            return
        }
		
		Story story = storyService.findByTitle(title)
        
        if(!story) {
            notFound()
            return
        }
            
        renderStory(story)
    }
	
	private renderStory(Story story) {
		
		if(!story.published && !SecurityUtils.subject.authenticated) {
			
			String uri = "/story/${story.urlTitle}"
			
			redirect(action:"login", controller:"auth", params:["targetUri":uri])
			return
		}
		
		def blogList = story?.category == "Blog" ? storyService.blogs() : null
		
		def tags = story.tags ? tagService.createCloudMap(story.tags) : null
		
		render(
			view:setLayout(story),
			model:[
				story: story,
				iOS: isIOS(),
				tags: tags,
				blogList: blogList,
				hasVideo: story.hasVideos()
			]
		)
	}
	
	private setLayout(Story story) {
		
		def view = "show"
		
		if(story.layout.toString() != 'STANDARD') {
			view = story.layout.toString().toLowerCase()
		}
		if(story?.category == "Person") {
			view = story.category.toLowerCase()
		}
		return view
	}
	
	private List storyQuery() {
		
		def storyList = storyService.list(
			params.max.toInteger(),
			params.offset.toInteger(),
			params.titleOnly as boolean,
			params.q,
			params.view,
			params.category,
			params.bool,
			params.sort,
			params.order,
			params.type
		)
	}

    def search() {
        
        setParams(params)

        def searchResult = searchService.objectSearch(params, "story")
		
        params.object = "story"
		
        render(
			view:"/search/index", 
			model:[searchResult:searchResult, params:params]
		)
    }

}
