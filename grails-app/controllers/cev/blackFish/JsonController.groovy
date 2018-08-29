package cev.blackFish

import org.codehaus.groovy.grails.commons.*

import grails.util.Holders

class JsonController extends BaseController {

    def index = {}

    /**
     *
     */
    def videos(Long id, String domain) {

        if (!id || !domain) {
            render "a parameter is missing"
        } else {

            def thisDomain = getDomainClassInstance(domain, id) ?: null

            //def files = findType(domain?.files, "Quicktime")

            def files = null

            if (domain == 'story' && thisDomain) files = storyQuery(thisDomain)

            if (domain == 'tag' && thisDomain) files = tagQuery(thisDomain)

            if (domain == 'user' && thisDomain) files = userQuery(thisDomain)

            if (files) render(view: 'video', model: [files: files, iOS: isIOS()], contentType: "text/json", encoding: "UTF-8")

            else render ""
        }
    }

    /**
     *
     * @param domain
     * @return
     */
    private storyQuery(domain) {

        def files = File.lookup.withCriteria {
            eq("type", "Quicktime")
            stories {
                eq("id", domain.id)
            }
            setReadOnly true
        }
    }

    /**
     *
     * @param domain
     * @return
     */
    private tagQuery(domain) {

        def files = File.lookup.withCriteria {
            eq("type", "Quicktime")
            tags {
                eq("id", domain.id)
            }
            setReadOnly true
        }
    }

    /**
     *
     * @param domain
     * @return
     */
    private userQuery(domain) {

        def files = File.lookup.withCriteria {
            eq("type", "Quicktime")
            eq("owner", domain)
            setReadOnly true
        }
    }

    /**
     *
     * @param name
     * @param id
     * @return
     */
    private getDomainClassInstance(name, id) {

        String domain = "cev.blackFish.${name.capitalize()}"

        def domainGrailsClass = Holders.getGrailsApplication().getArtefact("Domain", domain).getClazz()

        return domainGrailsClass.get(id)
    }

    /**
     *
     * @param files
     * @param type
     * @return
     */
    private findType(files, type) {

        def filesCopy = []

        files.each {
            if (it.type == type) {
                filesCopy.addAll(it)
            }
        }
        return filesCopy
    }
}
