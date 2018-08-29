package cev.blackFish

import org.apache.commons.lang.builder.HashCodeBuilder

class UserStoryAssociation implements Serializable {

    Story story

    User user

    static mapping = {
        table 'io2_user_stories'
        version false
        id composite: ['story', 'user']
        //cache 'true'
        datasources(['DEFAULT', 'lookup'])
    }

    static UserStoryAssociation create(Story story, User user) {

        UserStoryAssociation userStoryAssociation = new UserStoryAssociation(story: story, user: user)
        userStoryAssociation.save(flush: true)

        return userStoryAssociation
    }

    static boolean remove(Story story, User user) {

        UserStoryAssociation userStoryAssociation = UserStoryAssociation.findByStoryAndUser(story, user)

        if (userStoryAssociation && userStoryAssociation.delete()) return true

        return false
    }

    static void removeAllByStory(Story story) {

        executeUpdate("DELETE FROM UserStoryAssociation WHERE story=:story", [story: story])
    }

    static void removeAllByUser(User user) {

        executeUpdate("DELETE FROM UserStoryAssociation WHERE user=:user", [user: user])
    }

    boolean equals(other) {

        if (!(other instanceof UserStoryAssociation)) {
            return false
        }
        return other.story.id == story.id && other.user.id == user.id
    }

    /*int hashCode() {
        return new HashCodeBuilder().append(story.id).append(tag.id).toHashCode()
    }*/
}
