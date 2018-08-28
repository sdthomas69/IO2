package cev.blackFish

import groovy.json.JsonBuilder

class HomeController extends BaseController {
	
	def homeService
	def restService

    /**
     * 
     * @return
     */
	def index() { 
        
        def stories = homeService.homeStories(5, "slideshow")
		
		def features = homeService.homeStories(3, "featured")
		
		Story homeStory = homeService.getStoryByTitle("Home")
		
		render(
			view:"/home/index",
			model:[
				stories: stories, 
				features: features, 
				homeStory: homeStory
			]
		)
    }
	
	def map() {

	}

	private String serviceURL = "https://ooinet.oceanobservatories.org/api/uframe/status/"

	def getSite() {
		def data = restService.getURL("${serviceURL}sites/RS")
		render data.json
	}

	def getPlatform() {

		String platform = params.platform ?: "RS03AXPD"

		def data = restService.getURL("${serviceURL}platforms/${platform}")

		render data.json
	}

	/**
	 *
	 *
	 * @return
	 */
	private stringBuildJson(List stories) {

		def json = StringBuilder.newInstance()

		json << '{ "type": "FeatureCollection", "features": ['

		stories.eachWithIndex { story, index ->

			json << buildJson(story)

			if(index + 1 < stories.size()) json << ","
		}
		json << "]}"

		return json.toString()
	}

	/**
	 *
	 *
	 * @return
	 */
	private buildJson(Story story) {

		def jb = new JsonBuilder()

		String href = blackFish.setLink("object": story, "name": "story")

		String link = "<a href='${href}'>${story.pageTitle}</a>"

		Map feature = jb {
			type "Feature"
			geometry {
				type "Point"
				coordinates([story.longitude, story.latitude])
			}
			properties {
				title(link)
			}
		}
		jb.toString()
	}
}
