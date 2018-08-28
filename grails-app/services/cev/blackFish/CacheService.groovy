package cev.blackFish

import groovy.transform.CompileStatic
import net.sf.ehcache.Element

//@CompileStatic
class CacheService {
    
    boolean transactional = false
    
    def remoteAddressCache
	def ddosCache

    /**
     * 
     * @param address
     * @return
     */
    Boolean remoteAddressExpired(String address) {

        return remoteAddressCache.get(getKey(address)) == null
    }
    
    /**
     * 
     * @param address
     * @param attempts
     */
    void setRemoteAddress(String address, Integer attempts) {

        remoteAddressCache.put(new Element(getKey(address), attempts))
    }
    
    /**
     * 
     * @param address
     */
    void removeRemoteAddress(String address) {

        remoteAddressCache.remove(getKey(address))
    }
    
    /**
     * 
     * @param address
     * @param attempts
     */
    void incrementRemoteAddressAttempts(String address, Integer attempts) {
        
        removeRemoteAddress(address)
        setRemoteAddress(address, attempts + 1)
    }
    
    /**
     * 
     * @param address
     * @return
     */
    def getRemoteAddress(String address) {

        return remoteAddressCache?.get(getKey(address))?.value
    }
    
    /**
     * 
     * @param address
     * @return
     */
    def getKey(String address) {
        
        return new RemoteAddressKey(itemKey:address)
    }
	
	/**************************************************************************************
	 * 
	 * @param address
	 * @return
	 */
	
	Boolean ddosCacheExpired(String address) {
		
		return ddosCache.get(getDdosKey(address)) == null
	}
	
	void setDdosCache(String address, Integer attempts) {
		
		ddosCache.put(new Element(getDdosKey(address), attempts))
	}
	
	void removeDdosCache(String address) {
		
		ddosCache.remove(getDdosKey(address))
	}
	
	void incrementDdosCacheAttempts(String address, Integer attempts) {
		
		removeDdosCache(address)
		setDdosCache(address, attempts + 1)
	}
	
	def getDdosCache(String address) {
		
		return ddosCache?.get(getDdosKey(address))?.value
	}
	
	def getDdosKey(String address) {
		
		return new RemoteAddressKey(itemKey:address)
	}
}
