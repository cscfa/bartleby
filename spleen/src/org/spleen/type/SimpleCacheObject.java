package org.spleen.type;

import org.spleen.config.SpleenConfigurator;
import org.spleen.tool.Sizeof;

public class SimpleCacheObject implements CacheObject {

	private Long storedTime;
	private Long timeOut;
	private double size;
	protected Object cacheObject;
	
	
	public SimpleCacheObject(Object cacheObject) {
		this.setCacheObject(cacheObject);
	}
	
	@Override
	public Long getStoredTime() {
		return this.storedTime;
	}

	@Override
	public synchronized void setStoredTime(Long storedTime) {
		this.storedTime = storedTime;
	}

	@Override
	public Long getTimeOut() {
		return this.timeOut;
	}

	@Override
	public synchronized void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}

	@Override
	public Object getCacheObject() {
		return this.cacheObject;
	}

	@Override
	public synchronized void setCacheObject(Object cacheObject) {
		this.size = 16 + Sizeof.sizeof(cacheObject);
		this.cacheObject = cacheObject;
	}

	@Override
	public void init(SpleenConfigurator config) {
		this.storedTime = System.currentTimeMillis();
		this.timeOut = this.storedTime + config.getCacheTimeout();
	}

	@Override
	public double getSize() {
		return this.size;
	}

}
