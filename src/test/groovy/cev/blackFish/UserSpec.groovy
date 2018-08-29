package cev.blackFish

import grails.test.mixin.TestMixin
import grails.test.mixin.TestFor
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(User)
@TestMixin(GrailsUnitTestMixin)
class UserSpec extends Specification {

    def char50 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id libero in ex imperdiet"

    def char255 = "LoremipsumdolorsitametconsecteturadipiscingelitSedmolestieinmagnaneclaoreetAliquamidliberoineximperdietpellentesqueutvitaeaugueProingravidalobortisultriciesPrinimperdietiaculislacusegettempornequevolutpateuismodProinsedmollismassaacbibendumrisusSedac@gmail.com"

    void "test that the username constraint is valid"() {

        when: "the username is a valid string"

        User user = new User(username: 'test1',
                firstName: "Fred",
                lastName: 'White',
                email: 'test@yahoo.com',
                passwordHash: "hash",
                passwordSalt: "salt",
                urlTitle: "title",
                confirmed: false,
                hasPrimaryImage: false,
                view: true
        )

        user.save(flush: true)

        then: "validation should pass"

        user.validate()
        !user.hasErrors()
        user.errors.errorCount == 0


        when: "the username is null"

        User user1 = new User(username: null,
                firstName: "Fred",
                lastName: 'White',
                email: 'test1@yahoo.com',
                passwordHash: "hash",
                passwordSalt: "salt",
                urlTitle: "title",
                confirmed: false,
                hasPrimaryImage: false,
                view: true
        )

        then: "validation should fail"

        !user1.validate()
        user1.hasErrors()
        user1.errors.errorCount == 1

        when: "the username is blank"

        User user2 = new User(username: '',
                firstName: "Fred",
                lastName: 'White',
                email: 'test2@yahoo.com',
                passwordHash: "hash",
                passwordSalt: "salt",
                urlTitle: "title",
                confirmed: false,
                hasPrimaryImage: false,
                view: true
        )

        then: "validation should fail"

        !user2.validate()
        user2.hasErrors()
        user2.errors.errorCount == 1

        when: "the username is not unique"

        User user3 = new User(username: 'test1',
                firstName: "Fred",
                lastName: 'White',
                email: 'test3@yahoo.com',
                passwordHash: "hash",
                passwordSalt: "salt",
                urlTitle: "title",
                confirmed: false,
                hasPrimaryImage: false,
                view: true
        )

        then: "validation should fail"

        !user3.validate()
        user3.hasErrors()
        user3.errors.errorCount == 1

    }
    /*void "test that the firstName constraint is valid"(){

        when: "the firstName is blank"

        User user4 = new User(username: 'test123', firstName: "", lastName: 'White',  email: 'test@yahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: "validation should fail"

        !user4.validate()
        user4.hasErrors()
        user4.errors.errorCount == 1

        when: "the firstName is more than 50 characters"

        User user5 = new User(username: 'test123', firstName: char50, lastName: 'White',  email: 'test@yahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: "validation should fail"

        !user5.validate()
        user5.hasErrors()
        user5.errors.errorCount == 1
    }

    void "test that the lastName constraint is valid"(){

        when: 'the lastName is a valid string'

        User user6 = new User(username: 'test1', firstName: 'Fred', lastName: 'White',  email: 'test@yahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: 'validation should pass'
        user6.validate()
        !user6.hasErrors()
        user6.errors.errorCount == 0

        when: "the lastName is blank"

        User user7 = new User(username: 'test123', firstName: "Fred", lastName: '',  email: 'test@yahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: "validation should fail"

        !user7.validate()
        user7.hasErrors()
        user7.errors.errorCount == 1

        when: "the lastName is more than 50 characters"

        User user8 = new User(username: 'test123', firstName: "Fred", lastName: char50,  email: 'test@yahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: "validation should fail"

        !user8.validate()
        user8.hasErrors()
        user8.errors.errorCount == 1
    }

    void "test that the email constraint is valid"(){

        when: "email is blank"

        User user9 = new User(username: 'test123', firstName: "Fred", lastName: 'white',  email: '', passwordHash: "hash", passwordSalt: "salt")

        then: "validation shoud fail"

        !user9.validate()
        user9.hasErrors()
        user9.errors.errorCount == 1

        when: "email is not a valid email"

        User user10 = new User(username: 'test123', firstName: "Fred", lastName: "white",  email: 'testyahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: "validation should fail"

        !user10.validate()
        user10.hasErrors()
        user10.errors.errorCount == 1

        when: "email is a string longer than 255 characters"

        User user11 = new User(username: 'test123', firstName: "Fred", lastName: "white",  email: char255, passwordHash: "hash", passwordSalt: "salt")

        then: "validation should fail"

        !user11.validate()
        user11.hasErrors()
        user11.errors.errorCount == 1
    }

    void "test that the name constraint is valid"(){

        when: "name is a string longer than 255 characters"

        User user12 = new User(name: char255, username: 'test123', firstName: "Fred", lastName: 'white',  email: 'test@yahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: "validation shoud fail"

        !user12.validate()
        user12.hasErrors()
        user12.errors.errorCount == 1

    }

    void "test that the site constraint is valid"(){

        when: "site is a string longer than 255 characters"

        User user13 = new User(site: char255, username: 'test123', firstName: "Fred", lastName: 'white',  email: 'test@yahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: "validation shoud fail"

        !user13.validate()
        user13.hasErrors()
        user13.errors.errorCount == 1

    }

    void "test that the nonce constraint is valid"(){

        when: "nonce is a string longer than 255 characters"

        User user14 = new User(nonce: char255, username: 'test123', firstName: "Fred", lastName: 'white',  email: 'test@yahoo.com', passwordHash: "hash", passwordSalt: "salt")

        then: "validation shoud fail"

        !user14.validate()
        user14.hasErrors()
        user14.errors.errorCount == 1

    }

    void "test that passwordHash and passwordSalt are valid"(){

        when: "passwordHash and passwordSalt are null"

        User user15 = new User(username: 'test123', firstName: "Fred", lastName: 'white',  email: 'test@yahoo.com', passwordHash: null, passwordSalt: null)

        then:"the validation should fail"

        !user15.validate()
        user15.hasErrors()
        user15.errors.errorCount == 2
    }*/
}