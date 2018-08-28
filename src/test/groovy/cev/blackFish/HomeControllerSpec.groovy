package cev.blackFish

import grails.test.mixin.TestFor
import grails.test.mixin.Mock

import spock.lang.Specification

@TestFor(HomeController)
@Mock(Story)
class HomeControllerSpec extends Specification {
	
	HomeService homeServiceMock = Mock(HomeService)
	
	def setup() {
		controller.homeService = homeServiceMock
	}

	def "test the index method"() {
		
		when: "the index method is called"
		
			def model = controller.index()

		then: "the service methods are called and a view is set"
			
			1 * homeServiceMock.homeStories(5, "slideshow") >> new ArrayList()
			
			1 * homeServiceMock.homeStories(3, "featured") >> new ArrayList()
			
			1 * homeServiceMock.homeStories(3, "", "Congratulations") >> new ArrayList()
			
			1 * homeServiceMock.homeStories(3, "", "", "EVENT") >> new ArrayList()
			
			1 * homeServiceMock.homeStories(1, "director") >> new ArrayList()
		
			1 * homeServiceMock.getStoryByTitle("Home") >> new Story()
		
			view == '/home/index'
	}
}
