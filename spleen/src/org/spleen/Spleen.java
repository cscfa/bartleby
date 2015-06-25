package org.spleen;

import java.util.HashMap;

import org.spleen.collection.NamespaceInterface;
import org.spleen.collection.NamespaceMap;
import org.spleen.config.SpleenConfigurator;
import org.spleen.type.CacheObject;
import org.spleen.type.Sizeable;

/**
 * Spleen class.
 * 
 * Default spleen cache 
 * system main class.
 * 
 * @author vallance
 *
 */
public class Spleen implements Sizeable {

	protected HashMap<String, NamespaceInterface> namespacesObjects;
	
	/**
	 * Default constructor.
	 * 
	 * Spleen default constructor.
	 */
	public Spleen() {
		this.namespacesObjects = new HashMap<String, NamespaceInterface>();
	}

	/**
	 * Create a new namespace.
	 * 
	 * Create a new simple NamespaceMap
	 * and tell it the configuration.
	 * 
	 * @param key		The new namespace identifier.
	 * @param config	The new namespace configuration.
	 */
	public void createNamespace(String key, SpleenConfigurator config){
		if(!this.namespacesObjects.containsKey(key)){
			this.namespacesObjects.put(key, (new NamespaceMap(config)));
		}
	}
	
	/**
	 * Remove a namespace.
	 * 
	 * Remove a stored namespace.
	 * 
	 * @param key	The namespace identifier.
	 */
	public void removeNamespace(String key){
		if(this.namespacesObjects.containsKey(key)){
			this.namespacesObjects.remove(key);
		}
	}
	
	/**
	 * Get a namespace.
	 * 
	 * Get a stored namespace.
	 * 
	 * @param key	The namespace identifier.
	 * @return		The namespace.
	 */
	public NamespaceInterface get(String key){
		if(this.namespacesObjects.containsKey(key)){
			return this.namespacesObjects.get(key);
		}else{
			return null;
		}
	}
	
	/**
	 * Get an object.
	 * 
	 * Get an object from a namespace.
	 * 
	 * @param namespaceKey	The namespace identifier.
	 * @param key			The cache object identifier.
	 * @return				The object from the namespace or null if isn't exist.
	 */
	public CacheObject get(String namespaceKey, String key){
		if(this.namespacesObjects.containsKey(namespaceKey)){
			return this.namespacesObjects.get(namespaceKey).get(key);
		}else{
			return null;
		}
	}
	
	/**
	 * Put cache object.
	 * 
	 * Insert a new cache object into
	 * a stored namespace.
	 * 
	 * @param namespaceKey	The namespace indentifier.
	 * @param key			The namespace cache object identifier to store.
	 * @param obj			The objet to store.
	 */
	public void put(String namespaceKey, String key, CacheObject obj){
		if(this.namespacesObjects.containsKey(namespaceKey)){
			this.namespacesObjects.get(namespaceKey).add(key, obj);;
		}
	}

	/**
	 * @see org.spleen.type.Sizeable#getSize()
	 */
	public double getSize() {
		Object[] keys = this.namespacesObjects.keySet().toArray();
		double size = 0;
		
		for (Object key : keys) {
			size += this.namespacesObjects.get(key).getSize();
		}
		return size;
	}

}
