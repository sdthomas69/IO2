package cev.blackFish

import org.apache.shiro.SecurityUtils
import org.springframework.web.context.request.RequestContextHolder
import grails.transaction.*
import javax.servlet.ServletContext

class StoryAdminController extends BaseController {

    //def searchService
    def securityService
    def storyAdminService
    def storyService
    def fileService
    //def permissionService
    def userService

    def list() {

        setParams(params)

        def storyList = storyService.list(
                params.max.toInteger(),
                params.offset.toInteger(),
                params.titleOnly as boolean,
                params.q,
                params.view,
                params.category,
                params.bool,
                params.sort,
                params.order,
                params.type
        )

        return [storyList: storyList, params: params]
    }

    /**
     *
     * @return
     */
    def remoteStoryList() {

        setParams(params)

        def storyList = storyService.list(
                params.max.toInteger(),
                params.offset.toInteger(),
                params.titleOnly as boolean,
                params.q,
                params.view,
                params.category,
                params.bool,
                params.sort,
                params.order,
                params.type
        )

        render(
                template: 'remoteStoryList',
                model: [storyList: storyList, params: params]
        )
    }

    def delete(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found with id ${id}"
            redirect(controller: 'storyAdmin', action: 'list')
            return
        }

        if (story.author) securityService.removePermission("storyAdmin", story.id, story.author)

        story.deleteAllAssociations()

        deleteFile(story.uri)

        if (!storyAdminService.delete(story)) {

            flash.message = "The Story '${story}' could not be deleted"
            redirect(action: 'edit', id: params.id)
            return
        }

        writeAllObjects()

        flash.message = "The story has been deleted"

        if (securityService.hasRole(config.adminRole)) {
            redirect(controller: 'storyAdmin', action: 'list')
        } else {
            redirect(uri: "/shiroUserAdmin/edit/${securityService.getUser().id}")
        }
    }

    def edit = {

        if (!params.id) {
            response.status = 404
            redirect(action: '404', controller: 'errors')
            return
        }

        Story story = storyAdminService.getStory(params.id.toLong())

        if (!story) {
            flash.message = "Story not found with id ${params.id}"
            redirect(action: 'list', controller: 'storyAdmin')
            return
        } else {

            return [story: story]
        }
    }

    def update(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found"
            redirect(action: 'list', controller: 'storyAdmin')
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        boolean publishChange = false

        if (story.published && params.published != 'on') {

            deleteFile(story.uri)  //it's being unpublished so delete the html
        }

        if (params?.author?.id && story.author) {

            securityService.changeAuthor(params.author.id, story.author.id, story.id, "story")
        }

        story.properties = params

        story.updateMessage = lastUpdatedBy()

        if (!storyAdminService.save(story)) {

            flash.message = "The story ${story} could not be updated"
            render(view: 'edit', model: [story: story], params: params)
            return
        }

        writeAllObjects()

        flash.message = "The story ${story} has been updated"
        redirect(action: 'edit', id: story.id)
    }

    def updateText(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found with id ${params.id}"
            redirect(action: 'list', controller: 'storyAdmin')
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.properties = params

        setTextProperties(story, params.title, params.shortDescription, params.description)

        story.updateMessage = lastUpdatedBy()

        if (!storyAdminService.save(story)) {

            render(view: 'editText', model: [story: story])
            return
        }

        writeAllObjects()

        flash.message = "Story '${story}' has been updated"
        redirect(action: 'editText', id: story.id)
    }

    def updateLocation(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found with id ${params.id}"
            redirect(action: 'list', controller: 'storyAdmin')
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.properties = params
        story.updateMessage = lastUpdatedBy()

        if (!storyAdminService.save(story)) {

            render(view: 'setLocation', model: [story: story])
            return
        }

        writeAllObjects()

        flash.message = "The location for ${story} has been updated"
        redirect(action: setLocation, id: story.id)
    }

    def create = {

        Story story = new Story()
        story.properties = params
        render(view: 'editText', model: [story: story])
        //return ['story':story]
    }

    def addMenuSet(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found"
            redirect(action: 'list', controller: 'storyAdmin')
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (params?.menuSet?.id != "") {

            if (!storyAdminService.addMenuToStory(story, params.menuSet.id.toLong())) {

                render(view: 'addMenuSet', model: [story: story])
                return
            }

            writeAllObjects()

            flash.message = "The menuSet has been added to '${story}'"
            redirect(action: 'editMenuSet', params: [id: story.id])
        }

        if (params.menuSet.id == "") {

            if (!storyAdminService.removeMenuFromStory(story)) {

                render(view: 'addMenuSet', model: [story: story])
                return
            }
            flash.message = "The menuSet has been removed from '${story}'"
            redirect(action: 'editMenuSet', params: [id: story.id])
        }
    }

    def addSite(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found"
            redirect(action: 'list', controller: 'storyAdmin')
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (params?.site?.id != "") {

            if (!storyAdminService.addSiteToStory(story, params.site.id.toLong())) {

                render(view: 'addMenuSet', model: [story: story])
                return
            }

            writeAllObjects()

            flash.message = "The site has been added to '${story}'"
            redirect(action: 'editMenuSet', params: [id: story.id])
        }

        if (params.site.id == "") {

            if (!storyAdminService.removeSiteFromStory(story)) {

                render(view: 'addMenuSet', model: [story: story])
                return
            }

            writeAllObjects()

            flash.message = "The site has been removed from '${story}'"
            redirect(action: 'editMenuSet', params: [id: story.id])
        }
    }

    def save() {

        Story story = new Story(params)

        setTextProperties(story, params.title, params.shortDescription, params.description)

        story.updateMessage = lastUpdatedBy()

        if (!storyAdminService.save(story)) {

            render(view: 'createText', model: [story: story])
            return
        }

        User currentUser = securityService.getUser()

        if (currentUser) {

            securityService.addPermission("storyAdmin:${config.userPermission}:${story.id}", currentUser)

            story.author = currentUser

            if (!storyAdminService.save(story)) {

                render(view: 'createText', model: [story: story])
                return
            }
        }

        story.setUri(writeObject(story, "/story"))

        flash.message = "The story '${story}' has been created"

        redirect(action: 'edit', id: story.id)
    }

    def fileSearch(Long id, String template) {

        Story story = storyAdminService.getStory(id)

        if (!story || !template) {
            render "a required item was not submitted"
        } else {

            setParams(params)

            def searchResult = storyAdminService.fileSearch(
                    story,
                    params.max.toInteger(),
                    params.offset.toInteger(),
                    params.type,
                    params.q,
                    params.bool,
                    params.category
            )

            render(template: template, model: [searchResult: searchResult, story: story])
        }
    }

    def imageSearch() {

        setParams(params)

        def searchResult = fileService.list(
                params.max.toInteger(),
                params.offset.toInteger(),
                'Image',
                params.q,
                ""
        )

        render(template: 'remoteFileCkSearch', params: params, model: [searchResult: searchResult])
    }

    def remoteImageSearch() {

        setParams(params)

        def searchResult = fileService.list(
                params.max.toInteger(),
                params.offset.toInteger(),
                'Image',
                params.q,
                ""
        )

        render(template: 'remoteCk', params: params, model: [searchResult: searchResult])
    }

    def storySearch(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            render "the story was not found"
        } else {

            setParams(params)

            def searchResult = storyAdminService.storySearch(
                    story,
                    params.max.toInteger(),
                    params.offset.toInteger(),
                    params.q,
                    params.category,
                    params.titleOnly as boolean,
                    params.bool,
                    params.type
            )

            render(
                    template: 'remoteStorySearch',
                    model: [searchResult: searchResult, story: story]
            )
        }
    }

    def tagSearch(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            render "the story was not found"
        } else {

            params.sort = "title"
            params.order = "asc"
            setParams(params)

            def searchResult = storyAdminService.tagSearch(
                    story,
                    params.max.toInteger(),
                    params.offset.toInteger(),
                    params.q
            )

            render(template: 'remoteTagSearch', model: [searchResult: searchResult, story: story])
        }
    }

    def personSearch(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            render "the story was not found"
        } else {

            if (!params.max) params.max = 16
            if (!params.offset) params.offset = 0
            if (!params.order) params.sort = "lastName"
            if (!params.order) params.order = "asc"

            def searchResult = userService.people(
                    params.max.toInteger(),
                    params.offset.toInteger(),
                    params.nameOnly as boolean,
                    params.q,
                    params.view,
                    params.category,
                    params.sort,
                    params.order,
                    params.bool
            )

            render(
                    template: 'remotePersonSearch',
                    model: [searchResult: searchResult, story: story]
            )
        }
    }

    def setPrimaryImage(Long id, Long fileId) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (!fileId || !storyAdminService.addPrimaryImage(story, fileId)) {

            flash.message = "The file could not be added to the story '${story}'"
            render(view: 'associateFile', model: [story: story])
            return
        }

        writeAllObjects()

        flash.message = "The file is now the primary image of story '${story}'"
        redirect(action: 'associateFile', params: [id: story.id])
    }

    def unSetPrimaryImage(Long id, Long fileId) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (!storyAdminService.removePrimaryImage(story)) {

            flash.message = "The primary image could not be removed from '${story}'"
            render(view: 'associateFile', model: [story: story])
            return
        }

        writeAllObjects()

        flash.message = "The file is no longer the primary image of story '${story}'"
        redirect(action: 'associateFile', params: [id: story.id])
    }

    def embedImage1(Long id, Long fileId) {

        File file = File.get(params.fileId)

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (!fileId || !storyAdminService.addEmbeddedImage(story, fileId)) {

            flash.message = "The file could not be added to the story '${story}'"
            render(view: 'associateFile', model: [story: story])
            return
        }

        writeAllObjects()

        flash.message = "The file is now an embedded image in story '${story}'"
        redirect(action: 'associateFile', params: [id: story.id])
    }

    def unEmbedImage1(Long id, Long fileId) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (!storyAdminService.removeEmbeddedImage(story)) {

            flash.message = "The file could not be removed from '${story}'"
            render(view: 'associateFile', model: [story: story])
            return
        }

        writeAllObjects()

        flash.message = "The file is no longer an embedded image in story '${story}'"
        redirect(action: 'associateFile', params: [id: story.id])
    }

    def embedImage2(Long id, Long fileId) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (!fileId || !storyAdminService.addEmbeddedImage2(story, fileId)) {

            flash.message = "The file could not be added to the story '${story}'"
            render(view: 'associateFile', model: [story: story])
            return
        }

        writeAllObjects()

        flash.message = "The file is now an embedded image in story '${story}'"
        redirect(action: 'associateFile', params: [id: story.id])
    }

    def unEmbedImage2(Long id, Long fileId) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (!storyAdminService.removeEmbeddedImage2(story)) {

            flash.message = "The file could not be removed from '${story}'"
            render(view: 'associateFile', model: [story: story])
            return
        }

        writeAllObjects()

        flash.message = "The file is no longer an embedded image in story '${story}'"
        redirect(action: 'associateFile', params: [id: story.id])
    }

    def setKmlFile(Long id, Long fileId) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (!fileId || !storyAdminService.addKmlFile(story, fileId)) {

            flash.message = "The file could not be added to the story '${story}'"
            render(view: 'associateFile', model: [story: story])
            return
        }
        flash.message = "The file is now the KML file of story '${story}'"
        redirect(action: 'associateFile', params: [id: story.id])
    }

    def removeKmlFile(Long id, Long fileId) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        if (!fileId || !storyAdminService.removeKmlFile(story)) {

            flash.message = "The file could not be removed from the story '${story}'"
            render(view: 'associateFile', model: [story: story])
            return
        }
        flash.message = "The file has been removed"
        redirect(action: 'associateFile', params: [id: story.id])
    }

    def addChild(Long id, Long otherStoryId) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found"
            redirect(action: 'list', controller: 'storyAdmin')
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        if (!otherStoryId || !storyAdminService.addChild(story, otherStoryId)) {

            flash.message = "The story could not be added as a submenu to the story '${story}'"
            render(view: 'associateStory', model: [story: story])
            return
        }

        story.updateMessage = lastUpdatedBy()

        writeAllObjects()

        flash.message = "The story is now a submenu of '${story}'"
        redirect(action: 'associateStory', params: [id: story.id])
    }

    def addStories(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        List stories = params.list('stories')

        if (!stories || !storyAdminService.addStories(story, stories)) {

            flash.message = "The story could not be added"
            redirect(action: 'associateStory', id: story.id)
            return
        }

        story.updateMessage = lastUpdatedBy()

        writeAllObjects()

        flash.message = "The selected ${tense(stories) ? 'stories' : 'story'} ${tense(stories) ? 'have' : 'has'} been added to '${story}'"

        redirect(action: 'associateStory', id: story.id)
    }

    def removeStories(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        List stories = params.list('stories')

        if (!stories || !storyAdminService.removeStories(story, stories)) {

            flash.message = "The story could not be removed"
            redirect(action: 'associateStory', id: story.id)
            return
        }

        writeAllObjects()

        flash.message = "The selected ${tense(stories) ? 'stories' : 'story'} ${tense(stories) ? 'have' : 'has'} been removed from '${story}'"

        redirect(action: 'associateStory', id: story.id)
    }

    def removeChild(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found"
            redirect(action: 'list', controller: 'storyAdmin')
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        List stories = params.list('stories')

        if (!stories || !storyAdminService.removeChild(story, stories)) {

            flash.message = "The submenu could not be removed from the story '${story}'"
            redirect(action: 'associateStory', id: story.id)
            return
        }

        story.updateMessage = lastUpdatedBy()

        writeAllObjects()

        flash.message = "The story is no longer a submenu of '${story}'"
        redirect(action: 'associateStory', params: [id: story.id])
    }

    def addFiles(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: story.id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        List files = params.list('files')

        if (!files || !storyAdminService.addFiles(story, files)) {

            flash.message = "The file could not be added"
            redirect(action: 'associateFile', id: story.id)
            return
        }

        writeAllObjects()

        flash.message = "The selected file${tense(files) ? 's' : ''} ${tense(files) ? 'have' : 'has'} been added to '${story}'"

        redirect(action: 'associateFile', id: story.id)

    }

    def removeFiles(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: story.id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        List files = params.list('files')

        if (!files || !storyAdminService.removeFiles(story, files)) {

            flash.message = "The file could not be removed"
            redirect(action: 'associateFile', id: story.id)
            return
        }

        writeAllObjects()

        flash.message = "The selected file${tense(files) ? 's' : ''} ${tense(files) ? 'have' : 'has'} been removed from '${story}'"

        redirect(action: 'associateFile', id: story.id)
    }


    def addTags(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: story.id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        List tags = params.list('tags')

        if (!tags || !storyAdminService.addTags(story, tags)) {

            flash.message = "The tag could not be added"
            redirect(action: 'associateTag', id: story.id)
            return
        }

        writeAllObjects()

        flash.message = "The selected tag${tense(tags) ? 's' : ''} ${tense(tags) ? 'have' : 'has'} been added to '${story}'"

        redirect(action: 'associateTag', id: story.id)
    }

    def removeTags(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            redirect(action: 'associateFile', params: [id: story.id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        List tags = params.list('tags')

        if (!tags || !storyAdminService.removeTags(story, tags)) {

            flash.message = "The tag could not be removed"
            redirect(action: 'associateTag', id: story.id)
            return
        }

        writeAllObjects()

        flash.message = "The selected tag${tense(tags) ? 's' : ''} ${tense(tags) ? 'have' : 'has'} been removed from '${story}'"

        redirect(action: 'associateTag', id: story.id)
    }

    def addPeople(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found"
            redirect(action: 'associateFile', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        List people = params.list('people')

        story.updateMessage = lastUpdatedBy()

        if (!people || !storyAdminService.addPeople(story, people)) {

            flash.message = "The people could not be added"
            redirect(action: 'associatePerson', id: story.id)
            return
        }

        writeAllObjects()

        flash.message = "The selected ${tense(people) ? 'people' : 'person'} ${tense(people) ? 'have' : 'has'} been added to '${story}'"

        redirect(action: 'associatePerson', id: story.id)
    }

    def removePeople(Long id) {

        Story story = storyAdminService.getStory(id)

        if (!story) {
            flash.message = "Story not found"
            redirect(action: 'associatePerson', params: [id: id])
            return
        }

        if (checkVersion(story)) {
            optimisticLock(story)
            return
        }

        story.updateMessage = lastUpdatedBy()

        List people = params.list('people')

        if (!people || !storyAdminService.removePeople(story, people)) {

            flash.message = "The person could not be removed"
            redirect(action: 'associatePerson', id: story.id)
            return
        }

        writeAllObjects()

        flash.message = "The selected ${tense(people) ? 'people' : 'person'} ${tense(people) ? 'have' : 'has'} been removed from '${story}'"

        redirect(action: 'associatePerson', id: story.id)
    }

    /**
     * Closures that use other closures
     */

    def editText = {
        def closure = getController(fullName).edit.call()
    }

    def editMenuSet = {
        def closure = getController(fullName).edit.call()
    }

    def associateTag = {
        def closure = getController(fullName).edit.call()
    }

    def associateFile = {
        def closure = getController(fullName).edit.call()
    }

    def associateStory = {
        def closure = getController(fullName).edit.call()
    }

    def setLocation = {
        def closure = getController(fullName).edit.call()
    }

    def createText = {
        def closure = getController(fullName).create.call()
    }

    def associatePerson = {
        def closure = getController(fullName).edit.call()
    }

    private Object getController(controllerName) {
        def appContext = RequestContextHolder.getRequestAttributes().getAttributes().getApplicationContext()
        return appContext.getBean(controllerName)
    }

    private String fullName = "cev.blackFish.StoryAdminController"

    /*private boolean checkVersion(story) {
        
        if(params.version) {
            return story.version > params.version.toLong()
        }
        return false
    }
    
    private optimisticLock(story) {
        
        story.errors.rejectValue(
            "version", 
            "story.optimistic.locking.failure", 
            "Another user has updated this Story while you were editing it. Please refresh the page to get the latest version and try again."
        )
        render(view:'edit', model:[story:story])
    }*/

    private String lastUpdatedBy() {

        String updateMessage = ""
        User currentUser = securityService.getUser()

        if (currentUser) {

            def date = DateUtils.getDate("MM/dd/yyyy 'at' HH:mm:ss z")
            def link = g.link([controller: 'userAdmin', action: 'show', id: currentUser.id]) { currentUser }
            updateMessage = "This story was last updated by ${link} on ${date}"
        }
        return updateMessage
    }


}
