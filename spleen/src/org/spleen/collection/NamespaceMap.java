package org.spleen.collection;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import org.spleen.config.SpleenConfigurator;
import org.spleen.threading.CacheOutRemover;
import org.spleen.type.CacheObject;

/**
 * NamespaceMap class.
 * 
 * Simple namespace storage.
 * 
 * @author vallance
 *
 */
public class NamespaceMap implements NamespaceInterface {

	protected ConcurrentHashMap<String, CacheObject> namespaces;
	protected SpleenConfigurator config;
	protected LinkedList<String> cacheObjectOrder;
	private CacheOutRemover cacheOutRemover;
	private Thread cacheOutThread;
	private double size = 0;
	
	/**
	 * Default constructor.
	 * 
	 * Default constructor that set the configuration.
	 * 
	 * @param config	The namespace configuration.
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

	/**
	 * @see org.spleen.collection.NamespaceInterface#add(java.lang.String, org.spleen.type.CacheObject)
	 */
	public synchronized void add(String key, CacheObject cacheObject){
		cacheObject.init(this.config);
		this.namespaces.put(key, cacheObject);
		this.cacheObjectOrder.add(key);
		this.size += cacheObject.getSize();
	}
	
	/**
	 * @see org.spleen.collection.NamespaceInterface#remove(java.lang.Object)
	 */
	public synchronized void remove(Object key){
		if(this.namespaces.containsKey(key)){
			this.size -= this.namespaces.get(key).getSize();
			this.namespaces.remove(key);
			this.cacheObjectOrder.remove(key);
		}
	}
	
	/**
	 * @see org.spleen.collection.NamespaceInterface#removeFirst()
	 */
	public synchronized void removeFirst(){
		this.size -= this.namespaces.get(this.cacheObjectOrder.getFirst()).getSize();
		this.namespaces.remove(this.cacheObjectOrder.getFirst());
		this.cacheObjectOrder.removeFirst();
	}
	
	/**
	 * @see org.spleen.collection.NamespaceInterface#getConfig()
	 */
	public SpleenConfigurator getConfig() {
		return this.config;
	}

	/**
	 * Finalize object.
	 * 
	 * Finalize the object and set the
	 * remover observer Thread stop
	 * state to true.
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		this.cacheOutRemover.setStop(true);
		super.finalize();
	}

	/**
	 * @see org.spleen.collection.NamespaceInterface#getCacheObjectOrder()
	 */
	public LinkedList<String> getCacheObjectOrder() {
		return cacheObjectOrder;
	}
	
	/**
	 * @see org.spleen.collection.NamespaceInterface#isEmpty()
	 */
	public boolean isEmpty(){
		return this.namespaces.isEmpty();
	}

	/**
	 * @see org.spleen.type.Sizeable#getSize()
	 */
	public double getSize() {
		return this.size;
	}
	
	/**
	 * @see org.spleen.collection.NamespaceInterface#getCount()
	 */
	public int getCount(){
		return this.namespaces.size();
	}
	
	/**
	 * @see org.spleen.collection.NamespaceInterface#keySet()
	 */
	public Object[] keySet(){
		return this.namespaces.keySet().toArray();
	}
	
	/**
	 * @see org.spleen.collection.NamespaceInterface#up(java.lang.String)
	 */
	public synchronized void up(String key){
		this.namespaces.get(key).init(this.config);
	}
	
	/**
	 * @see org.spleen.collection.NamespaceInterface#get(java.lang.Object)
	 */
	public CacheObject get(Object key){
		if(this.namespaces.containsKey(key)){
			return this.namespaces.get(key);
		}else{
			return null;
		}
	}
	
}
