package cev.blackFish

class ErrorsController {

    def groovyPagesTemplateEngine

    def error403 = {}

    def error404 = {
        def contextPath = request.contextPath
        def path = request.forwardURI.replaceFirst("^$contextPath", "")
        def engine = groovyPagesTemplateEngine

        /*if (engine.getResourceForUri("${path}.gsp").exists() ||
			engine.getResourceForUri("/grails-app/views/${path}.gsp").exists() || 
			engine.getResourceForUri("WEB-INF/grails-app/views/${path}.gsp").exists()) {
            render(view: path, model: [])
        }      
        []*/
    }

    def error500 = {}
}
