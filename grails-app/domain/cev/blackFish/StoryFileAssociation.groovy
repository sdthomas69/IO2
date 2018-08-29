package cev.blackFish

import org.apache.commons.lang.builder.HashCodeBuilder

class StoryFileAssociation implements Serializable {

    Story story

    File file

    static mapping = {
        table 'story_files'
        version false
        id composite: ['story', 'file']
        cache 'true'
        datasources(['DEFAULT', 'lookup'])
    }

    static StoryFileAssociation create(Story story, File file) {
        StoryFileAssociation storyFileAssociation = new StoryFileAssociation(story: story, file: file)
        storyFileAssociation.save(flush: true)
        return storyFileAssociation
    }

    static boolean remove(Story story, File file) {
        StoryFileAssociation storyFileAssociation = StoryFileAssociation.findByStoryAndFile(story, file)
        if (storyFileAssociation && storyFileAssociation.delete(flush: true)) return true
        return false
    }

    static boolean exists(Story story, File file) {

        StoryFileAssociation storyFileAssociation = StoryFileAssociation.findByStoryAndFile(story, file)
        if (storyFileAssociation) return true
        return false
    }

    static void removeAllByStory(Story story) {
        executeUpdate("DELETE FROM StoryFileAssociation WHERE story=:story", [story: story])
    }

    static void removeAllByFile(File file) {
        executeUpdate("DELETE FROM StoryFileAssociation WHERE file=:file", [file: file])
    }

    boolean equals(other) {

        if (!(other instanceof StoryFileAssociation)) {
            return false
        }
        return other.story.id == story.id && other.file.id == file.id
    }

    /*int hashCode() {
        return new HashCodeBuilder().append(story.id).append(file.id).toHashCode()
    }*/
}
