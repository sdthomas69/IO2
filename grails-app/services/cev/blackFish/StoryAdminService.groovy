package cev.blackFish

import grails.transaction.Transactional

import org.hibernate.FetchMode as FM

@Transactional
class StoryAdminService {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Story getStory(Long id) {

		return Story.get(id)
	}

    /**
     * 
     * @param story
     * @return
     */
	boolean save(Story story) {
		
		return !story.hasErrors() && story.save(flush:true)
    }
	
	/**
	 * 
	 * @param story
	 * @return
	 */
	boolean delete(Story story) {
		
		try {
            story.delete(flush:true)
			return true   
        }
        catch(org.springframework.dao.DataIntegrityViolationException e) {
            log.error("exception ${e} occurred while attempting to delete a story")
			return false
        }
	}
	
	/**
	 * 
	 * @param story
	 * @param max
	 * @param offset
	 * @param q
	 * @return
	 */
	@Transactional(readOnly = true)
	def storySearch(
			Story story, 
			Integer max, 
			Integer offset, 
			String q, 
			String category, 
			boolean titleOnly, 
			String bool,
			String type) {
		
		def query = {

			if(q) {
				if(titleOnly) {
					ilike("title", "%${q}%")
				}
				else {
					or {
						story.searchableProps.each {
							ilike(it, "%${q}%")
						}
					}
				}
			}
			if(type) {
				Story.Type t = type.toUpperCase()
				if(t) eq("type", t)
			}
			if(bool) eq(bool, true)
			if(category) eq("category", category)
			story.stories.each {
				ne("id", it.id)
			}
			story.children.each {
				ne("id", it.id)
			}
			and {
				ne("id", story.id)
			}
			join 'primaryImage'
			order("date", "desc")
			maxResults(max)
			firstResult(offset)
		}
		
		def results = Story.createCriteria().list(['max': max, 'offset': offset], query)
		
		return results
	}
	
	/**
	 * 
	 * @param story
	 * @param max
	 * @param offset
	 * @param type
	 * @param q
	 * @return
	 */
	@Transactional(readOnly = true)
	def fileSearch(
		Story story, 
		Integer max, 
		Integer offset, 
		String type, 
		String q, 
		String bool, 
		String category) {
		
		File instance = new File()
		
		Long fileId = story?.primaryImage?.id ?: 0
		
		def query = {
			
			if(type) eq("type", type)
			if(category) eq("category", category)
			if(bool) eq(bool, true)
			if(q) {
				or {
					instance.searchableProps.each {
						ilike(it, "%${q}%")
					}
				}
			}
			story.files.each {
                ne("id", it.id)
            }
			if(fileId > 0) {
	            and {
	                ne("id", fileId)
	            }
			}
			order("date", "desc")
			maxResults(max)
			firstResult(offset)
		}
		
		def results = File.createCriteria().list(['max':max, 'offset':offset], query)
		
		return results
	}
	
	/**
	 * 
	 * @param story
	 * @param max
	 * @param offset
	 * @param q
	 * @return
	 */
	@Transactional(readOnly = true)
	def tagSearch(Story story=null, Integer max=1, Integer offset=0, String q="") {
		
		def query = {
			
			if(q) ilike("title", "%${q}%")
			
            story.tags.each {
                ne("id", it.id)
            }
			order("title", "asc")
			maxResults(max)
			firstResult(offset)
		}
		
		def results = Tag.createCriteria().list(['max':max, 'offset':offset], query)
		
		return results
	}
	
	/**
	 * 
	 * @param story
	 * @param menuId
	 * @return
	 */
	boolean addMenuToStory(Story story, Long menuId) {
		
		MenuSet menuSet = MenuSet.get(menuId)
		
		if(!menuSet) {
			log.error("MenuSet not found in storyAdminService.addMenuToStory()")
			return false
		}

		story.setMenuSet(menuSet)
		menuSet.addToChildren(story)
		
		if(!save(story)) {
			log.error("Story could not be saved in storyAdminService.addMenuToStory()")
			return false
		}
		return true
	}
	
	/**
	 * 
	 * @param story
	 * @return
	 */
	boolean removeMenuFromStory(Story story) {
		
		MenuSet menuSet = story.menuSet
		story.setMenuSet(null)
		menuSet.removeFromChildren(story)
		
		if(!save(story)) {
			log.error("Story could not be saved in storyAdminService.removeMenuFromStory()")
			return false
		}
		return true
	}
	
	boolean addSiteToStory(Story story, Long siteId) {
		
		Site site = Site.get(siteId)
		
		if(!site) {
			log.error("Site not found in storyAdminService.addSiteToStory()")
			return false
		}

		story.setSite(site)
		
		if(!save(story)) {
			log.error("Story could not be saved in storyAdminService.addSiteToStory()")
			return false
		}
		return true
	}
	
	boolean removeSiteFromStory(Story story) {
		
		story.setSite(null)
		
		if(!save(story)) {
			log.error("Story could not be saved in storyAdminService.removeSiteFromStory()")
			return false
		}
		return true
	}
	
	boolean addPrimaryImage(Story story, Long fileId) {
		
		File file = File.get(fileId)
		if(!file) {
			log.error("File not found")
			return false
		}
		
		story.setPrimaryImage(file)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removePrimaryImage(Story story) {
		
		story.setPrimaryImage(null)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean addEmbeddedImage(Story story, Long fileId) {
		
		File file = File.get(fileId)
		if(!file) {
			log.error("File not found")
			return false
		}
		
		story.setEmbeddedImage1(file)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removeEmbeddedImage(Story story) {
		
		story.setEmbeddedImage1(null)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean addEmbeddedImage2(Story story, Long fileId) {
		
		File file = File.get(fileId)
		if(!file) {
			log.error("File not found")
			return false
		}
		
		story.setEmbeddedImage2(file)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removeEmbeddedImage2(Story story) {
		
		story.setEmbeddedImage2(null)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean addKmlFile(Story story, Long fileId) {
		
		File file = File.get(fileId)
		if(!file) {
			log.error("File not found")
			return false
		}
		
		story.setKmlFile(file)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removeKmlFile(Story story) {
		
		story.setKmlFile(null)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean addChild(Story story, Long childId) {
		
		Story child = Story.get(childId)
		if(!child) {
			log.error("Child not found")
			return false
		}
		child.setParent(story)
		
		if(!save(child)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removeChild(Story story, List stories) {
		
		stories.each {
			Story child = Story.get(it)
			if(!child) {
				log.error("Child not found")
				return false
			}
			child.setParent(null)
			if(!save(child)) {
				log.error("Story could not be saved")
				return false
			}
		}
		return true
	}
	
	boolean addStories(Story story, List stories) {
		
		stories.each {
			Story otherStory = Story.get(it)
			if(otherStory) {
				StoryAssociation.create(story, otherStory)
				//StoryAssociation.create(otherStory, story)
			}
		}
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removeStories(Story story, List stories) {

		println "stories are ${stories}"
		
		for(id in stories) {
			Story otherStory = Story.get(id)
            if(otherStory) {
				/*if(!StoryAssociation.remove(story, otherStory)) {
					log.error("The story could not be disassociated")
					return false
				}*/
				StoryAssociation.remove(story, otherStory)
				//StoryAssociation.remove(otherStory, story)
			}
		}
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean addFile(Story story, File file) {
		
		StoryFileAssociation.create(story, file)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean addFiles(Story story, List files) {
		
		files.each {
			File file = File.get(it)
			if(file) StoryFileAssociation.create(story, file)
		}
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removeFile(Story story, File file) {
		
		StoryFileAssociation.remove(story, file)
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removeFiles(Story story, List files) {
		
		files.each {
			File file = File.get(it)
			if(file) StoryFileAssociation.remove(story, file)
		}
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean addTags(Story story, List tags) {
		
		if(!tags) {
			log.error("the tags list is empty")
			return false
		}
		
		for(tag in tags) {
			
			Tag thisTag = Tag.get(tag)
			
            if(!thisTag) {
				log.error("the Tag could not be initialized")
				return false
            }
			if(!StoryTagAssociation.create(story, thisTag)) {
				log.error("the StoryTagAssociation could not be created")
				return false
			}
		}
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removeTags(Story story, List tags) {
		
		if(!tags || !story) {
			log.error("the tags list is empty")
			return false
		}
		
		for(tag in tags) {
			
			Tag thisTag = Tag.get(tag)
			
			if(!thisTag) {
				log.error("the Tag could not be initialized")
				return false
			}
			/*if(StoryTagAssociation.remove(story, thisTag) != true) {
				log.error("the StoryTagAssociation could not be removed")
				return false
			}*/
			StoryTagAssociation.remove(story, thisTag)
		}
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean addPeople(Story story, List people) {
		
		people.each {
			User user = User.get(it)
			if(user) UserStoryAssociation.create(story, user)
		}
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
	
	boolean removePeople(Story story, List people) {
		
		people.each {
			User user = User.get(it)
			if(user) UserStoryAssociation.remove(story, user)
		}
		
		if(!save(story)) {
			log.error("Story could not be saved")
			return false
		}
		return true
	}
}
