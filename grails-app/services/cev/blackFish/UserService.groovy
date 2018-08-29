package cev.blackFish

import grails.transaction.Transactional
import org.apache.shiro.SecurityUtils
import org.hibernate.FetchMode as FM

@Transactional(readOnly = true)
class UserService {

    /**
     *
     * @param id
     * @return
     */
    User getUser(Long id) {

        User userInstance = User.lookup.read(id)
    }

    /**
     *
     * @param max
     * @param offset
     * @param nameOnly
     * @param q
     * @param view
     * @param category
     * @param subcategory
     * @param bool
     * @return
     */
    def list(
            Integer max,
            Integer offset,
            boolean nameOnly,
            String q,
            String view,
            String category,
            String sortBy = "",
            String orderBy = "",
            String bool) {

        def query = {
            if (q) {
                if (nameOnly) {
                    ilike("name", "%${q}%")
                } else {
                    or {
                        new User().searchableProps.each {
                            ilike(it, "%${q}%")
                        }
                    }
                }
            }
            if (bool) eq(bool, true)
            if (view != 'list') join 'primaryImage'
            if (category) eq("category", category.capitalize())
            order(sortBy ?: 'lastName', orderBy ?: 'asc')
            maxResults(max)
            firstResult(offset)
            setReadOnly true
        }

        def results = User.createCriteria().list(['max': max, 'offset': offset], query)

        return results
    }

    /**
     *
     * @param max
     * @param offset
     * @param nameOnly
     * @param q
     * @param view
     * @param category
     * @param subcategory
     * @param option
     * @param startsWith
     * @param cv
     * @param review
     * @param evaluation
     * @param bool
     * @param date
     * @return
     */
    def people(
            Integer max,
            Integer offset,
            boolean nameOnly,
            String q,
            String view,
            String category,
            String option,
            String startsWith,
            String sortBy = "",
            String orderBy = "",
            String cv,
            String review,
            String evaluation,
            String bool,
            Date date) {

        Integer maxAmount = SecurityUtils.getSubject().hasRole("Administrator") ? max : max

        def query = {
            if (q) {
                if (nameOnly) {
                    ilike("name", "%${q}%")
                } else {
                    or {
                        new User().searchableProps.each {
                            ilike(it, "%${q}%")
                        }
                    }
                }
            }
            if (option) eq("department", option)
            if (startsWith) ilike("lastName", "${startsWith}%")
            if (bool) eq(bool, true)
            if (view != 'list') join 'primaryImage'
            if (category) eq("category", category.capitalize())
            if (date) eq("date", date)
            //if(date) between('date', date-1, date)

            if (SecurityUtils.getSubject().hasRole("Administrator")) {
                if (cv == 'true') eq("hasCv", true)
                if (cv == 'false') eq("hasCv", false)
                if (review == 'true') eq("hasReview", true)
                if (review == 'false') eq("hasReview", false)
                if (evaluation == 'true') eq("hasEvaluation", true)
                if (evaluation == 'false') eq("hasEvaluation", false)
            } else eq("active", true)
            order(sortBy ?: 'lastName', orderBy ?: 'asc')
            //order('lastName', 'asc')
            firstResult(offset)
            maxResults(maxAmount)
            setReadOnly true
        }

        def results = User.createCriteria().list(['max': maxAmount, 'offset': offset], query)

        return results
    }

    /**
     *
     * @param email
     * @return
     */
    User findByEmail(String email) {

        User userInstance = User.findByEmail(email)
    }

    /**
     *
     * @param nonce
     * @return
     */
    User findByNonce(String nonce) {

        User userInstance = User.findByNonce(nonce)
    }

    /**
     *
     * @param firstAndLastName
     * @return
     */
    User findByName(String firstAndLastName) {

        User user = User.lookup.withCriteria(uniqueResult: true) {

            if (!SecurityUtils.getSubject().hasRole("Administrator")) {
                eq("confirmed", true)
            }
            eq("name", firstAndLastName)
            join 'primaryImage'
            setReadOnly true
        }
    }

    User findByUsername(String username) {

        User userInstance = User.lookup.findByUsername(username, [cache: true])
    }

    User findByUsernameAndConfirmed(String username) {

        User userInstance = User.lookup.findByUsernameAndConfirmed(username, true, [cache: true])
    }

    User findPermsAndRolesByUsername(String username) {

        User userInstance = User.lookup.findByUsername(
                username,
                [fetch: [permissions: "eager"], fetch: [roles: "eager"]],
                [cache: true]
        )
    }
}
