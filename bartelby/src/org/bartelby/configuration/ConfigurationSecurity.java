package org.bartelby.configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Security configuration.
 * 
 * Give security configuration access
 * from global application scope.
 * 
 * @author vallance
 *
 */
public class ConfigurationSecurity {

	/**
	 * mapped values.
	 * 
	 * Security configuration value storage
	 * with thread safe implementation.
	 */
	private static ConcurrentHashMap<String, Object> mappedValues = new ConcurrentHashMap<String, Object>();
	
	/**
	 * Default controller.
	 * 
	 * Default constructor with parent constructor calling.
	 */
	public ConfigurationSecurity() {
		super();
	}
	
	/**
	 * Key exist.
	 * 
	 * Return true if the given key exist in the security
	 * value storage map.
	 * 
	 * @param key	the key to test.
	 * @return		true if the key exist or false.
	 */
	static public boolean exist(Object key) {
		return ConfigurationSecurity.mappedValues.containsKey(key);
	}
	
	/**
	 * Put value.
	 * 
	 * Put a new key/value into the security storage.
	 * 
	 * @param key		The key to create or update.
	 * @param value		The value to insert.
	 */
	static public void put(String key, Object value) {
		ConfigurationSecurity.mappedValues.put(key, value);
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
		if(ConfigurationSecurity.mappedValues.containsKey(key)){
			return ConfigurationSecurity.mappedValues.get(key);
		}else{
			return null;
		}
	}

}
