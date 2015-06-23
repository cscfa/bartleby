/**
 * 
 */
package org.bartelby.tools;


/**
 * @author vallance
 *
 */
public class MaxValueMap<I, V> {

	protected Comparable<I> key = null;
	protected Comparable<V> value = null;

	public MaxValueMap() {
	}
	
	/**
	 * 
	 */
	public MaxValueMap(Comparable<I> index, Comparable<V> value) {
		this.key = index;
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public void insert(Comparable<I> index, Comparable<V> value){
		
		if((this.key == null || this.value == null) || this.value.compareTo((V) value) < 0 ){
			this.key = index;
			this.value = value;
		}
	}

	public I getKey(){
		return (I) this.key;
	}
	
	public V get(){
		return (V) this.value;
	}
	
	public boolean isEmpty(){
		return this.value == null && this.key == null;
	}
}
