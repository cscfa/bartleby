package org.bartelby.insideRouter;

import java.util.HashMap;

public class Transient {

	protected int code;
	protected String codeStatus;
	protected HashMap<String, Object> messages = new HashMap<String, Object>();
	
	public Transient() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param code
	 * @param codeStatus
	 */
	public Transient(int code, String codeStatus) {
		super();
		this.code = code;
		this.codeStatus = codeStatus;
	}

	/**
	 * @param code
	 * @param codeStatus
	 * @param messages
	 */
	public Transient(int code, String codeStatus, HashMap<String, Object> messages) {
		super();
		this.code = code;
		this.codeStatus = codeStatus;
		this.messages = messages;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCodeStatus() {
		return codeStatus;
	}

	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
	}

	public HashMap<String, Object> getMessages() {
		return messages;
	}

	public void setMessages(HashMap<String, Object> messages) {
		this.messages = messages;
	}
	
	public void setContentType(String contentType){
		this.messages.put("content-type", contentType);
	}
	
	public String getContentType(){
		if(this.messages.containsKey("content-type")){
			return (String) this.messages.get("content-type");
		}else{
			return null;
		}
	}

	public void setResponseText(String contentType) {
		this.messages.put("response-text", contentType);
	}

	public String getResponseText() {
		if(this.messages.containsKey("response-text")){
			return (String) this.messages.get("response-text");
		}else{
			return null;
		}
	}

}
