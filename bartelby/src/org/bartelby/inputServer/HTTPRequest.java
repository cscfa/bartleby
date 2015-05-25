package org.bartelby.inputServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class HTTPRequest {
	
	protected Socket client;
	
	protected String clientIp;
	protected Integer clientPort;
	
	protected String method;
	protected String route;
	
	protected ArrayList<String> request = new ArrayList<String>();
	
	public HTTPRequest(Socket client) throws IOException {
		
		this.client = client;
		
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

		this.clientIp = client.getInetAddress().getHostAddress();
		this.clientPort = client.getPort();

		String requestString = inFromClient.readLine();
		String headerLine = requestString;

		StringTokenizer tokenizer = new StringTokenizer(headerLine);
		this.method = tokenizer.nextToken();
		this.route = tokenizer.nextToken();

		while (inFromClient.ready()) {
			request.add(inFromClient.readLine());
		}
	}

	public DataOutputStream getOutput() {
		try {
			return new DataOutputStream(this.client.getOutputStream());
		} catch (IOException e) {
			return null;
		}
	}

	public String getClientIp() {
		return clientIp;
	}

	public Integer getClientPort() {
		return clientPort;
	}

	public String getMethod() {
		return method;
	}

	public String getRoute() {
		return route;
	}

	public ArrayList<String> getRequest() {
		return request;
	}

	@Override
	public String toString() {
		return "HTTPRequest [ip=" + clientIp + ", port=" + clientPort + ", method="
				+ method + ", route=" + route + ", request=" + request + "]";
	}

}
