/**
 * 
 */
package org.bartelby.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bartelby.configuration.ConfigurationSecurity;
import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.ressources.StringRessource;

/**
 * Security user access validator.
 * 
 * Allow access to data for user referenced
 * into the configuration files.
 * 
 * @author vallance
 *
 */
public class UserSecurityProcessor {

	protected HTTPResourceContainer container;
	private LinkedHashMap<String, Object> userAccess;
	
	/**
	 * Default constructor;
	 * 
	 * Default constructor with container
	 * registration and user access rules
	 * import.
	 * 
	 * @param container	The current container.
	 */
	public UserSecurityProcessor(HTTPResourceContainer container) {
		this.container = container;
		this.userAccess = (LinkedHashMap<String, Object>) ConfigurationSecurity.get("userAccess");
	}
	
	/**
	 * Valid request access.
	 * 
	 * Valid the access for the request if the request user
	 * is granted.
	 * 
	 * @return	true if the request user is valid. False if not.
	 */
	public boolean validRequest(){
		
		HashMap<String, String> urlParam = this.container.getRequest().getUrlParam();

		if(this.userAccess.get("default").toString().equals("grant")){
			return true;
		}else if(urlParam.containsKey(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM) &&
				urlParam.containsKey(StringRessource.DEFAULT_SECURITY_USER_KEY_URL_PARAM)){
			String user = urlParam.get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM);
			String userKey = urlParam.get(StringRessource.DEFAULT_SECURITY_USER_KEY_URL_PARAM);
			
			if(this.userAccess.containsKey(user)){
				
				LinkedHashMap userDefinition = (LinkedHashMap)this.userAccess.get(user);
				
				if(userKey.equals(userDefinition.get("key").toString())){
					if(userDefinition.containsKey("ip")){
						if(((ArrayList<String>)userDefinition.get("ip")).contains(this.container.getRequest().getClientIp())){
							return true;
						}else{
							return false;
						}
					}else{
						return true;
					}
				}else{
					return false;
				}
				
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}

}
