package cev.blackFish

import grails.test.mixin.*
import spock.lang.*

@TestFor(SiteController)
@Mock([Site, MenuSet])
class SiteControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params["title"] = 'test'
        params["stylesheet"] = 'test.css'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.siteInstanceList
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.siteInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def site = new Site()
        site.validate()
        controller.save(site)

        then: "The create view is rendered again with the correct model"
        model.siteInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        site = new Site(params)
        addMenus(site)
        controller.save(site)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/site/show'
        controller.flash.message != null
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def site = new Site(params)
        controller.show(site)

        then: "A model is populated containing the domain instance"
        model.siteInstance == site
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def site = new Site(params)
        controller.edit(site)

        then: "A model is populated containing the domain instance"
        model.siteInstance == site
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/site/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def site = new Site()
        site.validate()
        controller.update(site)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.siteInstance == site

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        site = new Site(params)
        addMenus(site)
        controller.update(site)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == "/site/show"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/site/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        def site = new Site()
        populateValidParams(params)
        site = new Site(params)
        addMenus(site)

        then: "It exists"
        site != null

        when: "The domain instance is passed to the delete action"
        controller.delete(site)

        then: "The instance is deleted"
        site.count() == 0
        response.redirectedUrl == '/site/index'
        flash.message != null
    }

    private Site addMenus(Site site) {

        def menu1 = new MenuSet(title: 'Menu1')
        def menu2 = new MenuSet(title: 'Menu2')
        site.setMain(menu1)
        site.setBottom(menu2)
    }
}
