package br.com.ceosites.cachedproperties.readers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;

import br.com.ceosites.cachedproperties.util.KeysIterable;
import com.google.common.base.Optional;

public class FileBasedConfigurationReaderStrategy implements PropertyReaderStrategy {

    private PropertiesConfiguration configuration;

    public FileBasedConfigurationReaderStrategy(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public KeysIterable getKeys() {
        return new KeysIterable(configuration.getKeys());
    }

    @Override
    public Optional<String> getValue(String key) {
        return Optional.fromNullable(configuration.getString(key));
    }

    @Override
    public Map<String, Optional<String>> getMapKeys() {
        Map<String, Optional<String>> mapedKeys = new HashMap<String, Optional<String>>();
        Iterator<String> itKeys = getKeys().iterator();
        while (itKeys.hasNext()) {
            String key = itKeys.next();
            mapedKeys.put(key, Optional.fromNullable(configuration.getString(key)));
        }
        return mapedKeys;
    }

}
