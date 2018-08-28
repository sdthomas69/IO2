package cev.blackFish

import org.springframework.dao.DataIntegrityViolationException
import grails.core.GrailsApplication

class PermissionController {
    
    def securityService
	def permissionService
	GrailsApplication grailsApplication

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def updateUserPermissions() {

		def permission = Permission.get(params.permissionId)

		if(permission && params.permissionsString) {
			permission.setPermissionsString(params.permissionsString)
		}

		flash.message = "The permission has been updated"

		redirect(action:'edit', controller:'userAdmin', params:[id:params.id])
	}

	def getUserPermissions() {

		if(!params.id || !params.objectId || !params.object) render "a parameter is missing"

		else {

			def permission = securityService.findPermission(params.object, params.objectId, params.id)

			if(permission) render(template:'permission', model:[permission:permission, params:params])

			else render "permission not found"
		}
	}

	def findPermissionsByObject(String objectName, String objectId) {

		if(!objectId || !objectName) {

			flash.message = "A parameter was missing or malformed"
			redirect(action:"list")
			return
		}

		def permissionInstanceList = permissionService.findPermissionsByObject(objectName, objectId)

		render(
			view: 'list',
			model: [
					permissionInstanceList: permissionInstanceList,
					objectName: objectName,
					objectId: objectId
			]
		)
	}

	def index() {
		redirect(action: "list", params: params)
	}

	def list() {

		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[permissionInstanceList: Permission.list(params), permissionInstanceTotal: Permission.count()]
	}

	def create() {

		Permission permissionInstance = new Permission()

		if(params.objectId && params.selectedController) {

			params.actions = "*"

			permissionInstance.permissionsString = buildPermissionsString(params)
		}
		else permissionInstance = new Permission(params)

		[permissionInstance: permissionInstance, params:params]
	}

	def save() {

		def permissionInstance = new Permission(params)

		permissionInstance.properties = params

		if(params.actions && params.selectedController) {

			permissionInstance.permissionsString = buildPermissionsString(params)
		}

		if(!permissionInstance.save(flush: true)) {
			render(view: "create", model: [permissionInstance: permissionInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'permission.label', default: 'Permission'), permissionInstance.id])
		redirect(action: "edit", id: permissionInstance.id)
	}

	def show() {

		def permissionInstance = Permission.get(params.id)
		if (!permissionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'permission.label', default: 'Permission'), params.id])
			redirect(action: "list")
			return
		}

		[permissionInstance: permissionInstance]
	}

	def edit() {

		def permissionInstance = Permission.get(params.id)
		if (!permissionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'permission.label', default: 'Permission'), params.id])
			redirect(action: "list")
			return
		}

		[
			permissionInstance: permissionInstance,
			actions:showActions(permissionInstance.controller())
		]
	}

	def update() {

		def permissionInstance = Permission.get(params.id)

		if (!permissionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'permission.label', default: 'Permission'), params.id])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (permissionInstance.version > version) {
				permissionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'permission.label', default: 'Permission')] as Object[],
						"Another user has updated this Permission while you were editing")

				render(view: "edit", model: [permissionInstance: permissionInstance])
				return
			}
		}

		permissionInstance.properties = params

		if (params.actions && params.selectedController) {

			permissionInstance.permissionsString = ""
			permissionInstance.permissionsString = buildPermissionsString(params)
		}

		if (!permissionInstance.save(flush: true)) {

			render(view: "edit", model: [permissionInstance: permissionInstance])
			return
		}

		flash.message = "The permission has been updated"

		if(params.returnTo) {

			redirect(
					controller: permissionInstance.controller(),
					action: "edit",
					id: permissionInstance.controllerId()
			)
		}

		else redirect(action: "edit", id: permissionInstance.id)
	}

	def delete() {

		def permissionInstance = Permission.get(params.id)
		if (!permissionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'permission.label', default: 'Permission'), params.id])
			redirect(action: "list")
			return
		}

		try {
			permissionInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'permission.label', default: 'Permission'), params.id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'permission.label', default: 'Permission'), params.id])
			redirect(action: "show", id: params.id)
		}
	}

	def showActions(String selectedController) {

		selectedController = selectedController.capitalize()

		def actions = null

		def controller = grailsApplication.getArtefact("Controller", "cev.blackFish.${selectedController}Controller")

		if(controller)
			actions = controller.getActions().collect({it}).unique().sort()

		return actions
	}

	private buildPermissionsString(params) {

		String tokenSep = ","
		String levelSep = ":"
		String wildcard = "*"
		String objectId = setObjectId()

		StringBuilder perms = new StringBuilder()

		perms.append(params.selectedController)
		perms.append(levelSep)

		if(params.actions == wildcard) {
			perms.append(wildcard)
		}
		else {

			def actions = params.list('actions')

			int last = actions.size() -1

			actions.eachWithIndex { token, i ->
				perms.append token
				if (i != last) {
					perms.append tokenSep
				}
			}
		}
		perms.append(levelSep)

		if(params?.objectId) perms.append(params.objectId)

		else if(objectId) perms.append(objectId)

		return perms.toString()
	}

	private String setObjectId() {

		String oid = ""

		if(params.permissionsString) {
			String ps = params.permissionsString
			if(ps.lastIndexOf(":") != -1) {
				oid = ps.substring(ps.lastIndexOf(":") + 1)
			}
		}
		return oid
	}

	private getActions(String actions) {

		return actions.lastIndexOf(":") != -1 ? actions.lastIndexOf(":") : actions
	}
}
