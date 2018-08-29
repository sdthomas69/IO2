package cev.blackFish

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.core.GrailsApplication

@Transactional(readOnly = true)
class HelpController {

    //static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    GrailsApplication grailsApplication
    def helpService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //respond Help.list(params), model:[helpInstanceCount: Help.count()]
        [helpInstanceList: Help.list(params), helpInstanceTotal: Help.count()]
    }

    def show(Help helpInstance) {
        //respond helpInstance
        [helpInstance: helpInstance]
    }

    def create() {

        if (params.selectedController) {
            //respond new Help(params), model:[actions:showActions(params.selectedController)]
            [helpInstance: new Help(params), actions: showActions(params.selectedController)]
        } else {
            //respond new Help(params)
            [helpInstance: new Help(params)]
        }
    }

    @Transactional
    def save(Help helpInstance) {
        if (helpInstance == null) {
            notFound()
            return
        }

        if (!helpService.save(helpInstance)) {
            respond helpInstance, view: 'create'
            return
        }

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'helpInstance.label', default: 'Help'), helpInstance.id])
                redirect action: "edit", id: helpInstance.id
            }
            '*' { respond helpInstance, [status: CREATED] }
        }
    }

    def edit(Help helpInstance) {

        //respond helpInstance, model:[actions:showActions(helpInstance.selectedController)]
        [helpInstance: helpInstance, actions: showActions(helpInstance.selectedController)]
    }

    @Transactional
    def update(Help helpInstance) {

        if (helpInstance == null) {
            notFound()
            return
        }

        if (!helpService.save(helpInstance)) {
            flash.message = "The help could not be updated"
            respond helpInstance, view: 'edit'
            return
        }

        request.withFormat {
            form {
                flash.message = "The help has been updated"
                redirect action: "edit", id: helpInstance.id
            }
            '*' { respond helpInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Help helpInstance) {

        if (helpInstance == null) {
            notFound()
            return
        }

        if (helpInstance.primaryImage) helpInstance.setPrimaryImage(null)

        if (!helpService.delete(helpInstance)) {
            flash.message = "The help could not be deleted"
            respond helpInstance, view: 'edit'
            return
        }

        request.withFormat {
            form {
                flash.message = "The help has been deleted"
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'helpInstance.label', default: 'Help'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def showActions(String selectedController) {

        selectedController = selectedController.capitalize()

        def actions = null

        def controller = grailsApplication.getArtefact("Controller", "cev.blackFish.${selectedController}Controller")

        if (controller)
            actions = controller.getActions().collect({ it }).unique().sort()

        return actions
    }

    @Transactional
    def unSetPrimaryImage(Long id) {

        Help help = helpService.getHelp(id)

        if (!help) {
            notFound()
            return
        }
        if (!helpService.removePrimaryImage(help)) {
            flash.message = "The file could not be removed"
            redirect(action: 'edit', params: [id: help.id])
            return
        }
        flash.message = "The file has been removed"
        redirect(action: 'edit', params: [id: help.id])
    }

    static String toDash(String camel) {
        //Example: "readySetGo" --> "ready-set-go"
        def joint = ~/[^A-Za-z0-9]|(?<=[a-z])(?=[A-Z])/
        def dashes = ~/-+/
        return camel?.trim().replaceAll(joint, "-").replaceAll(dashes, "-").toLowerCase()
    }
}
