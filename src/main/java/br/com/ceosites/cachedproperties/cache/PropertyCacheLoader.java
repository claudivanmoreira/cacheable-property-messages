package br.com.ceosites.cachedproperties.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheLoader;

import br.com.ceosites.cachedproperties.readers.PropertyReaderStrategy;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

public class PropertyCacheLoader extends CacheLoader<String, Optional<String>> {

    private final Logger LOGGER = LoggerFactory.getLogger(PropertyCacheLoader.class);

    private final PropertyReaderStrategy readerStrategy;

    public PropertyCacheLoader(PropertyReaderStrategy readerStrategy) {
        this.readerStrategy = readerStrategy;
    }

    @Override
    public Optional<String> load(String key) throws Exception {
        LOGGER.debug("Searching for key <{}> in the cache", key);
        return this.readerStrategy.getValue(key);
    }

    @Override
    public ListenableFuture<Optional<String>> reload(String key, Optional<String> oldValue) throws Exception {
        LOGGER.debug("Reloading value for key <{}>", key);
        return super.reload(key, oldValue); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    public Map<String, Optional<String>> loadAll() throws Exception {
        LOGGER.debug("Adding keys to cache.");
        return this.readerStrategy.getMapKeys();
    }

    public PropertyReaderStrategy getReaderStrategy() {
        return readerStrategy;
    }

}
