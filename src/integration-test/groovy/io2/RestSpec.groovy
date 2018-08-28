package io2

import spock.lang.Specification
import grails.plugins.rest.client.RestBuilder

abstract class RestSpec extends Specification {

    String baseUrl
    RestBuilder rest

    def setup() {
        baseUrl = "http://localhost:${serverPort}"
        rest = new RestBuilder()
    }

}
