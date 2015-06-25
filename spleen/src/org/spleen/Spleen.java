package org.spleen;

import java.util.HashMap;

import org.spleen.collection.NamespaceMap;
import org.spleen.config.SpleenConfigurator;
import org.spleen.type.CacheObject;
import org.spleen.type.Sizeable;

public class Spleen implements Sizeable {

	protected HashMap<String, NamespaceMap> namespacesObjects;
	
	public Spleen() {
		this.namespacesObjects = new HashMap<String, NamespaceMap>();
	}

	public void createNamespace(String key, SpleenConfigurator config){
		if(!this.namespacesObjects.containsKey(key)){
			this.namespacesObjects.put(key, (new NamespaceMap(config)));
		}
	}
	
	public void removeNamespace(String key){
		if(this.namespacesObjects.containsKey(key)){
			this.namespacesObjects.remove(key);
		}
	}
	
	public NamespaceMap get(String key){
		if(this.namespacesObjects.containsKey(key)){
			return this.namespacesObjects.get(key);
		}else{
			return null;
		}
	}
	
	public CacheObject get(String namespaceKey, String key){
		if(this.namespacesObjects.containsKey(namespaceKey)){
			return this.namespacesObjects.get(namespaceKey).get(key);
		}else{
			return null;
		}
	}
	
	public void put(String namespaceKey, String key, CacheObject obj){
		if(this.namespacesObjects.containsKey(namespaceKey)){
			this.namespacesObjects.get(namespaceKey).add(key, obj);;
		}
	}

	@Override
	public double getSize() {
		Object[] keys = this.namespacesObjects.keySet().toArray();
		double size = 0;
		
		for (Object key : keys) {
			size += this.namespacesObjects.get(key).getSize();
		}
		return size;
	}

}
