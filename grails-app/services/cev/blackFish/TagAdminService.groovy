package cev.blackFish

import grails.transaction.Transactional

@Transactional
class TagAdminService {

    /**
     * 
     * @param id
     * @return
     */
	Tag getTag(Long id) {

		return Tag.get(id)
	}
	
	/**
	 * 
	 * @param tag
	 * @return
	 */
	boolean save(Tag tag) {
		
		return !tag.hasErrors() && tag.save(flush:true)
	}
	
	/**
	 * 
	 * @param tag
	 * @return
	 */
	boolean delete(Tag tag) {
		
		try {
			tag.delete(flush:true)
			return true
		}
		catch(org.springframework.dao.DataIntegrityViolationException e) {
			log.error("exception ${e} occurred while attempting to delete a tag")
			return false
		}
	}
	
	/**
	 * 
	 * @param story
	 * @param tag
	 * @return
	 */
	boolean addStoryToTag(Story story, Tag tag) {
		
		StoryTagAssociation.create(story, tag)
		
		if(!save(tag)) {
			log.error("Tag could not be saved in tagAdmin.addStoryToTag()")
			return false
		}
		return true
	}
	
	/**
	 * 
	 * @param tag
	 * @param max
	 * @param offset
	 * @param q
	 * @return
	 */
	@Transactional(readOnly = true)
	def storySearch(
			Tag tag, 
			Integer max=10, 
			Integer offset=0, 
			String q="", 
			String category="", 
			boolean titleOnly, 
			String bool="",
			String type="",
			String sortBy = "",
			String orderBy = "") {

			def query = {

			if(q) {
				if(titleOnly) {
					ilike("title", "%${q}%")
				}
				else {
					Story story = new Story()
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
			tag.stories.each {
                ne("id", it.id)
            }
			join 'primaryImage'
			order(sortBy ?: 'date', orderBy ?: 'desc')
			maxResults(max)
			firstResult(offset)
		}
		
		def results = Story.createCriteria().list(['max': max, 'offset': offset], query)
		
		return results
	}
	
	/**
	 * 
	 * @param tag
	 * @param max
	 * @param offset
	 * @param type
	 * @param q
	 * @param bool
	 * @param category
	 * @return
	 */
	@Transactional(readOnly = true)
	def fileSearch(
			Tag tag, 
			Integer max=10, 
			Integer offset=0, 
			String type="", 
			String q="", 
			String bool="", 
			String category="",
			String sortBy = "",
			String orderBy = "") {
		
		File instance = new File()
		
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
			tag.files.each {
				ne("id", it.id)
			}
			order(sortBy ?: 'date', orderBy ?: 'desc')
			maxResults(max)
			firstResult(offset)
		}
		
		def results = File.createCriteria().list(['max':max, 'offset':offset], query)
		
		return results
	}
			
	@Transactional(readOnly = true)
	def userSearch(
			Tag tag,
			Integer max=10,
			Integer offset=0,
			String q="",
			String category="",
			String sortBy="",
			String orderBy="") {
		
		User instance = new User()
		
		def query = {
			
			if(q) {
				or {
					instance.searchableProps.each {
						ilike(it, "%${q}%")
					}
				}
			}
			tag.users.each {
				ne("id", it.id)
			}
			if(category) eq("category", category)
			
			order(sortBy ?: 'date', orderBy ?: 'desc')
			maxResults(max)
			firstResult(offset)
		}
		
		def results = User.createCriteria().list(['max':max, 'offset':offset], query)
		
		return results
	}
	
	/**
	 * 
	 * @param tag
	 * @param stories
	 * @return
	 */
	boolean addStories(Tag tag, List stories) {
		
		stories.each {
			Story story = Story.get(it)
			if(story) StoryTagAssociation.create(story, tag)
		}
		
		if(!save(tag)) {
			log.error("Tag could not be saved in tagAdmin.addStories()")
			return false
		}
		return true
	}
	
	/**
	 * 
	 * @param tag
	 * @param stories
	 * @return
	 */
	boolean removeStories(Tag tag, List stories) {
		
		stories.each {
			Story story = Story.get(it)
			if(story) StoryTagAssociation.remove(story, tag)
		}
		
		if(!save(tag)) {
			log.error("Tag could not be saved in tagAdmin.removeStories()")
			return false
		}
		return true
	}
	
	/**
	 * 
	 * @param tag
	 * @param files
	 * @return
	 */
	boolean addFiles(Tag tag, List files) {
		
		files.each {
			File file = File.get(it)
			if(file) FileTagAssociation.create(file, tag)
		}
		
		if(!save(tag)) {
			log.error("Tag could not be saved in tagAdmin.addStories()")
			return false
		}
		return true
	}
	
	/**
	 * 
	 * @param tag
	 * @param files
	 * @return
	 */
	boolean removeFiles(Tag tag, List files) {
		
		files.each {
			File file = File.get(it)
			if(file) FileTagAssociation.remove(file, tag)
		}
		
		if(!save(tag)) {
			log.error("Tag could not be saved in tagAdmin.removeStories()")
			return false
		}
		return true
	}
	
	boolean addUsers(Tag tag, List users) {
		
		users.each {
			User user = User.get(it)
			if(user) tag.addToUsers(user)
		}
		
		if(!save(tag)) {
			log.error("A user could not be added")
			return false
		}
		return true
	}
	
	boolean removeUsers(Tag tag, List users) {
		
		users.each {
			User user = User.get(it)
			if(user) tag.removeFromUsers(user)
		}
		
		if(!save(tag)) {
			log.error("A user could not be removed")
			return false
		}
		return true
	}
}
