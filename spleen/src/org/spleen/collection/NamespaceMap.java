package org.spleen.collection;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import org.spleen.config.SpleenConfigurator;
import org.spleen.threading.CacheOutRemover;
import org.spleen.type.CacheObject;
import org.spleen.type.Sizeable;

public class NamespaceMap implements Sizeable {

	protected ConcurrentHashMap<String, CacheObject> namespaces;
	protected SpleenConfigurator config;
	protected LinkedList<String> cacheObjectOrder;
	private CacheOutRemover cacheOutRemover;
	private Thread cacheOutThread;
	private double size = 0;
	
	/**
	 * 
	 */
	public NamespaceMap(SpleenConfigurator config) {
		this.cacheOutRemover = new CacheOutRemover(this);
		this.cacheOutThread = new Thread(this.cacheOutRemover);
		this.namespaces = new ConcurrentHashMap<String, CacheObject>();
		this.config = config;
		this.cacheObjectOrder = new LinkedList<String>();
		
		this.cacheOutThread.setDaemon(true);
		this.cacheOutThread.start();
	}

	public ConcurrentHashMap<String, CacheObject> getNamespaces() {
		return namespaces;
	}
	
	public synchronized void add(String key, CacheObject cacheObject){
		cacheObject.init(this.config);
		this.getNamespaces().put(key, cacheObject);
		this.cacheObjectOrder.add(key);
		this.size += cacheObject.getSize();
	}
	
	public synchronized void remove(Object key){
		if(this.namespaces.containsKey(key)){
			this.size -= this.namespaces.get(key).getSize();
			this.namespaces.remove(key);
			this.cacheObjectOrder.remove(key);
		}
	}
	
	public synchronized void removeFirst(){
		this.size -= this.getNamespaces().get(this.cacheObjectOrder.getFirst()).getSize();
		this.getNamespaces().remove(this.cacheObjectOrder.getFirst());
		this.cacheObjectOrder.removeFirst();
	}
	
	public SpleenConfigurator getConfig() {
		return this.config;
	}

	@Override
	protected void finalize() throws Throwable {
		this.cacheOutRemover.setStop(true);
		super.finalize();
	}

	public LinkedList<String> getCacheObjectOrder() {
		return cacheObjectOrder;
	}
	
	public boolean isEmpty(){
		return this.namespaces.isEmpty();
	}

	@Override
	public double getSize() {
		return this.size;
	}
	
	public int getCount(){
		return this.namespaces.size();
	}
	
	public Object[] keySet(){
		return this.namespaces.keySet().toArray();
	}
	
	public synchronized void up(String key){
		this.namespaces.get(key).init(this.config);
	}
	
	public CacheObject get(Object key){
		if(this.namespaces.containsKey(key)){
			return this.namespaces.get(key);
		}else{
			return null;
		}
	}
	
}
