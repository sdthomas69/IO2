package cev.blackFish

import grails.transaction.Transactional

@Transactional(readOnly = true)
class MenuSetService {

    def getMenuSetByTitle(String title) {
		
		MenuSet menuSet = MenuSet.findByTitle(
			title, 
			[fetch:[children:"eager"], 
				cache:true, 
				readOnly:true
			]
		)
    }
}
