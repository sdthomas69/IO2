package cev.blackFish


import spock.lang.*
import grails.test.mixin.TestFor
import grails.util.Environment

@TestFor(TagController)
//@Mock(Story) //([File, Story]) 

class TagControllerSpec extends Specification {
	
	TagService tagServiceMock = Mock(TagService)
	
	def setup() {
		controller.tagService = tagServiceMock
	}

	//what is [tagInstanceList: tagInstanceList] doing? 
    def "test the list method"() {
		
		when: "params.sort and params.order are not already set"
			def model = controller.list()
			
		then: "params.sort is set to title and params.order is set to asc"
			assert controller.params.sort == "title"
			assert controller.params.order == "asc"
			assert controller.params.max == 24
			assert controller.params.offset == 0
			1 * tagServiceMock.list(24, 0) >> new ArrayList()
			response.status == 200
			model.tagInstanceList != null
			model.tagInstanceList.size() == 0
    }
	
	def "test the title method with a null title parameter" (){
		
		when: "the title method is called with a null object"
			def model = controller.title(null)
		then: "then an error should result"
			response.redirectedUrl == '/errors/404'
	}
	
	def "test the title method with a valid title parameter"(){
		
		when: "the title method is called with an existing title string"
			
			controller.params.selectedTag = "test"
			controller.title()
			
		then: "view should be set to show"
			1 * tagServiceMock.findByTitle("test") >> new Tag()
			view == '/tag/show'
			model.tagInstance
			response.status == 200
	}
	
	def "test the show method with a null id parameter"(){
		
		when: "the show method is called with a null object"
			params.id = null
			def model = controller.show()
		
		then: "an error should result"
			response.redirectedUrl == '/errors/404'
	}
	
	def "test the show method with an invalid id parameter"(){
		
		when: "the show method is called with a nonexistant object"
			params.id = "test"
			def model = controller.show()
		then: "an error should result"
			response.redirectedUrl == '/errors/404'
	}
	
	def "test the show method"(){
		
		when: "the show method is called with valid id parameter"
			params.id = 1
			def model = controller.show()
			
		then: "the service method is invoked"
			1 * tagServiceMock.getTag(1) >> new Tag()
			response.status == 200
			model.tagInstance
	}
	
	def "test the getTagCloud method with a missing parameter"(){
		
		when: "the getTagCloud is called with a null template parameter"
			params.template = null
			def model = controller.getTagCloud()
			
		then: "an error should result"
			response.text =="the template parameter is missing"
	}
	
	def "test the getTagCloud method"(){
		
		when: "the getTagCloud method is called"
			params.template = "/tag/tags"
			views['/tag/_tags.gsp'] = 'mock template contents'
			controller.getTagCloud()
			
		then: "the service method is invoked and the template and model are returned"
			1 * tagServiceMock.createCloudMap() >> [:]
			response.text == "mock template contents"
			response.status == 200
	}
	
}
