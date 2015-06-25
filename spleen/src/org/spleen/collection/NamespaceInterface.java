package org.spleen.collection;

import java.util.LinkedList;

import org.spleen.config.SpleenConfigurator;
import org.spleen.type.CacheObject;
import org.spleen.type.Sizeable;

/**
 * Namespace interface.
 * 
 * This interface define namespace usage.
 * 
 * @author vallance
 *
 */
public interface NamespaceInterface extends Sizeable {
	
	/**
	 * Add CacheObject.
	 * 
	 * Insert a new cache object into
	 * the cache storage.
	 * 
	 * @param key			The cache object identifier.
	 * @param cacheObject	The object to store.
	 */
	public void add(String key, CacheObject cacheObject);
	
	/**
	 * Remove a cached object.
	 * 
	 * Remove a stored object from
	 * the cache objects storage.
	 * 
	 * @param key	The cache object identifier.
	 */
	public void remove(Object key);
	
	/**
	 * Remove first object.
	 * 
	 * Remove the first inserted cache
	 * object from the storage.
	 */
	public void removeFirst();
	
	/**
	 * Get config.
	 * 
	 * Return the current configuration.
	 * 
	 * @return	The current SpleenConfigurator.
	 */
	public SpleenConfigurator getConfig();

	/**
	 * Get the cache object list.
	 * 
	 * Get the cached object list that
	 * reference objects in the insertion
	 * order.
	 * 
	 * @return	The list of objects in insertion order.
	 */
	public LinkedList<String> getCacheObjectOrder();
	
	/**
	 * Get empty state.
	 * 
	 * Get the empty state of the 
	 * storage.
	 * 
	 * @return	A boolean that represent the empty state of the storage.
	 */
	public boolean isEmpty();
	
	/**
	 * Get objects count.
	 * 
	 * Get the count of the objects stored.
	 * 
	 * @return	The object count.
	 */
	public int getCount();
	
	/**
	 * Get the keySet.
	 * 
	 * Get the complete keySet of the
	 * cached objects.
	 * 
	 * @return	The complete keySet of the cached objects.
	 */
	public Object[] keySet();
	
	/**
	 * Up a cached object.
	 * 
	 * Reset the time out of a cached
	 * object.
	 * 
	 * @param key	The cached object key.
	 */
	public void up(String key);
	
	/**
	 * Get a cached object.
	 * 
	 * Get a stored cache object by it
	 * identifier.
	 * 
	 * @param key	The cached object identifier.
	 * @return		The cached object.
	 */
	public CacheObject get(Object key);
	
}
