package cev.blackFish

//import grails.transaction.Transactional
import grails.plugins.rest.client.RestBuilder

//@Transactional
class RestService {

    def getURL(String url) {

        def rest = new RestBuilder()
        def resp = null

        try {
            resp = rest.get(url)
        } catch (Exception e) {
            e.printStackTrace()
            log.error(e)
        }
        return resp
    }
}
