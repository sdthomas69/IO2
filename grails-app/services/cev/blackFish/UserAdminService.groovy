package cev.blackFish

import grails.transaction.Transactional

@Transactional
class UserAdminService {

    User getUser(Long id) {

		return User.get(id)
	}
	
	boolean save(User user) {
		
		return !user.hasErrors() && user.save(flush:true)
	}
	
	boolean delete(User user) {
		
		try {
			user.delete(flush:true)
			return true
		}
		catch(org.springframework.dao.DataIntegrityViolationException e) {
			log.error("exception ${e} occurred while attempting to delete a user")
			return false
		}
	}
	
	boolean confirmUser(User user) {
		
		user.confirmed = true
		user.nonce = ""
		
		if(!save(user)) {
			log.error("The user could not be saved")
			return false
		}
		return true
	}
	
	boolean addPrimaryImage(User user, Long fileId) {
		
		File file = File.get(fileId)
		if(!file) {
			log.error("File not found")
			return false
		}
		
		user.setPrimaryImage(file)
		user.hasPrimaryImage = true
		
		if(!save(user)) {
			log.error("The user could not be saved")
			return false
		}
		return true
	}
	
	boolean removePrimaryImage(User user) {
		
		user.setPrimaryImage(null)
		user.hasPrimaryImage = false
		
		if(!save(user)) {
			log.error("The user could not be saved")
			return false
		}
		return true
	}
	
	boolean addTags(User user, List tags) {
		
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
			if(!user.addToTags(thisTag)) {
				log.error("the Tag could not be added")
				return false
			}
		}
		
		if(!save(user)) {
			log.error("User could not be saved")
			return false
		}
		return true
	}
	
	boolean removeTags(User user, List tags) {
		
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
			if(!user.removeFromTags(thisTag)) {
				log.error("the Tag could not be removed")
				return false
			}
		}
		
		if(!save(user)) {
			log.error("User could not be saved")
			return false
		}
		return true
	}
	
	@Transactional(readOnly = true)
	def tagSearch(User user=null, Integer max=1, Integer offset=0, String q="") {
		
		def query = {
			
			if(q) ilike("title", "%${q}%")
			
			user.tags.each {
				ne("id", it.id)
			}
			order("title", "asc")
			maxResults(max)
			firstResult(offset)
		}
		
		def results = Tag.createCriteria().list(['max':max, 'offset':offset], query)
		
		return results
	}
}
