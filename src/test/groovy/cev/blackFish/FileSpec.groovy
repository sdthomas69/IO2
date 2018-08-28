package cev.blackFish

import grails.test.mixin.TestFor

import spock.lang.Specification
@TestFor(File)

class FileSpec extends Specification {
	
	def char50 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id libero in ex imperdiet"
	def char255 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id libero in ex imperdiet pellentesque ut vitae augue. Proin gravida lobortis ultricies. Proin imperdiet iaculis lacus, eget tempor neque volutpat euismod. Proin sed mollis massa, ac bibendum risus. Sed ac"
	
	void "test that the type constraint is valid"(){
		
		when:"the type constraint is a valid type"
			
			def file1 = new File(type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then:"the validation should pass"
			
			file1.validate()
			
			!file1.hasErrors()
			
			file1.errors.errorCount == 0
			
		when: "the type constraint in blank"
		
			def file2 = new File(type: "", title: "title", urlTitle: "url", description: "description")
		
		then:"then validation should fail"
		
			!file2.validate()
		
			file2.hasErrors()
		
			file2.errors.errorCount == 1
		
		when: "the type constraint is more than 50 characters"
		
			def file3 = new File(type: char50, title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should fail"
		
			!file3.validate()
		
			file3.hasErrors()
		
			file3.errors.errorCount == 1
	}
	
	void "test that the path constraint is valid"(){
		
		when: "the path constraint is a unique string"
		
			def file4 = new File(path: "path", type: "EVENT", title: "title1", urlTitle: "url", description: "description")
		
			file4.save(flush: true)
		
		then: "the validation should pass"
		
			file4.validate()
			
			!file4.hasErrors()
			
			file4.errors.errorCount == 0

		when: "the path constraint is a non-unique string"
		
			def file5 = new File(path: "path", type: "EVENT", title: "title2", urlTitle: "url", description: "description")
			
		then: "the validation shoud fail"
		
			!file5.validate()
			
			file5.hasErrors()
			
			file5.errors.errorCount == 1
		
		when: "the path constraint is a unique string of more than 255 characters"
		
			def file6 = new File(path: char255, type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should fail"
		
			!file6.validate()
		
			file6.hasErrors()
		
			file6.errors.errorCount == 1
	}
	
	void "test that the measuredSize constraint is valid"(){
		
		when: "the measuredSize constraint is a valid string"
		
			def file7 = new File(measuredSize: "measuredSize", type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should pass"
		
			file7.validate()
		
			!file7.hasErrors()
		
			file7.errors.errorCount == 0
		
		when: "the measuedSize constraint is a string longer than 50 characters"
		
			def file8 = new File(measuredSize: char50, type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should fail"
		
			!file8.validate()
		
			file8.hasErrors()
		
			file8.errors.errorCount == 1
		
	}
	
	void "test that the smallImage, smallImageSquared, tinyImage, inbetweenImage, mediumImage, slideImage, and smallSlideImage constraints are valid"(){
		
		when: "the image constraints are valid strings"
		
			def file9 = new File(smallImage: "smallImage", smallImageSquared: "smallImageSquared", tinyImage: "tinyImage", inbetweenImage: "inbetweenImage", mediumImage: "mediumImage", slideImage: "slideImage", smallSlideImage: "smallSlideImage", type: "EVENT", title: "title", urlTitle: "url", description: "description")
			
		then: "the validation should pass"
		
			file9.validate()
		
			!file9.hasErrors()
		
			file9.errors.errorCount == 0
		
		when: "the image constraints are more than 255 characters"
		
			def file10 = new File(smallImage: char255, smallImageSquared: char255, tinyImage: char255, inBetweenImage: char255, mediumImage: char255, slideImage: char255, smallSlideImage: char255, type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should fail"
		
			!file10.validate()
		
			file10.hasErrors()
		
			file10.errors.errorCount == 7
			
	}
	
	void "test that the size constraint is valid"(){
		
		when: "the size constraint is a valid integer"
		
			def file11 = new File(size: 2, type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should pass"
		
			file11.validate()
		
			!file11.hasErrors()
		
			file11.errors.errorCount == 0

	}
	
	void "test that the phoneMovie, desktopMovie, and posterImage constraints are valid"(){
		
		when: "the constraints are valid strings"
		
			def file12 = new File(phoneMovie: "phoneMovie", desktopMovie: "desktopMovie", posterImage: "posterImage", type: "EVENT", title: "title1", urlTitle: "url", description: "description")
		
			file12.save(flush: true)
			
		then: "the validation should pass"
		
			file12.validate()
		
			!file12.hasErrors()
		
			file12.errors.errorCount == 0
		
		when: "the constraints are not unique strings"
		
			def file13 = new File(phoneMovie: "phoneMovie", desktopMovie: "desktopMovie", posterImage: "posterImage", type: "EVENT", title: "title2", urlTitle: "url", description: "description")

		then:"the validation should fail"
		
			!file13.validate()
		
			file13.hasErrors()
		
			file13.errors.errorCount == 3

		when: "the constraints are more than 255 characters"
		
			def file14 = new File(phoneMovie: char255, desktopMovie: char255, posterImage: char255, type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should fail"
		
			!file14.validate()
		
			file14.hasErrors()
		
			file14.errors.errorCount == 3
		
	}
	
	void "test that the flashURL, iPhoneURL, largeImage, xLargeImage, xSlideImage, caption, and mediumSlideImage constraints are valid"(){
		
		when: "the constraints are valid strings"
		
			def file15 = new File(flashURL: "flashURL", iPhoneURL: "iPhoneURL", largeImage: "largeImage", xLargeImage: "xLargeImage", xSlideImage: "xSlideImage", caption: "caption", mediumSlideImage: "mediumSlideImage", type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should pass"
		
			file15.validate()
			
			!file15.hasErrors()
			
			file15.errors.errorCount == 0
		
		when: "the constraints are more than 255 characters"
		
			def file16 = new File(flashURL: char255, iPhoneURL: char255, largeImage: char255, xLargeImage: char255, xSlideImage: char255, caption: char255, mediumSlideImage: char255, type: "EVENT", title: "title", urlTitle: "url", description: "description")
		
		then: "the validation should fail"
		
			!file16.validate()
		
			file16.hasErrors()
		
			file16.errors.errorCount == 7
	
	}
	
	void "test that the tilesDirectory constraint is valid"(){
		
		when: "the tilesDirectory constraint is a strings"
		
			def file17 = new File(tilesDirectory: "titlesDirectory", type: "EVENT", title: "title1", urlTitle: "url", description: "description")
		
			file17.save(flush: true)
	
		then: "the validation should pass"
		
			file17.validate()
		
			!file17.hasErrors()
		
			file17.errors.errorCount == 0
			
		when: "the tilesDirectory constraint is not a unique string"
		
			def file18 = new File(tilesDirectory: "titlesDirectory", type: "EVENT", title: "title2", urlTitle: "url", description: "description")
		
		then: "the validation should fail"
		
			!file18.validate()
		
			file18.hasErrors()
		
			file18.errors.errorCount == 1
		
		when: "the titlesDirectory is more than 255 characters"
		
			def file19 = new File(tilesDirectory: char255, type: "EVENT", title: "title2", urlTitle: "url", description: "description")
		
		then: "the validation should fail"
		
			!file19.validate()
		
			file19.hasErrors()
		
			file19.errors.errorCount == 1
	}
}


