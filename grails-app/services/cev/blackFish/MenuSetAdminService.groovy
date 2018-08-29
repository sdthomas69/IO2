package cev.blackFish

import grails.transaction.Transactional

@Transactional
class MenuSetAdminService {

    def storyAdminService

    MenuSet getMenuSet(Long id) {

        return MenuSet.get(id)
    }

    boolean save(MenuSet menuSet) {

        return !menuSet.hasErrors() && menuSet.save(flush: true)
    }

    boolean delete(MenuSet menuSet) {

        try {
            menuSet.delete(flush: true)
            return true
        }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            log.info("exception ${e} occurred while attempting to delete a menuSet")
            return false
        }
    }

    MenuSet findByTitle(String title) {

        MenuSet menuSet = MenuSet.findByTitle(title)
        return menuSet
    }

    boolean removeStory(Story story, MenuSet menuSet) {

        story.setMenuSet(null)
        menuSet.removeFromChildren(story)

        if (!save(menuSet) && !storyAdminService.save(story)) {
            log.info("MenuSet could not be saved in menuSetAdminService.removeStory()")
            return false
        }
        return true
    }
}
