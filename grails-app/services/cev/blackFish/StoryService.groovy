package cev.blackFish

import grails.transaction.Transactional
import org.hibernate.FetchMode as FM
import org.apache.shiro.SecurityUtils

@Transactional(readOnly = true)
class StoryService {

    /**
     *
     * @param id
     * @return
     */
    Story readStory(Long id) {

        return Story.lookup.read(id)
    }

    /**
     *
     * @param title
     * @return
     */
    Story findByTitle(String title) {

        Story story = Story.lookup.withCriteria(uniqueResult: true) {

            eq("urlTitle", title)
            join 'primaryImage'
            join 'embeddedImage1'
            join 'embeddedImage2'
            setReadOnly true
        }
    }

    Story findLatestByBoolean(String bool) {

        Story story = null

        def query = Story.lookup.withCriteria {
            eq("published", true)
            eq(bool, true)
            join 'primaryImage'
            order('date', 'desc')
            maxResults(1)
            setReadOnly true
        }

        if (query) story = query[0]
    }

    /**
     *
     * @param max
     * @param offset
     * @param q
     * @param view
     * @return
     */
    List list(
            Integer max = 10,
            Integer offset = 0,
            boolean titleOnly,
            String q = "",
            String view = "",
            String category = "",
            String bool = "",
            String sortBy = "",
            String orderBy = "",
            String type = "") {

        def query = {

            if (q) {
                if (titleOnly) {
                    ilike("title", "%${q}%")
                } else {
                    Story instance = new Story()
                    or {
                        instance.searchableProps.each {
                            ilike(it, "%${q}%")
                        }
                    }
                }
            }
            if (type) {
                Story.Type t = type.toUpperCase()
                if (t) eq("type", t)
            }
            if (bool) eq(bool, true)
            if (view != 'list') join 'primaryImage'
            if (category) eq("category", category)
            if (!SecurityUtils.subject.isAuthenticated()) {
                eq("published", true)
            }
            order(sortBy ?: 'date', orderBy ?: 'desc')
            maxResults(max)
            firstResult(offset)
            setReadOnly true
        }

        def results = Story.createCriteria().list(['max': max, 'offset': offset], query)

        return results
    }

    /**
     *
     * @return
     */
    def blogs() {

        def blogList = Story.lookup.withCriteria {
            eq("published", true)
            eq("category", "Blog")
            order("date", "desc")
            join 'primaryImage'
            setReadOnly true
        }
        return blogList
    }

}
