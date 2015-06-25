package org.spleen.type;

import org.spleen.config.SpleenConfigurator;

/**
 * Cache object Interface.
 * 
 * Interface of cached object that implements
 * time storage methods, object stored getter
 * and setters, sizeable tools.
 * 
 * @author vallance
 *
 */
public interface CacheObject extends Sizeable{
	
	/**
	 * Get stored time.
	 * 
	 * Get the CacheObject creation timestamp.
	 * 
	 * @return	the CacheObject creation timestamp.
	 */
	public Long getStoredTime();
	
	/**
	 * Set up stored time.
	 * 
	 * Set up the creation timestamp of
	 * the current object.
	 * 
	 * @param storedTime The new timestamp
	 */
	public void setStoredTime(Long storedTime);
	
	/**
	 * Get the timeout.
	 * 
	 * Get the timeout timestamp of
	 * the current object.
	 * 
	 * @return	The timeout timestamp.
	 */
	public Long getTimeOut();
	
	/**
	 * Set up timeout.
	 * 
	 * Set up the timeout timestamp of the
	 * current object.
	 * 
	 * @param timeOut The new timeout timestamp.
	 */
	public void setTimeOut(Long timeOut);
	
	/**
	 * Get the cached object.
	 * 
	 * Return the cached object of the current
	 * CacheObject.
	 * 
	 * @return the cached object.
	 */
	public Object getCacheObject();
	
	/**
	 * Set up the cache object.
	 * 
	 * Set up the cached object of the current
	 * CacheObject.
	 * 
	 * @param cacheObject The object to cache.
	 */
	public void setCacheObject(Object cacheObject);
	
	/**
	 * Init the CacheObject.
	 * 
	 * Automatic setup the creating timeStamp
	 * and the timeout timeStamp.
	 * 
	 * @param config The namespace configuration.
	 */
	public void init(SpleenConfigurator config);
	
}
