import org.apache.shiro.authc.credential.HashedCredentialsMatcher

// Place your Spring DSL code here
beans = {

    credentialMatcher(org.apache.shiro.authc.credential.Sha512CredentialsMatcher) {
        storedCredentialsHexEncoded = false
        hashSalted = true
        hashIterations = 1024
    }

    remoteAddressCache(org.springframework.cache.ehcache.EhCacheFactoryBean) {
        timeToLive = 300 // 5 min
        maxElementsInMemory = 100
    }

    dosCache(org.springframework.cache.ehcache.EhCacheFactoryBean) {
        timeToLive = 30 // 1 min
        maxElementsInMemory = 2000
    }
    //customMarshallerRegistrar(CustomMarshallerRegistrar)
}
