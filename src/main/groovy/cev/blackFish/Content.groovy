package cev.blackFish

abstract class Content implements Comparable, Serializable {

    String title, 
		urlTitle, 
		pageTitle, 
		category, 
		subcategory, 
		description, 
		shortDescription, 
		kml, 
		tagline, 
		pullQuote,
		metaDescription,
		metaKeywords,
		updateMessage,
		banner,
		uri

	Date date = new Date()
	Date lastUpdated
	
	Boolean published = false
	Boolean featured = false
	Boolean slideshow = false
	Boolean homepage = false
	Boolean news = false

	BigDecimal latitude
	BigDecimal longitude

    Site site
	
    User author
	
    static constraints = {
		//title(shared:"commonTitle")
        title(blank:false, maxSize:100, unique:true, validator: { val ->
            //if (val.matches('^.*[!@#$%^&?/;_()*-+-`~=<>,‘\\.].*$')) {
			/*if (val.matches('^.*[?/‘;_].*$')) {
                return 'title.illegal.character'
            }*/
        })
		urlTitle(maxSize:255)
		pageTitle(nullable:true, maxSize:255)
		kml(nullable:true)
		category(nullable:true, maxSize:50)
		description(blank:false)
        shortDescription(nullable:true, maxSize:255)
		latitude(nullable:true, scale:8, maxSize:19)
		longitude(nullable:true, scale:8, maxSize:19)
		tagline(nullable:true, maxSize:100)
        pullQuote(nullable:true, maxSize:255)
        subcategory(nullable:true)
        metaDescription(nullable:true, maxSize:255)
        metaKeywords(nullable:true, maxSize:255)
        site(nullable:true)
        author(nullable:true)
        updateMessage(nullable:true, maxSize:255)
		banner(nullable:true)
		uri(nullable:true, maxSize:255)
    }

	static mapping  = {
        id generator:'sequence', params:[sequence:'content_seq'] 
		description type:'text'
		kml type:'text'
		cache true
		site cache:true
        tablePerHierarchy false
		dynamicUpdate true
    }

	static transients = ['searchableProps']
    
    def searchableProps = [
		'title', 
		'category', 
		'subcategory', 
		'description', 
		'tagline', 
		'pullQuote',
        'shortDescription', 
		'metaDescription', 
		'metaKeywords'
	]

	int compareTo(obj) {
        obj.date.compareTo(date)
    }

    boolean equals(def o) { 
        if (is(o)) return true 
        if (o instanceof Content) { 
            return title == o.title 
        } 
        return false 
    }
    
    String toString() {
        return "${title}"
    }
	
	String getPageTitle() {
		return pageTitle ?: title
	}
    
    private String stripTags(String html) {
        return html?.replaceAll("\\<.*?>", "")
    }
    
    void fillShortDescription() {
		
        this.description.size() > 150 ? 
			this.setShortDescription(stripTags(this.description[0..150])) : 
			this.setShortDescription(stripTags(this.description))
    }
	
	public String cleanTitle(String t) {
		
		return t.replaceAll(' ', '_').encodeAsURL()
	}
}
