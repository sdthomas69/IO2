package cev.blackFish

import org.apache.commons.lang.builder.HashCodeBuilder

class StoryTagAssociation implements Serializable {

    Story story

    Tag tag

    static mapping = {
        table 'story_tags'
        version false
        id composite: ['story', 'tag']
        cache 'true'
        datasources(['DEFAULT', 'lookup'])
    }

    static StoryTagAssociation create(Story story, Tag tag) {

        StoryTagAssociation storyTagAssociation = new StoryTagAssociation(story: story, tag: tag)
        storyTagAssociation.save(flush: true)

        return storyTagAssociation
    }

    static boolean remove(Story story, Tag tag) {

        StoryTagAssociation storyTagAssociation = StoryTagAssociation.findByStoryAndTag(story, tag)

        if (storyTagAssociation && storyTagAssociation.delete()) return true

        return false
    }

    static void removeAllByStory(Story story) {

        executeUpdate("DELETE FROM StoryTagAssociation WHERE story=:story", [story: story])
    }

    static void removeAllByTag(Tag tag) {

        executeUpdate("DELETE FROM StoryTagAssociation WHERE tag=:tag", [tag: tag])
    }

    boolean equals(other) {

        if (!(other instanceof StoryTagAssociation)) {
            return false
        }
        return other.story.id == story.id && other.tag.id == tag.id
    }

    /*int hashCode() {
        return new HashCodeBuilder().append(story.id).append(tag.id).toHashCode()
    }*/
}
