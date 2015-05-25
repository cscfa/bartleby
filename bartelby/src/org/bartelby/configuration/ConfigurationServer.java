package org.bartelby.configuration;

import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationServer {
	
	public static ConcurrentHashMap<String, Object> mappedValues = new ConcurrentHashMap<String, Object>();

	public ConfigurationServer() {
		super();
	}
	
	static public boolean exist(Object key) {
		return ConfigurationServer.mappedValues.containsKey(key);
	}
	
	static public void put(String key, Object value) {
		ConfigurationServer.mappedValues.put(key, value);
	}
	
	static public Object get(String key){
		if(ConfigurationServer.mappedValues.containsKey(key)){
			return ConfigurationServer.mappedValues.get(key);
		}else{
			return null;
		}
	}

}
