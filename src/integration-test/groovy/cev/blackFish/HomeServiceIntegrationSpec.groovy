package cev.blackFish

import grails.test.spock.IntegrationSpec

class HomeServiceIntegrationSpec extends IntegrationSpec {

    def homeService

    def setup() {
        /*Story story = Story.build(title:"Home")
        Story story1 = Story.build(title:"Test1", urlTitle:'test1', slideshow:true, published:true)
        Story story2 = Story.build(title:"Test2", urlTitle:'test2', slideshow:true, published:true)*/
    }

    def cleanup() {
    }

    def "dummy test"() { //This is just to get the spec initialized.
        expect:
        1 == 1
    }

    def "getStoryByTitle should return a story by its title"() {

        when:
        Story testStory = homeService.getStoryByTitle("test")

        then:
        assert testStory.title == "test"
    }

    def "homeStories should return a list of stories filtered by parameters"() {

        when: "find stories that are filtered by slideshow"

        List stories = homeService.homeStories(1, "slideshow", "", "")

        then:
        assert stories.size() == 1
        assert stories[0].slideshow == true

        when: "find stories that are filtered by category"

        stories = homeService.homeStories(1, "", "Congratulations", "")

        then:
        assert stories.size() == 1
        assert stories[0].category == "Congratulations"
    }
}
