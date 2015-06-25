package org.spleen.type;

import org.spleen.config.SpleenConfigurator;

public interface CacheObject extends Sizeable{
	
	public Long getStoredTime();
	
	public void setStoredTime(Long storedTime);
	
	public Long getTimeOut();
	
	public void setTimeOut(Long timeOut);
	
	public Object getCacheObject();
	
	public void setCacheObject(Object cacheObject);
	
	public void init(SpleenConfigurator config);
	
}
