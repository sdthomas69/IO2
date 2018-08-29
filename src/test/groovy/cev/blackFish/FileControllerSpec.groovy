package cev.blackFish

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(FileController)
@Mock([File, User])
@TestMixin(GrailsUnitTestMixin)
class FileControllerSpec extends Specification {

    FileService fileServiceMock = Mock(FileService)
    SearchService searchServiceMock = Mock(SearchService)
    def secUtil = mockFor(SecurityUtils)

    def setup() {
        controller.fileService = fileServiceMock
        controller.searchService = searchServiceMock
    }

    def "test the title method with a null title"() {

        when:
        params.title = null
        controller.title()

        then:
        response.redirectedUrl == '/errors/404'
    }

    def "test the title method with a valid title"() {

        File file = new File(
                title: "test",
                published: true
        ).save(validate: false)

        secUtil.demand.static.getSubject { ->
            [login: { authToken -> false }]
        }

        when:
        params.title = file.title
        controller.title()

        then:
        1 * fileServiceMock.findByTitle(file.title) >> file
        response.status == 200
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

        File file = new File(
                title: "test",
                published: true,
                id: 1
        ).save(validate: false)

        secUtil.demand.static.getSubject { ->
            [login: { authToken -> false }]
        }

        when:
        params.id = file.id
        controller.show()

        then:
        1 * fileServiceMock.readFile(file.id) >> file
        response.status == 200
    }

    def "test the pano method with a valid id parameter"() {

        File file = new File(
                title: "test",
                published: true,
                filesDirectory: "test",
                height: 1,
                width: 1,
                id: 1
        ).save(validate: false)

        secUtil.demand.static.getSubject { ->
            [login: { authToken -> false }]
        }

        when:
        params.id = file.id
        controller.pano()

        then:
        1 * fileServiceMock.readFile(file.id) >> file
        response.status == 302 //WFT??
    }

    def "test the pano method with an null id"() {

        when:
        params.id = null
        controller.pano()

        then:
        response.redirectedUrl == '/errors/404'
    }

    def "test the pano method with an unpublished file"() {

        File file = new File(
                title: "test",
                published: true,
                id: 1
        ).save(validate: false)

        secUtil.demand.static.getSubject { ->
            [login: { authToken -> false }]
        }

        when:
        params.id = file.id
        controller.pano()

        then:
        1 * fileServiceMock.readFile(file.id) >> file
        response.status == 302 // Hmmm
        //response.redirectedUrl == '/auth/login?targetUri=%2Ffile%2Fpano%2F1'
    }

    def "setView returns stream for a streaming file"() {

        File file = new File(title: "test", stream: true)

        when:
        controller.setView(file)

        then:
        controller.setView(file) >> "stream"
    }

    def "setView returns the default view for a file"() {

        File file = new File(title: "test")

        when:
        controller.setView(file)

        then:
        controller.setView(file) >> "show"
    }

    def "test renderFile with a published file"() {

        File file = new File(title: "test", published: true)

        when:
        controller.renderFile(file)

        then:
        view == "/file/show"
        model.file != null
        model.iOS == false
    }

    def "test renderFile with an unpublished story"() {

        secUtil.demand.static.getSubject { ->
            [login: { authToken -> false }]
        }

        File file = new File(title: "test", urlTitle: "test", published: false)

        when:
        controller.renderFile(file)

        then:
        response.redirectedUrl == '/auth/login?targetUri=%2Ffile%2Ftest'
    }

    def "test search"() {

        when:
        params.q = "test"
        controller.search()

        then:
        //1 * searchServiceMock.objectSearch(['q':'test', 'max':24, 'sort':'date', 'order':'desc', 'offset':0, 'update':'list', 'object':'story'], 'story') >> new ArrayList()
        response.status == 200
        view == "/search/index"
    }
}