package br.com.ceosites.cachedproperties.readers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import br.com.ceosites.cachedproperties.util.KeysIterable;

public class ProperyReaderStrategy implements ReaderStrategy {
	
	private PropertiesConfiguration configuration;
	
	public ProperyReaderStrategy(PropertiesConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public KeysIterable getKeys() {
		return new KeysIterable(configuration.getKeys());
	}

	@Override
	public String getValue(String key) {
		return configuration.getString(key, StringUtils.EMPTY);
	}

	@Override
	public Map<String, String> getMapKeys() {
		Map<String, String>  mapedKeys = new HashMap<String, String>();
		Iterator<String> itKeys = getKeys().iterator();
		while(itKeys.hasNext()) {
			String key = itKeys.next();
			mapedKeys.put(key, configuration.getString(key));
		}
		return mapedKeys;
	}

}
