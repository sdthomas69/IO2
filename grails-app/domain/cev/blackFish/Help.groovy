package cev.blackFish

class Help {

    String selectedController, activity, description, title
	
	File primaryImage

    static constraints = {
		selectedController(blank:false, maxSize:25)
		activity(nullable:true, maxSize:25)
		description(nullable:true)
		title(blank:false, maxSize:100)
		primaryImage(nullable:true)
    }
	
	static mapping = {
		id generator:'sequence', params:[sequence:'help_seq']
		cache 'true'
		dynamicUpdate true
		description type:'text'
	}
	
	String toString() {
		return "${title}"
	}
}
