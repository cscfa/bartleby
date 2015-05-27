package org.bartelby.unixServer;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.bartelby.configuration.ConfigurationParameters;
import org.bartelby.console.ConsoleArgument;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;

public class BartelbyUnixCommand {

	BartelbyUnixServer server;
	
	public BartelbyUnixCommand(BartelbyUnixServer server) {
		this.server = server;
	}
	
	public boolean stop(){

		if(this.server.getUser().getRole().equals("admin")){
			((Logger) ServiceContainer.get("logger")).info(this.server.getClient().toString().hashCode() + " Unix client request stop.");
			if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
				((Logger) ServiceContainer.get("logger")).debug(this.server.getClient().toString().hashCode() + " Unix client request stop.");
			}
			
			this.server.send(BartelbyUnixServer.OUTPUT, "Server stopping.\n");
			((ConcurrentHashMap)ConfigurationParameters.get("server")).put("status", "stop");
			this.server.send(BartelbyUnixServer.OUTPUT, "bye\n");
			
			return false;
		}else{
			this.server.send(BartelbyUnixServer.OUTPUT, "You'r not allowed to execute this command.\n");
			
			return true;
		}
	}

	public Boolean exit() {
		this.server.send(BartelbyUnixServer.OUTPUT, "bye\n");
		return false;
	}
	
	public Boolean status(){
		((Logger) ServiceContainer.get("logger")).info(this.server.getClient().toString().hashCode() + " Unix client request status.");
		if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
			((Logger) ServiceContainer.get("logger")).debug(this.server.getClient().toString().hashCode() + " Unix client request status.");
		}
		
		this.server.send(BartelbyUnixServer.OUTPUT, ((String)((ConcurrentHashMap)ConfigurationParameters.get("server")).get("status"))+"\n");
		return true;
	}
	
	public Boolean listCommand(){
		
		ArrayList<String> commandList = new ArrayList<String>();
		commandList.add("stop");
		commandList.add("exit");
		commandList.add("status");
		commandList.add("show:commands");
		commandList.add("help");
		
		for(int i = 0;i < commandList.size();i++){
			this.server.send(BartelbyUnixServer.OUTPUT, commandList.get(i));
			
			
			if((i+1)%3 == 0 && i > 0){
				this.server.send(BartelbyUnixServer.OUTPUT, "\n");
			}else{
				this.server.send(BartelbyUnixServer.OUTPUT, "\t");
			}
		}
		
		this.server.send(BartelbyUnixServer.OUTPUT, "\n");

		return true;
	}
	
	public Boolean help(){

		this.server.send(BartelbyUnixServer.OUTPUT, "Use 'show:commands' to view the allowed commands.\n");

		return true;
	}

}
