package cev.blackFish

import org.apache.commons.lang.builder.HashCodeBuilder

class StoryAssociation implements Serializable {
    
    Story story
    Story otherStory

    static mapping = {
        table 'story_stories' 
        version false 
        otherStory column:'stories__id'
        id composite: ['story', 'otherStory']
        cache true
        datasources(['DEFAULT', 'lookup'])
    }
	
	static boolean exists(Story story, Story otherStory) {
		
		StoryAssociation storyAssociation = StoryAssociation.findByStoryAndOtherStory(story, otherStory)
		if(storyAssociation) return true
		return false
	}
    
    static StoryAssociation create(Story story, Story otherStory) {
		
        StoryAssociation storyAssociation = new StoryAssociation(story: story, otherStory: otherStory)
        storyAssociation.save(flush:true)
		
		StoryAssociation otherAssociation = new StoryAssociation(story: otherStory, otherStory: story)
		otherAssociation.save(flush:true)
		
        return storyAssociation
    }

    /**
     *
     * @param story
     * @param otherStory
     * @return
     */
    static boolean remove(Story story, Story otherStory) {

        StoryAssociation storyAssociation1 = StoryAssociation.findByStoryAndOtherStory(otherStory, story)
        storyAssociation1.delete(flush: true)

        StoryAssociation storyAssociation2 = StoryAssociation.findByStoryAndOtherStory(story, otherStory)
        return (storyAssociation1.delete(flush: true) && storyAssociation2.delete(flush: true))
    }

    static void removeAll(Story story) {
        executeUpdate("DELETE FROM StoryAssociation WHERE story=:story", [story: story])
        executeUpdate("DELETE FROM StoryAssociation WHERE otherStory=:story", [story: story])
    }
	
	boolean equals(other) {
		
		if (!(other instanceof StoryAssociation)) {
		   return false
		}
		return other.story.id == story.id && other.otherStory.id == otherStory.id
	}
	
	/*int hashCode() {
		return new HashCodeBuilder().append(story.id).append(otherStory.id).toHashCode()
	}*/
}
