package cev.blackFish

import grails.transaction.Transactional

@Transactional
class FileAdminService {

    File getFile(Long id) {

        return File.get(id)
    }

    boolean save(File file) {

        return !file.hasErrors() && file.save(flush: true)
    }

    boolean delete(File file) {

        try {
            file.delete(flush: true)
            return true
        }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            log.info("exception ${e} occurred while attempting to delete a file")
            return false
        }
    }

    def getAvailableTags(File file) {

        def availableTags = Tag.withCriteria {

            file.tags.each {
                ne("id", it.id)
            }
            order("title", "asc")
        }
        return availableTags
    }

    @Transactional(readOnly = true)
    def fileSearch(
            File file,
            Integer max = 10,
            Integer offset = 0,
            String type = "",
            String q = "",
            String bool = "",
            String category = "",
            String sortBy = "",
            String orderBy = "") {

        def query = {

            if (type) eq("type", type)
            if (category) eq("category", category)
            if (bool) eq(bool, true)
            if (q) {
                or {
                    file.searchableProps.each {
                        ilike(it, "%${q}%")
                    }
                }
            }
            file.files.each {
                ne("id", it.id)
            }
            ne("id", file.id)
            order(sortBy ?: 'date', orderBy ?: 'desc')
            maxResults(max)
            firstResult(offset)
        }

        def results = File.createCriteria().list(['max': max, 'offset': offset], query)

        return results
    }

    @Transactional(readOnly = true)
    def tagSearch(File file, Integer max, Integer offset, String q) {

        def query = {

            if (q) ilike("title", "%${q}%")

            file.tags.each {
                ne("id", it.id)
            }
            order("title", "asc")
            maxResults(max)
            firstResult(offset)
        }

        def results = Tag.createCriteria().list(['max': max, 'offset': offset], query)

        return results
    }

    boolean addTag(File file, Tag tag) {

        FileTagAssociation.create(file, tag)

        if (!save(file)) {
            log.info("File could not be saved in fileAdmin.addTag()")
            return false
        }
        return true
    }

    boolean removeTag(File file, Tag tag) {

        FileTagAssociation.remove(file, tag)

        if (!save(file)) {
            log.info("File could not be saved in fileAdmin.removeTag()")
            return false
        }
        return true
    }

    boolean addStories(File file, List stories) {

        stories.each {
            Story story = Story.get(it)
            if (story) StoryFileAssociation.create(story, file)
        }

        if (!save(file)) {
            log.error("The File could not be saved")
            return false
        }
        return true
    }

    boolean removeStories(File file, List stories) {

        stories.each {
            Story story = Story.get(it)
            if (story) StoryFileAssociation.remove(story, file)
        }

        if (!save(file)) {
            log.error("The File could not be saved")
            return false
        }
        return true
    }

    boolean addFiles(File file, List files) {

        files.each {
            File thisFile = File.get(it)
            if (thisFile) file.addToFiles(thisFile)
        }

        if (!save(file)) {
            log.error("The File could not be saved")
            return false
        }
        return true
    }

    boolean removeFiles(File file, List files) {

        files.each {
            File thisFile = File.get(it)
            if (thisFile) file.removeFromFiles(thisFile)
        }

        if (!save(file)) {
            log.error("The File could not be saved")
            return false
        }
        return true
    }
}
