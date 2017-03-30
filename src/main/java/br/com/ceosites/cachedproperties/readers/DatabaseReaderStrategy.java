package br.com.ceosites.cachedproperties.readers;

import java.util.Map;

import br.com.ceosites.cachedproperties.CustomDatabaseConfiguration;
import br.com.ceosites.cachedproperties.util.KeysIterable;

public class DatabaseReaderStrategy implements ReaderStrategy {
	
	private CustomDatabaseConfiguration configuration;
	
	public DatabaseReaderStrategy(CustomDatabaseConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public KeysIterable getKeys() {
		return new KeysIterable(this.configuration.getKeys());
	}

	@Override
	public String getValue(String key) {
		return this.configuration.getString(key);
	}

	@Override
	public Map<String, String> getMapKeys() {
		return this.configuration.getMapKeys();
	}

}
