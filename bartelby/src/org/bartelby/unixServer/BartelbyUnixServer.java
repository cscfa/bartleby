package org.bartelby.unixServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

import org.bartelby.configuration.ConfigurationParameters;
import org.bartelby.configuration.ConfigurationServer;
import org.bartelby.configuration.ConfigurationUser;
import org.bartelby.console.ConsoleArgument;
import org.bartelby.console.ConsoleUser;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;

public class BartelbyUnixServer extends Thread {

	protected static final String OUTPUT = "output";
	protected static final String INPUT = "input";
	protected static final String CLOSE = "close";
	
	private Socket client;
	private ConsoleUser user;
	private DataInputStream is;
	private DataOutputStream os;
	
	public BartelbyUnixServer(Socket client) {
		
		this.client = client;
		
	}
	
	public void run(){

		try {

            this.is = new DataInputStream(this.client.getInputStream());
			this.os = new DataOutputStream(this.client.getOutputStream());

			try {
				if(this.grantAccess()){
					
					Boolean loop = true;
					BartelbyUnixCommand consoleDiscover = new BartelbyUnixCommand(this);
					
					while(loop){
						this.send(BartelbyUnixServer.OUTPUT, "> ");
						String command = (String) this.send(BartelbyUnixServer.INPUT, null);
	
						if(command.equals("stop")){
							loop = consoleDiscover.stop();
						}else if(command.equals("exit")){
							loop = consoleDiscover.exit();
						}else if(command.equals("status")){
							loop = consoleDiscover.status();
						}else if(command.equals("show:commands")){
							loop = consoleDiscover.listCommand();
						}else if(command.equals("help")){
							loop = consoleDiscover.help();
						}
					}
				}else{
					this.send(BartelbyUnixServer.OUTPUT, "Connection denied.\n");
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.send("close", null);

		} catch (IOException e) {
			((Logger) ServiceContainer.get("logger")).error("Unix socket server fail to start.");
			if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
				((Logger) ServiceContainer.get("logger")).debug("Unix socket server fail to start.");
			}
		} catch(Exception e){
			if(e instanceof StringIndexOutOfBoundsException){
				
        		((Logger) ServiceContainer.get("logger")).info(this.client.toString().hashCode() + " Unix client connection closed.");
    			if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
    				((Logger) ServiceContainer.get("logger")).debug(this.client.toString().hashCode() + " Unix client connection closed.");
    			}
    			
				try {
					this.client.close();
				} catch (IOException e1) {}
			}
		}

	}
	
	public Object send(String output, String message){
        int read, readresponse;
        byte[] buf = new byte[1024];
        byte[] bufresp = new byte[1024];
        
		if(output.equals("output")){
			try{
				this.os.write("output".getBytes());
				this.os.flush();
				
		        read = this.is.read(buf);
		        String state = new String(buf, 0, read);
		        if(state.equals("ready")){
					this.os.write(message.getBytes());
					this.os.flush();
					

					readresponse = this.is.read(bufresp);
		        }else{
		        	try {
						Thread.currentThread().sleep(10);
					} catch (InterruptedException e) {
						return null;
					}
		        	return this.send(output, message);
		        }
			}catch(IOException e){}
		}else if(output.equals("close")){
			try{
				this.os.write("close".getBytes());
				this.os.flush();
				this.client.close();
				
        		((Logger) ServiceContainer.get("logger")).info(this.client.toString().hashCode() + " Unix client connection closed.");
    			if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
    				((Logger) ServiceContainer.get("logger")).debug(this.client.toString().hashCode() + " Unix client connection closed.");
    			}
			}catch(IOException e){}
		}else if(output.equals("input")){
			try{
				this.os.write("input".getBytes());
				this.os.flush();

				readresponse = this.is.read(bufresp);
		        String response = new String(bufresp, 0, readresponse);
		        return response;
			}catch(IOException e){}
		}
		return null;
	}
	
	public boolean grantAccess() throws NoSuchAlgorithmException, IOException{
		
		this.send(BartelbyUnixServer.OUTPUT, "Connection needed\n");
		this.send(BartelbyUnixServer.OUTPUT, "user : ");
		
		String user = (String) this.send(BartelbyUnixServer.INPUT, null);

		this.send(BartelbyUnixServer.OUTPUT, "password : ");
		
		String password = (String) this.send(BartelbyUnixServer.INPUT, null);

        if(ConfigurationUser.exist(user)){
        	ConsoleUser currentUser = (ConsoleUser) ConfigurationUser.get(user);

			MessageDigest md = MessageDigest.getInstance(currentUser.getPasswordType());
			md.update(password.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			if(currentUser.getPassword().equals(sb.toString())){
				
				this.user = currentUser;
				
        		((Logger) ServiceContainer.get("logger")).info(this.client.toString().hashCode() + " Unix client connection granted with user : " + user);
    			if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
    				((Logger) ServiceContainer.get("logger")).debug(this.client.toString().hashCode() + " Unix client connection granted with user : " + user);
    			}
				
				return true;
			}
        }
		
		((Logger) ServiceContainer.get("logger")).info(this.client.toString().hashCode() + " Unix client connection failed with user : " + user);
		if ((boolean) ((ConsoleArgument) ServiceContainer.get("console")).getOption("debug")) {
			((Logger) ServiceContainer.get("logger")).debug(this.client.toString().hashCode() + " Unix client connection failed with user : " + user);
		}
		
		return false;
	}

	public ConsoleUser getUser() {
		return user;
	}

	public Socket getClient() {
		return client;
	}

}
