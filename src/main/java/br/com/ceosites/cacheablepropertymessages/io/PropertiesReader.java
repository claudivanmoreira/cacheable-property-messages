package br.com.ceosites.cacheablepropertymessages.io;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author claudivan.a.moreira
 *
 */
public class PropertiesReader {

	private Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);
	private String basePath;
	private String[] properties;
	
	public PropertiesReader(String basePath, String[] properties) {
		this.basePath = basePath;
		this.properties = properties;
	}

	public PropertiesConfiguration getAllProperties() throws ConfigurationException {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		if (StringUtils.isNotBlank(basePath)) {
			LOGGER.debug("Path to load properties: {}", basePath);
			configuration.setBasePath(basePath);
		} else {
			LOGGER.debug("Path to load properties is empty. The classes folder will be used.");
		}
		
		if (ArrayUtils.isNotEmpty(properties)) {
			for (String file: properties) {
				LOGGER.debug("Loading properties from file {}.", file);
				configuration.load(file);
			}
		}
		
		return configuration;
	}	
}
