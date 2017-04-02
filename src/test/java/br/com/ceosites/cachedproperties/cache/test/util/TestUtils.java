/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceosites.cachedproperties.cache.test.util;

import br.com.ceosites.cachedproperties.CustomDatabaseConfiguration;
import br.com.ceosites.cachedproperties.cache.PropertyCache;
import br.com.ceosites.cachedproperties.cache.PropertyCacheBuilder;
import br.com.ceosites.cachedproperties.cache.PropertyCacheLoader;
import br.com.ceosites.cachedproperties.cache.test.DatabaseReaderStrategyTest;
import br.com.ceosites.cachedproperties.readers.DatabaseBasedConfigurationReaderStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Claudivan Moreira
 */
public class TestUtils {
    
    
    public static PropertyCache createPropertyCache(CustomDatabaseConfiguration dataseConfiguration) {
        DatabaseBasedConfigurationReaderStrategy readerStrategy = new DatabaseBasedConfigurationReaderStrategy(dataseConfiguration);
        PropertyCacheLoader cacheLoader = new PropertyCacheLoader(readerStrategy);
        PropertyCache cache = null;
        try {
            cache = PropertyCacheBuilder.newBuilder()
                    .withCacheSize(100L)
                    .withIdleTime(2L)
                    .withTimeToLive(2L)
                    .loadOnStartup(false)
                    .withPropertyCacheLoader(cacheLoader)
                    .buildCache();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseReaderStrategyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  cache;
    }
    
    public static CustomDatabaseConfiguration createDatabaseConfiguration() {
        String table = "TB_CONFIG_PROPERTY";
        String nameColumn = "CONFIG_NAME";
        String keyColumn = "KEY";
        String valueColumn = "VALUE";
        String name = "APP_CONFIG";
        DataSource datasource = DatabaseUtils.prepareDatabase();
        return  new CustomDatabaseConfiguration(datasource, table, nameColumn, keyColumn, valueColumn, name);
    }
}
