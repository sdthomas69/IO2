package cev.blackFish

import grails.test.mixin.TestFor

import spock.lang.Specification

@TestFor(Site)

class SiteSpec extends Specification {
	
	def char55 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id libero in ex imperdiet"
	
	def main = new MenuSet()
	
	def bottom = new MenuSet()
	
	void "test that the title constraint is valid"(){
		
		when:"the title is a valid string"
			
			def site1 = new Site(title: "title123", styleSheet: "styleSheet123", main: main, bottom: bottom)
			
			site1.save(flush: true)
		
		then:"the validation should pass"
			
			site1.validate()
			
			!site1.hasErrors()
			
			site1.errors.errorCount == 0
			
		when: "the title is a non unique string"
			
			def site2 = new Site(title: "title123", styleSheet: "styleSheet123", main: main, bottom: bottom)
		
		then:"the validation should fail"
		
			!site2.validate()
			
			site2.hasErrors()
			
			site2.errors.errorCount == 1
		
		when: "the title is more than 55 characters"
		
			def site3 = new Site(title: char55, styleSheet: "styleSheet123", main: main, bottom: bottom)
		
		then: "the validation should fail"
		
			!site3.validate()
		
			site3.hasErrors()
		
			site3.errors.errorCount == 1
			
		when: "the title is blank"
			
			def site4 = new Site(title: " ", styleSheet: "styleSheet123", main: main, bottom: bottom)
			
		then: "the validation should fail"
			
			!site4.validate()
		
			site4.hasErrors()
			
			site4.errors.errorCount == 1

	}
	
	void "test that the styleSheet constraint is valid"(){
		
		when:"the styleSheet blank"
		
			def site5 = new Site(title: "title", styleSheet: " ", main: main, bottom: bottom)
		
		then: "the validation should fail"
		
			!site5.validate()
		
			site5.hasErrors()
			
			site5.errors.errorCount == 1
		
		when: "the styleSheet is more than 55 characters"
		
			def site6 = new Site(title: "title", styleSheet: char55, main: main, bottom: bottom)
		
		then: "the validation should fail"
		
			!site6.validate()
		
			site6.hasErrors()
			
			site6.errors.errorCount == 1
	}
	
	void "test that the portal constraint is valid"(){
		
		when:"the portal is a valid story"
		
			def portal = new Story()
		
			def site7 = new Site(portal: portal, title: "title", styleSheet: "styleSheet", main: main, bottom: bottom)
		
		then: "the validation should pass"
		
			site7.validate()
		
			!site7.hasErrors()
			
			site7.errors.errorCount == 0

	}
	
}
