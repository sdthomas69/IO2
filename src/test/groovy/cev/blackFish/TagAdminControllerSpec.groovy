package cev.blackFish

import spock.lang.*
import spock.lang.Specification

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import grails.util.Environment

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.util.WebUtils


@TestFor(TagAdminController)
@Mock([Tag, User])
class TagAdminControllerSpec extends Specification {

    TagAdminService tagAdminServiceMock = Mock(TagAdminService)

    StoryAdminService storyAdminServiceMock = Mock(StoryAdminService)

    def secUtil = mockFor(SecurityUtils)

    Tag tag = null

    def setup() {
        controller.tagAdminService = tagAdminServiceMock
        controller.storyAdminService = storyAdminServiceMock
        tag = new Tag(title: "test", id: 1)
        tag.save(validate: false)
    }

    def "test the edit method with a null id parameter"() {

        when:
        params.id = null
        def model = controller.edit()

        then: "redirect to list page"
        response.redirectedUrl == '/tag/list'
    }

    def "test the edit method with an invalid id parameter"() {

        when:
        params.id = "test"
        def model = controller.edit()

        then: "redirect to list page"
        response.redirectedUrl == '/tag/list'
    }

    def "test the edit method with a valid id parameter"() {

        when:
        params.id = 1
        def model = controller.edit()

        then: "the service method is invoked"
        1 * tagAdminServiceMock.getTag(1) >> tag
        response.status == 200
        model.tagInstance != null
    }

    def "Test the create action returns the correct model"() {

        when: "The create action is executed"
        def model = controller.create()

        then: "The model is created"
        model.tagInstance != null
    }

    def "Test the story search method"() {

        when:
        params.id = 1
        def model = controller.storySearch()
        views['/tagAdmin/_remoteStorySearch.gsp'] = 'mock template contents'

        then: "The search results are returned"
        1 * tagAdminServiceMock.getTag(1) >> tag
        1 * tagAdminServiceMock.storySearch(tag, 24, 0, null, null, false, null, null)
        response.status == 200
        response.text == "<p>There are no results for your query</p>"
    }

    /*def "Test the save action correctly persists an instance"() {
        
        Tag tag = new Tag(title:"test", id:1)

        when:"The save action is executed with an invalid instance"
            request.method = 'POST'
            params.title = ""
            controller.save()
        
        then:"The create view is rendered again with the correct model"
            model.tagInstance!= null
            view == '/tagAdmin/edit'
            
        when:"The save action is executed with a valid instance"
            response.reset()
            //populateValidParams(params)
            request.method = 'POST'
            params.title = "test"
            def model = controller.save()

        then:"A redirect is issued to the edit action"
            
            //1 * tagAdminServiceMock.save(model.tagInstance)
            response.status == 200
            view == '/tagAdmin/edit'
            !model.tagInstance.hasErrors()
            //response.redirectedUrl == '/tagAdmin/edit/1'
            //model.tagInstance != null
            //controller.flash.message != null
    }*/
}
