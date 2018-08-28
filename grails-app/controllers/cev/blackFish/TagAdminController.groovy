package cev.blackFish

class TagAdminController extends BaseController {
	
	def tagService
	def tagAdminService
	def storyAdminService
	def userService
	def securityService
	
	def list() {
		
		if(!params.sort) params.sort = "title"
		if(!params.order) params.order = "asc"
		setParams(params)
		
		def tagInstanceList = tagService.list(
			params.max.toInteger(),
			params.offset.toInteger(),
			params.sort,
			params.order,
			params.q
		)
		
		[tagInstanceList: tagInstanceList]
	}

    /**
     * Delete a Tag, its associations and its User's permissions
     * @param id The Tag id
     * @return
     */
	def delete(Long id) {
        
        Tag tagInstance = tagAdminService.getTag(id)
		
		if(!tagInstance) {
			flash.message = "Tag not found"
			redirect(controller:'tag', action:'list')
			return
		}
            
        tagInstance.deleteAllAssociations()
		
		if(!tagAdminService.delete(tagInstance)) {
			
			flash.message = "The Story could not be deleted"
			redirect(action:'edit', id:id)
			return
		}
		
		writeAllObjects()
		
		flash.message = "The tag has been deleted"
		
		redirect(controller:"tagAdmin", action:"list")
    }

    /**
     * Edit a Tag
     * @param id The id of the Tag
     * @return The edited Tag
     */
	def edit(Long id) {
        
        Tag tagInstance = tagAdminService.getTag(id)

        if(!tagInstance) {
            flash.message = "Tag not found"
            redirect(controller:"tag", action:"list")
        }
        else {
            return [ tagInstance : tagInstance ]
        }
    }

    /**
     * Update a Tag
     * @param id The Tag id
     * @return The updated Tag
     */
	def update(Long id) {
        
        Tag tagInstance = tagAdminService.getTag(id)
		
		if(checkVersion(tagInstance)) {
			optimisticLock(tagInstance)
			return
		}
		
		tagInstance.properties = params
		
		if(params.title) tagInstance.setUrlTitle(cleanTitle(tagInstance.title))
		
		if(!tagAdminService.save(tagInstance)) {
			
			flash.message = "The tag could not be updated"
			render(view:'edit', model:[tagInstance:tagInstance])
			return
		}
		
		writeAllObjects()
		
        flash.message = "Tag ${tagInstance} updated"
		
        redirect(action:"edit", id:tagInstance.id)
    }

    /**
     * Create a Tag
     * @return The new (but not saved) tag
     */
	def create() {
        
        Tag tagInstance = new Tag()
        tagInstance.properties = params
        return [tagInstance: tagInstance]
    }

    /**
     * Save a Tag. Enforce validation and add user permissions.  If there's a Story id, associate it.
     * @param id The Tag id
     * @param story.id Optional Story id
     * @return The saved Tag.  If there's a Story id parameter, return to the Story controller.
     */
	def save(Long id) {
        
        Tag tagInstance = new Tag(params)
		
		if(params.title) tagInstance.setUrlTitle(cleanTitle(tagInstance.title))
		
		if(!tagAdminService.save(tagInstance)) {
			
			render(view:'edit', model:[tagInstance:tagInstance])
			return
		}
		
		User currentUser = securityService.getUser()
		
		if(currentUser) {
					
			if(!tagAdminService.save(tagInstance)) {
			
				render(view:'edit', model:[tagInstance:tagInstance])
				return
			}
		}
		
		tagInstance.setUri(writeObject(tagInstance, "/tag"))
 
        if(params?.story?.id) {
			
            Story story = storyAdminService.getStory(params.story.id.toLong())
			
			if(!story) {
				
				flash.message = "The story could not be added to the tag ${tagInstance}"
				redirect(action:"edit", id:tagInstance.id)
				return
			}
			
            tagAdminService.addStoryToTag(story, tagInstance)
			
			writeAllObjects()
			
			flash.message = "The story ${story} has been added to the tag '${tagInstance}'"
			redirect(controller:"storyAdmin", action:"associateTag", id: "${story.id}")
			
        } else if(params?.user?.id) {
			
			User user = userService.getUser(params.user.id.toLong()) 
			
			if(!user) {
				
				flash.message = "The user could not be added to the tag ${tagInstance}"
				redirect(action:"edit", id:tagInstance.id)
				return
			}
			
			List users = [user.id]
			
			if(!tagAdminService.addUsers(tagInstance, users)) {
				flash.message = "The user could not be added to the tag ${tagInstance}"
				redirect(action:"edit", id:tagInstance.id)
				return
			}
			
			writeAllObjects()
			
			flash.message = "The user ${user} has been added tagged with '${tagInstance}'"
			redirect(controller:"userAdmin", action:"edit", id: "${user.id}")
		
		} else {
            flash.message = "Tag ${tagInstance} created"
            redirect(action:"edit", id:tagInstance.id)
        }
    }
    
    /**
     * Look up a Tag by id, search for stories, excluding those already associated with the Tag.
     * @param id The Tag id
     * @return A list of stories, the Tag, the Tag id, and the request parameters
     */
	def storySearch(Long id) {
        
        Tag tag = tagAdminService.getTag(id)
        
        if(!tag) {
            render "the tag was not found"
        }

        else {
                
            setParams(params)
			
			def searchResult = tagAdminService.storySearch(
				tag,
				params.max.toInteger(),
				params.offset.toInteger(),
				params.q,
				params.category,
				params.titleOnly as boolean,
				params.bool,
				params.type,
				params.sort,
				params.order
			)

            render(
				template:'remoteStorySearch', 
				model:[
					searchResult:searchResult, 
					tag:tag, 
					id:tag.id, 
					params:params
				]
			)
        }
    }
    
    /**
     * Look up a Tag by id, search for files based on max, offset, type, query, boolean and category 
     * parameters, excluding those files already associated with the Tag.
     * @param id The Tag id
     * @return A List of files, the Tag and the request parameters
     */
	def fileSearch(Long id) {
        
        Tag tag = tagAdminService.getTag(id)
        
        if(!tag) {
            render "The tag was not found"
        }

        else {
            
            setParams(params)
			
            def searchResult = tagAdminService.fileSearch(
				tag,
				params.max.toInteger(),
				params.offset.toInteger(),
				params.type,
				params.q,
				params.bool,
				params.category,
				params.sort,
				params.order
			)
            
            render(
				template:'remoteFileSearch', 
				model:[searchResult:searchResult, tag:tag, params:params]
			)
        }
    }
	
	def userSearch(Long id) {
		
		Tag tag = tagAdminService.getTag(id)
		
		if(!tag) {
			render "The tag was not found"
		}

		else {
			
			if(!params.sort) params.sort = "lastName"
			if(!params.order) params.order = "asc"
			
			setParams(params)
			
			def searchResult = tagAdminService.userSearch(
				tag,
				params.max.toInteger(),
				params.offset.toInteger(),
				params.q,
				params.category,
				params.sort,
				params.order
			)

			render(
				template:'remoteUserSearch',
				model:[searchResult:searchResult, tag:tag, params:params]
			)
		}
	}
    
    /**
     * Add stories to a Tag
     * @param id The id of the Tag
     * @param stories A List of story id's
     * @return the Tag id
     */
	def addStories(Long id) {
		
		Tag tag = tagAdminService.getTag(id)
		
		if(!tag) {
			redirect(action:'edit', params:[id.id])
			return
		}
		
		List stories = params.list('stories')
        
        if(!stories || !tagAdminService.addStories(tag, stories)) {
			
            flash.message = "The story could not be added"
            redirect(action:'edit', id:id)
			return
        }

		writeAllObjects()
		
		flash.tab = "stories"
        flash.message = "The stories have been added to the tag ${tag}"
        redirect(action:"edit", id:tag.id)
    }
    
    /**
     * Remove stories from a Tag
     * @param The Tag id
     * @param stories A List of Story id's
     * @return The Tag id
     */
	def removeStories(Long id) {
		
		Tag tag = tagAdminService.getTag(id)
		
		if(!tag) {
			redirect(action:'edit', params:[id.id])
			return
		}
		
		if(checkVersion(tag)) {
			optimisticLock(tag)
			return
		}
        
        def stories = params.list('stories')
        
        if(!stories || !tagAdminService.removeStories(tag, stories)) {
			
            flash.message = "The story could not be removed"
            redirect(action:'edit', id:id)
			return
        }
		
		writeAllObjects()
		
		flash.tab = "stories"
        flash.message = "The stories have been removed from the tag ${tag}"
        
        redirect(action:"edit", id:tag.id)
    }
    
    /**
     * Add files to a Tag
     * @param id The Tag id
     * @param stories A List of Story id's
     * @return The Tag id
     */
	def addFiles(Long id) {
		
		Tag tag = tagAdminService.getTag(id)
		
		if(!tag) {
			redirect(action:'edit', id:id)
			return
		}
		
		if(checkVersion(tag)) {
			optimisticLock(tag)
			return
		}
		
        def files = params.list('files')
		
        if(!files || !tagAdminService.addFiles(tag, files)) {
			
            flash.message = "The file could not be added"
            redirect(action:'edit', id:tag.id)
			return
        }
		
		writeAllObjects()
		
		flash.tab = "files"
        flash.message = "The files have been added to the tag ${tag}"
		
        redirect(action:"edit", id:tag.id)
    }
    
    /**
     * Remove files from a Tag
     * @param id The Tag id
     * @param files A List of files
     * @return The Tag id
     */
	def removeFiles(Long id) {
		
		Tag tag = tagAdminService.getTag(id)
		
		if(!tag) {
			redirect(action:'edit', id:id)
			return
		}
		
		if(checkVersion(tag)) {
			optimisticLock(tag)
			return
		}
		
        def files = params.list('files')
		
        if(!files || !tagAdminService.removeFiles(tag, files)) {
			
            flash.message = "The file could not be removed"
            redirect(action:'edit', id:tag.id)
			return
        }
		
		writeAllObjects()
		
		flash.tab = "files"
        flash.message = "The files have been removed from the tag ${tag}"
		
        redirect(action:"edit", id:tag.id)
    }
	
	def addUsers(Long id) {
		
		Tag tag = tagAdminService.getTag(id)
		
		if(!tag) {
			redirect(action:'edit', id:id)
			return
		}
		
		if(checkVersion(tag)) {
			optimisticLock(tag)
			return
		}
		
		List users = params.list('users')
		
		if(!users || !tagAdminService.addUsers(tag, users)) {
			
			flash.message = "The users could not be added"
			redirect(action:'edit', id:id)
			return
		}
		
		writeAllObjects()
		
		flash.tab = "users"
		flash.message = "The users have been added to the tag ${tag}"
		redirect(action:"edit", id:tag.id)
	}
	
	def removeUsers(Long id) {
		
		Tag tag = tagAdminService.getTag(id)
		
		if(!tag) {
			redirect(action:'edit', params:[id.id])
			return
		}
		
		if(checkVersion(tag)) {
			optimisticLock(tag)
			return
		}
		
		List users = params.list('users')
		
		if(!users || !tagAdminService.removeUsers(tag, users)) {
			
			flash.message = "The users could not be removed"
			redirect(action:'edit', id:tag.id)
			return
		}
		
		writeAllObjects()
		
		flash.tab = "users"
		flash.message = "The users have been removed from the tag ${tag}"
		
		redirect(action:"edit", id:tag.id)
	}
}