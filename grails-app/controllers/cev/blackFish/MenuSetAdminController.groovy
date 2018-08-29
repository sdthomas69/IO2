package cev.blackFish

import grails.transaction.Transactional

//import grails.plugin.cache.CacheEvict
//import grails.plugin.cache.CachePut

//@Transactional
class MenuSetAdminController extends BaseController {

    def menuSetAdminService

    //@Transactional(readOnly = true)
    def list() {

        setParams(params)
        [menuSetInstanceList: MenuSet.list(params), menuSetInstanceTotal: MenuSet.count()]
    }

    def show(Long id) {

        MenuSet menuSetInstance = menuSetAdminService.getMenuSet(id)

        if (!menuSetInstance) {
            flash.message = "MenuSet not found"
            redirect(action: 'list')
            return
        }

        [menuSetInstance: menuSetInstance]
    }

    //@CacheEvict(value='menuSetCache, allEntries=true')
    def delete(Long id) {

        MenuSet menuSetInstance = menuSetAdminService.getMenuSet(id)

        if (!menuSetInstance) {
            flash.message = "MenuSet not found"
            redirect(action: 'list')
            return
        }

        if (!menuSetAdminService.delete(menuSetInstance)) {
            flash.message = "The menuSet could not be deleted"
            redirect(action: 'edit', id: params.id)
            return
        }

        flash.message = "The menuSet has been deleted"
        redirect(action: 'list')
    }

    def edit(Long id) {

        MenuSet menuSetInstance = menuSetAdminService.getMenuSet(id)

        if (!menuSetInstance) {
            flash.message = "MenuSet not found with id ${params.id}"
            redirect(action: 'list')
            return
        }

        [menuSetInstance: menuSetInstance]
    }

    //@CachePut(value='menuSetCache')
    def update(Long id) {

        MenuSet menuSetInstance = menuSetAdminService.getMenuSet(id)

        if (!menuSetInstance) {
            flash.message = "MenuSet not found with id ${params.id}"
            redirect(action: 'list')
            return
        }

        if (checkVersion(menuSetInstance)) {
            optimisticLock(menuSetInstance)
            return
        }

        menuSetInstance.properties = params

        if (!menuSetAdminService.save(menuSetInstance)) {
            render(view: 'edit', model: [menuSetInstance: menuSetInstance])
            return
        }

        flash.message = "The MenuSet has been updated"
        redirect(action: 'edit', id: menuSetInstance.id)
    }

    def create() {

        MenuSet menuSetInstance = new MenuSet()

        menuSetInstance.properties = params

        return ['menuSetInstance': menuSetInstance]
    }

    //@CachePut(value='menuSetCache')
    def save() {

        MenuSet menuSetInstance = null

        if (params.title) menuSetInstance = menuSetAdminService.findByTitle(params.title)

        if (!menuSetInstance) menuSetInstance = new MenuSet(params)

        if (!menuSetAdminService.save(menuSetInstance)) {

            render(view: 'create', model: [menuSetInstance: menuSetInstance])
            return
        }

        flash.message = "The menuSet ${menuSetInstance} has been created"
        redirect(action: 'edit', id: menuSetInstance.id)
    }

    //@CachePut(value='menuSetCache')
    def removeStory() {

        Story story = Story.get(params?.story?.id)

        MenuSet menuSet = MenuSet.get(params.id)

        if (!menuSet || !story) {
            flash.message = "Either a menuSet or story id is missing"
            redirect(action: 'list')
            return
        }

        if (checkVersion(menuSet)) {
            optimisticLock(menuSet)
            return
        }

        if (!menuSetAdminService.removeStory(story, menuSet)) {
            flash.message = "The story could not be removed"
            redirect(action: 'removeStory', id: menuSet.id)
            return
        }

        flash.message = "The story '${story}' has been removed from '${menuSet}'"
        redirect(action: 'edit', params: [id: menuSet.id])
    }

}
