package cev.blackFish

import grails.test.spock.IntegrationSpec

class TagServiceIntegrationSpec extends IntegrationSpec {

    def grailsApplication
    def tagService
    def tagAdminService
    def storyService


    def setup() {

    }

    def cleanup() {}

    def "dummy test"() { //This is just to get the spec initialized.
        expect:
        1 == 1
    }

    def "getTag should should return a Tag by its id"() {

        when:

        Tag testTag = tagService.getTag(1)

        then:

        assert testTag.id == 1
    }

    def "list should should return a list of tags filtered by parameters"() {

        when: "find a list of tags when max and offset are filled"

        List tags = tagService.list(2, 0)

        then:

        assert tags.size() == 2

    }

    def "findByTitle should return a tag by its title"() {

        when:

        Tag testTag = tagService.findByTitle("test3")

        then:
        assert testTag.title == "test3"
    }

    def "createCloudMap should return a Map of Tag names and the the number of Stories and Files each Tag has"() {

        when:

        def tagMap = tagService.createCloudMap()

        then:

        assert tagMap.size() == 3
        assert tagMap.test3 == 2
        assert tagMap.test4 == 2
        assert tagMap.test5 == 2

    }


    def "createCloudMap(tagList) should return a Map of Tag names and the the number of Stories and Files each Tag has"() {

        when:
        def tagList = Tag.list(sort: "title")

        def tagMap = tagService.createCloudMap(tagList)

        then:

        assert tagMap.size() == 3
        assert tagMap.test3 == 2
        assert tagMap.test4 == 2
        assert tagMap.test5 == 2

    }

    /* def "createStoryCloudMap should return a Map of Tags and the number of Stories each Tag has"(){
        
        when:
            def tagList = Tag.lookup.list(sort:"title")
        
            Map tagListMap = tagService.createStoryCloudMap(tagList)
    
        then:
    
            assert tagListMap.size() == 3
        
            assert tagListMap.get("test3") == 0
        
    }
    
    def "createFileCloudMap should return a Map of Tags and the number of Files each Tag has"(){
        
        when:
            
            def tagList = Tag.lookup.list(sort:"title")
        
            Map tagListMap = tagService.createFileCloudMap(tagList)
    
        then:
    
            assert tagListMap.size() == 3
        
            assert tagListMap.get("test5") == 1
        
    }*/


}
