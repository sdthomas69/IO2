package cev.blackFish

class Site {

    String title, styleSheet

    MenuSet main
    MenuSet bottom

    Story portal

    static constraints = {
        title(maxSize: 55, blank: false, unique: true)
        styleSheet(maxSize: 55, blank: false)
        portal(nullable: true)
    }
    static mapping = {
        id generator: 'sequence', params: [sequence: 'site_seq']
        datasources(['DEFAULT', 'lookup'])
        cache true
        main cache: true
        bottom cache: true
    }

    String toString() {
        return "${title}"
    }
}
