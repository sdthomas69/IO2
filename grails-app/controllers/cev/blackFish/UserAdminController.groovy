package cev.blackFish

import org.springframework.dao.DataIntegrityViolationException

class UserAdminController extends BaseController {

    def securityService
    def userAdminService
    def userService
    def mailService

    def resetPasswords() {

        for (u in User.list()) {

            User user = userAdminService.getUser(id)

            if (!user) continue

            if (user.username == 'admin') continue

            def nonce = org.apache.commons.lang.RandomStringUtils.random(10, true, true)

            nonce = nonce + "0cean@"

            securityService.saltAndHashUser(nonce, user)

            user.setConfirmed(false)

            if (!userAdminService.save(user)) {
                continue
            }

            flash.message = "the passwords have been reset"

            redirect(action: "list")
        }
    }

    def getPermission() {

        def User = User.get(params.id)

        def objectId = params.objectId

        def object = params.object

        if (!User || !objectId || !object) {
            render "an item is missing"
        } else {

            def permission = User.permissions.find { it == "${object}Admin:*:${objectId}" }

            render(template: 'permission', model: [permission: permission, User: User])
        }
    }

    def createInvitation() {
        User currentUser = securityService.getUser()
        [currentUser: currentUser]
    }

    def sendInvitation() {

        if (!params.emailText || !params.from || !params.subject || !params.tAddress) {
            flash.message = "A parameter is missing"
            render(view: 'createInvitation')
            return
        }

        String nonce = org.apache.commons.lang.RandomStringUtils.randomAlphabetic(64)

        def link = g.link([controller: 'user', action: 'create', params: ['nonce': nonce]]) { "here " }

        //println "link is ${link}"

        def linkMessage = "Click ${link} to sign up "

        //println "message is ${linkMessage}"

        try {
            sendMail {
                async true
                to params.tAddress
                from params.from
                subject params.subject
                //body params.emailText + linkMessage
                html g.render(template: "/userAdmin/sendInvitation", model: [nonce: nonce, eText: params.emailText])
            }

        } catch (Exception e) {
            log.error("an exception occurred while attempting to send an email because of ${e}")
            flash.message = "The email could not be sent.  The error has been logged"
            redirect(action: 'list')
            return
        }

        flash.message = "The invitation has been sent"

        redirect(action: 'list')
    }

    def list() {

        if (!params.sort) params.sort = "lastName"
        if (!params.order) params.order = "asc"

        setParams(params)

        def userInstanceList = userService.list(
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

        [userInstanceList: userInstanceList]
    }

    def show(Long id) {

        User userInstance = userService.getUser(id)

        if (!userInstance) {
            flash.message = "user not found"
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def login() {

        User currentUser = securityService.getUser()

        if (currentUser) {
            redirect(controller: "userAdmin", action: "edit", id: currentUser.id)
        } else {
            flash.message = "User not found"
            redirect(action: "404", controller: "errors")
        }
    }

    def create() {

        [userInstance: new User(params)]
    }

    def save() {

        User userInstance = new User()

        if (securityService.hasRole("Administrator")) userInstance.properties = params

        else bindData(userInstance, params, [exclude: 'roles'])

        userInstance.name = userInstance.firstName + " " + userInstance.lastName

        userInstance.setUrlTitle(cleanTitle(userInstance.name))

        userInstance.confirmed = true

        if (params.password) securityService.saltAndHashUser(params.password, userInstance)

        if (!userAdminService.save(userInstance)) {

            render(view: "create", model: [userInstance: userInstance])
            return
        }

        Role userRole = Role.findByName(config.userRole) // Default User role

        if (userRole) userInstance.addToRoles(userRole)

        securityService.addPermission("userAdmin:*:${userInstance.id}", userInstance)

        if (!userAdminService.save(userInstance)) {

            render(view: "create", model: [userInstance: userInstance])
            return
        }

        flash.message = "the user ${userInstance} has been created"

        redirect(action: "edit", id: userInstance.id)
    }

    def edit(Long id) {

        User userInstance = userAdminService.getUser(id)

        if (!userInstance) {
            flash.message = "user not found"
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def updatePasswordForm(Long id) {

        User userInstance = userAdminService.getUser(id)

        if (!userInstance) {
            flash.message = "user not found"
            redirect(action: "create")
            return
        }
        [userInstance: userInstance]
    }

    def updatePassword(Long id) {

        User userInstance = userAdminService.getUser(id)

        if (!userInstance) {
            flash.message = "user not found"
            redirect(action: "create")
            return
        }

        if (checkVersion(userInstance)) {
            optimisticLock(userInstance)
            return
        }

        bindData(userInstance, params, [include: ['password', 'version', 'confirm']])

        securityService.saltAndHashUser(params.password, userInstance)

        if (!userAdminService.save(userInstance)) {

            render(view: "updatePasswordForm", model: [userInstance: userInstance])
            return
        }

        flash.message = "the user ${userInstance} has been updated"

        redirect(action: "edit", id: userInstance.id)
    }

    def update(Long id) {

        User userInstance = userAdminService.getUser(id)

        if (!userInstance) {
            flash.message = "user not found"
            redirect(action: "list")
            return
        }

        if (checkVersion(userInstance)) {
            optimisticLock(userInstance)
            return
        }

        if (securityService.hasRole("Administrator")) {
            userInstance.properties = params
            processRoleParameters(params, userInstance)
        } else bindData(userInstance, params, [exclude: ['roles', 'confirmed']])

        userInstance.setUrlTitle(cleanTitle(userInstance.name))

        if (params.password) securityService.saltAndHashUser(params.password, userInstance)

        if (!userAdminService.save(userInstance)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        if (securityService.hasRole("Administrator") && params.roles) {

            addRoles(params, userInstance)
        }

        flash.message = "the user ${userInstance} has been updated"

        redirect(action: "edit", id: userInstance.id)
    }

    def delete(Long id) {

        User userInstance = userAdminService.getUser(id)

        if (!userInstance) {
            flash.message = "the user has been deleted"
            redirect(action: "list")
            return
        }

        userInstance.deleteAllAssociations()

        if (!userAdminService.delete(userInstance)) {

            flash.message = "The User could not be deleted"
            redirect(action: 'edit', id: params.id)
            return
        }

        // Log the user out if not admin
        if (!securityService.hasRole(config.adminRole)) {
            securityService.logout()
            redirect(controller: "home", action: "index")
        } else redirect(action: "list")
    }

    def setPrimaryImage(Long id, Long fileId) {

        User userInstance = userAdminService.getUser(id)

        if (!userAdminService.addPrimaryImage(userInstance, fileId)) {

            flash.message = "The primary image could not be added"
            redirect(action: 'edit', id: id)
            return
        }

        flash.message = "The file has been made the primary image of ${userInstance}"
        redirect(action: 'edit', params: [id: id])
    }

    def unSetPrimaryImage(Long id) {

        User userInstance = userAdminService.getUser(id)

        if (!userAdminService.removePrimaryImage(userInstance)) {

            flash.message = "The primary image could not be removed"
            redirect(action: 'edit', id: id)
            return
        }

        flash.message = "The primary image for ${userInstance} has been removed"
        redirect(action: 'edit', params: [id: id])
    }

    def addStory(Long id, Long storyId) {

        User user = User.get(params.id)
        Story story = Story.get(params.storyId)

        if (user && story) {
            securityService.addPermission("storyAdmin:*:${story.id}")
            user.addToStories(story)
            flash.message = "Story '${story}' has been added to '${user}'"
            redirect(action: 'edit', params: [id: user.id])
        } else render "an error has occurred"
    }

    def removeStory(Long id, Long storyId) {

        User user = User.get(id)
        Story story = Story.get(storyId)

        if (user && story) {
            securityService.removePermission("storyAdmin:*:${story.id}")
            user.removeFromStories(story)
            flash.message = "Story '${story}' has been removed from '${user}'"
            redirect(action: 'edit', params: [id: user.id])
        } else render "an error has occurred"
    }

    def addFile(Long id, Long fileId) {

        File file = File.get(fileId)
        User user = User.get(id)

        if (file && user) {
            securityService.addPermission("fileAdmin:*:${file.id}")
            user.addToFiles(file)
            flash.message = "File '${file}' has been added to '${user}'"
            redirect(action: 'edit', params: [id: user.id])
        } else render "an error has occurred"
    }

    def removeFile(Long id, Long fileId) {

        File file = File.get(fileId)
        User user = User.get(id)

        if (file && user) {
            securityService.removePermission("fileAdmin:*:${file.id}")
            user.removeFromFiles(file)
            flash.message = "File '${file}' has been removed from '${user}'"
            redirect(action: 'edit', params: [id: user.id])
        } else render "an error has occurred"
    }

    def tagSearch(Long id) {

        User user = userAdminService.getUser(id)

        if (!user) {
            render "the user was not found"
        } else {

            params.max = "50"
            setParams(params)

            def searchResult = userAdminService.tagSearch(
                    user,
                    params.max.toInteger(),
                    params.offset.toInteger(),
                    params.q
            )

            render(
                    template: 'remoteTagSearch',
                    model: [searchResult: searchResult, user: user]
            )
        }
    }

    def addTags(Long id) {

        User user = userAdminService.getUser(id)

        if (!user) {
            redirect(action: 'edit', id: id)
            return
        }

        List tags = params.list('tags')

        if (!tags || !userAdminService.addTags(user, tags)) {

            flash.message = "The tags could not be added"
            redirect(action: 'edit', id: id)
            return
        }

        writeAllObjects()

        flash.tab = "tags"

        flash.message = "The tags have been added to the user ${user}"
        redirect(action: "edit", id: user.id)
    }

    def removeTags(Long id) {

        User user = userAdminService.getUser(id)

        if (!user) {
            redirect(action: 'edit', id: id)
            return
        }

        List tags = params.list('tags')

        if (!tags || !userAdminService.removeTags(user, tags)) {

            flash.message = "The tags could not be removed"
            redirect(action: 'edit', id: id)
            return
        }

        writeAllObjects()

        flash.tab = "tags"

        flash.message = "The tags have been removed from the user ${user}"
        redirect(action: "edit", id: user.id)
    }

    def test() {

        /*User currentUser = securityService.getUser()
        
        def storyCount = User.withSession { session ->
            session.createFilter(currentUser.stories, 'select count(*)').uniqueResult()
        }
        
        render storyCount*/
    }

    private processRoleParameters(params, userInstance) {

        if (params.roles) {

            /* Grails default parameter binding can't add and remove at the same time. So we have to... */

            List currentRoles = []

            if (userInstance.roles) {

                for (u in userInstance.roles) {
                    currentRoles.add(u.id)
                }
            }

            def roles = request.getParameterValues('roles').collect { it.toLong() }

            /* Check to see if there is a difference between current roles and incoming
            (and possibly different) role parameters */

            if (currentRoles.sort() != roles.sort()) {

                List rolesToRemove = (currentRoles + roles) - currentRoles.intersect(roles)

                // Remove the difference
                if (rolesToRemove) {
                    User.removeRoles(rolesToRemove, userInstance)
                }
            }
        }
    }

    private addRoles(params, userInstance) {

        if (params.list('roles')) {

            User.addRoles(params.list('roles'), userInstance)
        }
    }

}
