package cev.blackFish

import grails.util.Holders

import org.codehaus.groovy.grails.commons.*

class SearchService {

    boolean transactional = false
    
    def config = Holders.config
	
	//def config = ConfigurationHolder.config
	
	def grailsApplication
    
    /**
     * 
     * @param params
     * @param objectName
     * @return
     */
    def objectSearch(params, objectName) {
        
        def searchResult = null

        def newObject = getObject(objectName)
		
		if(newObject) {
        
	        def query = createQuery(newObject, params)
	        
	        if(query) searchResult = newObject.createCriteria().list(params, query)
		}
		return searchResult
    }
    
    /**
     * 
     * @param params
     * @return
     */
    public Map searchAll(params) {
        
        def searchResult = [:]
        
        for(obj in grailsApplication.config.searchableObjects) {
            
            def newObject = getObject(obj)
			
			if(newObject) {
            
	            def query = createQuery(newObject, params)
				
				String result = "${obj}Result"
	            
	            if(query) searchResult.put(result, newObject.createCriteria().list(params, query))
			}
        }
        return searchResult
    }
    
    /**
     *
     * @param objectName
     * @return
     */
    private getObject(objectName) {
        
        def grailsDomainClass = null
        
        def newObject = null
		
		String className = "cev.blackFish.${objectName.capitalize()}"

        grailsDomainClass = Holders.getGrailsApplication().getArtefact("Domain", className)?.getClazz()
        
        if(grailsDomainClass) newObject = grailsDomainClass.newInstance()
    }
    
    /**
     * 
     * @param newObject
     * @param params
     * @return
     */
    private createQuery(newObject, params) {
        
        def query = {
            eq("published", true)
            or {
                newObject.searchableProps.each {
                    ilike(it, "%${params.q}%")
                }
            }
            maxResults(params.max.toInteger())
            firstResult(params.offset.toInteger())
            setReadOnly true
        }
    }
}
