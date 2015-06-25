package org.spleen.config;

/**
 * Spleen configuration class.
 * 
 * The spleen configuration is used by
 * the created namespace to define the
 * cache limits and the schedules.
 * 
 * @author vallance
 *
 */
public class SpleenConfigurator {

	protected long removerSheduleTime = 5l;
	protected long cacheTimeout = 1000l;
	protected double maxSize = 1_048_576;
	protected int maxCount = 100;
	
	/**
	 * Default constructor.
	 * 
	 * Default configuration constructor.
	 * Set removerSheduleTime to 5 
	 * milliseconds, cacheTimeout to 1
	 * second, maxSize to 1 megaBit.
	 */
	public SpleenConfigurator() {
		super();
	}

	/**
	 * Spleen configuration constructor.
	 * 
	 * Spleen complete constructor.
	 * 
	 * @param removerSheduleTime	The schedule time for the deletion search of the CacheOutRemover thread.
	 * @param cacheTimeout			The cache time range of the CacheObjects.
	 * @param maxSize				The max bit size of the NamespaceMap stored objects.
	 * @param maxCount				The max count of the NamespaceMap stored objects.
	 */
	public SpleenConfigurator(long removerSheduleTime, long cacheTimeout,
			double maxSize, int maxCount) {
		super();
		this.removerSheduleTime = removerSheduleTime;
		this.cacheTimeout = cacheTimeout;
		this.maxSize = maxSize;
		this.maxCount = maxCount;
	}

	/**
	 * Get the removerSheduleTime.
	 * 
	 * Return the schedule time for the 
	 * deletion search of the CacheOutRemover 
	 * thread
	 * 
	 * @return	The schedule time.
	 */
	public long getRemoverSheduleTime() {
		return this.removerSheduleTime;
	}

	/**
	 * Set up the removerSheduleTime.
	 * 
	 * Set up the schedule time for the 
	 * deletion search of the CacheOutRemover 
	 * thread
	 * 
	 * @param removerSheduleTime	The new time for the deletion search schedule.
	 */
	public void setRemoverSheduleTime(long removerSheduleTime) {
		this.removerSheduleTime = removerSheduleTime;
	}

	/**
	 * Get the cacheTimeout.
	 * 
	 * Get the cache time range of 
	 * the CacheObjects.
	 * 
	 * @return	The cache time range of the CacheObjects.
	 */
	public long getCacheTimeout() {
		return this.cacheTimeout;
	}

	/**
	 * Set up the cacheTimeout.
	 * 
	 * Set up the cache time range 
	 * of the CacheObjects.
	 * 
	 * @param cacheTimeout	The new time range.
	 */
	public void setCacheTimeout(long cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}

	/**
	 * Get the maxSize.
	 * 
	 * Get the max bit size of 
	 * the NamespaceMap stored objects.
	 * 
	 * @return	The max bit size of the NamespaceMap stored objects.
	 */
	public double getMaxSize() {
		return this.maxSize;
	}

	/**
	 * Set up the maxSize.
	 * 
	 * Set up the max bit size of the 
	 * NamespaceMap stored objects.
	 * 
	 * @param maxSize	The new max bit size.
	 */
	public void setMaxSize(double maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * Get max count.
	 * 
	 * Get the max count of the 
	 * NamespaceMap stored objects.
	 * 
	 * @return	The max count of the NamespaceMap stored objects.
	 */
	public int getMaxCount() {
		return this.maxCount;
	}

	/**
	 * Set up maxCount.
	 * 
	 * Set up the max count of the 
	 * NamespaceMap stored objects.
	 * 
	 * @param maxCount	The new max count.
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

}
