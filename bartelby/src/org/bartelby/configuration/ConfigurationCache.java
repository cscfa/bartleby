package org.bartelby.configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.spleen.config.SpleenConfigurator;

public class ConfigurationCache {
	
	protected static HashMap<String, LinkedHashMap> mappedValues = new HashMap<String, LinkedHashMap>();

	public ConfigurationCache() {
		// TODO Auto-generated constructor stub
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
		return mappedValues.containsKey(key);
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
		mappedValues.put(key, (LinkedHashMap) value);
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
		if(mappedValues.containsKey(key)){
			return mappedValues.get(key);
		}else{
			return null;
		}
	}
	
	static public Object[] getAll(){
		return mappedValues.keySet().toArray();
	}

}
