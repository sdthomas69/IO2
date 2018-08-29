package cev.blackFish

import grails.test.spock.IntegrationSpec

class StoryAdminServiceIntegrationSpec extends IntegrationSpec {

    def grailsApplication

    def storyAdminService

    def setup() {

        Story storyToDelete = Story.build(title: "Delete Me")

        Story.Type t = "EVENT"

        Story story = Story.build(
                title: 't',
                description: 'test',
                type: t,
                id: '1'
        )

        Story story1 = Story.build(
                title: 't1',
                description: 'test',
                category: 'Congratulations',
                magazine: true,
                type: t
        )

        Story story2 = Story.build(
                title: 't2',
                description: 'test',
                category: 'Blog',
                slideshow: true
        )

        File file = File.build(
                title: "f",
                urlTitle: "test",
                type: "Image"
        )

        Tag tag = Tag.build(title: "t")
    }

    def cleanup() {}

    def "dummy test"() { //This is just to get the spec initialized.
        expect:
        1 == 1
    }

    // Depends on a BootStrapped Story
    def "getStory should get a story by its id"() {

        when:
        Story testStory = storyAdminService.getStory(1)

        then:
        assert testStory.id == 1
    }

    def "delete should delete a story"() {

        when:
        Story story = Story.findByTitle("Delete Me")
        assert Story.findByTitle("Delete Me") != null

        then:
        storyAdminService.delete(story) == true
        assert Story.findByTitle("Delete Me") == null
    }

    def "tagSearch should return a list of tags filtered by parameters"() {

        Story testStory = Story.findByTitle("t")

        when: "find a Tag with the title 't'"

        List tags = storyAdminService.tagSearch(testStory, 1, 0, "t")

        then:
        assert tags.size() == 1
        assert tags[0].title == 't'
    }

    def "addTags should add a Tag to a Story"() {

        Story testStory = Story.findByTitle("test")

        //Story testStory = Story.build(title:"tagStory", urlTitle:"tag_story", description:"test")

        //Tag testTag = Tag.findByTitle("test3")
        Tag testTag = Tag.build(title: "tagMe", urlTitle: "tag_me")

        when:
        assert testStory != null
        assert testTag != null
        List tags = [testTag.id]

        then:
        assert storyAdminService.addTags(testStory, tags) == true
        assert testStory.tags.title == ["tagMe"]
    }

    def "removeTags should remove a Tag from a Story"() {

        Tag testTag = Tag.findByTitle("test3")
        //Tag testTag = Tag.build(title:"tagMe", urlTitle:"tag_me")

        Story testStory = Story.findByTitle("test1")

        when:
        assert testTag != null
        assert testStory != null

        assert testStory.tags.size() > 0
        List tags = [testTag.id]
        //assert storyAdminService.addTags(testStory, tags) == true
        //assert testStory.tags.size() > 0

        then:
        assert storyAdminService.removeTags(testStory, tags) == true
        assert testStory.tags.size() == 0
    }

    def "fileSearch should return a list of files filtered by parameters"() {

        Story testStory = Story.findByTitle("t")

        when: "find a file with the type 'Image'"

        List files = storyAdminService.fileSearch(testStory, 1, 0, "Image", "", "", "")

        then:
        assert files.size() == 1
        assert files[0].type == 'Image'

        when: "find a story with the title 'f'"

        files = storyAdminService.fileSearch(testStory, 1, 0, "", "f", "", "")

        then:
        assert files.size() == 1
        assert files[0].title == 'f'
    }

    def "storySearch should return a list of stories filtered by parameters"() {

        Story testStory = Story.findByTitle("t")

        when: "find a story with the 'Blog' category"

        List stories = storyAdminService.storySearch(testStory, 1, 0, "", "Blog", false, "", "")

        then:
        assert stories.size() > 0
        assert stories[0].category == 'Blog'

        when: "find a story where the boolean 'magazine' property is true"

        stories = storyAdminService.storySearch(testStory, 1, 0, "", "", false, "magazine", "")

        then:
        assert stories.size() == 1
        assert stories[0].magazine == true

        when: "find a story with the title 't2'"

        stories = storyAdminService.storySearch(testStory, 1, 0, "t2", "", true, "", "")

        then:
        assert stories.size() == 1
        assert stories[0].title == "t2"

        when: "find 2 stories with 'test' in a searchable field"

        stories = storyAdminService.storySearch(testStory, 2, 0, "test", "", false, "", "")

        then:
        assert stories.size() == 2
        assert stories[0].description.contains("test")


        when: "find a story with the type EVENT"

        stories = storyAdminService.storySearch(testStory, 1, 0, "", "", false, "", "event")

        then:
        Story.Type t = "EVENT"
        assert stories.size() == 1
        assert stories[0].type.toString() == t.toString()
    }
}
