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
class HomeFunctionalSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "test the home page title"() {
        when: "The home page is visited"
        go '/'

        then: "The title is correct"
        title == "Interactive Oceans"
    }
}
