package cev.blackFish

class User implements Serializable {
	
    String username, 
	passwordHash, 
	passwordSalt,
	password, 
	confirm, 
	firstName, 
	lastName, 
	name, 
	email, 
	description, 
	nonce, 
	department, 
	urlTitle,
	position,
	url

    File primaryImage
    
    Site site
    
    Date date = new Date()
	
	Boolean confirmed = false
	Boolean hasPrimaryImage = false
	Boolean view = true

    Set<Role> roles
	//Set<Tag> tags
	
	def searchableProps = ['firstName', 'lastName', 'description', 'department', 'position']
    
    //static hasMany = [tags:Tag]
    
    static transients = ['password', 'confirm', 'searchableProps', 'permissions', 'stories', 'files', 'roles']

    static constraints = {
        username(nullable:false, blank:false, unique:true, maxSize:20)
        firstName(blank:false, maxSize:50)
        lastName(blank:false, maxSize:50)
		description(nullable:true, maxSize:255)
		department(nullable:true, maxSize:255)
		position(nullable:true, maxSize:255)
		urlTitle(maxSize:255)
        email(nullable:false, blank:false, email:true, unique:true, maxSize:255)
		url(nullable:true, url:true)
        primaryImage(nullable:true)
		name(nullable:true, maxSiz:255)
        site(nullable:true, maxSize:255)
		nonce(nullable:true, maxSize:255)
        confirm(bindable:true, nullable:true)
		passwordHash(maxSize:255, nullable:false)
        passwordSalt(maxSize:255)
        password(bindable:true, nullable:true, size:7..20, validator:{ val, obj ->
            
            if(!val) return true
            
            if (val != obj.confirm) return 'user.password.error.dontmatch'

			if(obj.username.equals(val)) return 'user.password.error.unique'
			
			//if(obj.password.equals(val)) return 'user.password.error.same'
            
            if (!val.matches('^.*\\p{Alpha}.*$') || !val.matches('^.*\\p{Digit}.*$') || !val.matches('^.*[!@#$%^&].*$')) {
                return 'user.password.error.username'
            }
        })
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'io2_user_seq']
        cache true
		table "io2_user"
        datasources(['DEFAULT', 'lookup'])
    }
    
    String toString() { 
        return "${firstName}" + " ${lastName}" 
    }
    
    boolean equals(def o) {
        if (is(o)) return true
        if (o instanceof User) {
            return username == o.username
        }
        return false
    }
    
    Set<Permission> getPermissions() {
        Permission.findAllByUser(this)
    }
    
    Set<Story> getStories() {
        Story.findAllByAuthor(this)
    }
    
    Set<Story> getFiles() {
        File.findAllByAuthor(this)
    }
	
	Set<Story> getUserStories() {
		UserStoryAssociation.findAllByUser(this).collect { it.story } as Set
	}

    Set<Role> getRoles() {
        UserRoleAssociation.findAllByUser(this, [cache:true, readOnly:true]).collect { it.role } as Set
    }

    static void addRoles(List roles, User user) {

        roles.each {
            Role role = Role.get(it)
            if(role) {
                if(!UserRoleAssociation.exists(user, role)) {
                    UserRoleAssociation.create(user, role)
                }
            }
        }
    }

    static void removeRoles(List roles, User user) {

        roles.each {
            Role role = Role.get(it)
            if(role) UserRoleAssociation.remove(user, role)
        }
    }
    
    void deleteAllAssociations() {
        
        executeUpdate("DELETE FROM Permission WHERE user=:user", ['user': this])
		
		UserStoryAssociation.removeAllByUser(this)
        
        if(this.roles) {
            def roles = []
            roles.addAll this.roles
            roles.each {
                this.removeFromRoles(it)
            }
        }
        
        def stories = Story.findAllByAuthor(this)
		
		if(stories) {
			
			for(story in stories) {
				story.setAuthor(null)
			}
		}
        
        def files = File.findAllByAuthor(this)
		
		if(files) {
			
			for(file in files) {
				file.setAuthor(null)
			}
		}
		
		if(this.tags) {
			def tags = []
			tags.addAll this.tags
			tags.each {
				this.removeFromTags(it)
			}
		}
        
        this.setPrimaryImage(null)
    }
}
