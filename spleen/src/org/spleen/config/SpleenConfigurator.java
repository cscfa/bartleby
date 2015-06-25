package org.spleen.config;

public class SpleenConfigurator {

	protected long removerSheduleTime = 5l;
	protected long cacheTimeout = 1000l;
	protected double maxSize = 1_048_576;
	protected int maxCount = 100;
	
	public SpleenConfigurator() {
		// TODO Auto-generated constructor stub
	}

	public long getRemoverSheduleTime() {
		return this.removerSheduleTime;
	}

	public void setRemoverSheduleTime(long removerSheduleTime) {
		this.removerSheduleTime = removerSheduleTime;
	}

	public long getCacheTimeout() {
		return this.cacheTimeout;
	}

	public void setCacheTimeout(long cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}

	public double getMaxSize() {
		return this.maxSize;
	}

	public void setMaxSize(double maxSize) {
		this.maxSize = maxSize;
	}

	public int getMaxCount() {
		return this.maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

}
