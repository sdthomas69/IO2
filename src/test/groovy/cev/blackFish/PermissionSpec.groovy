package cev.blackFish

import grails.test.mixin.TestFor

import spock.lang.Specification

@TestFor(Permission)

class PermissionSpec extends Specification {

    void "test that the permissionsString constraint is valid"(){
		
		def user = new User()
		
		when: "the permissionsString is a valid string"
		
			def permission1 = new Permission(permissionsString: "permissionString", user: user)
		
		then: "the validation should pass"
		
			permission1.validate()
		
			!permission1.hasErrors()
		
			permission1.errors.errorCount == 0
		
		when: "the permissionsString is null"
		
			def permission2 = new Permission(permissionsString: null, user: user)
		
		then: "the validation should fail"
		
			!permission2.validate()
		
			permission2.hasErrors()
		
			permission2.errors.errorCount == 1
		
		when: "the permissionsString is blank"
		
			def permission3 = new Permission(permissionsString: " ", user: user)
			
		then: "the validation should fail"
		
			!permission3.validate()
			
			permission3.hasErrors()
			
			permission3.errors.errorCount == 1
			
	}
	
	void "test that the user constraint is valid"(){
		
		when: "the user is null"
		
			def permission4 = new Permission(permissionsString: "permissionString", user: null)
		
		then: "the validation should fail"
		
			!permission4.validate()
		
			permission4.hasErrors()
		
			permission4.errors.errorCount == 1
	}
}
