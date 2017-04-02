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

public class DatabaseReaderStrategyTest {

    private CustomDatabaseConfiguration configuration;
    private PropertyCache cache;

    @Before
    public void setup() {
        configuration = TestUtils.createDatabaseConfiguration();
        cache = TestUtils.createPropertyCache(configuration);
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
            Logger.getLogger(DatabaseReaderStrategyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testCacheDatabaseTimeout() {
        try {
            /* Get keys from configuration APP_CONFIG */
            configuration.setName("APP_CONFIG");
            /* Initial size is 0 */
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            /* Two keys in cache */
            /* Get keys from configuration USER_MESSAGES */
            configuration.setName("USER_MESSAGES");
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));
            Assert.assertEquals("User add with sucess", cache.getCachedProperty("add.user.success.message"));
            /* Three keys in cache */
            /* Waiting for cache timeout */
            System.out.println("Waiting for cache expire");
            Thread.sleep(2000);
            /* Cached keys has expired and size is 0 */
            /* Reload Keys */
            configuration.setName("APP_CONFIG");
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            Assert.assertEquals("123", cache.getCachedProperty("myrest.api.auth.pwd"));
            Assert.assertEquals("admin", cache.getCachedProperty("myrest.api.auth.user"));
            /* One key in cache */
        } catch (Exception ex) {
            Logger.getLogger(DatabaseReaderStrategyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
