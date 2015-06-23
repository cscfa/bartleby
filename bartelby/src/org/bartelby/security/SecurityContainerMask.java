/**
 * 
 */
package org.bartelby.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * container pattern container.
 * 
 * Contain the childs that match
 * the same container as parent.
 * 
 * @author vallance
 *
 */
public class SecurityContainerMask<T> implements Serializable{

	/**
	 * Generated serial UID for serialization.
	 */
	private static final long serialVersionUID = 1120316231868664854L;

	protected String ContainerName;
	protected boolean grant;
	protected ArrayList<T> childs;

	/**
	 * Default constructor.
	 * 
	 * Container default constructor. Set grant to true, childs to empty
	 * ArrayList and container name to empty String.
	 */
	public SecurityContainerMask() {
		this.grant = true;
		this.ContainerName = "";
		this.childs = new ArrayList<T>();
	}
	
	/**
	 * Constructor.
	 * 
	 * Container default constructor. Set grant to
	 * true, childs to empty ArrayList and container name
	 * to the given name.
	 * 
	 * @param containerName	The container name.
	 */
	public SecurityContainerMask(String containerName) {
		this.grant = true;
		this.childs = new ArrayList<T>();
		this.ContainerName = containerName;
	}

	/**
	 * Constructor.
	 * 
	 * Container default constructor. Set grant to
	 * the given value, childs to empty ArrayList 
	 * and container name to the given name.
	 * 
	 * @param containerName	The container name.
	 * @param grant		The container access state.
	 */
	public SecurityContainerMask(String containerName, boolean grant) {
		super();
		this.ContainerName = containerName;
		this.grant = grant;
		this.childs = new ArrayList<T>();
	}

	/**
	 * Constructor.
	 * 
	 * Container complete constructor with container name,
	 * grant and childs.
	 * 
	 * @param containerName	The container name.
	 * @param grant		The container access state.
	 * @param childs	The container childs.
	 */
	public SecurityContainerMask(String containerName, boolean grant,
			ArrayList<T> childs) {
		super();
		this.ContainerName = containerName;
		this.grant = grant;
		this.childs = childs;
	}

	/**
	 * Get container name.
	 * 
	 * Get the current container name.
	 * 
	 * @return	the container name.
	 */
	public String getcontainerName() {
		return ContainerName;
	}

	/**
	 * Set up the container name.
	 * 
	 * Set up the current container name.
	 * 
	 * @param containerName	The container name.
	 */
	public void setcontainerName(String containerName) {
		this.ContainerName = containerName;
	}

	/**
	 * Check if container is granted.
	 * 
	 * Check if the container is accessible
	 * and return true if it is, or false.
	 * 
	 * @return	The access state of the container.
	 */
	public boolean isGrant() {
		return grant;
	}

	/**
	 * Set up the access.
	 * 
	 * Set up the access to the container. If
	 * the container is not granted, all of his
	 * childs will be hide.
	 * 
	 * @param grant	The grant access state.
	 */
	public void setGrant(boolean grant) {
		this.grant = grant;
	}

	/**
	 * Get the childs.
	 * 
	 * Get all of the container childs by
	 * acces to the ArrayList of childs.
	 * 
	 * @return	The ArrayList of childs.
	 */
	public ArrayList<T> getchilds() {
		return childs;
	}

	/**
	 * Set up the childs.
	 * 
	 * Set up the childs of the container
	 * by given new ArrayList to replace 
	 * current list.
	 * 
	 * @param childs	the ArrayList of new childs.
	 */
	public void setchilds(ArrayList<T> childs) {
		this.childs = childs;
	}
	
	/**
	 * Add child.
	 * 
	 * Adding a child into the container.
	 * 
	 * @param child	the child to add.
	 */
	public void addchild(T child){
		this.childs.add(child);
	}

}
