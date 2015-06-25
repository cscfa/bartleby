package org.spleen.type;

import org.spleen.config.SpleenConfigurator;
import org.spleen.tool.Sizeof;

public class SimpleCacheObject implements CacheObject {

	private Long storedTime;
	private Long timeOut;
	private double size;
	protected Object cacheObject;
	
	
	/**
	 * Default constructor.
	 * 
	 * Default SimpleCacheObject constructor.
	 * 
	 * @param cacheObject the object to cache. Won't be initialized.
	 */
	public SimpleCacheObject(Object cacheObject) {
		this.setCacheObject(cacheObject);
	}
	
	/**
	 * @see org.spleen.type.CacheObject#getStoredTime()
	 */
	@Override
	public Long getStoredTime() {
		return this.storedTime;
	}

	/**
	 * @see org.spleen.type.CacheObject#setStoredTime(java.lang.Long)
	 */
	@Override
	public synchronized void setStoredTime(Long storedTime) {
		this.storedTime = storedTime;
	}

	/**
	 * @see org.spleen.type.CacheObject#getTimeOut()
	 */
	@Override
	public Long getTimeOut() {
		return this.timeOut;
	}

	/**
	 * @see org.spleen.type.CacheObject#setTimeOut(java.lang.Long)
	 */
	@Override
	public synchronized void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * @see org.spleen.type.CacheObject#getCacheObject()
	 */
	@Override
	public Object getCacheObject() {
		return this.cacheObject;
	}

	/**
	 * @see org.spleen.type.CacheObject#setCacheObject(java.lang.Object)
	 */
	@Override
	public synchronized void setCacheObject(Object cacheObject) {
		this.size = 16 + Sizeof.sizeof(cacheObject);
		this.cacheObject = cacheObject;
	}

	/**
	 * @see org.spleen.type.CacheObject#init(org.spleen.config.SpleenConfigurator)
	 */
	@Override
	public void init(SpleenConfigurator config) {
		this.storedTime = System.currentTimeMillis();
		this.timeOut = this.storedTime + config.getCacheTimeout();
	}

	/**
	 * @see org.spleen.type.Sizeable#getSize()
	 */
	@Override
	public double getSize() {
		return this.size;
	}

}
