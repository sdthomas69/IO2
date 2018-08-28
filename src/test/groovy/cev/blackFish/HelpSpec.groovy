package cev.blackFish

import grails.test.mixin.TestFor

import spock.lang.Specification

@TestFor(Help)

class HelpSpec extends Specification {
	
	def char25 = "Lorem ipsum dolor sit amet, consectetur"
	def char100 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id libero in ex imperdiet pellentesque ut" 
	
	void "test the selectedController constraint"(){
	
		when: "selectedController is a valid string"
		
			def help1 = new Help(selectedController: "selected", title: "title")
		
		then: "the validation should pass"
		
			help1.validate()
			
			!help1.hasErrors()
			
			help1.errors.errorCount == 0
		
		when: "selectedController is blank"
		
			def help2 = new Help(selectedController: " ", title: "title")
		
		then: "the validation should fail"
		
			!help2.validate()
		
			help2.hasErrors()
		
			help2.errors.errorCount == 1
		
		when: "selectedController is more than 25 characters"
		
			def help3 = new Help(selectedController: char25, title: "title")
		
		then: "the validation should fail"
		
			!help3.validate()
		
			help3.hasErrors()
		
			help3.errors.errorCount == 1
	}
	
	void "test the title constraint"(){
	
		when: "title is blank"
		
			def help4 = new Help(selectedController: "selected", title: " ")
		
		then: "the validation should fail"
		
			!help4.validate()
		
			help4.hasErrors()
		
			help4.errors.errorCount == 1
		
		when: "the title constraint is more than 100 characters"
		
			def help5 = new Help(selectedController: "selected", title: char100)
		
		then: "the validation should fail"
		
			!help5.validate()
		
			help5.hasErrors()
		
			help5.errors.errorCount == 1
	}
	
	void "test the activity constraint"(){
		
		when: "activity is a valid string"
		
			def help6 = new Help(activity: "activity", selectedController: "selected", title: "title")	
		
		then: "the validation should pass"
		
			help6.validate()
		
			!help6.hasErrors()
		
			help6.errors.errorCount == 0
		
		when: "activity is more than 25 characters"
		
			def help7 = new Help(activity: char25, selectedController: "selected", title: "title")
		
		then: "the validation should fail"
		
			!help7.validate()
		
			help7.hasErrors()
		
			help7.errors.errorCount == 1	
	}
	
	void "test the description constraint"(){
		
		when: "description is a valid string"
		
			def help8 = new Help(description: "description", selectedController: "selected", title: "title")
		
		then: "the validation should pass"
		
			help8.validate()
		
			!help8.hasErrors()
		
			help8.errors.errorCount == 0
	}
	
	void "test the primaryImage constraint"(){
		
		when: "primaryImage is a valid file"
		
			def primary = new File()
		
			def help9 = new Help(primaryImage: primary, selectedController: "selected", title: "title")
		
		then: "the validation should pass"
	
			help9.validate()
		
			!help9.hasErrors()
		
			help9.errors.errorCount == 0
	}
	
}
