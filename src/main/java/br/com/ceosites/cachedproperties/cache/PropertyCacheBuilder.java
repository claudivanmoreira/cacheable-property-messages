/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceosites.cachedproperties.cache;

/**
 *
 * @author Claudivan Moreira
 */
public class PropertyCacheBuilder {
    
    private Long cacheSize, timeToLive, idleTimeOut;
    private boolean loadOnStartup;
    private static PropertyCacheBuilder cacheBuilder;
    private PropertyCacheLoader propertyCacheLoader;
    private PropertyCache propertyCache;

    private PropertyCacheBuilder() {
    }
    
    public static PropertyCacheBuilder newBuilder() {
        if (cacheBuilder == null) {
            cacheBuilder = new PropertyCacheBuilder();
        }
        return cacheBuilder;
    }

    public PropertyCacheBuilder withCacheSize(Long cacheSize) {
        this.cacheSize = cacheSize;
        return cacheBuilder;
    }

    public PropertyCacheBuilder withTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
        return cacheBuilder;
    }

    public PropertyCacheBuilder withIdleTime(Long idleTime) {
        this.idleTimeOut = idleTime;
        return cacheBuilder;
    }
    
    public PropertyCacheBuilder loadOnStartup(Boolean loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
        return cacheBuilder;
    }

    public PropertyCacheBuilder withPropertyCacheLoader(PropertyCacheLoader propertyCacheLoader) {
        this.propertyCacheLoader = propertyCacheLoader;
        return cacheBuilder;
    }
    
    public PropertyCache buildCache() throws Exception {
        return new PropertyCache(propertyCacheLoader, cacheSize, timeToLive, idleTimeOut, loadOnStartup);
    }
}
