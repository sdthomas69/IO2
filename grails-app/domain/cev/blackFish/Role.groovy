package cev.blackFish

class Role {
    
    String name
	
	Set<User> users

    static hasMany = [permissions: String ]
    
    //static belongsTo = User

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
    
    static mapping = {
        id generator:'sequence', params:[sequence:'role_seq']
        cache true
		users cache:true
        permissions cache:true
        datasources(['DEFAULT', 'lookup'])
    }

    static transients = ['users']

    String toString() {
        return "${name}"
    }

    Set<User> getUsers() {
        UserRoleAssociation.findAllByRole(this).collect { it.user } as Set
    }
}
