package cev.blackFish

import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authz.permission.WildcardPermission

class ShiroNPWRealm {
    
    /*static authTokenClass = cev.blackFish.UsernameToken

    def credentialMatcher
    def shiroPermissionResolver
	def userService

    def authenticate(authToken) {
        log.info "Attempting to authenticate ${authToken.username} in NPW realm..."
        def username = authToken.username

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.")
        }

        // Get the user with the given username. If the user is not
        // found, then they don't have an account and we throw an
        // exception.
        User user = User.lookup.findByUsernameAndConfirmed(username, true, [cache:true, readOnly:true])
		
        if (!user) {
            throw new UnknownAccountException("No account found for user [${username}]")
        }

        log.info "Found user '${user.username}' in DB"
		println "Found user '${user.username}' in the DB"

		def account = new SimpleAccount(username, "", "ShiroNPWRealm")
		
		println "${user.username} has been authenticated in the ShiroNPWRealm"

        return account
    }

    def hasRole(principal, roleName) {
		
		User user = userService.findByUsername(principal)
		
		if(!user) {
			return false
		}
		
		def roles = UserRoleAssociation.findRolesByUser(user)

        return roles*.role.name.contains(roleName)
	}
	
	def hasAllRoles(principal, roles) {
		
		User user = userService.findByUsername(principal)
		
		if(!user) {
			return false
		}
		
		def r = UserRoleAssociation.findUsersByRoles(roles)

        return r.size() == roles.size()
	}

    def isPermitted(principal, requiredPermission) {
        // Does the user have the given permission directly associated
        // with himself?
        //
        // First find all the permissions that the user has that match
        // the required permission's type and project code.
		
		User user = userService.findByUsername(principal)
		
		if(!user) {
			return false
		}
		
		def permissions = user?.permissions*.permissionsString
		
		def rolePermssions = user?.roles*.permissions
		
		permissions.addAll(rolePermssions.flatten())

        // Try each of the permissions found and see whether any of
        // them confer the required permission.
        def retval = permissions?.find { permString ->
            // Create a real permission instance from the database
            // permission.
            def perm = shiroPermissionResolver.resolvePermission(permString)

            // Now check whether this permission implies the required
            // one.
            if (perm.implies(requiredPermission)) {
                // User has the permission!
                return true
            }
            else {
                return false
            }
        }

        if (retval != null) {
            // Found a matching permission!
            return true
        }
    }*/
}
