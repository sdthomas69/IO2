package cev.blackFish

import grails.transaction.Transactional

@Transactional
class PermissionService {

    @Transactional(readOnly = true)
    public findPermission(String object, String objectId, String userId) {

        Permission permission = permissionQuery(object, objectId, userId)
    }

    @Transactional(readOnly = true)
    def permissionQuery(String controller, String objectId, String userId) {

        def query = Permission.withCriteria(uniqueResult: true) {
            ilike("permissionsString", "%${objectId}%")
            ilike("permissionsString", "%${controller}%")
            user {
                eq("id", userId.toLong())
            }
        }
    }

    @Transactional(readOnly = true)
    def findPermissionsByUser(Long userId) {

        def query = Permission.withCriteria() {
            user {
                eq("id", userId)
            }
        }
    }

    @Transactional(readOnly = true)
    def findPermissionsByObject(String controller, String objectId) {

        def query = Permission.withCriteria() {
            ilike("permissionsString", "%${objectId}%")
            ilike("permissionsString", "%${controller}%")
        }
    }

    @Transactional(readOnly = true)
    def findPermissionsByUserAndObject(String controller, String objectId, Long userId) {

        def query = Permission.withCriteria() {
            ilike("permissionsString", "%${objectId}%")
            ilike("permissionsString", "%${controller}%")
            user {
                eq("id", userId)
            }
        }
    }
}
