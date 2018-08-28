package cev.blackFish

import grails.test.spock.IntegrationSpec

import javax.servlet.ServletContext
import grails.util.Environment 

class FileServiceIntegrationSpec extends IntegrationSpec {

	def grailsApplication
	def fileService

	def cleanup() {}
	
	def "dummy test"() { //This is just to get the spec initialized.
		expect:
			1==1
	}
	
	def "readFile should should return a Tag by its id"(){
		
		when:
		
			File testFile = fileService.readFile(1)
		
		then:
			
			assert testFile.id == 1
	}
	
	def "findByTitle should return a file by its title"() {
		
		when:
			
			File testFile = fileService.findByTitle("file1")
		
		then:
			assert testFile.title == "file1"
	}
	
	def "list should should return a list of files filtered by parameters"() {
		
		when: "find a list of files when type is filled"
		
			List files = fileService.list(1, 0, false, "Image") 
		
		then:
		
			assert files.size() == 1
			
		when: "find a list of files when bool is filled"
		
			List files1 = fileService.list(1, 0, false, "Image", "", "document", "", "", "")
			
		then:
			
			assert files1.size() == 1
			
		when: "find a list of files when category is filled"
			
			List files2 = fileService.list(1, 0, false, "Image", "", "", "", "", "Blog")
				
		then:
				
			assert files2.size() == 1
	}
	

	
	def "getFileSize should return the size of the file"() {
		
		when:
		
			def size = fileService.getFileSize(2)
		
		then: "the file size should be returned"
		
			assert size == "2 bytes" 
	}
	
	def "stripCharacters should clean up filenames"() {
		
		when:
		
			def strippedName = fileService.stripCharacters("Test\\^")
		
		then:
		
			assert strippedName == "test"
	}
	
	def "addDate should add the date to a filenames"() {
		
		when:
		
			def datedName = fileService.addDate("test.")
		
		then:
		
			assert datedName.replaceAll("\\d+" , "") == "test-."
	}
	
	def "makeDirectory should create a directory or file"() {
		when:
		
			def result = fileService.MakeDirectory("testDirectory")
		
		then:
			assert result == false
	}
	
	/* def "deleteDirectory should delete a directory or file"() {
	
		when:
		
			File file = fileService.findByTitle("file3")
			assert fileService.findByTitle("file3") != null
			
			//def result = fileService.deleteDirectory("file3")
		
		then:
			
			fileService.deleteDirectory("file3") == true
			assert Story.findByTitle("file3") == null

	}
	
	
		def "deletePhysicalFiles should should deletes files from the system"() {
	 
	 when:
	 
		 File testFile = fileService.findByTitle("file1")
		 
		 
		 def context = ServletContext.getContext()
	 
		 fileService.deletePhysicalFiles(testFile, context)
	 
	 then: "the files should be deleted from the system"
	 
		 assert testFile == null
 }*/
 
/*	def "magickDir should"() {
	 
	 when:""
	 
		 def model = fileService.magickDir
	 then:""
	 
		 //assert model == config.magickDir
		 
		 assert Environment == "test"
 }*/
 
 /*def "createThumbnail(String pathName, String extension, String arg) should create a thumbnail"() {
	 when:
	 
		 fileService.createThumbnails("filepath")
	 
	 then: "the thumbnail should be created"
		 
		 assert fileService.thumbnail != null
	 
	 
	 
 }*/
	
/*	createThumbnails(String filePath)
	createThumbnail(String pathName, String extension, String arg)
	createSlideThumbnails(String pathName, String width, String extension)
	createTiles(String filePath, String directory, String sSize)
	getImageDimension(java.io.File imgFile)
	getFileBytes(String fileName, String applicationLocation)
	//getFileSize(int size)
	getThumbnail(String path, String fullPath)
	getTinyThumbnail(String path, String fullPath)
	getMedImage(String path, String fullPath, String extension)
	//stripCharacters(String filename)
	//addDate(String filename)
	//MakeDirectory (String directoryName)
	//deleteDirectory(String directory)*/
}
