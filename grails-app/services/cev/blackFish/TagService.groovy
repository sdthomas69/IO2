package cev.blackFish

import grails.transaction.Transactional

import org.hibernate.FetchMode as FM

@Transactional(readOnly = true)
class TagService {

    //boolean transactional = false
	
	Tag getTag(Long id) {
		
		Tag tagInstance = Tag.lookup.read(id)
	}
	
	def list(
		Integer max=10, 
		Integer offset=0,
		String sortBy="",
		String orderBy="",
		String q="") {
		
		def query = {
			if(q) {
				ilike("title", "%${q}%")
			}
			order(sortBy ?: 'title', orderBy ?: 'asc')
			maxResults(max)
			firstResult(offset)
			setReadOnly true
		}
		
		def results = Tag.createCriteria().list(['max':max, 'offset':offset], query)
		
		return results
	}
	
	Tag findByTitle(String title) {
		
		Tag tag = Tag.lookup.withCriteria(uniqueResult:true) {
			
			or{
				eq("urlTitle", title)
				eq("title", title.replaceAll("%20", " "))
			}
			setReadOnly true
		}
		return tag
	}

    /**
     * @param tagList: List of tags
     * @return tagMap: a Map of Tag names and the the number of Stories and Files each Tag has
     */
    def createCloudMap() {
		
		def tagList = Tag.lookup.list(sort:"title")
        def tagMap = [:] 
        for(item in tagList) { 
            tagMap.putAt(item.title, item.stories.size() + item.files.size())
        }
        return tagMap
    }
	
	def createCloudMap(tagList) {

		def tagMap = [:]
		for(item in tagList) {
			tagMap.putAt(item.title, item.stories.size() + item.files.size())
		}
		return tagMap
	}
    
    def createStoryCloudMap(tagList) {
		
        def tagMap = [:]
        for(item in tagList) {
            tagMap.putAt(item.title, item.stories.size())
        }
        return tagMap
    }
    
    /**
    * @param tagList: List of tags
    * @return tagMap: a Map of Tag names and the the number of Stories each Tag has
    */
   def createFileCloudMap(tagList) {
	   
       def tagMap = [:]
       for(item in tagList) {
           tagMap.putAt(item.title, item.files.size())
       }
       return tagMap
   }
}
