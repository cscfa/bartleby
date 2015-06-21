package org.bartelby.unixServer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentHashMap;

import org.bartelby.configuration.ConfigurationParameters;
import org.bartelby.console.ConsoleArgument;
import org.bartelby.ressources.StringRessource;
import org.bartelby.service.ServiceContainer;
import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.slf4j.Logger;

public class BartelbyUnixServerListener extends Thread {

	Socket sock;
	
	public BartelbyUnixServerListener() {
		// TODO Auto-generated constructor stub
	}
    
	public void run(){
		
        final File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "bartelby-unixsocket.sock");
        
        try{
	        AFUNIXServerSocket server = AFUNIXServerSocket.newInstance();
	        server.bind(new AFUNIXSocketAddress(socketFile));
	        
	        while (!Thread.interrupted()) {
	        	try{
	        		this.sock = server.accept();
	        		
	        		((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info(this.sock.toString().hashCode() + " Unix client connection.");
        			if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
        				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug(this.sock.toString().hashCode() + " Unix client connection.");
        			}
        			
	        		(new BartelbyUnixServer(this.sock)).start();
	        	}catch(SocketTimeoutException e){}
	        	
	        }
        }catch(IOException e){

			((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("Unix socket server fail to start.");
			if ((boolean) ((ConsoleArgument) ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)) {
				((Logger) ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("Unix socket server fail to start.");
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
	}
}
