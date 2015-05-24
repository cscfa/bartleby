package org.bartelby.configuration;

import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationParameters {
	
	private static ConcurrentHashMap<String, Object> mappedValues = new ConcurrentHashMap<String, Object>();

	public ConfigurationParameters() {
		super();
	}
	
	static public void put(String key, Object value) {
		ConfigurationParameters.mappedValues.put(key, value);
	}
	
	static public Object get(String key){
		if(ConfigurationParameters.mappedValues.containsKey(key)){
			return ConfigurationParameters.mappedValues.get(key);
		}else{
			return null;
		}
	}

}
