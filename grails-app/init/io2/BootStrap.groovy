package io2

import cev.blackFish.Role
import cev.blackFish.User
import cev.blackFish.MenuSet
import cev.blackFish.UserRoleAssociation
import cev.blackFish.SecurityService
import grails.util.Holders

class BootStrap {

    def securityService
    def config = Holders.config
    def shiroSecurityService

    def init = { servletContext ->
        //createAdminUserIfRequired()
    }
    def destroy = {
    }

    void createAdminUserIfRequired() {


        if(!Role.findByName("Administrator") && !User.findByUsername('admin')) {

            println("Creating initial roles and admin user")

            Role adminRole = new Role(name: 'Administrator')
            adminRole.addToPermissions("*:*:*")
            adminRole.save(flush:true, failOnError:true)

            println("The Admin role '${adminRole}' has been created")

            Role userRole = new Role(name: config.userRole)
            userRole.addToPermissions("${config.userRolePermissions}")
            userRole.save()

            println("The User role '${userRole}' has been created")

            User adminUser = new User(
                    username:'admin',
                    firstName:'admin',
                    lastName:'admin',
                    email:'foo@bar.com',
                    name:'admin',
                    urlTitle:'admin_admin',
                    password:'admin123@',
                    //passwordHash: shiroSecurityService.encodePassword("admin123@"),
                    confirm:'admin123@',
                    confirmed:true
            )

            securityService.saltAndHashUser('admin123@', adminUser)

            adminUser.save(flush:true, failOnError:true)

            UserRoleAssociation.create(adminUser, adminRole)

            MenuSet menuSet = new MenuSet(title: "Main").save()
        }
    }
}

//GRANT SELECT ON ALL TABLES IN SCHEMA public TO io2_public;
