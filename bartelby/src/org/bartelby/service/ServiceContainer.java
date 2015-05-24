package org.bartelby.service;

import java.util.concurrent.ConcurrentHashMap;

public class ServiceContainer {

	protected static ConcurrentHashMap<String, Object> serviceContainer = new ConcurrentHashMap<String, Object>();
	
	public ServiceContainer() {}
	
	static public Boolean exist(String service){
		if(ServiceContainer.serviceContainer.containsKey(service)){
			return true;
		}else{
			return false;
		}
	}
	
	static public Object get(String service){
		if(ServiceContainer.exist(service)){
			return ServiceContainer.serviceContainer.get(service);
		}else{
			return null;
		}
	}
	
	static public void set(String service, Object serviceElement){
		ServiceContainer.serviceContainer.put(service, serviceElement);
	}

}
