package cev.blackFish

import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authz.permission.WildcardPermission
import org.apache.commons.codec.binary.Base64

class ShiroDbRealm {

    static authTokenClass = org.apache.shiro.authc.UsernamePasswordToken

    def credentialMatcher
    def shiroPermissionResolver
    def userService

    def authenticate(authToken) {

        log.info "Attempting to authenticate ${authToken.username} in DB realm..."
        def username = authToken.username

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.")
        }

        // Get the user with the given username. If the user is not
        // found, then they don't have an account and we throw an
        // exception.

        //def user = User.findByUsername(username, [cache:true, readOnly:true])
        User user = userService.findByUsernameAndConfirmed(username)
        //User user = User.lookup.findByUsernameAndConfirmed(username, true)
        if (!user) {
            throw new UnknownAccountException("No account found for user [${username}]")
        }

        log.info "Found user '${user.username}' in DB"

        // Now check the user's password against the hashed value stored
        // in the database.

        byte[] salt = Base64.decodeBase64(user.passwordSalt.getBytes())

        def account = new SimpleAccount(username,
                user.passwordHash,
                new org.apache.shiro.util.SimpleByteSource(salt),
                "ShiroDbRealm"
        )

        if (!credentialMatcher.doCredentialsMatch(authToken, account)) {
            log.info "Invalid password (DB realm)"
            println "Invalid password (DB realm)"
            throw new IncorrectCredentialsException("Invalid password for user '${username}'")
        }

        return account
    }

    def hasRole(principal, roleName) {

        //User user = userService.findByUsername(principal)
        User user = userService.findByUsernameAndConfirmed(principal)

        if(!user) {
            return false
        }

        def roles = UserRoleAssociation.findRolesByUser(user)

        /*if(!roles) {
            return false
        }*/

        return roles*.role.name.contains(roleName)
    }

    def hasAllRoles(principal, roles) {

        //User user = userService.findByUsername(principal)
        User user = userService.findByUsernameAndConfirmed(principal)

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
        //def user = User.findByUsername(principal, [cache:true, readOnly:true])

        //User user = userService.findByUsername(principal)
        User user = userService.findByUsernameAndConfirmed(principal)

        if(!user) {
            return false
        }

        def permissions = user?.permissions*.permissionsString

        //println "user perms are ${permissions}"

        def rolePermssions = user?.roles*.permissions

        //println "role perms are ${rolePermssions}"

        permissions.addAll(rolePermssions.flatten())

        //println "all permissions are ${permissions}"

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
    }
}
