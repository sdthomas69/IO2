package cev.blackFish

import grails.test.spock.IntegrationSpec

class TagAdminServiceIntegrationSpec extends IntegrationSpec {

	
	def grailsApplication
	def tagAdminService

	def setup() {

		Tag tag11 = Tag.build(
			title: "t1",
			urlTitle: "urlT1"
		)
		
		Tag tag22 = Tag.build(
			title: "t2",
			urlTitle: "urlT2"
		)
		
		Tag tag33 = Tag.build(
			title: "t3",
			urlTitle: "urlT3"
		)
		
		Story storyForTag = Story.build(
			title:'st',
			description:'st',
			type:"EVENT",
			id:'1'
		)

	}

	def cleanup() {}
	
	def "dummy test"() { //This is just to get the spec initialized.
		expect:
		
			1==1
	}
	
	//finds bootstrap object
	def "getTag should get a tag by its id"() {
		
		when:
		
			Tag testTag = tagAdminService.getTag(1)
		
		then:
			
			assert testTag.id == 1
	}
	
	def "save should save the tag"() {
		
		def tag = Tag.findByTitle("test3")
		
		assert tag != null
				
		when:
				
			def bool = tagAdminService.save(tag)
				
		then:
				
			assert bool == true
	}
	
	def "delete should delete a tag"() {
		
		when:
		
			Tag tag = tagAdminService.getTag(4)
			
			assert tag.title == "delete me"
			
			assert tag != null 
		
		then:
		
			assert tagAdminService.delete(tag) == true
			
			assert tagAdminService.getTag(4) == null
	}
	
	def "addStoryToTag should add a story to a tag"() {
		
		Tag tag = Tag.findByTitle("test7")
		
		Story story = Story.findByTitle("st")
			
		when:
			assert tag != null
			
			assert story != null
			
			def model = tagAdminService.addStoryToTag(story, tag)

		then:
			
			assert model == true

			assert tag.stories != null
			
			tag.stories.title == ["st"]
	}
	
	def "storySearch should return a list of stories filtered by parameters" (){
		
		Tag tag = Tag.findByTitle("t1")
		
		assert tag != null
		
		when: "find a story with the 'Blog' category is filled"

			List stories = tagAdminService.storySearch(tag, 1, 0, "", "Blog", false, "", "")
		
		then:
			assert stories.size() > 0
			
			assert stories[0].category == 'Blog'
		
		when: "find a story where the boolean 'magazine' property is true"
		
			stories = tagAdminService.storySearch(tag, 1, 0, "", "", false, "magazine", "")
		
		then:
			assert stories.size() == 1
			
			assert stories[0].magazine == true
		
		when: "find a story with the title 'test2'"
		
			stories = tagAdminService.storySearch(tag, 1, 0, "test2", "", true, "", "")
		
		then:
		
			assert stories.size() == 1
			
			assert stories[0].title == "test2"
		
		when: "find 2 stories with 'test' in a searchable field"
		
			stories = tagAdminService.storySearch(tag, 2, 0, "test", "", false, "", "")
		
		then:
			assert stories.size() == 2
			
			assert stories[0].description.contains("test")
			
		when: "find a story with the type EVENT"
		
			stories = tagAdminService.storySearch(tag, 1, 0, "", "", false, "", "event")
		
		then:
			
			assert stories.size() == 1
			
			assert stories[0].type.toString() == "EVENT"

	}
	
	def "fileSearch should return a list of stories filtered by parameters" (){
		
		Tag tag = Tag.findByTitle("t1")
		
		assert tag != null
		
		when: "find a list of files when type is filled"
		
			List files = tagAdminService.fileSearch(tag, 1, 0, "Image", "", "", "")
		
		then:
		
			assert files.size() == 1
			
			assert files[0].type == "Image"
			
		when: "find a list of files when bool is filled"
		
			files = tagAdminService.fileSearch(tag, 1, 0, "Image", "", "document", "")
			
		then:
			
			assert files.size() == 1
			
			assert files[0].document == true
			
		when: "find a list of files when category is filled"
			
			files = tagAdminService.fileSearch(tag, 1, 0, "Image", "", "", "Blog")
				
		then:
				
			assert files.size() == 1
			
			assert files[0].category == "Blog"

	}
	
	def "addStories should add a list of stories to a tag"() {
		
		Tag tag = Tag.findByTitle("test7")
		
		List ids = [1,2]
			
		when:
			assert tag != null

			assert ids.size() == 2

			
			def model = tagAdminService.addStories(tag, ids)

		then:
			
			assert model == true
			
			assert tag.stories.findAll{it.title in ["test", "test1"]}.size() == 2
	}

	def "removeStories should remove a list of stories from a tag"() {
		
		Tag tag = Tag.findByTitle("test7")
		
		List ids = [1,2]
			
		when:
			assert tag != null

			assert ids.size() == 2

			
			def model = tagAdminService.removeStories(tag, ids)

		then:
			
			assert model == true
			
			assert tag.stories.findAll{it.title in ["test", "test1"]}.size() == 0
	}
	
	def "addFiles should add a list of files to a tag"() {
		
		Tag tag = Tag.findByTitle("test7")
		
		List ids = [1,2]
			
		when:
			assert tag != null

			assert ids.size() == 2

			
			def model = tagAdminService.addFiles(tag, ids)

		then:
			
			assert model == true
			
			assert tag.files.findAll{it.title in ["file1", "file2"]}.size() == 2
	}
	
	
	def "removeFiles should remove a list of files from a tag"() {
		
		Tag tag = Tag.findByTitle("test7")
		
		List ids = [1,2]
			
		when:
			assert tag != null

			assert ids.size() == 2

			
			def model = tagAdminService.removeFiles(tag, ids)

		then:
			
			assert model == true
			
			assert tag.files.findAll{it.title in ["file1", "file2"]}.size() == 0
	}
	
}
