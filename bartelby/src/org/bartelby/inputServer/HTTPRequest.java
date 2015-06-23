package org.bartelby.inputServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class HTTPRequest {
	
	protected Socket client;
	
	protected String clientIp;
	protected Integer clientPort;
	
	protected String method;
	protected String route;
	protected String uri;

	protected ArrayList<String> request = new ArrayList<String>();
	protected HashMap<String, String> urlParam = new HashMap<String, String>();
	
	public HTTPRequest(Socket client) throws IOException {
		
		this.client = client;
		
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

		this.clientIp = client.getInetAddress().getHostAddress();
		this.clientPort = client.getPort();

		String requestString = inFromClient.readLine();
		String headerLine = requestString;

		StringTokenizer tokenizer = new StringTokenizer(headerLine);
		this.method = tokenizer.nextToken();
		this.uri = tokenizer.nextToken();
		
		if(this.uri.indexOf('?') >= 0){
			this.route = this.uri.substring(0, this.uri.indexOf('?'));
		}else{
			this.route = this.uri;
		}
		
		String[] getParams = this.uri.substring(this.uri.indexOf('?') + 1).split("&");
		for (String param : getParams) {
			if(param.indexOf('=') == -1){
				this.urlParam.put(param, "1");
			}else{
			    StringTokenizer paramTokenizer = new StringTokenizer(param, "=");
			    String key = paramTokenizer.nextToken();
			    String value = paramTokenizer.nextToken();
				this.urlParam.put(key, value);
			}
		}

		while (inFromClient.ready()) {
			request.add(inFromClient.readLine());
		}
	}

	public String getUri() {
		return uri;
	}

	public HashMap<String, String> getUrlParam() {
		return urlParam;
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
		return "HTTPRequest [client=" + client + ", clientIp=" + clientIp
				+ ", clientPort=" + clientPort + ", method=" + method
				+ ", route=" + route + ", uri=" + uri + ", request=" + request
				+ ", getParam=" + urlParam + "]";
	}

}
