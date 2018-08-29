package cev.blackFish

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import grails.util.Environment
import grails.util.Holders
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(BaseController)
@Mock([File, Tag, Story])
@TestMixin(GrailsUnitTestMixin)
class BaseControllerSpec extends Specification {

    //returns false because the platform is currently mac, not iphone, ipod, or ipad
    def "test the isIOS method"() {

        when: "the isIOS method is called"

        def model = controller.isIOS()

        then: "the correct platform is returned"

        assert model == false

    }

    def "test the optimisticLock method"() {

        Story story = new Story(title: "title", description: "description").save(validate: false)

        when: "the optimisticLock method is called"

        controller.optimisticLock(story)

        then: "view is set to show, and the model is returned"

        view == '/base/edit'

        model.object != null

        response.status == 200

    }

    def "test the writeObjects method"() {

        File file = new File(title: "title", description: "description").save(validate: false)

        Story story = new Story(title: "title1", description: "description1").save(validate: false)

        when: "writeObjects is called with a nonexistant filesystem path"

        def model = controller.writeObject(story, file)

        then: "uri will be returned as an empty string"

        assert model == ""

    }


    def "test the setTextProperties"() {

        Story story = new Story(title: "title", description: "description").save(validate: false)

        when: "the short description is empty"

        controller.setTextProperties(story, "title with space", "", "description")

        then: "the short description is filled from the description field"

        story.shortDescription == "description"

        when: "there are spaces in the title"

        controller.setTextProperties(story, "title with space", "", "description")

        then: "the urlTitle field is filled with the title field sans spaces"

        story.urlTitle == "title_with_space"

    }

    //false, because we are currently in the testing environment
    void "test the isDevelopment method"() {

        when: "isDevelopment is called"

        controller.isDevelopment()

        then: "it should return false"

        controller.isDevelopment() == false

    }


    void "test the checkVersion method"() {

        when: "params version is not already set"

        Tag tag1 = new Tag(title: "test tag").save(validate: false)

        tag1.version = 1

        def model1 = controller.checkVersion(tag1)

        then: "return false"

        assert model1 == false

        when: "params version is set and the object version > the params version"

        Tag tag2 = new Tag(title: "test tag").save(validate: false)

        params['version'] = '0'

        tag2.version = 1

        def model2 = controller.checkVersion(tag2)

        then: "return true"

        assert model2 == true

        when: "params version is set and the object version is < the params version"

        Tag tag3 = new Tag(title: "test tag").save(validate: false)

        params['version'] = '1'

        tag3.version = 0

        def model3 = controller.checkVersion(tag3)

        then: "return false"

        assert model3 == false

    }


    void "test the hasVideosMethod"() {

        when: "a tag has a Quicktime video in its files collection"

        Tag tag1 = new Tag(title: "test tag").save(validate: false)

        File file1 = new File(title: "test file", description: "test", type: "Quicktime").save(validate: false)

        tag1.addToFiles(file1)

        then: "hasVideos will return true"

        assert controller.hasVideos(tag1) == true

        when: "a tag has a non-Quicktime video in its files collection"

        Tag tag2 = new Tag(title: "test tag").save(validate: false)

        File file2 = new File(title: "test file", description: "test", type: "VLC").save(validate: false)

        tag2.addToFiles(file2)

        then: "has videos will return false"

        assert controller.hasVideos(tag2) == false

        when: "a tag does not contain any type of video in its file collection"

        Tag tag3 = new Tag(title: "test tag").save(validate: false)

        then: "has videos will return false"

        assert controller.hasVideos(tag3) == false
    }

    void "Test the isNumeric method"() {

        when: "A numbered String is passed in"

        then: "The method returns true"

        controller.isNumeric("23") == true

        when: "A non-numeric string is passed in"

        then: "The method returns false"

        controller.isNumeric("foo") == false
    }

    void "test the stripTags method"() {

        when: "a string contains html tags"

        String tagged = "I am a <a href='http://www.cev.washington.edu'>link</a>"

        then: "The method removes the tags"

        assert controller.stripTags(tagged) == "I am a link"
    }

    void "test the cleanTitle method"() {

        when: "a string is passed in"

        String tagged = "black & Ocean"

        then: "The method replaces any spaces with underscores and encodes the string in html"

        assert controller.cleanTitle(tagged) == "black_&amp;_Ocean"
    }

    void "test the cleanOldTitle method"() {

        when: "a string containing a space is passed in"

        String tagged = "3D model"

        then: "The method replaces the space with an underscore"

        assert controller.cleanOldTitle(tagged) == "3D_model"
    }

    void "test the addShortDescription method"() {

        when: "a string of less than 151 characters is passed in"

        String shorttagged = "foo<a href='http://www.cev.washington.edu'>"

        then: "The method returns the string with html tags stripped"

        assert controller.addShortDescription(shorttagged) == "foo"

        when: "a string of more than 151 characters is passed in"

        String longtagged = "Scientific Visualization converts Earth and Ocean science data into compelling visual representations with high aesthetic and quantitative value. Through a masterful application of color theory, text"

        then: "The method ends the string at its 151st character"

        assert controller.addShortDescription(longtagged) == "Scientific Visualization converts Earth and Ocean science data into compelling visual representations with high aesthetic and quantitative value. Throu"
    }

    void "test the setParams method"() {

        when: "params are passed in without inital values"

        controller.setParams(params)

        then: "params should equal default values"

        assert controller.params.max == 24

        assert controller.params.sort == "date"

        assert controller.params.order == "desc"

        assert controller.params.offset == 0

        assert controller.params.update == "list"

        when: "params are passed in with some inital values"

        params.remove('max')

        params['max'] = "23"

        params.remove('order')

        params['order'] = "ASC"

        controller.setParams(params)

        then: "params should equal those inital values"

        assert controller.params.max == 23

        assert controller.params.sort == "date"

        assert controller.params.order == "ASC"

        assert controller.params.offset == 0

        assert controller.params.update == "list"


        when: "params.max is greater than the max per-page configuration"

        params.remove('max')

        params['max'] = "100"

        controller.setParams(params)

        then: "params should equal the max per-page configuration"

        assert controller.params.max == Holders.grailsApplication.config.maxPerPage.toInteger()
        assert controller.params.max == 64

        when: "params.max is less than 0"

        params.remove('max')

        params['max'] = "-100"

        controller.setParams(params)

        then: "params should equal the default per-page configuration"

        assert params.max == Holders.grailsApplication.config.perPage.toInteger()
        assert controller.params.max == 24
    }


    void "test the buildQuery method"() {

    }
}