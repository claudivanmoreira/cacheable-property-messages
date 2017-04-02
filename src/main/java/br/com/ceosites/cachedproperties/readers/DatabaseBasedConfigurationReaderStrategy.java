package br.com.ceosites.cachedproperties.readers;

import java.util.Map;

import br.com.ceosites.cachedproperties.CustomDatabaseConfiguration;
import br.com.ceosites.cachedproperties.util.KeysIterable;
import com.google.common.base.Optional;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseBasedConfigurationReaderStrategy implements PropertyReaderStrategy {

    private final Logger LOGGER = LoggerFactory.getLogger(DatabaseBasedConfigurationReaderStrategy.class);
    private final CustomDatabaseConfiguration configuration;

    public DatabaseBasedConfigurationReaderStrategy(CustomDatabaseConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public KeysIterable getKeys() {
        LOGGER.debug("Reading all keys from configuration <{}>", configuration.getName());
        return new KeysIterable(this.configuration.getKeys());
    }

    @Override
    public Optional<String> getValue(String key) {
        return this.configuration.getProperty(key);
    }

    @Override
    public Map<String, Optional<String>> getMapKeys() {
        LOGGER.debug("Starting reading the keys from the database to configuration <{}>...", configuration.getName());
        Map<String, Optional<String>> mapKeys = this.configuration.getPropertiesMap();
        LOGGER.debug("Keys loaded from the database: {}.", ReflectionToStringBuilder.reflectionToString(mapKeys, ToStringStyle.MULTI_LINE_STYLE));
        return mapKeys;
    }

    public CustomDatabaseConfiguration getConfiguration() {
        return configuration;
    }
}
