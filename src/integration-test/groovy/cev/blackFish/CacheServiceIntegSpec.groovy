package cev.blackFish

import grails.test.spock.IntegrationSpec

class CacheServiceIntegSpec extends IntegrationSpec {
    
    def remoteAddressCache
    def cacheService
	def dosCache
    
    String ipAddress = "0.0.0.0"
    Integer attempts = 1

	def setup() {
	}

	def cleanup() {
	}
    
    def "setRemoteAddress should create a new remoteAddressCache"() {
        
        setup:
        RemoteAddressKey key = new RemoteAddressKey(itemKey:ipAddress)
        
        when:
        cacheService.setRemoteAddress(ipAddress, attempts)
        
        then:
        remoteAddressCache.get(key).value == attempts
    }
    
    def "getRemoteAddress should return the specified remoteAddressCache value"() {
        
        setup:
        RemoteAddressKey key = new RemoteAddressKey(itemKey:ipAddress)
        
        when:
        cacheService.setRemoteAddress(ipAddress, attempts)
        
        then:
        cacheService.getRemoteAddress(ipAddress) == attempts
    }
    
    def "remoteAddressExpired should return false if the remoteAddressCache has not expired"() {
        
        when:
        cacheService.setRemoteAddress(ipAddress, attempts)
        
        then:
        cacheService.remoteAddressExpired(ipAddress) == false
    }
    
    def "removeRemoteAddress will remove the remoteAddressCache"() {
        
        setup:
        cacheService.setRemoteAddress(ipAddress, attempts)
        
        when:
        cacheService.removeRemoteAddress(ipAddress)
        
        then:
        cacheService.remoteAddressExpired(ipAddress) == true
    }
    
    def "incrementRemoteAddressAttempts will add 1 to the remoteAddressCache value"() {
        
        setup:
        RemoteAddressKey key = new RemoteAddressKey(itemKey:ipAddress)
        cacheService.setRemoteAddress(ipAddress, attempts)
        
        when:
        cacheService.incrementRemoteAddressAttempts(ipAddress, attempts)
        
        then:
        remoteAddressCache.get(key).value == attempts + 1
    }
	
	
	/*****************************************************************************************/
	
	
	def "setDosCache should create a new dosCache"() {
		
		setup:
		RemoteAddressKey key = new RemoteAddressKey(itemKey:ipAddress)
		
		when:
		cacheService.setDosCache(ipAddress, attempts)
		
		then:
		dosCache.get(key).value == attempts
	}
	
	def "getDosCache should return the specified dosCache value"() {
		
		setup:
		RemoteAddressKey key = new RemoteAddressKey(itemKey:ipAddress)
		
		when:
		cacheService.setDosCache(ipAddress, attempts)
		
		then:
		cacheService.getDosCache(ipAddress) == attempts
	}
	
	def "dosCacheExpired should return false if the dosCache has not expired"() {
		
		when:
		cacheService.setDosCache(ipAddress, attempts)
		
		then:
		cacheService.dosCacheExpired(ipAddress) == false
	}
	
	def "removeDosCache will remove the ddosCache"() {
		
		setup:
		cacheService.setDosCache(ipAddress, attempts)
		
		when:
		cacheService.removeDosCache(ipAddress)
		
		then:
		cacheService.dosCacheExpired(ipAddress) == true
	}
	
	def "incrementDosCacheAttempts will add 1 to the dosCache value"() {
		
		setup:
		RemoteAddressKey key = new RemoteAddressKey(itemKey:ipAddress)
		cacheService.setDosCache(ipAddress, attempts)
		
		when:
		cacheService.incrementDosCacheAttempts(ipAddress, attempts)
		
		then:
		dosCache.get(key).value == attempts + 1
	}
}