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
import org.bartelby.security.UserSecurityProcessor;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;

public class SecurityComponent implements Component {
	
	public static final String NAME = "security";

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
				result.addMessages("Security.ip.response", "deny ip");
				result.addMessages("Security.ip.response.ip", container.getRequest().getClientIp());
				
				return result;
			}else{
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("grant access for ip : "+container.getRequest().getClientIp());
				if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
					((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tgrant access for ip : "+container.getRequest().getClientIp());
				}
				result.addMessages("Security.ip.response", "granted ip");
				result.addMessages("Security.ip.response.ip", container.getRequest().getClientIp());
			}
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
				result.addMessages("Security.user.response", "deny user");
				result.addMessages("Security.user.response.user", container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM));
				result.addMessages("Security.user.response.key", container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_KEY_URL_PARAM));
				
				return result;
			}else{
				String username = container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM);
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("grant access for user : "+username);
				if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
					((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tgrant access for user : "+username);
				}
				result.addMessages("Security.user.response", "granted user");
				result.addMessages("Security.user.response.user", container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_URL_PARAM));
				result.addMessages("Security.user.response.key", container.getRequest().getUrlParam().get(StringRessource.DEFAULT_SECURITY_USER_KEY_URL_PARAM));
			}
		}

		result.setCode(100);
		result.setCodeStatus("Continue");
		result.setContentType("text/plain");
		result.setResponseText("100 : Continue");
		
		return result;
	}

}
