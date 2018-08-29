package cev.blackFish

class Permission {

    String permissionsString

    User user

    static constraints = {
        permissionsString(nullable: false, blank: false)
        user(nullable: false)
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'permission_seq']
        cache true
        permissionsString cache: true
        user cache: true
        datasources(['DEFAULT', 'lookup'])
    }

    static transients = ['controller', 'controllerId']

    String controller() {

        this.permissionsString?.substring(0, permissionsString?.indexOf(":")) ?: ""
    }

    String controllerId() {

        this.permissionsString?.lastIndexOf(":")?.replaceAll(':', '') ?: ""
    }

    List actions() {

        def a = null

        if (this.permissionsString) {

            a = this.permissionsString?.split(':')[1]?.split(',')?.toList()
        } else a = []
    }
}
