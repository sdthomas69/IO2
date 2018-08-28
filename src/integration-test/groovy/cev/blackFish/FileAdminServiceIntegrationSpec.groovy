package cev.blackFish

import grails.test.spock.IntegrationSpec

class FileAdminServiceIntegrationSpec extends IntegrationSpec {
	
	def grailsApplication
	def fileAdminService
	def tagService

	def setup() {
		
		File file1 = File.build(
			title:"file11",
			urlTitle:"file1",
			description:"file1",
			type:"Image",
			category:"CV"
		)
		
		File file2 = File.build(
			title:"file22",
			urlTitle:"file2",
			description:"file2",
			type:"Image",
			category: "Blog",
		)
		
		File file3 = File.build(
			title:"file33",
			urlTitle:"file3",
			description:"file3",
			type:"Image",
			document: true
		)
		
		File file4 = File.build(
			title:"file44", 
			urlTitle:"file4",
			description:"file4",
			type:"Image",
			category: "Blog"
		)

		Tag tag1 = new Tag(
			title: "t", 
			urlTitle: "urlT",
		).save(flush: true, failOnError: true)	
	}

	def cleanup() {}
	
	def "dummy test"() { //This is just to get the spec initialized.
		expect:
			1==1
	}
	
	def "getFile should get a file by its id"() {
		
		when:
		
			File testFile = fileAdminService.getFile(1)
		
		then:
			
			assert testFile.id == 1

	}
	
	def "save should save the file"() {

		def file = File.findByTitle("file11")
		
		when:
		
			def bool = fileAdminService.save(file)
		
		then:
		
			assert bool == true
	}

	def "delete should delete a file"() {
		
		when:
		
			File file = fileAdminService.getFile(4)
			
			assert file != null 
		
		then:
		
			assert fileAdminService.delete(file) == true
			
			assert fileAdminService.getFile(4) == null
	}
	

	def "getAvailableTags should return a list of available tags filtered by parameters"(){
	
		when: "find a Tag with the title 't'"
		
			File file = File.findByTitle("file11")
		
			List tags = fileAdminService.getAvailableTags(file)
		
		then:

			assert tags.size() == 4
			
			assert tags[0].title == 't'
	
		}
	
	def "tagSearch should return a list of tags filtered by parameters" (){
		
		when: "find a Tag with the title 't'"
		
			File file = File.findByTitle("file11")
		
			List tags = fileAdminService.tagSearch(file, 1, 0, "t")
		
		then:

			assert tags.size() == 1
			
			assert tags[0].title == 't'
		
	}

	def "addTags should add a Tag to a File"() {
		
		File file = File.findByTitle("file2")
		
		Tag tag = Tag.findByTitle("t")
			
		when:
			assert file != null
			
			assert tag != null
			
			def model = fileAdminService.addTag(file, tag)

		then:
			assert model == true

			assert file.tags.title == ["t"]
	}
	
	//doesn't remove the tag 
	def "removeTag should remove a Tag from a File"() {
		
		File file = File.findByTitle("file1")
		
		Tag tag = Tag.findByTitle("test3")
			
		when:
		
			assert tag != null
			
			assert file != null
			
			assert file.tags.size() == 3
			
			def model = fileAdminService.removeTag(file, tag)

		then:
		
			assert model  == true
			
			assert file.tags.size() == 2
	}
}
