package org.spleen.type;

/**
 * Sizeable Interface.
 * 
 * Sizeable interface implements method
 * that allow to retreive the memory space
 * used by an instance.
 * 
 * @author vallance
 *
 */
public interface Sizeable {

	/**
	 * Get size.
	 * 
	 * Return a memory space size in byte.
	 * 
	 * @return The memory space in byte.
	 */
	public double getSize();
	
}
