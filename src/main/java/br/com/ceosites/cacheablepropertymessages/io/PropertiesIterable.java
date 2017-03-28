package br.com.ceosites.cacheablepropertymessages.io;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.ArrayUtils;

public class PropertiesIterable implements Iterable<String> {

	private PropertiesConfiguration configuration;
	private String[] propertiesKeys; 
			
	public PropertiesIterable(PropertiesConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public PropertiesIterable(String... keys) {
		this.propertiesKeys = keys;
	}

	@Override
	public Iterator<String> iterator() {
		if (this.configuration != null) {
			return configuration.getKeys();
		} else if (this.propertiesKeys != null) {
			return Arrays.asList(this.propertiesKeys).iterator();
		} else {
			return Arrays.asList(ArrayUtils.EMPTY_STRING_ARRAY).iterator();
		}
	}	
}
