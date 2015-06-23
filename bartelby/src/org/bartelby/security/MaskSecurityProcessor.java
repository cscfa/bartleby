/**
 * 
 */
package org.bartelby.security;

import java.util.ArrayList;

import org.bartelby.configuration.ConfigurationSecurity;
import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.insideRouter.Transient;
import org.bartelby.ressources.StringRessource;
import org.bartelby.tools.MaxValueMap;

/**
 * @author vallance
 *
 */
public class MaskSecurityProcessor {

	protected String user = null;
	protected String ip = null;
	
	/**
	 * 
	 */
	public MaskSecurityProcessor(HTTPResourceContainer container) {
		
		if(container.getRequest().getUrlParam().containsKey(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM)){
			this.user = container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM);
		}
		this.ip = container.getRequest().getClientIp();
	}
	
	public MaskPattern selectMask(){
		if(ConfigurationSecurity.exist("mask")){
			ArrayList<MaskPattern> masks = (ArrayList<MaskPattern>) ConfigurationSecurity.get("mask");
			MaxValueMap<Integer, Integer> maskSelector = new MaxValueMap<Integer, Integer>();
			
			for(Integer i = 0;i < masks.size();i++){
				MaskPattern currentMask = masks.get(i);
				
				if(currentMask.getIpRules().isEmpty() && currentMask.getUserRules().isEmpty()){
					maskSelector.insert(i, 1);
				}else if(currentMask.getUserRules().contains(this.user) && currentMask.getIpRules().contains(this.ip)){
					maskSelector.insert(i, 4);
				}else if(currentMask.getUserRules().contains(this.user) && !currentMask.getIpRules().contains(this.ip)){
					maskSelector.insert(i, 3);
				}else if(!currentMask.getUserRules().contains(this.user) && currentMask.getIpRules().contains(this.ip)){
					maskSelector.insert(i, 2);
				}
				
			}
			
			if(!maskSelector.isEmpty()){
				return masks.get(maskSelector.getKey());
			}else{
				return null;
			}
			
		}else{
			return null;
		}
	}

}
