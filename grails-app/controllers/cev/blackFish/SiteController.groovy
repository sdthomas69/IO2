package cev.blackFish

import static org.springframework.http.HttpStatus.*

import grails.transaction.Transactional

//import grails.plugin.cache.CacheEvict
//import grails.plugin.cache.CachePut

@Transactional(readOnly = true)
class SiteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Site.list(params), model: [siteInstanceCount: Site.count()]
    }

    def show(Site siteInstance) {
        respond siteInstance
    }

    def create() {
        respond new Site(params)
    }

    //@CachePut(value='siteCache')
    @Transactional
    def save(Site siteInstance) {
        if (siteInstance == null) {
            notFound()
            return
        }

        if (siteInstance.hasErrors()) {
            respond siteInstance.errors, view: 'create'
            return
        }

        siteInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(
                        code: 'default.created.message',
                        args: [message(code: 'siteInstance.label', default: 'Site'), siteInstance.id]
                )
                //redirect siteInstance
                redirect(action: "show", id: siteInstance.id)
            }
            '*' { respond siteInstance, [status: CREATED] }
        }
    }

    def edit(Site siteInstance) {
        respond siteInstance
    }

    //@CachePut(value='siteCache')
    @Transactional
    def update(Site siteInstance) {
        if (siteInstance == null) {
            notFound()
            return
        }

        if (siteInstance.hasErrors()) {
            respond siteInstance.errors, view: 'edit'
            return
        }

        siteInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(
                        code: 'default.updated.message',
                        args: [message(code: 'Site.label', default: 'Site'), siteInstance.id]
                )
                //redirect siteInstance
                redirect(action: "show", id: siteInstance.id)
            }
            '*' { respond siteInstance, [status: OK] }
        }
    }

    //@CacheEvict(value='siteCache')
    @Transactional
    def delete(Site siteInstance) {

        if (siteInstance == null) {
            notFound()
            return
        }

        siteInstance.delete flush: true

        request.withFormat {
            form {
                flash.message = message(
                        code: 'default.deleted.message',
                        args: [message(code: 'Site.label', default: 'Site'), siteInstance.id]
                )
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(
                        code: 'default.not.found.message',
                        args: [message(code: 'siteInstance.label', default: 'Site'), params.id]
                )
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
