package org.bartelby.inputServer;

import java.io.DataOutputStream;

import org.bartelby.configuration.ConfigurationServer;

import java.util.LinkedHashMap;

public class HTTPResponse {

	protected HTTPRequest request = null;
	
	protected String httpVersion = (String) ((LinkedHashMap)ConfigurationServer.get("response")).get("httpVersion");
	protected int responseCode = (Integer) ((LinkedHashMap)((LinkedHashMap)ConfigurationServer.get("response")).get("default")).get("code");
	protected String responseCodeText = (String) ((LinkedHashMap)((LinkedHashMap)ConfigurationServer.get("response")).get("default")).get("codeText");
	protected String serverDetail = (String) ((LinkedHashMap)ConfigurationServer.get("response")).get("serverDetail");
	protected String contentType = (String) ((LinkedHashMap)((LinkedHashMap)ConfigurationServer.get("response")).get("default")).get("contentType");
	
	public HTTPResponse(HTTPRequest request) {
		this.request = request;
	}

	public void sendResponse(String responseString) throws Exception {
		
		String result = this.httpVersion + " " + this.responseCode + " "+this.responseCodeText+"\r\n";
		result += this.serverDetail;
		result += "Content-Type: " + this.contentType + "\r\n";
		result += "Content-Length: " + responseString.length() + "\r\n";
		result += "Connection: close\r\n";
		result += "\r\n";

		DataOutputStream output = this.request.getOutput();
		if(output != null){
			output.writeBytes(result + responseString);
			output.close();
		}
		
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseCode(int responseCode, String responseCodeText) {
		this.responseCode = responseCode;
		this.responseCodeText = responseCodeText;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
