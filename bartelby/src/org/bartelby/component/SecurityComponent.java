package org.bartelby.component;

import org.bartelby.configuration.ConfigurationServer;
import org.bartelby.console.ConsoleArgument;
import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.insideRouter.Transient;
import org.bartelby.insideRouter.TransientCarrier;
import org.bartelby.interfaces.Component;
import org.bartelby.ressources.StringRessource;
import org.bartelby.security.IpSecurityProcessor;
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
		// TODO Auto-generated method stub
		
		IpSecurityProcessor ipValidator = new IpSecurityProcessor(container);
		
		if(!ipValidator.validRequest()){
			((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("deny access for ip : "+container.getRequest().getClientIp());
			if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tdeny access for ip : "+container.getRequest().getClientIp());
			}
			
			Transient result = new Transient(403, "Forbiden");
			result.setContentType("text/plain");
			result.setResponseText("Error 403 : Permission denied");
			
			return result;
		}else{
			((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("grant access for ip : "+container.getRequest().getClientIp());
			if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tgrant access for ip : "+container.getRequest().getClientIp());
			}
		}

		Transient result = new Transient(100, "Continue");
		result.setContentType("text/plain");
		result.setResponseText("100 : Continue");
		
		return result;
	}

}
