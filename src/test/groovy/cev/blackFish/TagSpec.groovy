package cev.blackFish

import grails.test.mixin.TestMixin
import grails.test.mixin.TestFor
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Tag)
@TestMixin(GrailsUnitTestMixin)
class TagSpec extends Specification {

    void "test that the title constraint is valid"() {

        when: "the title is valid"

        def tag1 = new Tag(title: "title", urlTitle: "test", uri: "uri")

        tag1.save(flush: true)

        then: "the validation should pass"

        tag1.validate()


        when: "the title is blank"

        def tag2 = new Tag(title: " ", urlTitle: "test", uri: "uri")

        then: "the validation should fail"

        !tag2.validate()


        when: "the title is more than 100 characters"

        def tag3 = new Tag(title: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id ", urlTitle: "test", uri: "uri")

        then: "the validation should fail"

        !tag3.validate()


        when: "the title is not unique"

        def tag4 = new Tag(title: "title", urlTitle: "test", uri: "uri")

        then: "the validation should fail"

        !tag4.validate()


        when: "the title contains invalid characters"

        def tag5 = new Tag(title: "^", urlTitle: "test", uri: "uri")

        then: "the validation should fail"

        !tag5.validate()

    }

    void "test that the urlTitle constraint is valid"() {

        when: "the urlTitle is more than 100 characters"

        def tag6 = new Tag(title: "title1", urlTitle: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id ", uri: "uri")

        then: "the validation should fail"

        !tag6.validate()
    }

    void "test that the uri constraint is valid"() {

        when: "the uri is more than 255 characters"

        def tag8 = new Tag(title: "title3", urlTitle: "test", uri: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id libero in ex imperdiet pellentesque ut vitae augue. Proin gravida lobortis ultricies. Proin imperdiet iaculis lacus, eget tempor neque volutpat euismod. Proin sed mollis massa, ac bibendum risus. Sed ac arcu sit amet nunc aliquam ")

        then: "the validation should fail"

        !tag8.validate()

    }

}