package cev.blackFish

import grails.transaction.Transactional

@Transactional(readOnly = true)
class HomeService {

    /**
     *
     * @param max
     * @param boolProp a boolean Story property
     * @param category a Story category
     * @param type a Story Type  
     * @return
     */
    def homeStories(Integer max = 1, String boolProp = "", String category = "", String type = "") {

        def stories = Story.lookup.withCriteria {
            eq("published", true)
            if (category) eq("category", category)
            if (boolProp) eq(boolProp, true)
            if (type) {
                Story.Type t = type.toUpperCase()
                if (t) eq("type", t)
            }
            join 'primaryImage'
            order("date", "desc")
            maxResults(max)
            setReadOnly true
        }
    }

    /**
     * Looks up a Story by its title and returns it
     * @param title
     * @return
     */
    Story getStoryByTitle(String title) {

        Story homeStory = Story.lookup.findByTitle(title)
    }
}
