package org.bartelby.inputServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bartelby.configuration.ConfigurationParameters;
import org.bartelby.configuration.ConfigurationServer;
import org.bartelby.console.ConsoleArgument;
import org.bartelby.ressources.StringRessource;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;

public class BartelbyServerListener extends Thread {
	
	public BartelbyServerListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("Start server socket on port "+ (Integer) ConfigurationServer.get("port") + ".");
		if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("Start server socket on port "+ (Integer) ConfigurationServer.get("port") + ".");
		}
		
		ServerSocket socket = null;
		
		try {
			socket = new ServerSocket((Integer) ConfigurationServer.get("port"));
		} catch (IOException e) {
			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("Starting server fail.");
			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("Starting fail");
			if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("Starting server fail.");
			}

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).trace(sw.toString());
			pw.close();
			try {
				sw.close();
			} catch (IOException e1) {
			}
			((ConcurrentHashMap)ConfigurationParameters.get(StringRessource.DEFAULT_SERVER_SPACE)).put(StringRessource.DEFAULT_STATUS_SPACE, StringRessource.DEFAULT_STATUS_SPACE_STOP);
			return;
		}

		Socket soc = null;
		int soTimeout = 1000;
		
		if(ConfigurationServer.get("socketTimeOut") != null && (int)ConfigurationServer.get("socketTimeOut") > 1000){
			soTimeout = 1000;
		}else if(ConfigurationServer.get("socketTimeOut") != null && (int)ConfigurationServer.get("socketTimeOut") <= 10){
			soTimeout = 10;
		}else if(ConfigurationServer.get("socketTimeOut") != null){
			soTimeout = (int)ConfigurationServer.get("socketTimeOut");
		}
		
		try {
			socket.setSoTimeout(soTimeout);
			
			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("Server started.");
			if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tServer started.");
			}
			
		} catch (SocketException e2) {
			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("Starting server fail.");
			if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\tStarting server fail.");
			}
			((ConcurrentHashMap)ConfigurationParameters.get(StringRessource.DEFAULT_SERVER_SPACE)).put(StringRessource.DEFAULT_STATUS_SPACE, StringRessource.DEFAULT_STATUS_SPACE_STOP);
			return;
		}
		
		while(((String)((ConcurrentHashMap)ConfigurationParameters.get(StringRessource.DEFAULT_SERVER_SPACE)).get(StringRessource.DEFAULT_STATUS_SPACE)).equals(StringRessource.DEFAULT_STATUS_SPACE_STARTED)){
			try {
				soc = socket.accept();
				(new Thread(new BartelbyServer(soc))).start();
			}catch (IOException e) {

				if(e instanceof SocketTimeoutException){
					String serverStatus = (String)((ConcurrentHashMap)ConfigurationParameters.get(StringRessource.DEFAULT_SERVER_SPACE)).get(StringRessource.DEFAULT_STATUS_SPACE);
					if(serverStatus.equals(StringRessource.DEFAULT_STATUS_SPACE_STARTED)){
						//none
					}else if(serverStatus.equals(StringRessource.DEFAULT_STATUS_SPACE_WAITING)){
						try {
							while(((String)((ConcurrentHashMap)ConfigurationParameters.get(StringRessource.DEFAULT_SERVER_SPACE)).get(StringRessource.DEFAULT_STATUS_SPACE)).equals(StringRessource.DEFAULT_STATUS_SPACE_WAITING)){
								Thread.currentThread().sleep(100);
							}
						} catch (InterruptedException e1) {}
					}else if(serverStatus.equals(StringRessource.DEFAULT_STATUS_SPACE_STOP)){
						return;
					}
					
				}else{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).trace(sw.toString());
					pw.close();
					try {
						sw.close();
					} catch (IOException e1) {
					}
					return;
				}
			}
		}
		try {
			soc.close();
		} catch (IOException e) {}
	}

	public boolean validServerConfiguration() {

		Boolean result = true;

		if (result) {
			result = ConfigurationServer.exist("port");
			if (result) {
				result = (ConfigurationServer.get("port") instanceof Integer);
			}
		}

		return result;

	}

}
