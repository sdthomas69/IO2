package io2

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import grails.util.Environment
import grails.util.Holders
import org.springframework.boot.CommandLineRunner

class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    /*@Override
    void run(final String... args) throws Exception {
        println "Running in ${Environment.current.name}"

        // Get configuration from Holders.
        def config = Holders.getConfig()
        def locations = config.grails.config.locations
        locations.each {
            String configFileName = it.split("file:")[1]
            File configFile = new File(configFileName)
            if (configFile.exists()) {
                config.merge(new ConfigSlurper(Environment.current.name).parse(configFile.text))
            }
        }
    }*/
}