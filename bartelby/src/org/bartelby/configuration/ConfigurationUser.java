package org.bartelby.configuration;

import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationUser {
	
	public static ConcurrentHashMap<String, Object> mappedValues = new ConcurrentHashMap<String, Object>();

	public ConfigurationUser() {
		super();
	}
	
	static public boolean exist(Object key) {
		return ConfigurationUser.mappedValues.containsKey(key);
	}
	
	static public void put(String key, Object value) {
		ConfigurationUser.mappedValues.put(key, value);
	}
	
	static public Object get(String key){
		if(ConfigurationUser.mappedValues.containsKey(key)){
			return ConfigurationUser.mappedValues.get(key);
		}else{
			return null;
		}
	}

}
