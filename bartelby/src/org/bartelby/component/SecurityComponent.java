package org.bartelby.component;

import org.bartelby.configuration.ConfigurationSecurity;
import org.bartelby.configuration.ConfigurationServer;
import org.bartelby.console.ConsoleArgument;
import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.insideRouter.Transient;
import org.bartelby.insideRouter.TransientCarrier;
import org.bartelby.interfaces.Component;
import org.bartelby.ressources.StringRessource;
import org.bartelby.security.IpSecurityProcessor;
import org.bartelby.security.MaskPattern;
import org.bartelby.security.MaskSecurityProcessor;
import org.bartelby.security.UserSecurityProcessor;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;

public class SecurityComponent implements Component {
	
	public static final String NAME = "security";
	
	public static final String IP = "Security.ip.response.ip";
	public static final String IP_EXIST = "Security.ip.response.ip.exist";
	public static final String IP_STATE = "Security.ip.response";
	
	public static final String USER = "Security.user.response.user";
	public static final String USER_KEY = "Security.user.response.key";
	public static final String USER_EXIST = "Security.user.response.user.exist";
	public static final String USER_STATE = "Security.user.response";

	public static final String MASK = "Security.mask.response.mask";
	public static final String MASK_EXIST = "Security.mask.response.exist";
	
	public SecurityComponent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return SecurityComponent.NAME;
	}

	@Override
	public Transient process(HTTPResourceContainer container,
			TransientCarrier transients) {

		Transient result = new Transient();
		// IP checking
		if(ConfigurationSecurity.exist("ipAccess")){
			IpSecurityProcessor ipValidator = new IpSecurityProcessor(container);
			if(!ipValidator.validRequest()){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("deny access for ip : "+container.getRequest().getClientIp());
				if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
					((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tdeny access for ip : "+container.getRequest().getClientIp());
				}
				
				result.setCode(403);
				result.setCodeStatus("Forbiden");
				result.setContentType("text/plain");
				result.setResponseText("Error 403 : Permission denied");
				result.addMessages(SecurityComponent.IP_STATE, "deny ip");
				result.addMessages(SecurityComponent.IP_EXIST, true);
				result.addMessages(SecurityComponent.IP, container.getRequest().getClientIp());
				
				return result;
			}else{
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("grant access for ip : "+container.getRequest().getClientIp());
				if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
					((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tgrant access for ip : "+container.getRequest().getClientIp());
				}
				result.addMessages(SecurityComponent.IP_STATE, "granted ip");
				result.addMessages(SecurityComponent.IP_EXIST, true);
				result.addMessages(SecurityComponent.IP, container.getRequest().getClientIp());
			}
		}else{
			result.addMessages(SecurityComponent.IP_EXIST, false);
		}

		//User checking
		if(ConfigurationSecurity.exist("userAccess")){
			UserSecurityProcessor userValidator = new UserSecurityProcessor(container);
			if(!userValidator.validRequest()){
				
				String username = "unknown";
				if(container.getRequest().getUrlParam().containsKey(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM)){
					username = container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM);
				}
				
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("deny access for user : "+username);
				if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
					((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tdeny access for user : "+username);
				}

				result.setCode(403);
				result.setCodeStatus("Forbiden");
				result.setContentType("text/plain");
				result.setResponseText("Error 403 : Permission denied");
				result.addMessages(SecurityComponent.USER_STATE, "deny user");
				result.addMessages(SecurityComponent.USER_EXIST, true);
				result.addMessages(SecurityComponent.USER, container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM));
				result.addMessages(SecurityComponent.USER_KEY, container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_KEY_URL_PARAM));
				
				return result;
			}else{
				String username = "unknown";
				if(container.getRequest().getUrlParam().containsKey(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM)){
					username = container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM);
				}
				
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("grant access for user : "+username);
				if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
					((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tgrant access for user : "+username);
				}
				result.addMessages(SecurityComponent.USER_STATE, "granted user");
				result.addMessages(SecurityComponent.USER_EXIST, true);
				result.addMessages(SecurityComponent.USER, container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM));
				result.addMessages(SecurityComponent.USER_KEY, container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_KEY_URL_PARAM));
			}
		}else{
			result.addMessages(SecurityComponent.USER_EXIST, false);
		}

		//Create mask
		if(ConfigurationSecurity.exist("mask")){
			MaskSecurityProcessor maskProcessor = new MaskSecurityProcessor(container);
			MaskPattern securityMask = maskProcessor.selectMask();
			
			if(securityMask != null){
				result.addMessages(SecurityComponent.MASK_EXIST, true);
				result.addMessages(SecurityComponent.MASK, securityMask);
			}else{
				result.addMessages(SecurityComponent.MASK_EXIST, false);
			}
		}else{
			result.addMessages(SecurityComponent.MASK_EXIST, false);
		}
		
		result.setCode(100);
		result.setCodeStatus("Continue");
		result.setContentType("text/plain");
		result.setResponseText("100 : Continue");
		
		return result;
	}

}
