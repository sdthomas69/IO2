package cev.blackFish

class Story extends Content {
	
	enum Layout {
		STANDARD, PORTAL, GALLERY, MAP, ONECOLUMN
	}
	
	Layout layout = Layout.STANDARD
	
	enum Type {
		EVENT
	}
	
	Type type

	File primaryImage, embeddedImage1, embeddedImage2, kmlFile

	Boolean bottomStories = false
	Boolean mapped = false
	Boolean isLink = false
	
	Date endDate = new Date()
    
	MenuSet menuSet
    
	Integer orderIndex
	
	Double depth
    
	String headScript, bodyScript, frameworkScript, layerId, dataURL
    
    Story parent
	
	static transients = ['images', 'nonImageFiles', 'videos', 'hasImages', 'hasVideos', 'stories', 'files', 'tags']

    static constraints = {
		primaryImage(nullable:true)
		embeddedImage1(nullable:true)
		embeddedImage2(nullable:true)
        kmlFile(nullable:true)
		parent(nullable: true)
		orderIndex(nullable: true)
		layout(blank:false, maxSize:55)
		menuSet(nullable:true)
		endDate(nullable:true)
		headScript(nullable:true)
		bodyScript(nullable:true)
		frameworkScript(nullable:true)
		type(nullable:true, maxSize:55)
		depth(nullable:true)
		layerId(nullable:true, maxSize:255)
		dataURL(nullable:true, maxSize:255)
    }

	static mapping = {
		id generator:'sequence', params:[sequence:'story_seq']
		cache true
		datasources(['DEFAULT', 'lookup'])
		headScript type:'text'
		bodyScript type:'text'
		frameworkScript type:'text'
		parent cache:true
		menuSet cache:true
		children cache:true
		//stories cache:true
		//files cache:true
		//tags cache:true
    }

	Boolean hasVideos() {
		this.files*.type?.contains('Quicktime')
	}

	Boolean hasImages() {
		this.files*.type?.contains('Image')
	}

	Boolean hasNonImages() {

		def files = this.files.findAll {
			(it.type != 'Image' && it.type != 'Quicktime')
		}
		return (files.size() > 0)
	}

	Set<File> getImages() {

		def files = StoryFileAssociation.withCriteria {
			story {
				eq("id", this.id)
			}
			file {
				eq("type", "Image")
			}
			setReadOnly true
			//cache true
		}.collect { it.file } as Set
	}
	
	Set<File> getVideos() {

		def files = StoryFileAssociation.withCriteria {
			story {
				eq("id", this.id)
			}
			file {
				eq("type", "Quicktime")
			}
			setReadOnly true
			//cache true
		}.collect { it.file } as Set
	}

	Set<File> getNonImageFiles() {

		def files = StoryFileAssociation.withCriteria {
			story {
				eq("id", this.id)
			}
			file {
				ne("type", "Image")
				ne("type", "Quicktime")
			}
			setReadOnly true
			//cache true
		}
	}

	Set<Story> getPeople() {
		UserStoryAssociation.findAllByStory(this).collect { it.user } as Set
	}

	Set<Story> getStories() {

		StoryAssociation.findAll(
			"from StoryAssociation as ft left outer join fetch ft.story.primaryImage where ft.otherStory = :storyInstance",
			[storyInstance: this],
			[readOnly:true]
		).collect { it.story }.unique() as Set
	}

	Set<Story> getChildren() {

		Story.withCriteria {
			eq("parent.id", this.id.toLong())
			setReadOnly true
			//cache true
		}.unique() as Set
	}

	Set<Story> getTags() {
		StoryTagAssociation.findAllByStory(this, [readOnly:true]).collect { it.tag } as Set
	}
	
	Set<Story> getFiles() {
		StoryFileAssociation.findAllByStory(this, [readOnly:true]).collect { it.file } as Set
	}

	void deleteAllAssociations() {

		StoryTagAssociation.removeAllByStory(this)

		StoryAssociation.removeAll(this)

		StoryFileAssociation.removeAllByStory(this)

		UserStoryAssociation.removeAllByStory(this)

		this.setAuthor(null)
		this.setPrimaryImage(null)
		this.setEmbeddedImage1(null)
		this.setEmbeddedImage2(null)
		this.setKmlFile(null)
	}
}