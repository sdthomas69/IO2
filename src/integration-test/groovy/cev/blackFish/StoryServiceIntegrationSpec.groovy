package cev.blackFish

import grails.test.spock.IntegrationSpec

class StoryServiceIntegrationSpec extends IntegrationSpec {

    def grailsApplication
    def storyService

    def setup() {

        /*Story.Type t = "EVENT"
        
        Story story = Story.build(
            title:'test',
            description:'test',
            urlTitle:'test',
            published:true,
            type:t,
            id:'1'
        )
        
        Story story1 = Story.build(
            title:'test1',
            description:'test1',
            urlTitle:'test1',
            category:'Congratulations',
            published:true,
            magazine:true,
            type:t
        )
        
        Story story2 = Story.build(
            title:'test2',
            description:'test2',
            urlTitle:'test2',
            category:'Blog',
            slideshow:true,
            published:true
        )*/
    }

    def cleanup() {}

    def "dummy test"() { //This is just to get the spec initialized.
        expect:
        1 == 1
    }

    def "findByTitle should return a story by its title"() {

        when:

        Story testStory = storyService.findByTitle("test")

        then:
        assert testStory.title == "test"
    }

    def "readStory should get a story by its id"() {

        when:

        Story testStory = storyService.readStory(1)

        then:
        assert testStory.id == 1
    }

    def "blogs should return a list containing only blog stories"() {

        when:

        List blogs = storyService.blogs()

        then:

        assert blogs.size() == 1
        assert blogs[0].category == 'Blog'
    }

    def "list should return a list of stories filtered by parameters"() {

        when: "find a story with the Blog category"

        List stories = storyService.list(1, 0, false, "", "", "Blog", "", "", "", "")

        then:

        assert stories.size() == 1
        assert stories[0].category == 'Blog'

        when: "find a story where the boolean magazine property is true"

        stories = storyService.list(1, 0, false, "", "", "", "magazine", "", "", "")

        then:

        assert stories.size() == 1
        assert stories[0].magazine == true

        when: "find a story with test in the title text"

        stories = storyService.list(1, 0, true, "test")

        then:

        assert stories.size() == 1
        assert stories[0].title.contains("test")

        when: "find 3 stories with test in the any of the searchable text fields"

        stories = storyService.list(3, 0, true, "test")

        then:

        assert stories.size() == 3
        assert stories[0].description.contains("test")


        when: "find a story with the type EVENT"

        stories = storyService.list(1, 0, false, "", "", "", "", "", "", "event")

        then:

        Story.Type t = "EVENT"
        assert stories.size() == 1
        assert stories[0].type.toString() == t.toString()
    }
}
