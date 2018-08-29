package cev.blackFish

import org.hibernate.FetchMode as FM

class TagController extends BaseController {

    def tagService

    def getTagCloud(String template) {

        if (!template) {
            render "the template parameter is missing"
            return
        }

        def tags = tagService.createCloudMap()

        render(template: template, model: [tags: tags, params: params])
    }

    def show(Long id) {

        if (!id) {
            redirect(action: "404", controller: "errors")
            return
        }

        Tag tagInstance = tagService.getTag(id)

        if (!tagInstance) {
            redirect(action: "404", controller: "errors")
            return
        }

        [tagInstance: tagInstance, hasVideo: tagInstance.hasVideos(), params: params]
    }

    def title(String selectedTag) {

        if (!selectedTag) {
            redirect(action: "404", controller: "errors")
            return
        }

        Tag tagInstance = tagService.findByTitle(selectedTag)

        if (!tagInstance) {
            redirect(action: "404", controller: "errors")
            return
        }

        render(view: 'show',
                model: [tagInstance: tagInstance,
                        hasVideo   : tagInstance.hasVideos(),
                        params     : params
                ]
        )
    }
}
