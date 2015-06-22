/**
 * 
 */
package org.bartelby.security;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bartelby.configuration.ConfigurationSecurity;
import org.bartelby.insideRouter.HTTPResourceContainer;

/**
 * IP security validator.
 * 
 * Allow or reject access of a specified IP.
 * 
 * @author vallance
 *
 */
public class IpSecurityProcessor {

	private final String DEFAULT = "default";
	private final String DEFAULT_DENY = "deny";
	private final String DEFAULT_GRANT = "grant";
	private final String DENY = "deny";
	private final String GRANT = "grant";
	
	protected HTTPResourceContainer container;
	private LinkedHashMap<String, Object> ipAccessConfig;
	
	/**
	 * Default constructor.
	 * 
	 * Default constructor with container import.
	 * 
	 * @param container	The current request container.
	 */
	public IpSecurityProcessor(HTTPResourceContainer container) {
		this.container = container;
		this.ipAccessConfig = (LinkedHashMap) ConfigurationSecurity.get("ipAccess");
	}
	
	public boolean validRequest(){
		String clientIp = container.getRequest().getClientIp();
		String defaultAccess = (String) this.ipAccessConfig.get(this.DEFAULT);
		
		if(defaultAccess.equals(this.DEFAULT_GRANT)){
			return this.detectDeny(clientIp, (ArrayList)this.ipAccessConfig.get(this.DENY));
		}else if(defaultAccess.equals(this.DEFAULT_DENY)){
			return this.validGrant(clientIp, (ArrayList)this.ipAccessConfig.get(this.GRANT));
		}
		
		return false;
	}
	
	protected boolean validGrant(String clientIp, ArrayList<String> allowed){
		if(allowed.contains(clientIp)){
			return true;
		}else{
			return false;
		}
	}
	
	protected boolean detectDeny(String clientIp, ArrayList<String> notAllowed){
		if(notAllowed.contains(clientIp)){
			return false;
		}else{
			return true;
		}
	}

}
