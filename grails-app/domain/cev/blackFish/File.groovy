package cev.blackFish

class File extends Content {

    String mfile
    String measuredSize
    String smallImage
    String smallImageSquared
    String tinyImage
    String inBetweenImage
    String mediumImage
    String largeImage
    String xLargeImage
    String slideImage
    String smallSlideImage
    String mediumSlideImage
    String xSlideImage
    String type
    String path
    String phoneMovie
    String desktopMovie
    String posterImage
    String flashURL
    String iPhoneURL
    String caption
    String tilesDirectory

    Boolean document = false
    Boolean publication = false
    Boolean data = false
    Boolean stream = false
    Boolean playlist = false
    Boolean tablet = false
    Boolean tiled = false

    Integer size
    Integer width
    Integer height

    List generatedFiles = [
            'path',
            'smallImage',
            'smallSlideImage',
            'smallImageSquared',
            'tinyImage',
            'inBetweenImage',
            'mediumImage',
            'largeImage',
            'xLargeImage',
            'slideImage',
            'mediumSlideImage',
            'xSlideImage',
            'phoneMovie',
            'desktopMovie',
            'posterImage']

    static belongsTo = [Story]

    static transients = ['mfile', 'generatedFiles', 'tags', 'stories']

    static constraints = {
        path(nullable: true, unique: true, maxSize: 255)
        type(blank: false, maxSize: 50)
        measuredSize(nullable: true, maxSize: 50)
        smallImage(nullable: true, maxSize: 255)
        smallImageSquared(nullable: true, maxSize: 255)
        tinyImage(nullable: true, maxSize: 255)
        inBetweenImage(nullable: true, maxSize: 255)
        mediumImage(nullable: true, maxSize: 255)
        slideImage(nullable: true, maxSize: 255)
        smallSlideImage(nullable: true, maxSize: 255)
        size(nullable: true)
        phoneMovie(nullable: true, unique: true, maxSize: 100)
        desktopMovie(nullable: true, unique: true, maxSize: 100)
        posterImage(nullable: true, unique: true, maxSize: 100)
        width(nullable: true)
        height(nullable: true)
        flashURL(nullable: true, maxSize: 255)
        iPhoneURL(nullable: true, maxSize: 255)
        largeImage(nullable: true, maxSize: 255)
        xLargeImage(nullable: true, maxSize: 255)
        xSlideImage(nullable: true, maxSize: 255)
        caption(nullable: true, maxSize: 255)
        mediumSlideImage(nullable: true, maxSize: 255)
        tilesDirectory(nullable: true, unique: true, maxSize: 255)
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'file_seq']
        datasources(['DEFAULT', 'lookup'])
        cache true
        stories cache: true
        tags cache: true
    }

    Set<Story> getStories() {
        StoryFileAssociation.findAllByFile(this).collect { it.story } as Set
    }

    Set<Tag> getTags() {
        FileTagAssociation.findAllByFile(this).collect { it.tag } as Set
    }

    void deleteAllAssociations() {

        def primaries = Story.findAllByPrimaryImage(this)

        if (primaries) {
            for (story in primaries) {
                story.setPrimaryImage(null)
            }
        }

        def embeddedImages1 = Story.findAllByEmbeddedImage1(this)

        if (embeddedImages1) {
            for (story in embeddedImages1) {
                story.setEmbeddedImage1(null)
            }
        }

        def embeddedImages2 = Story.findAllByEmbeddedImage2(this)

        if (embeddedImages2) {
            for (story in embeddedImages2) {
                story.setEmbeddedImage2(null)
            }
        }

        def users = User.findAllByPrimaryImage(this)

        if (users) {
            for (user in users) {
                user.setPrimaryImage(null)
            }
        }

        if (this.author) this.setAuthor(null)

        StoryFileAssociation.removeAllByFile(this)

        FileTagAssociation.removeAllByFile(this)
    }
}