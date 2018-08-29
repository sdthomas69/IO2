package cev.blackFish

import grails.util.Holders

class UtilsTagLib {

    static namespace = "blackFish"

    def config = Holders.config
    def securityService

    /**
     *
     */
    def getHelp = { attrs, body ->

        if (attrs.controller && attrs.action && attrs.template) {

            def controller = attrs.controller

            def action = attrs.action

            Help help = Help.findBySelectedControllerAndActivity(controller, action)

            out << render(template: attrs.template, model: [help: help, selectedController: controller, activity: action])
        }
    }

    /**
     *
     */
    def shorten = { attrs, body ->
        def item = attrs.item
        def endpoint = attrs.endpoint
        if (item && endpoint) out << item.substring(0, item.lastIndexOf(endpoint))
    }

    /**
     * Returns a block of text whose size can be tailored.
     * The text and size attributes are required.
     * If the size (character count) of the text is less than the size attribute, all of the text is returned.
     * If the size of the text is greater than the size attribute, a range is applied to the text using the size attribute.
     * If a url attibute is supplied, it is concatenated to the ranged text.
     */
    def shortenText = { attrs, body ->

        def text = attrs?.text
        Integer thisSize = attrs?.size?.toInteger()

        if (text && thisSize) {

            def shortened = ""

            if (attrs.url) {

                def url = "<a href='${attrs.url}'> ...</a>"

                shortened = text.size() > thisSize ? text[0..thisSize] + url : text
            } else shortened = text.size() > thisSize ? text[0..thisSize] : text

            out << shortened
        }
    }

    def breadcrumbs = { attrs, body ->

    }

    /**
     *
     */
    def getAllMenus = { attrs, body ->

        if (attrs.template && attrs.menuSet) {

            def menuSet = MenuSet.findByTitle(attrs.menuSet, [cach: true])

        }
    }

    /**
     * This Closure finds a particular MenuSet by name and then renders the MenuSet's sorted menus to the specified template.
     * The results are cached.
     */
    def getMenuSet = { attrs, body ->

        if (attrs.title && attrs.template) {
            //def menuSet = menuService.getMenuSetByTitle(attrs.title)
            //def menuSet = MenuSet.findByTitle(attrs.title, [cache:true])
            def menuSet = MenuSet.findByTitle(attrs.title, [fetch: [children: "eager"]])

            if (menuSet) {

                menuSet.children?.sort()

                out << render(template: attrs.template, collection: menuSet.children?.sort(), var: "thisStory")
            }
        }
    }

    /**
     *
     */
    def getSites = { attrs, body ->

        def sites = Site.list([sort: "date", order: "desc", cache: true])
        if (attrs.template && sites) {
            out << render(template: attrs.template, collection: sites, var: "site")
        }
    }

    /**
     * Given a Story, this Closure finds a specified number of stories with the same category and renders to the 
     * specified template
     */
    def relatedCategories = { attrs, body ->

        if (attrs.template && attrs.story && attrs.max) {

            def max = attrs.max?.toInteger() ?: 12

            def categorizedStories = Story.createCriteria().list {
                eq("published", true)
                eq("category", attrs.story?.category)
                ne("id", attrs.story.id)
                order("date", "desc")
                maxResults(max)
            }
            if (categorizedStories) {
                out << render(template: attrs.template, model: [categorizedStories: categorizedStories])
            }
        }
    }

    /**
     * Creates an image tag with Foundation media queries.
     * A default attribute is required.
     * Options are medium, large, and retina.
     * Optional <img> attributes are width, class, alt and title
     */
    def interchange = { attrs, body ->

        def writer = getOut()

        if (attrs.default) {

            writer << "<img data-interchange='[${attrs.default}, (default)]"

            if (attrs.medium) writer << ", [${attrs.medium}, (medium)]"

            if (attrs.large) writer << ", [${attrs.large}, (large)]"

            if (attrs.retina) writer << ", [${attrs.retina}, (retina)]"

            writer << "' "

            if (attrs.width) writer << "width='${attrs.width}' "

            if (attrs.imageClass) writer << "class='${attrs.imageClass}' "

            if (attrs.title) writer << "title='${attrs.title}' "

            if (attrs.alt) writer << "alt='${attrs.alt}'"

            writer << " />"

            writer << "<noscript><img src='${attrs.default}'></noscript>"

        } else writer << ""
    }

    /**
     *
     */
    def wflowChange = { attrs, body ->

        def writer = getOut()

        if (attrs.sizes && attrs.src) {

            writer << "<img "

            writer << "sizes='${attrs.sizes}' "

            writer << "src='${attrs.src}' "

            writer << "srcset='"

            if (attrs.small) writer << "${attrs.small} ${config.smallSlideWidth}w, "

            if (attrs.medium) writer << "${attrs.medium} ${config.slideWidth}w, "

            if (attrs.large) writer << "${attrs.large} ${config.mediumSlideWidth}w, "

            if (attrs.retina) writer << "${attrs.retina} ${config.xSlideWidth}w"

            writer << "' "

            if (attrs.width) writer << "width='${attrs.width}' "

            if (attrs.imageClass) writer << "class='${attrs.imageClass}' "

            if (attrs.title) writer << "title='${attrs.title}' "

            if (attrs.alt) writer << "alt='${attrs.alt}'"

            writer << " />"

        } else writer << ""
    }

    def setLink = { attrs, body ->

        if (attrs.object && attrs.name) {

            def object = attrs.object
            def name = attrs.name

            // Use the urlTitle

            def link = g.createLink(controller: name, action: 'title', params: ['title': object.urlTitle])

            // Story is a link so use its dataURL field

            if (name == "story" && object.isLink && object.dataURL) {

                link = g.createLink(uri: object.dataURL)
            }

            // Not logged in and writing html files so use the uri for all links

            if (!securityService.isLoggedIn() && config.writeHtmlFiles && object.uri) {

                link = g.createLink(uri: object.uri)
            }
            out << link
        }
    }

    /**
     *
     */
    def menuLink = { attrs, body ->

        def writer = getOut()
        def elementId = attrs.remove('elementId')
        def linkAttrs
        if (attrs.params instanceof Map && attrs.params.containsKey('attrs')) {
            linkAttrs = attrs.params.remove('attrs').clone()
        } else {
            linkAttrs = [:]
        }
        if (attrs.params instanceof Map && attrs.params.containsKey('title')) {
            def title = attrs.params.title.encodeAsURL()
            attrs.params.remove('title')
            attrs.params.put('title', title)
        }
        writer << '<a href=\"'
        writer << createLink(attrs).encodeAsHTML()
        writer << '"'
        if (elementId) {
            writer << " id=\""
            writer << elementId
            writer << "\""
        }
        linkAttrs << attrs
        linkAttrs.each { k, v ->
            writer << ' '
            writer << k
            writer << '='
            writer << '"'
            writer << v.encodeAsHTML()
            writer << '"'
        }
        writer << '>'
        writer << body()
        writer << '</a>'
    }

    /**
     * This Closure iterates through a Map of Tags and builds objects that can rendered as links into a tag cloud.
     * The more Tags in the Map, the larger the font size
     */
    def buildTagLink = { attrs, body ->

        def template = attrs.template

        attrs?.values.each { entry ->

            int fontSize = Integer.parseInt(entry?.value?.toString()) + Integer.parseInt(attrs?.minSize?.toString())

            if (attrs?.maxSize) {
                if (fontSize > attrs.maxSize.toInteger()) {
                    fontSize = attrs.maxSize.toInteger()
                }
            }
            if (template) out << render(template: template, model: [attrs: attrs, entry: entry, fontSize: fontSize, params: params])
        }
    }

    def tagWrapper = { attrs, body ->

        def tag = attrs.tag
        def className = attrs.className
        def iterations = attrs.iterations?.toInteger()
        def counter = attrs.counter?.toInteger()
        def total = attrs.total?.toInteger()

        if (tag && className && total) {

            Boolean first = (counter % iterations) == 0
            Boolean last = (counter % iterations) == (iterations - 1)

            //println("${counter} % ${iterations} == " + counter % iterations)

            def writer = getOut()

            if (first) {
                writer << "<${tag} class=\"${className}\">"
                writer << body()
            } else if (last) {
                writer << body()
                writer << "</${tag}>"
            } else {
                writer << body()
            }
            if (counter == (total - 1)) {
                writer << "</${tag}>"
            }
        }
    }
}
