package org.spleen.threading;

import org.spleen.collection.NamespaceMap;
import org.spleen.type.CacheObject;

/**
 * CacheOutRemover class.
 * 
 * MultiThread class that remove automatically
 * the out of date cache objects. Also, remove this 
 * objects in order of insertion if the memory
 * space allowed by the configuration is
 * overload.
 * 
 * @author vallance
 *
 */
public class CacheOutRemover implements Runnable {

	private boolean stop = false;
	private NamespaceMap observable;
	
	/**
	 * Default constructor.
	 * 
	 * Construct and refer the NamespaceMap as
	 * observable parent to remove CacheObjects.
	 * 
	 * @param observable	The NamespaceMap parent.
	 */
	public CacheOutRemover(NamespaceMap observable) {
		this.observable = observable;
	}
	
	/**
	 * Run observer.
	 * 
	 * Observe NamespaceMap and automatically
	 * remove out of date object and objects in 
	 * order of insertion if the memory
	 * space allowed by the configuration is
	 * overload.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		while(!this.stop){
			Object[] observablesKeys = this.observable.keySet();

			for (Object key : observablesKeys) {
				CacheObject objTmp = this.observable.get(key);
				
				if(System.currentTimeMillis() > objTmp.getTimeOut()){
					this.observable.remove(key);
				}
			}

			double maxSize = this.observable.getConfig().getMaxSize();
			int maxCount = this.observable.getConfig().getMaxCount();
			while(this.observable.getSize() > maxSize || this.observable.getCount() > maxCount){
				this.observable.removeFirst();
			}

			try { Thread.sleep(this.observable.getConfig().getRemoverSheduleTime()); } catch(InterruptedException e) { /* we tried */}
		}
		
	}

	/**
	 * Get stop.
	 * 
	 * Inform if the thread stop
	 * is requested or set to true.
	 * 
	 * @return	The stop state.
	 */
	public boolean isStop() {
		return stop;
	}

	/**
	 * Set up stop.
	 * 
	 * Set stop. If stop is set to true,
	 * the obeserver will stop and the thread
	 * will finish. The stop state is false
	 * by default.
	 * 
	 * @param stop	The stop requester
	 */
	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
