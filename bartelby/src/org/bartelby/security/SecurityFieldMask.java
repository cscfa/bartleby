/**
 * 
 */
package org.bartelby.security;

import java.io.Serializable;

/**
 * Security data field mask.
 * 
 * Mask pattern for data response.
 * 
 * @author vallance
 *
 */
public class SecurityFieldMask implements Serializable{

	/**
	 * Generated serialUID.
	 * 
	 * Generated serialUID for serialization.
	 */
	private static final long serialVersionUID = 622868855511300781L;
	
	protected boolean grant;
	protected String field;
	protected String replace;

	/**
	 * Default constructor.
	 * 
	 * Default empty constructor. Set grant to
	 * false and replace to 'forbidden'.
	 */
	public SecurityFieldMask() {
		this.grant = false;
		this.field = null;
		this.replace = "forbidden";
	}

	/**
	 * Constructor with field.
	 * 
	 * Set up field parameter from given
	 * value, set grant to false and replace
	 * to 'forbidden'.
	 * 
	 * @param fields	the field to match.
	 */
	public SecurityFieldMask(String fields) {
		super();
		this.field = fields;
		this.grant = false;
		this.replace = "forbidden";
	}

	/**
	 * Constructor with field and grant.
	 * 
	 * Set up field parameter from given
	 * value, set grant from given value
	 * and replace to 'forbidden'.
	 * 
	 * @param fields	the field to match.
	 * @param grant		boolean to mark as show or hide.
	 */
	public SecurityFieldMask(String fields, boolean grant) {
		super();
		this.grant = grant;
		this.field = fields;
		this.replace = "forbidden";
	}

	/**
	 * Complete constructor.
	 * 
	 * Complete constructor with grant,
	 * field and replace parameters.
	 * 
	 * @param fields	the field to match.
	 * @param grant		boolean to mark as show or hide.
	 * @param replace	the string to use as replacement.
	 */
	public SecurityFieldMask(String fields, boolean grant, String replace) {
		super();
		this.grant = grant;
		this.field = fields;
		this.replace = replace;
	}

	/**
	 * Check if field is granted.
	 * 
	 * Check if the field is currently accessible
	 * by the user behin the security parameters.
	 * 
	 * @return	true if the field can be accessed, or false.
	 */
	public boolean isGrant() {
		return grant;
	}

	/**
	 * Set up the field access.
	 * 
	 * Set up the field access data response.
	 * 
	 * @param grant	a boolean that represent the accessibility of the field.
	 */
	public void setGrant(boolean grant) {
		this.grant = grant;
	}

	/**
	 * Get field.
	 * 
	 * Return the current field name.
	 * 
	 * @return	the field name as String.
	 */
	public String getFields() {
		return field;
	}

	/**
	 * Set up the field.
	 * 
	 * Set up the field name to hide.
	 * 
	 * @param fields	The field name as String.
	 */
	public void setFields(String fields) {
		this.field = fields;
	}

	/**
	 * Set up the replacement.
	 * 
	 * Set up the String replacement of the 
	 * current field's data.
	 * 
	 * @return
	 */
	public String getReplace() {
		return replace;
	}

	/**
	 * Set up the replacement.
	 * 
	 * Set up the field data replacement to hide
	 * real data.
	 * 
	 * @param replace	a String that will be render as field.
	 */
	public void setReplace(String replace) {
		this.replace = replace;
	}

}
