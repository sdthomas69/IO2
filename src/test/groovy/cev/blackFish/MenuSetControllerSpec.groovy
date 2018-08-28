package cev.blackFish

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock(MenuSet)
@TestFor(MenuSetController)
class MenuSetControllerSpec extends Specification {
	
	MenuSetService menuSetServiceMock = Mock(MenuSetService)

    def setup() {
		controller.menuSetService = menuSetServiceMock
    }

	def "test menuSetTitle without a title"() {
		
		when:
			params.title = null
			controller.getMenuSetByTitle()
		
		then:
			response.status == 404
	}
	
	def "test menuSetTitle without a template"() {
		
		when:
			params.template = null
			controller.getMenuSetByTitle()
		
		then:
			response.status == 404
	}
	
	def "test menuSetTitle with a template and title"() {
		
		when:
			params.template = "/common/menu"
			params.title = "main"
			views['/common/_menu.gsp'] = 'mock template contents'
			controller.getMenuSetByTitle()
		
		then:
			1 * menuSetServiceMock.getMenuSetByTitle("main") >> new MenuSet()
			//response.text == "mock template contents"
			response.status == 200
	}
}
