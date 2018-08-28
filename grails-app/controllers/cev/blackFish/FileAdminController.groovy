package cev.blackFish

import javax.servlet.ServletContext

import org.springframework.web.multipart.MultipartFile

class FileAdminController extends BaseController {
    
    def fileService
    def fileAdminService
    def securityService
	def tagAdminService
	def storyAdminService
	def helpService
	//def streamService
	def permissionService
	
	/**
	 * 
	 * @return
	 */
	def list() {
		
		setParams(params)
		
		def fileList = fileService.list(
			params.max.toInteger(),
			params.offset.toInteger(),
			params.titleOnly as boolean,
			params.type,
			params.q,
			params.bool,
			params.sort,
			params.order,
			params.category
		)
		
		return [fileList:fileList, params:params]
	}
	
	/**
	 *
	 * @return
	 */
	def remoteFileList() {
	
		setParams(params)
		
		def fileList = fileService.list(
			params.max.toInteger(),
			params.offset.toInteger(),
			params.titleOnly as boolean,
			params.type,
			params.q,
			params.bool,
			params.sort,
			params.order,
			params.category
		)
		
		render(
			template:'remoteFileList', 
			model:[fileList:fileList, params:params]
		)
	}
    
    /**
     * 
     * @param id
     * @return
     */
	def delete(Long id) {
        
        File file = fileAdminService.getFile(id)
		
        if(!file) {
			flash.message = "File not found with id ${id}"
			redirect(action:'list', controller:'fileAdmin')
        }
	
		if(file.author) {
			User user = User.get(file.author.id)
			securityService.removePermission("fileAdmin", file.id, user)
		}
		
		file.deleteAllAssociations()

		ServletContext context = getServletContext()
		
		fileService.deletePhysicalFiles(file, context)
		
		if(!fileAdminService.delete(file)) {
			
			flash.message = "The file could not be deleted"
			redirect(action:'edit', id:id)
			return
		}
		
		writeAllObjects()
        
        flash.message = "The file has been deleted"

        if(securityService.hasRole(config.adminRole)) {
			
			redirect(action:'list', controller:'fileAdmin')
        }
        else {
			redirect(
				action:'userAdmin', 
				id:securityService.getUser().id
			)
        }
    }

    /**
     * 
     * @param id
     * @return
     */
	def edit(Long id) {
        
        File file = fileAdminService.getFile(id)

        if(!file) {
            
            flash.message = "File not found"
            redirect(action:'list', controller:'fileAdmin')
			return
        }
		
		Permission permission = null
		
		if(file.author) {
			
			permission = permissionService.findPermission(
				"fileAdmin",
				file.id.toString(),
				file.author.id.toString()
			)
		}
        return [ file: file, permission: permission ]
    }

    /**
     * 
     * @param id
     * @return
     */
	def update(Long id) {
        
        File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "File not found"
			redirect(action:'list', controller:'fileAdmin')
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
		
		if(file.published && params.published != 'on') {
			
			deleteFile(file.uri)  //it's being unpublished so delete the html
		}
            
        if(params?.author?.id && file.author) {
            
            securityService.changeAuthor(params.author.id.toLong(), file.author.id, file.id, "file")
        }
        
        file.properties = params
        
        setTextProperties(file, params.title, params.shortDescription, params.description)
		
		if(!fileAdminService.save(file)) {
			
			flash.message = "The file ${file} could not be updated"
			render(view:'edit', model:[file:file])
			return
		}
		
		writeAllObjects()

        flash.message = "The file ${file} has been updated"
        redirect(action:'edit', id:file.id)
    }

    /**
     * 
     * @return
     */
	def create() {
		
        File file = new File()
        file.properties = params
        return ['file':file]
    }
    
    /**
     * 
     * @return
     */
	def swap() {
		
		if(!params.id) {
			flash.message = "id not submitted"
			redirect(action:'list', controller:'fileAdmin')
			return
		}
		
        File file = new File()
        file.properties = params
        return ['file':file, id:params.id]
    }

    /**
     * 
     * @return
     */
	def swapIcon() {
		
		if(!params.id) {
			flash.message = "id not submitted"
			redirect(action:'list', controller:'fileAdmin')
			return
		}
		
        File file = new File()
        file.properties = params
        return ['file':file, id:params.id]
    }

	/**
	 * 
	 * @return
	 */
	def generateImages() {
		
		def images = File.list()
		
		boolean imageOrMovie = false
		
		ServletContext context = getServletContext()
		
		String contextPath = context.getRealPath("/")
			
		for(image in images) {

			if(!image.path) {
				
				println "could not get path for file image.title"
				continue
			}
			
			//println "generating images for ${image.title}"
			
			String path = image.path

			java.io.File fileFromPath = new java.io.File(context.getRealPath(path))

			if(fileFromPath && contextPath) {
				
				if(image.type == 'Image') {
					
					fileService.createThumbnails(fileFromPath.toString())
					image.tinyImage = fileService.getMedImage(path, contextPath, "_smaller.jpg")
					imageOrMovie = true
				}
				else if(image.type == 'Quicktime' && image.posterImage) {
					
					fileService.createThumbnails(contextPath + image.posterImage)
					image.tinyImage = fileService.getMedImage(image.posterImage, contextPath, "_smaller.jpg")
					imageOrMovie = true
				}
				else image.tinyImage = fileService.getTinyThumbnail(path, contextPath)

				if(imageOrMovie) {
					
					image.smallImage = fileService.getThumbnail(path, contextPath)
					image.smallImageSquared = fileService.getMedImage(path, contextPath, "_smsq.jpg")
					image.mediumImage = fileService.getMedImage(path, contextPath, "_med.jpg")
					image.inBetweenImage = fileService.getMedImage(path, contextPath, "_bigger.jpg")
					image.largeImage = fileService.getMedImage(path, contextPath, "_large.jpg")
					image.xLargeImage = fileService.getMedImage(path, contextPath, "_large@2x.jpg")
					image.slideImage = fileService.getMedImage(path, contextPath, "_slide.jpg")
					image.xSlideImage = fileService.getMedImage(path, contextPath, "_slide@2x.jpg")
					image.mediumSlideImage = fileService.getMedImage(path, contextPath, "_mediumSlide.jpg")
				}
				image.save(flush:true)
			}
			imageOrMovie = false
		}

		redirect(controller:'sync', action:'index')
	}
	
	/**
	 * 
	 * @return
	 */
	def generateThumbsFor() {
		
		def images = File.findAllByType("Image")
		
		ServletContext context = getServletContext()
		
		String contextPath = context.getRealPath("/")
			
		for(image in images) {

			if(!image.path) {
				continue
			}
			
			String path = image.path

			java.io.File fileFromPath = new java.io.File(context.getRealPath(path))

			if(fileFromPath && contextPath) {
				
				def arg = """-thumbnail ${config.slideWidth}X${config.slideHeight}^ -gravity center -crop ${config.slideWidth}X${config.slideHeight}-0-0"""
				fileService.createThumbnail(fileFromPath.toString(), "_slide.jpg", arg)
				
				arg = " -thumbnail " + config.medIconLength.toString()
				fileService.createThumbnail(fileFromPath.toString(), "_med.jpg", arg)
				
				//fileService.createThumbnail(fileFromPath.toString(), "_sm_slide.jpg", arg)

				//image.smallSlideImage = fileService.getMedImage(path, contextPath, "_sm_slide.jpg")
				image.mediumImage = fileService.getMedImage(path, contextPath, "_med.jpg")
				image.slideImage = fileService.getMedImage(path, contextPath, "_slide.jpg")

				image.save(flush:true)
			}
		}

		redirect(controller:'sync', action:'index')
	}

    /**
     * 
     * @return
     */
	def save() {
    
        File file = new File(params)
        
        MultipartFile mfile = request.getFile('tfile')
		
		if (!mfile || mfile.isEmpty()) {
			flash.message = "A file was not selected"
			render(view:'create', model:[file:file, params:params])
			return
		}

        if(!checkFileSize(mfile.getSize(), file.type)) {
			
			flash.message = "Your file exceeds the size limit!"
			render(view:'create', model:[file:file, params:params])
			return
		}
		
		setTextProperties(file, params.title, params.shortDescription, params.description)

		if(!fileAdminService.save(file)) {
			
			render(view:'create', model:[file:file, params:params])
			return
		}
		
		java.io.File fileOnSystem = addFile(mfile)
		
		if(!fileOnSystem) {
			
			flash.message = "The file could not be saved"
			render(view:'create', model:[file:file, params:params])
			return
		}
		
		setFileProperties(file, fileOnSystem)

        User currentUser = securityService.getUser()
		
		if(currentUser) {
			
			file.author = currentUser
			
			securityService.addPermission("fileAdmin:${config.userPermission}:${file.id}", currentUser)
		}
		
		file.setUri(writeObject(file, "/file"))

        if(params.storyId) {
            
            Story story = storyAdminService.getStory(params.storyId.toLong())

			if(!story || !storyAdminService.addFile(story, file)) {
				
				flash.message = "The file could not be added to the story"
				redirect(controller:"storyAdmin", action:"associateFile", id:story.id)
			}
			
			writeAllObjects()
			
            flash.message = "The file ${file} has been added to the Story '${story}'"
            redirect(controller:"storyAdmin", action:"associateFile", id:story.id)

        } else if(params?.author?.id) {
		
			if(params.portrait) {
				currentUser.setPrimaryImage(file)
				currentUser.save(flush:true)
			}
		
            flash.message = "The file '${file}' has been added to '${currentUser}'"
            redirect(controller:"userAdmin", action:"edit", id:params.author.id)

        } else if(params?.helpId) {
		
			Help help = helpService.getHelp(params.helpId.toLong())
			
			if(!help) {
				flash.message = "The help could not be found"
				redirect(action:'edit', id:file.id)
			}
				
			if(!helpService.addPrimaryImage(help, file.id)) {
				flash.message = "The file could not be added to the help"
				redirect(controller:'help', action:'edit', id:params.helpId)
			}
			
			flash.message = "The file '${file}' has been added to the help '${help}'"
			redirect(controller:"help", action:"edit", id:params.helpId)	
			
		} else if(params?.fileId) {
		
			File parent = File.get(params.fileId)
			
			if(!parent) {
				flash.message = "The file could not be found"
				redirect(action:'edit', id:file.id)
			}
			
			List files = [parent.id]
			
			if(!fileAdminService.addFiles(file, files)) {
				flash.message = "The file could not be added"
				redirect(action:'edit', id:file.id)
			}
		
			flash.message = "The file '${parent}' has been added to '${file}'"
			redirect(controller:"fileAdmin", action:"edit", id:parent.id)

        } else {
        
            flash.message = "The file ${file} has been added"
            redirect(action:'edit', id:file.id)
        }
    }
    
    /**
     * 
     * @param id
     * @return
     */
	def changePath(Long id) {
            
        File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "File not found"
			redirect(action:'list', controller:'fileAdmin')
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
            
        MultipartFile mfile = request.getFile('mfile')
		
		if (!mfile || mfile.isEmpty()) {
			flash.message = "A file was not selected"
			render(view:'swap', model:[file:file, params:params])
			return
		}
    
        if(!checkFileSize(mfile.getSize(), file.type)) {
			
			flash.message = "Your file exceeds the size limit"
			render(view:'create', model:[file:file, params:params])
			return
		}
		
		java.io.File fileOnSystem = addFile(mfile)
		
		if(!fileOnSystem) {
			
			flash.message = "The file could not be saved"
			render(view:'swap', model:[file:file, params:params])
			return
		}
		
		setFileProperties(file, fileOnSystem)
		
		if(!fileAdminService.save(file)) {
			
			render(view:'swap', model:[file:file, params:params])
			return
		}
		
		writeAllObjects()

        flash.message = "File ${file} has been changed"
        redirect(action:'edit', id:file.id)
    }

    /**
     * 
     * @param id
     * @return
     */
	def customIcon(Long id) {
        
        File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "File not found"
			redirect(action:'list', controller:'fileAdmin')
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
  
        MultipartFile mfile = request.getFile('mfile')
		
		if(!mfile || mfile.isEmpty()) {
			flash.message = "A file was not selected"
			render(view:'swapIcon', model:[file:file, params:params])
			return
		}
    
        if(!checkFileSize(mfile.getSize(), file.type)) {
			
			flash.message = "Your file exceeds the size limit"
			render(view:'create', model:[file:file, params:params])
			return
		}

		java.io.File fileOnSystem = addFile(mfile)
		
		changeThumbnailProperties(file, fileOnSystem)
		
		if(!fileAdminService.save(file)) {
			
			render(view:'swap', model:[file:file, params:params])
			return
		}
		
		writeAllObjects()
		
        flash.message = "The custom icon has been added"
        redirect(action:'edit', id:file.id)

    }
	
	/**
	 * 
	 * @return
	 */
	def multiples() {}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	def multiple(Long id) {
			
		File file = File.get( id )

		if(!file) {
			flash.message = "File not found with id ${id}"
			redirect(controller:'fileAdmin', action:'list')
		} else {
		
			User currentUser = securityService.getUser()
			
			def filesDir = config.filesDir
			ServletContext context = getServletContext()
			def path = context.getRealPath(filesDir)

			request.getFileNames().each {
				
				MultipartFile mfile = request.getFile(it)
				
				String name = fileService.stripCharacters(mfile.getOriginalFilename())
				
				java.io.File renamedFile = new java.io.File("$path/$name")
				
				String uri = "$filesDir/$name"

				if(mfile && !mfile.isEmpty()) {
					
					mfile.transferTo(renamedFile)

					if(it.equals("phoneMovie")) {
						file.phoneMovie = uri
						if(file.stream && currentUser) {
							streamService.uploadFile(renamedFile, currentUser)
							file.iPhoneURL = config.iPhoneURL.toString() + (name).encodeAsURL() + config.iPhoneSuffix.toString()
						}
					}
					if(it.equals("desktopMovie")) file.desktopMovie = uri
					
					if(it.equals("posterImage")) {
						
						file.posterImage = uri
						fileService.createThumbnails(renamedFile.toString())
						file.tinyImage = fileService.getMedImage("$filesDir/$name", context.getRealPath("/").toString(), "_smaller.jpg")
						
						String pathToAppend = filesDir + "/" + name.substring(0, name.lastIndexOf('.'))
						
						file.smallImage = pathToAppend + "_sm.jpg"
						file.smallImageSquared = pathToAppend + "_smsq.jpg"
						file.mediumImage = pathToAppend + "_med.jpg"
						file.tinyImage = pathToAppend + "_smaller.jpg"
						file.inBetweenImage = pathToAppend + "_bigger.jpg"
						file.largeImage = pathToAppend + "_large.jpg"
						file.xLargeImage = pathToAppend + "_large@2x.jpg"
						file.slideImage = pathToAppend + "_slide.jpg"
						file.xSlideImage = pathToAppend + "_slide@2x.jpg"
						file.mediumSlideImage = pathToAppend + "_mediumSlide.jpg"
					}
				}
			}

			if(!file.hasErrors() && file.save()) {
				writeAllObjects()
				flash.message = "The file ${file} has been updated"
				redirect(action:'edit', id:file.id)
			}
			else {
				render(view:'edit', model:[file:file])
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	def generateTiles() {
		
		File file = File.get(params.id)
		
		if(!file) {
			flash.message = "File not found"
			redirect(action:'404', controller:'errors')
			return
		}
		
		ServletContext context = getServletContext()
		
		def path = context.getRealPath(file.path)
		
		java.io.File fileFromPath = new java.io.File(path)
		
		def tilesDirectory = "${config.filesDir}/$file.urlTitle"

		java.io.File tileDir = new java.io.File(context.getRealPath(tilesDirectory))
		
		if(!fileFromPath || !tileDir) {
			flash.message = "The tiles directory path could not be created"
			redirect(action:'edit', id:file.id)
			return
		}
		
		if(tileDir.isDirectory()) {
			tileDir.deleteDir()
		}
		
		if(!tileDir.mkdirs()) {
			flash.message = "The tiles directory could not be created"
			redirect(action:'edit', id:file.id)
			return
		}

		fileService.createTiles(fileFromPath.toString(), tileDir.toString(), "256")
		
		java.awt.Dimension dimension = fileService.getImageDimension(fileFromPath)

		if(!dimension) {
			flash.message = "The dimensions could not be generated"
			redirect(action:'edit', id:file.id)
			return
		}

		file.height = dimension.height
		file.width = dimension.width
		file.tilesDirectory = tilesDirectory
		file.tiled = true
		
		if(!fileAdminService.save(file)) {
			
			flash.message = "The file ${file} could not be updated"
			render(view:'edit', model:[file:file])
			return
		}
		
		flash.message = "Tiles have been generated for the file '${file}'"
		
		redirect(action:"edit", id:file.id)
	}
	
	/**
	 * 
	 * @return
	 */
	def addToStreamingServer() {
		
		File file = File.get(params.id)
		
		if(!file) {
			flash.message = "File not found"
			redirect(action:'404', controller:'errors')
			return
		}

		ServletContext context = getServletContext()
		
		def path = context.getRealPath(file.path)
		
		java.io.File fileFromPath = new java.io.File(path)
		
		User currentUser = securityService.getUser()

		if(fileFromPath.exists() && currentUser) {
			
			if(!streamService.uploadFile(fileFromPath, currentUser)) {
				flash.message = "The file could not be added to the streaming server"
				redirect(action:"edit", id:file.id)
				return
			}
			
			file.flashURL = "mp4:" + removeDirName(file.path)
			
			if(file.phoneMovie) {
				
				def phonePath = context.getRealPath(file.phoneMovie)
				java.io.File fileFromPhonePath = new java.io.File(phonePath)
				
				if(!streamService.uploadFile(fileFromPhonePath, currentUser)) {
					flash.message = "The file could not be added to the streaming server"
					redirect(action:"edit", id:file.id)
					return
				}

				file.iPhoneURL = config.iPhoneURL + removeDirName(file.phoneMovie) + config.iPhoneSuffix
			}
			
			file.stream = true
			
			if(!fileAdminService.save(file)) {
				
				flash.message = "The file ${file} could not be updated"
				render(view:'edit', model:[file:file])
				return
			}
			
			flash.message = "The file ${file} has been updated"
			redirect(action:"edit", id:file.id)
			
		} else {
			flash.message = "The file could not be found"
			redirect(action:"edit", id:file.id)
		}
	}
    
    /**
     * 
     * @param id
     * @return
     */
	def listTags(Long id) {
		
        File file = fileAdminService.getFile(id)
		
        if(!file) {
            render "the tags could not be listed"
			return
        }

        setParams(params)
		
		def availableTags = fileAdminService.tagSearch(
			file,
			params.max.toInteger(),
			params.offset.toInteger(),
			params.q
		)
        
        render(
			template:'remoteTagList', 
			model:[availableTags:availableTags, file:file, params:params, id:file.id]
		)
    }
    
    /**
     * 
     * @param id
     * @param tagId
     * @return
     */
	def addTag(Long id, String tagId) {
		
		File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "File not found"
			redirect(action:'list', controller:'fileAdmin')
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
		
        Tag tag = tagAdminService.getTag(tagId.toLong())
				
		if(!tag || !fileAdminService.addTag(file, tag)) {
			
			flash.message = "The tag could not be added"
			redirect(action:'edit', params:[id:id])
			return
		}
		
		writeAllObjects()
		
		flash.message = "The tag '${tag}' has been added to '${file}'"
		redirect(action:'edit', params:[id:id]) 
    }

    /**
     * 
     * @param id
     * @param tagId
     * @return
     */
	def removeTag(Long id, String tagId) {
		
        File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "File not found"
			redirect(action:'list', controller:'fileAdmin')
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
		
        Tag tag = tagAdminService.getTag(tagId.toLong())
		
		if(!tag || !fileAdminService.removeTag(file, tag)) {
			
			flash.message = "The tag could not be removed"
			redirect(action:'edit', params:[id:id])
			return
		}
		
		writeAllObjects()
		
		flash.message = "The tag '${tag}' has been removed from '${file}'"
		redirect(action:'edit', params:[id:id])
    }
	
	def addFiles(Long id) {
		
		File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "The file was not found"
			redirect(action:'edit', params:[id:file.id])
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
		
		List files = params.list('files')
		
		if(!files || !fileAdminService.addFiles(file, files)) {
			
			flash.message = "The file(s) could not be added"
			redirect(action:'edit', id:file.id)
			return
		}
		
		writeAllObjects()
		
		flash.message = "The selected file${tense(files) ? 's' : ''} ${tense(files) ? 'have' : 'has'} been added to '${file}'"
		
		redirect(action:'edit', id:file.id)

	}
	
	def removeFiles(Long id) {
		
		File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "The file was not found"
			redirect(action:'edit', params:[id:file.id])
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
		
		List files = params.list('files')
		
		if(!files || !fileAdminService.removeFiles(file, files)) {
			
			flash.message = "The file(s) could not be removed"
			redirect(action:'edit', id:file.id)
			return
		}
		
		writeAllObjects()
		
		flash.message = "The selected file${tense(files) ? 's' : ''} ${tense(files) ? 'have' : 'has'} been removed from '${file}'"
		
		redirect(action:'edit', id:file.id)

	}
	
	def addStories(Long id) {
		
		File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "The file was not found"
			redirect(action:'edit', params:[id:file.id])
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
		
		List stories = params.list('stories')
		
		if(!stories || !fileAdminService.addStories(file, stories)) {
			
			flash.message = "The story could not be added"
			redirect(action:'edit', id:file.id)
			return
		}
		
		writeAllObjects()
		
		flash.message = "The selected story${tense(stories) ? 's' : ''} ${tense(stories) ? 'have' : 'has'} been added to '${file}'"
		
		redirect(action:'edit', id:file.id)

	}
	
	def removeStories(Long id) {
		
		if(!id) {
			flash.message = "The is no ID"
			redirect(action:'list')
			return
		}
		
		File file = fileAdminService.getFile(id)
		
		if(!file) {
			flash.message = "The file was not found"
			redirect(action:'edit', id:id)
			return
		}
		
		if(checkVersion(file)) {
			optimisticLock(file)
			return
		}
		
		List stories = params.list('stories')
		
		if(!stories || !fileAdminService.removeStories(file, stories)) {
			
			flash.message = "The story could not be removed"
			redirect(action:'edit', id:file.id)
			return
		}
		
		writeAllObjects()
		
		flash.message = "The selected story${tense(stories) ? 's' : ''} ${tense(stories) ? 'have' : 'has'} been removed from '${file}'"
		
		redirect(action:'edit', id:file.id)
	}
	
	def fileSearch(Long id) {
		
		File file = fileAdminService.getFile(id)
		
		if(!file) {
			render "The file was not found"
		}

		else {
			
			setParams(params)
			
			def searchResult = fileAdminService.fileSearch(
				file,
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
				model:[searchResult:searchResult, file:file, params:params]
			)
		}
	}

	/**
	 * 
	 * @param mfile
	 * @return
	 */
	private addFile(MultipartFile mfile) {
		
		String name = fileService.stripCharacters(mfile.getOriginalFilename())
		
		java.io.File renamedFile = null
		
		def path = getServletContext().getRealPath(config.filesDir)
		
		renamedFile = new java.io.File("$path/$name")
		
		if(renamedFile) {
			
			try {
				
				if(!renamedFile.parentFile.exists()) renamedFile.parentFile.mkdirs()
				
				mfile.transferTo(renamedFile)
	
			} catch (Exception e) {
				log.error("An error occurred in ${controllerName}.${actionName} because of ${e.getCause()}")
				return null
			}
		}
		return renamedFile
	}
	
	/**
	 * 
	 * @param file
	 * @param fileOnSystem
	 * @return
	 */
	private setFileProperties(File file, java.io.File fileOnSystem) {
		
		String filePath = "${config.filesDir}/${fileOnSystem.getName()}"
		
		String contextPath = getServletContext().getRealPath("/")
		
		if(file.type == 'Image') {
			
			fileService.createThumbnails(contextPath + filePath)
			file.tinyImage = fileService.getMedImage(filePath, contextPath, "_smaller.jpg")
			file.smallImage = fileService.getThumbnail(filePath, contextPath)
			file.smallImageSquared = fileService.getMedImage(filePath, contextPath, "_smsq.jpg")
			file.mediumImage = fileService.getMedImage(filePath, contextPath, "_med.jpg")
			file.inBetweenImage = fileService.getMedImage(filePath, contextPath, "_bigger.jpg")
			file.largeImage = fileService.getMedImage(filePath, contextPath, "_large.jpg")
			file.xLargeImage = fileService.getMedImage(filePath, contextPath, "_large@2x.jpg")
			file.slideImage = fileService.getMedImage(filePath, contextPath, "_slide.jpg")
			file.smallSlideImage = fileService.getMedImage(filePath, contextPath, "_sm_slide.jpg")
			file.xSlideImage = fileService.getMedImage(filePath, contextPath, "_slide@2x.jpg")
			file.mediumSlideImage = fileService.getMedImage(filePath, contextPath, "_mediumSlide.jpg")
		} else {
			file.tinyImage = fileService.getTinyThumbnail(filePath, contextPath)
			file.smallImage = fileService.getThumbnail(filePath, contextPath)
			file.smallImageSquared = fileService.getTinyThumbnail(filePath, contextPath)
		}
		file.size = fileOnSystem.size()
		file.path = filePath
		file.measuredSize = fileService.getFileSize(file.size)
	}
	
	/**
	 * 
	 * @param file
	 * @param fileOnSystem
	 * @return
	 */
	private changeThumbnailProperties(File file, java.io.File fileOnSystem) {
		
		String filePath = "${config.filesDir}/${fileOnSystem.getName()}"
		
		String contextPath = getServletContext().getRealPath("/")
		
		fileService.createThumbnails(contextPath + filePath)
		
		String pathToAppend = config.filesDir + "/" + 
			fileOnSystem.getName().substring(0, fileOnSystem.getName().lastIndexOf('.'))

		file.smallImage = pathToAppend + "_sm.jpg"
		file.smallImageSquared = pathToAppend + "_smsq.jpg"
		file.mediumImage = pathToAppend + "_med.jpg"
		file.tinyImage = pathToAppend + "_smaller.jpg"
		file.inBetweenImage = pathToAppend + "_bigger.jpg"
		file.largeImage = pathToAppend + "_large.jpg"
		file.xLargeImage = pathToAppend + "_large@2x.jpg"
		file.slideImage = pathToAppend + "_slide.jpg"
		file.xSlideImage = pathToAppend + "_slide@2x.jpg"
		file.mediumSlideImage = pathToAppend + "_mediumSlide.jpg"
		file.smallSlideImage = pathToAppend + "_sm_slide.jpg"
	}
	
	/**
	 * 
	 * @param file
	 * @param title
	 * @param shortDescription
	 * @param description
	 * @return
	 */
	private setFileTextProperties(File file, String title, String shortDescription, String description) {
		
		if(!shortDescription && description) file.fillShortDescription()

		if(title) file.setUrlTitle(file.cleanTitle(title))
	}

    /**
     * 
     * @param fileSize
     * @param type
     * @return
     */
	private boolean checkFileSize(Long fileSize, String type) {

        if (type == "Image" && fileSize > config.maxImageSize) {
            return false
        }
        
        else if (type != "Image" && fileSize > config.maxFileSize) {
			return false
        }
		return true
    }
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private String removeDirName(String path) {
		
		String[] s = path.split("[/]")
		String name = s[s.length -1]
		return name
	}
	
}
