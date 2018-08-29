package cev.blackFish

class AdminController {

    def home() {
        //println("site is " + session.site)
        render(view: '/admin')
    }
}
