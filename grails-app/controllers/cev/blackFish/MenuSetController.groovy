package cev.blackFish

class MenuSetController extends BaseController {

    def menuSetService

    /**
     * Looks up  a menuSet by its title and returns a collection of its children.  
     * If the menuSet doesn't have children an empty string is rendered.
     * @param title
     * @param template
     * @return children
     */
    def getMenuSetByTitle(String title, String template) {

        if (!title || !template) {
            response.status = 404
            //render ""
            return
        }

        MenuSet menuSet = menuSetService.getMenuSetByTitle(title)

        if (!menuSet?.children) {
            response.status = 404
            //render ""
            return
        }

        Set children = menuSet.children.sort()

        render(
                template: template,
                collection: children,
                var: "thisMenu"
        )
    }
}
