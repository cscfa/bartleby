package org.bartelby.configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Parameter configuration.
 * 
 * Give parameter configuration access
 * from global application scope.
 * 
 * @author vallance
 *
 */
public class ConfigurationParameters {
	
	/**
	 * mapped values.
	 * 
	 * Parameter configuration value storage
	 * with thread safe implementation.
	 */
	private static ConcurrentHashMap<String, Object> mappedValues = new ConcurrentHashMap<String, Object>();

	/**
	 * Default constructor.
	 * 
	 * Default constructor with parent constructor calling.
	 */
	public ConfigurationParameters() {
		super();
	}
	
	/**
	 * Key exist.
	 * 
	 * Return true if the given key exist in the parameter
	 * value storage map.
	 * 
	 * @param key	the key to test.
	 * @return		true if the key exist or false.
	 */
	static public boolean exist(Object key) {
		return ConfigurationParameters.mappedValues.containsKey(key);
	}
	
	/**
	 * Put value.
	 * 
	 * Put a new key/value into the parameter storage.
	 * 
	 * @param key		The key to create or update.
	 * @param value		The value to insert.
	 */
	static public void put(String key, Object value) {
		ConfigurationParameters.mappedValues.put(key, value);
	}
	
	/**
	 * Get a value.
	 * 
	 * Return a previously stored value from a given key.
	 * 
	 * @param key	The key to return the value.
	 * @return		The value of the given key or null if the key doesn't exist.
	 */
	static public Object get(String key){
		if(ConfigurationParameters.mappedValues.containsKey(key)){
			return ConfigurationParameters.mappedValues.get(key);
		}else{
			return null;
		}
	}

}
