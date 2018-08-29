package cev.blackFish

import grails.transaction.Transactional

@Transactional
class HelpService {

    Help getHelp(Long id) {

        return Help.get(id)
    }

    boolean save(Help help) {

        return !help.hasErrors() && help.save(flush: true)
    }

    boolean delete(Help help) {

        try {
            help.delete(flush: true)
            return true
        }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            log.error("exception ${e} occurred while attempting to delete a help")
            return false
        }
    }

    boolean removePrimaryImage(Help help) {

        help.setPrimaryImage(null)

        if (!save(help)) {
            log.error("help could not be saved in helpService.removePrimaryImage()")
            return false
        }
        return true
    }

    boolean addPrimaryImage(Help help, Long fileId) {

        File file = File.get(fileId)
        if (!file) {
            log.error("File not found in helpService.addPrimaryImage()")
            return false
        }

        help.setPrimaryImage(file)

        if (!save(help)) {
            log.error("Help could not be saved in helpService.addPrimaryImage()")
            return false
        }
        return true
    }
}
