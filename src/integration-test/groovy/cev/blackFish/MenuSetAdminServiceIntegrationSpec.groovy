package cev.blackFish

import grails.test.spock.IntegrationSpec

class MenuSetAdminServiceIntegrationSpec extends IntegrationSpec {

	def grailsApplication
	
	def MenuSetAdminService
	
	def setup() {
		
		MenuSet menu = MenuSet.build(title:'menu')
		
		MenuSet menuToDelete = MenuSet.build(title:"Delete Me")
		
		Story story = Story.build(
			title:'t',
			description:'test',
			type:"EVENT",
			id:'1'
		)
				
		MenuSet menu2 = MenuSet.build(title: "menu2").save(flush: true, failOnError: true)
					
		menu2.addToChildren(story).save(flush: true, failOnError: true)

	}
	
	def cleanup() {}
		
	def "dummy test"() { //This is just to get the spec initialized.
		expect:
			1==1
	}
	
	//depends on a bootstrapped object 
	def "getMenuSet should find a menuset by its id"() {
		
		when:
		
			MenuSet testMenu = MenuSetAdminService.getMenuSet(1)
		
		then:
		
			assert testMenu.id == 1
	}
	
	def "findByTitle should get a MenusSet by its title"() {
		
		when:
		
			MenuSet testMenu = MenuSetAdminService.findByTitle('menu')
		
		then:
		
			assert testMenu.title == 'menu'
	}
		
	def "save should save the menuset"() {
		
		def menu = MenuSetAdminService.findByTitle('menu')
		
		when:
		
			def bool = MenuSetAdminService.save(menu)
		
		then:
		
			assert bool == true
	}
	
	def "delete should delete a menuset"() {
		
		when:
		
			MenuSet menu = MenuSet.findByTitle("Delete Me")
			
			assert MenuSet.findByTitle("Delete Me") != null
		
		then:
		
			MenuSetAdminService.delete(menu) == true
			
			assert MenuSet.findByTitle("Delete Me") == null
	}
	
	def "removeStory should remove a story from a menuset"() {

		when:
		
			Story story = Story.findByTitle("t")

			def model = MenuSetAdminService.removeStory(story, MenuSetAdminService.findByTitle('menu2'))
		
		then:

			assert model == true 
		
	}

}
