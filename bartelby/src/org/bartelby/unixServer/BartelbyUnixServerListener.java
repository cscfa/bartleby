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
	        		(new BartelbyUnixServer(this.sock)).start();
	        	}catch(SocketTimeoutException e){}
	        	
	        }
        }catch(IOException e){

			((Logger) ServiceContainer.get("logger")).error("Unix socket server fail to start.");
			if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
				((Logger) ServiceContainer.get("logger")).debug("Unix socket server fail to start.");
			}

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			((Logger) ServiceContainer.get("logger")).trace(sw.toString());
			pw.close();
			try {
				sw.close();
			} catch (IOException e1) {
			}
			((ConcurrentHashMap)ConfigurationParameters.get("server")).put("status", "stop");
			return;

        }
	}
}
