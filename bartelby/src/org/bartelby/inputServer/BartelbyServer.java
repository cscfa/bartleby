package org.bartelby.inputServer;

import java.net.Socket;

import org.bartelby.console.ConsoleArgument;
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

			HTTPRequest request = new HTTPRequest(this.connectedClient);
			
			((Logger) ServiceContainer.get("logger")).info("Client connection."+request.getClientIp() + ":" + request.getClientPort());
			if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
				((Logger) ServiceContainer.get("logger")).debug("\tClient connection."+request.getClientIp() + ":" + request.getClientPort());
			}

			HTTPResponse response = new HTTPResponse(request);
			response.sendResponse("ok");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		BartelbySniffer.rmProcess();
		Long executionTime = (System.currentTimeMillis() - this.time);
		((Logger) ServiceContainer.get("logger")).info("Response send in "+executionTime+"ms");
		if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
			((Logger) ServiceContainer.get("logger")).debug("\tResponse send in "+executionTime+"ms");
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		this.connectedClient.close();
		super.finalize();
	}
}
