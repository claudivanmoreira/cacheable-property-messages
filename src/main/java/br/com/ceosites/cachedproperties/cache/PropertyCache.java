package br.com.ceosites.cachedproperties.cache;

import com.google.common.base.Optional;
import com.google.common.base.Ticker;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyCache {

    private Logger LOGGER = LoggerFactory.getLogger(PropertyCache.class);
    private PropertyCacheLoader cacheLoader;
    private LoadingCache<String, Optional<String>> cache;
    private Long cacheSize, timeToLive, idleTimeOut;
    private boolean loadOnStartup;

    /**
     *
     */
    private PropertyCache() {
    }

    /**
     * 
     * @param cacheLoader
     * @param cacheSize
     * @param timeToLive
     * @param idleTimeOut
     * @param loadOnStartup
     * @throws Exception 
     */
    public PropertyCache(PropertyCacheLoader cacheLoader, Long cacheSize, Long timeToLive, Long idleTimeOut, boolean loadOnStartup) throws Exception {
        this.cacheLoader = cacheLoader;
        this.cacheSize = cacheSize;
        this.timeToLive = timeToLive;
        this.idleTimeOut = idleTimeOut;
        this.loadOnStartup = loadOnStartup;
        buildCache();
    }

    private void buildCache() throws Exception {
        validateCacheConfiguration();
        LOGGER.info("Cache will be created with configuration: cacheLoader = <{}>, idleTimeOut = <{}>, timeToLive = <{}>, cacheSize = <{}>, loadOnStartup = <{}>",
                new Object[]{ReflectionToStringBuilder.reflectionToString(cacheLoader), idleTimeOut, timeToLive, cacheSize, loadOnStartup});
        cache = CacheBuilder.newBuilder()
                .recordStats()
                .expireAfterAccess(idleTimeOut, TimeUnit.SECONDS)
                .expireAfterWrite(timeToLive, TimeUnit.SECONDS)
                .maximumSize(cacheSize)
                .build(cacheLoader);
        if (loadOnStartup) {
            LOGGER.debug("Populating the cache after creation.");
            cache.putAll(cacheLoader.loadAll());
            LOGGER.debug("Cache populated.");
        }
    }

    public ConcurrentMap<String, Optional<String>> getCachedProperties() {
        LOGGER.debug("CacheStats: {}", cache.stats().toString());
        return cache.asMap();
    }

    public String getCachedProperty(String key) throws ExecutionException {
        LOGGER.info("Reading cached value to property <{}>", key);
        Optional<String> cachedOptional = cache.get(key);
        LOGGER.debug("CacheStats: {}", cache.stats().toString());
        String value = StringUtils.EMPTY;
        if (isAbsentValue(cachedOptional)) {
            LOGGER.debug("Invalidating absent cached value to key <{}>.", key);
            cache.invalidate(key);
        } else {
            value = cachedOptional.get();
        }
        LOGGER.info("Found value to property {}: <{}>", key, value);
        return value;
    }

    private boolean isAbsentValue(Optional<String> cachedOptional) {
        LOGGER.debug("Value of cached key is an reference to nothing value? <{}>", cachedOptional.equals(Optional.<String>absent()));
        return cachedOptional.equals(Optional.<String>absent());
    }

    private void validateCacheConfiguration() {
        if (idleTimeOut == null) {
            LOGGER.debug("The value of <idleTime> is empty. Default idle timeout timeout will be used: {}", CacheConstants.DEFAULT_IDLE_TIME);
            idleTimeOut = CacheConstants.DEFAULT_IDLE_TIME;
        }

        if (timeToLive == null) {
            LOGGER.debug("The value of <timeToLive> is empty. Default time to live will be used: {}", CacheConstants.DEFAULT_TIME_TO_LIVE);
            timeToLive = CacheConstants.DEFAULT_TIME_TO_LIVE;
        }

        if (cacheSize == null) {
            LOGGER.debug("The value of <cacheSize> is empty. Default cache size will be used: {}", CacheConstants.DEFAULT_CACHE_SIZE);
            cacheSize = CacheConstants.DEFAULT_CACHE_SIZE;
        }
    }

    public PropertyCacheLoader getCacheLoader() {
        return cacheLoader;
    }

    public Long getCacheSize() {
        return cacheSize;
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public Long getIdleTime() {
        return idleTimeOut;
    }

    public boolean isLoadOnStartup() {
        return loadOnStartup;
    }
}
