package cev.blackFish

class Tag implements Serializable {

    String title, urlTitle, uri

    Date date = new Date()

    static constraints = {
        title(blank: false, maxSize: 100, unique: true, validator: {
            if (it.matches('^.*[!@#$%^&?/;_()*-+-].*$')) {
                return 'title.illegal.character'
            }
        })
        urlTitle(maxSize: 100)
        uri(nullable: true, maxSize: 255)
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'tag_seq']
        cache true
        stories cache: true
        files cache: true
        datasources(['DEFAULT', 'lookup'])
    }

    static transients = ['videos', 'hasVideos', 'stories', 'files']

    Set<Story> getStories() {
        StoryTagAssociation.findAllByTag(this).collect { it.story } as Set
    }

    Set<File> getFiles() {
        FileTagAssociation.findAllByTag(this).collect { it.file } as Set
    }

    String toString() {
        return "${title}"
    }

    Boolean hasVideos() {
        this.files*.type?.contains('Quicktime')
    }

    Set<File> getVideos() {

        def files = FileTagAssociation.withCriteria {
            tag {
                eq("id", this.id)
            }
            file {
                eq("type", "Quicktime")
            }
            setReadOnly true
            cache true
        }.collect { it.file } as Set
    }


    void deleteAllAssociations() {

        StoryTagAssociation.removeAllByTag(this)

        FileTagAssociation.removeAllByTag(this)
    }
}
