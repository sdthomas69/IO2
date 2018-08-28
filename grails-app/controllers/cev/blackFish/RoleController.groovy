package cev.blackFish

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //respond Role.list(params), model:[roleInstanceCount: Role.count()]
        [roleInstanceList: Role.list(params), roleInstanceCount: Role.count()]
    }

    def show(Role roleInstance) {
        println "role is ${roleInstance}"
        //respond roleInstance
        [roleInstance:roleInstance]
    }

    def create() {
        //respond new Role(params)
        Role roleInstance = new Role(params)
        [roleInstance: roleInstance]
    }

    @Transactional
    def save(Role roleInstance) {
        if (roleInstance == null) {
            notFound()
            return
        }

        if (roleInstance.hasErrors()) {
            respond roleInstance.errors, view:'create'
            return
        }

        roleInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])
                redirect roleInstance
            }
            '*' { respond roleInstance, [status: CREATED] }
        }
    }

    def edit(Role roleInstance) {
        //respond roleInstance
        [roleInstance: roleInstance]
    }

    @Transactional
    def update(Role roleInstance) {

        if (roleInstance == null) {
            notFound()
            return
        }

        if (roleInstance.hasErrors()) {
            respond roleInstance.errors, view:'edit'
            return
        }

        roleInstance.save flush:true

        if(params.list('permissionsToRemove')) {

            Boolean foundOne = false
            List existingPerms = []
            existingPerms.addAll(roleInstance.permissions)

            for(ptr in params.list('permissionsToRemove')) {
                for(perm in existingPerms) {
                    if(perm == ptr) {
                        roleInstance.removeFromPermissions(ptr)
                        foundOne = true
                    }
                }
            }
            if(foundOne) {
                roleInstance.save(flush:true)
            }
        }

        if(params.newPermission) {
            roleInstance.addToPermissions(params.newPermission)
            roleInstance.save(flush:true)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Role.label', default: 'Role'), roleInstance.id])
                redirect roleInstance
            }
            '*'{ respond roleInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Role roleInstance) {

        if (roleInstance == null) {
            notFound()
            return
        }

        roleInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Role.label', default: 'Role'), roleInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
