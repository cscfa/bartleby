/**
 * 
 */
package org.bartelby.security;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * System mask.
 * 
 * System containers mask.
 * 
 * @author vallance
 *
 */
@SuppressWarnings("rawtypes")
public class SecuritySystemMask implements Serializable {

	/**
	 * Generated serial UID for serialization.
	 */
	private static final long serialVersionUID = -5700339400147290435L;

	protected String system;
	protected boolean grant;
	protected ArrayList<SecurityContainerMask> Containers;

	/**
	 * Default constructor.
	 * 
	 * System default constructor. Set grant to true, containers to empty
	 * ArrayList and system name to empty String.
	 */
	public SecuritySystemMask() {
		this.grant = true;
		this.Containers = new ArrayList<SecurityContainerMask>();
		this.system = "";
	}

	/**
	 * Default constructor.
	 * 
	 * System default constructor. Set grant to true, containers to empty
	 * ArrayList and system name to given String.
	 * 
	 * @param system	the system name.
	 */
	public SecuritySystemMask(String system) {
		this.grant = true;
		this.Containers = new ArrayList<SecurityContainerMask>();
		this.system = system;
	}

	/**
	 * Default constructor.
	 * 
	 * System default constructor. Set grant to given value, containers 
	 * to empty ArrayList and system name to given String.
	 * 
	 * @param system	the system name.
	 * @param grant		the grant access rule.
	 */
	public SecuritySystemMask(String system, boolean grant) {
		this.system = system;
		this.grant = grant;
		this.Containers = new ArrayList<SecurityContainerMask>();
	}

	/**
	 * Complete constructor.
	 * 
	 * The complete system mask constructor with system name,
	 * grant rules and containers.
	 * 
	 * @param system		the system name.
	 * @param grant			the grant rule.
	 * @param containers	the containers.
	 */
	public SecuritySystemMask(String system, boolean grant,
			ArrayList<SecurityContainerMask> containers) {
		this.system = system;
		this.grant = grant;
		Containers = containers;
	}

	/**
	 * Get system name.
	 * 
	 * Return the system name.
	 * 
	 * @return	the system name.
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * Set up system.
	 * 
	 * Set up the system name.
	 * 
	 * @param system	the system name.
	 */
	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * Check if system is grant.
	 * 
	 * Check if the system is accessible. Return true if it is
	 * or false.
	 * 
	 * @return	the access rule state.
	 */
	public boolean isGrant() {
		return grant;
	}

	/**
	 * Set up grant.
	 * 
	 * Set up the access rules of the system. If
	 * the system is not granted, all of his
	 * containers will be hide.
	 * 
	 * @param grant	the access rule.
	 */
	public void setGrant(boolean grant) {
		this.grant = grant;
	}

	/**
	 * Get containers.
	 * 
	 * Get all of the system containers.
	 * 
	 * @return	The containers as ArrayList.
	 */
	public ArrayList<SecurityContainerMask> getContainers() {
		return Containers;
	}

	/**
	 * Set up the containers.
	 * 
	 * Set up the containers of the system.
	 * 
	 * @param containers	the system containers.
	 */
	public void setContainers(ArrayList<SecurityContainerMask> containers) {
		Containers = containers;
	}

}
