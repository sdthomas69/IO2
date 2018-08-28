package cev.blackFish

import grails.test.mixin.TestFor

import spock.lang.Specification
@TestFor(Role)

class RoleSpec extends Specification {
	
	void "test that the name constraint is valid"(){

		when: 'the name is unique'

			def role1 = new Role(name: "Test")
			
			role1.save(flush: true)
			
		then: 'validation should pass'
		
			role1.validate()
		
		when: 'the name is null'
			
			def role2 = new Role(name: null)
			
		then: 'validation should fail'
					
			!role2.validate()
		
		when: 'the name is blank'
			
			def role3 = new Role(name: " ")
			
		then: 'validation should fail'
					
			!role3.validate()
		
			
		when: 'the name is not unique'

			def role4 = new Role(name: "Test")
			
		then: 'validation should fail'
			
			!role4.validate()
	}

}
