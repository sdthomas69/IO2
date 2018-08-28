package io2

class UrlMappings {

    static mappings = {

        /* File Mappings ****************************************/

        "/presentations"(controller:"file", action:"list")

        "/file/$title?" {
            controller = "file"
            action = "title"
            params = "$title"
        }

        "/file/search"(controller:"file", action:"search")

        "/file/list" (controller:"file", action:"list")

        "/remoteFileList" {
            controller = "file"
            action = "remoteFileList"
        }

        "/file/pano/$id?" {
            controller = "file"
            action = "pano"
        }

        /* Story Mappings ****************************************/

        "/news" (controller:"story", action:"news")

        "/people" (controller:"story", action:"people")

        "/directory"(controller:"user", action:"people")

        "/story/$title?" {
            controller = "story"
            action = "title"
            params = "$title"
        }

        "/story/map/$title?" {
            controller = "story"
            action = "map"
            params = "$title"
        }

        "/story/search"(controller:"story", action:"search")

        "/story/list" (controller:"story", action:"list")

        "/remoteStoryList" {
            controller = "story"
            action = "remoteStoryList"
        }

        /* Tag Mappings ****************************************/

        "/tag/$selectedTag?" {
            controller = "tag"
            action = "title"
            params = "$selectedTag"
        }

        "/tag/list" (controller:"tag", action:"list")

        /* User mappings ****************************************/

        "/person/$url?" {
            controller = "user"
            action = "home"
            params = "$url"
        }

        "/login" (controller:"userAdmin", action:"login")

        /* Misc. Mappings ****************************************/

        "/" {
            controller = "home"
            action = "index"
            site = "main"
            name = "Home"
        }


        //"/signUp" (controller:user, action:create)

        "/json/videos/$domain?/$id?" {
            controller = "json"
            action = "videos"
        }

        "/admin" (controller:"admin", action:"home")

        // Added by Errors plugin
        "403"(controller: "errors", action: "error403")
        "404"(controller: "errors", action: "error404")
        "500"(controller: "errors", action: "error500")

        //"/$controller/$action?/$id?(.$format)?"{
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        //"/"(view:"/index")
        //"500"(view:'/error')
        //"404"(view:'/notFound')
    }
}
