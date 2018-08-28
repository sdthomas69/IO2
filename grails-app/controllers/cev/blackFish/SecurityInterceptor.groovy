package cev.blackFish

import org.apache.shiro.SecurityUtils
//import grails.compiler.GrailsCompileStatic

//@GrailsCompileStatic
class SecurityInterceptor {

    def secureControllers = "menuSetAdmin|admin|storyAdmin|fileAdmin|tagAdmin|role|userAdmin|permission|site|help"

    SecurityInterceptor() {
        match(controller:"(${secureControllers})", action:"*")
    }

    boolean before() {

        accessControl {
            SecurityUtils.subject.isPermitted("${controllerName}:${actionName}${params.id ? ':' + params.id : ''}")
        }
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
