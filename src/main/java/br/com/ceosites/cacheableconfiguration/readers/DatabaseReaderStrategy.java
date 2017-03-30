package br.com.ceosites.cacheableconfiguration.readers;

import java.util.Map;

import br.com.ceosites.cacheableconfiguration.CustomDatabaseConfiguration;
import br.com.ceosites.cacheableconfiguration.util.KeysIterable;

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
