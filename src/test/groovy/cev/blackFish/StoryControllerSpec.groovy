package cev.blackFish

import grails.test.mixin.TestMixin
import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.util.WebUtils
import org.springframework.beans.factory.annotation.*
import groovy.mock.interceptor.MockFor
import org.grails.plugins.htmlcleaner.HtmlCleaner

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(StoryController)
@Mock([StoryService, SearchService, TagService])
//@TestMixin(GrailsUnitTestMixin)
class StoryControllerSpec extends Specification {

    /*StoryService storyServiceMock = new MockFor(StoryService)

    SearchService searchServiceMock = new MockFor(SearchService)

    def secUtil = new MockFor(SecurityUtils)

    def setup() {
        controller.storyService = storyServiceMock.proxyInstance()
        controller.searchService = searchServiceMock.proxyInstance()
    }*/
    //@Autowired(HtmlCleaner)
    //StoryController.metaClass.cleanHtml

    void "test hello"() {

        given:
        StoryService storyServiceMock = Mock()
        controller.storyService = storyServiceMock
        SearchService searchServiceMock = Mock()
        controller.searchService = searchServiceMock
        TagService tagServiceMock = Mock()
        controller.tagService = tagServiceMock
        HtmlCleaner htmlCleanerMock = Mock()
        controller.htmlCleaner = htmlCleanerMock

        when:
        controller.hello()

        then:
        response.text == 'hello'
    }

    /*def "test the breadcrumbs method with a null id" (){

        when:
        params.id = null
        controller.breadcrumbs()

        then:
        response.status == 404
    }

    def "test the breadcrumbs method"() {

        when:
        views['/common/_breadcrumbs.gsp'] = 'mock template contents'
        params.id = 1
        controller.breadcrumbs()

        then:
        1 * storyServiceMock.readStory(1) >> new Story()
        response.text == "mock template contents"
        response.status == 200
    }*/

    /*def "test the title method with a null title"() {

        //def storyServiceMock = new MockFor(StoryService)

        when:
        params.title = null
        controller.title()

        then:
        response.redirectedUrl == '/errors/404'
    }

    def "test the title method with a valid title"() {

        secUtil.demand.static.getSubject { ->
            [login: {authToken -> false}]
        }

        when:
        params.title = "test"
        controller.title("test")

        then:
        1 * storyServiceMock.findByTitle("test") >> new Story()
        response.status == 302
    }


    def "test the show method with an null id"() {

        when:
        params.id = null
        controller.show()

        then:
        response.redirectedUrl == '/errors/404'
    }

    def "test the show method with a non-numeric id"() {

        when:
        params.id = "foo"
        controller.show()

        then:
        response.redirectedUrl == '/errors/404'
    }

    def "test the show method with a valid id parameter"() {

        secUtil.demand.static.getSubject { ->
            [login: {authToken -> false}]
        }

        when:
        params.id = 1
        controller.show()

        then:
        1 * storyServiceMock.readStory(1) >> new Story()
        response.status == 302
    }

    def "setLayout returns a lower-case stringified Story Layout"() {

        Story.Layout l = "GALLERY"

        Story story = new Story(title:"test", layout:l)

        when:
        controller.setLayout(story)

        then:
        controller.setLayout(story) >> "gallery"
    }

    def "setLayout returns the default view String"() {

        Story.Layout l = "STANDARD"

        Story story = new Story(title:"test", layout:l)

        when:
        controller.setLayout(story)

        then:
        controller.setLayout(story) >> "show"
    }

    def "test renderStory with a published story"() {

        Story.Layout l = "STANDARD"

        Story story = new Story(title:"test", published:true, layout:l)

        when:
        controller.renderStory(story)

        then:
        view == "/story/show"
        model.story != null
        model.iOS == false
    }

    def "test renderStory with an unpublished story"() {

        secUtil.demand.static.getSubject { ->
            [login: {authToken -> false}]
        }

        Story story = new Story(title:"test", urlTitle:"test", published:false)

        when:
        controller.renderStory(story)

        then:
        response.redirectedUrl == '/auth/login?targetUri=%2Fstory%2Ftest'
    }

    def "test search"() {

        when:
        params.q = "test"
        controller.search()

        then:
        //1 * searchServiceMock.objectSearch(['q':'test', 'max':24, 'sort':'date', 'order':'desc', 'offset':0, 'update':'list', 'object':'story'], 'story') >> new ArrayList()
        response.status == 200
        view == "/search/index"
    }*/
}