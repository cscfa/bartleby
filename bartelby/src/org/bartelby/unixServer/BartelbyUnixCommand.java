package org.bartelby.unixServer;

import java.util.concurrent.ConcurrentHashMap;

import org.bartelby.configuration.ConfigurationParameters;

public class BartelbyUnixCommand {

	BartelbyUnixServer server;
	
	public BartelbyUnixCommand(BartelbyUnixServer server) {
		this.server = server;
	}
	
	public boolean stop(){
		this.server.send(BartelbyUnixServer.OUTPUT, "Server stopping.\n");
		((ConcurrentHashMap)ConfigurationParameters.get("server")).put("status", "stop");
		this.server.send(BartelbyUnixServer.OUTPUT, "bye\n");
		
		return false;
	}

	public Boolean exit() {
		this.server.send(BartelbyUnixServer.OUTPUT, "bye\n");
		return false;
	}
	
	public Boolean status(){
		this.server.send(BartelbyUnixServer.OUTPUT, ((String)((ConcurrentHashMap)ConfigurationParameters.get("server")).get("status"))+"\n");
		return true;
	}

}
