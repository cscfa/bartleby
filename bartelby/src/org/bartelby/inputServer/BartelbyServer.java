package org.bartelby.inputServer;

import java.net.Socket;

import org.bartelby.console.ConsoleArgument;
import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.insideRouter.Router;
import org.bartelby.insideRouter.Transient;
import org.bartelby.insideRouter.TransientCarrier;
import org.bartelby.ressources.StringRessource;
import org.bartelby.service.ServiceContainer;
import org.bartelby.stat.BartelbySniffer;
import org.slf4j.Logger;

public class BartelbyServer implements Runnable {
	
	protected Socket connectedClient = null;
	long time;

	public BartelbyServer(Socket client) {
		this.connectedClient = client;
	}

	public void run() {
		
		this.time = System.currentTimeMillis();
		BartelbySniffer.addProcess();
		
		try {
			
			HTTPResourceContainer container = new HTTPResourceContainer(connectedClient);
			
			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("Client connection."+container.getRequest().getClientIp() + ":" + container.getRequest().getClientPort());
			if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tClient connection."+container.getRequest().getClientIp() + ":" + container.getRequest().getClientPort());
			}

			Router router = new Router(container);
			
			TransientCarrier routerTransients = router.process();
			
			Transient lastTransient = routerTransients.getLast();
			
			if(lastTransient != null){
				container.getResponse().setResponseCode(lastTransient.getCode(), lastTransient.getCodeStatus());
				container.getResponse().setContentType(lastTransient.getContentType());
				container.getResponse().sendResponse(lastTransient.getResponseText());
			}else{
				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("TransientCarrier do not contain response.");
				if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
					((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tTransientCarrier do not contain response.");
				}
				
				container.getResponse().setResponseCode(500, "Internal server error");
				container.getResponse().setContentType("text/plain");
				container.getResponse().sendResponse("Error 500 : Internal server error");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		BartelbySniffer.rmProcess();
		Long executionTime = (System.currentTimeMillis() - this.time);
		((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("Response send in "+executionTime+"ms");
		if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tResponse send in "+executionTime+"ms");
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		this.connectedClient.close();
		super.finalize();
	}
}
