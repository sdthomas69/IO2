package io2

import grails.test.mixin.integration.Integration
import grails.transaction.*

import spock.lang.*
import geb.spock.*

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class HomeRestFunctionalSpec extends RestSpec {

    void "test json response"() {

        when: "The service URL is accessed"
        def response = rest.get("${baseUrl}/home/getSite") {
            accept('application/json')
        }

        then: "API call success"
        response.status == 200
        response.json.sites.uid == "ATAPL-71403-00002"
    }
}
