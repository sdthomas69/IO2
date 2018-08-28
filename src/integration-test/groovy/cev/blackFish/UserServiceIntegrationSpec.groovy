package cev.blackFish

import grails.test.spock.IntegrationSpec

class UserServiceIntegrationSpec extends IntegrationSpec {

	def grailsApplication
	
	def UserService
	
	def setup() {

	}
	
	def cleanup() {}
		
	def "dummy test"() { //This is just to get the spec initialized.
		expect:
			1==1
	}
	
	def "getUser should find a user by its id"() {
		
		when:
		
			User testUser = UserService.getUser(1)
		
		then:
		
			assert testUser.id == 1
	}
	
	def "list should return a list of users filtered by parameters"() {
		
		when: "find 2 users with 'user' in any of the searchable text fields"
		
			List users = userService.list(2, 0, false, "user", "", "", "", "", "", "")
		
		then:
		
			assert users.size() == 2
			
			assert users[0].firstName.contains("user")
			
			
		when: "find a user with user1 in the name field"
			
			users = userService.list(1, 0, true, "user1", "", "", "", "", "", "")
			
		then:

			assert users.size() == 1
			
			assert users[0].name== "user1"
			
		
		when: "find a user by category"
		
			users = userService.list(1, 0, false, "", "", "student", "", "", "", "")
		
		then:
			
			assert users.size() == 1
			
			assert users[0].category == 'Student'
		
		when: "find a user where the boolean gradAdvisor property is true"
		
			users = userService.list(1, 0, false, "", "", "", "", "", "", "gradAdvisor")
		
		then:
			
			assert users.size() == 1
			
			assert users[0].gradAdvisor == true
		
	}
	
	def "findByEmail should find a user by its email"(){
		
		when:
		
			User testUser = UserService.findByEmail("test1@gmail.com")
		
		then:
		
			assert testUser.email == "test1@gmail.com"
		
	}
	
	def "findByNonce should find a user by its nonce"(){
		
		when:
		
			User testUser = UserService.findByNonce("user1")
		
		then:
		
			assert testUser.nonce == "user1"
		
	}
	
	def "findByName should find a user by its name"(){
		
		when:
		
			User testUser = UserService.findByName("user1")
		
		then:
		
			assert testUser.name == "user1"
		
	}
	
}
