package org.bartelby.configuration;

import java.util.ArrayDeque;

import org.bartelby.interfaces.Component;

public class ConfigurationRouter {

	public static ArrayDeque<Component> componentQueue = new ArrayDeque<Component>();
	
	public ConfigurationRouter() {
		super();
	}
	
	static public boolean exist(Object key) {
		return ConfigurationRouter.componentQueue.contains(key);
	}
	
	static public void add(Component value) {
		ConfigurationRouter.componentQueue.addFirst(value);
	}
	
	static public Object getQueueClone(){
		return ConfigurationRouter.componentQueue.clone();
	}

}
