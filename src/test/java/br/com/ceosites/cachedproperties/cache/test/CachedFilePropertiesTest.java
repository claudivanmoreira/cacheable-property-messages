package br.com.ceosites.cachedproperties.cache.test;

import org.junit.Before;
import org.junit.Test;

import br.com.ceosites.cachedproperties.cache.PropertyCache;
import br.com.ceosites.cachedproperties.cache.test.util.TestUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Assert;
import org.junit.Ignore;

public class CachedFilePropertiesTest {

    private PropertiesConfiguration configuration;
    private PropertyCache cache;

    @Before
    public void setup() {
        configuration = TestUtils.createFileConfiguration();
        cache = TestUtils.createFilePropertyCache(configuration);
    }

    @Test
    @Ignore
    public void testCacheFiles() {
        try {
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            Assert.assertEquals("jdbc:hsqldb:mem:dataSource?hsqldb.sqllog=3", cache.getCachedProperty("databse.url"));
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));
        } catch (Exception ex) {
            Logger.getLogger(CachedDatabasePropertiesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testCacheFilesTimeOut() {
        try {
            /* Cache miss */
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            /* Cache miss */
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            /* Cache miss */
            Assert.assertEquals("jdbc:hsqldb:mem:dataSource?hsqldb.sqllog=3", cache.getCachedProperty("databse.url"));
            /* Cache miss */
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));
            
            /* Cache hit */
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            /* Cache hit */
            Assert.assertEquals("jdbc:hsqldb:mem:dataSource?hsqldb.sqllog=3", cache.getCachedProperty("databse.url"));
            
            System.out.println("Waiting for cache expire");
            Thread.sleep(3000);
            
            /* Cache miss, evictionCount=1 */
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));
            /* Cache miss, evictionCount=2 */
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            
            /* Cache hit */
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));
            /* Cache hit */
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            
        } catch (Exception ex) {
            Logger.getLogger(CachedDatabasePropertiesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
