package cev.blackFish

import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.RandomNumberGenerator
import org.apache.shiro.crypto.SecureRandomNumberGenerator
import org.apache.shiro.crypto.hash.Sha512Hash

//import org.apache.shiro.authc.AuthenticationException
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Qualifier
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SecurityService {

    //def shiroSecurityManager

    //boolean transactional = false
    //@Autowired
    //@Qualifier('lookup')
    //def lookup


    public User getUser() {
        String subject = SecurityUtils.getSubject()?.principal
        //User currentUser = User.findByUsername(subject)
        User currentUser = User.find { username == subject }
        return currentUser
    }

    public boolean hasRole(String role) {
        SecurityUtils.subject.hasRole(role)
    }

    public boolean isLoggedIn() {
        SecurityUtils.subject.isAuthenticated()
    }

    public void logout() {
        SecurityUtils.subject?.logout()
    }

    public boolean isPermitted(String permString) {

        SecurityUtils.subject.isPermitted(permString)
    }

    public saltAndHashUser(String password, User user) {

        RandomNumberGenerator rng = new SecureRandomNumberGenerator()
        Object salt = rng.nextBytes()
        user.passwordHash = new Sha512Hash(password, salt, 1024).toBase64()
        user.passwordSalt = salt
        return user
    }

    public String getRandomString() {

        String randomString = org.apache.commons.lang.RandomStringUtils.random(60, true, true)
        return randomString
    }

    @Transactional
    public void removePermission(object, objectId, user) {

        def permission = permissionQuery(object, objectId, user.id)

        if (permission) {
            permission.delete()
        }
    }

    @Transactional
    public void addPermission(permString, user) {

        if (!permissionExists(permString, user)) {
            def permission = new Permission(permissionsString: permString, user: user).save(flush: true)
        }
    }

    boolean permissionExists(permString, user) {

        Permission exists = Permission.findByPermissionsStringAndUser(permString, user)

        if (exists) return true
        else return false
    }

    public findPermission(object, objectId, userId) {

        def permission = permissionQuery(object, objectId, userId)
    }

    def permissionQuery(object, objectId, userId) {

        def query = Permission.withCriteria(uniqueResult: true) {

            ilike("permissionsString", "%:${objectId}")
            ilike("permissionsString", "${object}:%")
            user {
                eq("id", userId.toLong())
            }
        }
    }

    @Transactional
    public changeAuthor(newAuthorId, oldAuthorId, objectId, type) {

        User newAuthor = User.get(newAuthorId)

        User oldAuthor = User.get(oldAuthorId)

        if (newAuthor && oldAuthor && newAuthor.id != oldAuthor.id) {

            // add a new permission if needed

            def action = "${type}Admin"

            def permission = findPermission(action, objectId, newAuthor.id)

            if (!permission) {

                addPermission("${action}:*:${objectId}", newAuthor)

                // check to see if the old author's permission needs to be removed

                def oldPermission = findPermission(action, objectId, oldAuthor.id)

                if (oldPermission) removePermission(action, objectId, oldAuthor)
            }
        }
    }
}
