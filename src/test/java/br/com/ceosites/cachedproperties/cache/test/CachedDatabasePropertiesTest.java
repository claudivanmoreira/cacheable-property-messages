package br.com.ceosites.cachedproperties.cache.test;

import br.com.ceosites.cachedproperties.CustomDatabaseConfiguration;
import br.com.ceosites.cachedproperties.cache.PropertyCache;
import br.com.ceosites.cachedproperties.cache.test.util.TestUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CachedDatabasePropertiesTest {

    private CustomDatabaseConfiguration configuration;
    private PropertyCache cache;

    @Before
    public void setup() {
        configuration = TestUtils.createDatabaseConfiguration();
        cache = TestUtils.createDatabasePropertyCache(configuration);
    }

    @Test
    @Ignore
    public void testCacheDatabase() {
        try {

            /* Get keys from configuration APP_CONFIG */
            Assert.assertEquals(0, cache.getCachedProperties().size());
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));

            /* Get key from configuration USER_MESSAGES */
            Assert.assertEquals("", cache.getCachedProperty("add.user.success.message"));

            /* Changing configuration name to USER_MESSAGES */
            configuration.setName("USER_MESSAGES");

            /* Get keys from configuration USER_MESSAGES */
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));

        } catch (Exception ex) {
            Logger.getLogger(CachedDatabasePropertiesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testCacheDatabaseTimeout() {
        try {
            /* Get keys from configuration APP_CONFIG */
            configuration.setName("APP_CONFIG");
            /* Cache miss */
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            /* Cache miss */
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            /* Cache miss */
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            /* Cache miss */
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            
            /* Get keys from configuration USER_MESSAGES */
            configuration.setName("USER_MESSAGES");
            /* Cache miss */
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));
            /* Cache hit */
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));
            
            System.out.println("Waiting for cache expire");
            Thread.sleep(3000);
            
            /* Reload keys from configuration APP_CONFIG */
            configuration.setName("APP_CONFIG");
            /* Cache miss, evictionCount=1 */
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            /* Cache miss, evictionCount=2 */
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            /* Cache hit */
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
        } catch (Exception ex) {
            Logger.getLogger(CachedDatabasePropertiesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
