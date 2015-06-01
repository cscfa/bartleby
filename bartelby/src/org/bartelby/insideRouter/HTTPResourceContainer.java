package org.bartelby.insideRouter;

import java.io.IOException;
import java.net.Socket;

import org.bartelby.inputServer.HTTPRequest;
import org.bartelby.inputServer.HTTPResponse;

public class HTTPResourceContainer {
	
	protected HTTPRequest request;
	protected HTTPResponse response;
	protected TransientCarrier transientCarrier = new TransientCarrier();
	
	public HTTPResourceContainer(Socket soc) throws IOException {
		this.request = new HTTPRequest(soc);
		this.response = new HTTPResponse(request);
	}

	public HTTPRequest getRequest() {
		return request;
	}

	public HTTPResponse getResponse() {
		return response;
	}

	public TransientCarrier getTransientCarrier() {
		return transientCarrier;
	}

	

}
