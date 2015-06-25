package org.spleen.threading;

import org.spleen.collection.NamespaceMap;
import org.spleen.tool.Sizeof;
import org.spleen.type.CacheObject;

public class CacheOutRemover implements Runnable {

	private boolean stop = false;
	private NamespaceMap observable;
	
	public CacheOutRemover(NamespaceMap observable) {
		this.observable = observable;
	}
	
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

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
