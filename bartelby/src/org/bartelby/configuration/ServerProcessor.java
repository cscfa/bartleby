package org.bartelby.configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.interfaces.Processor;

public class ServerProcessor implements Processor {

	public ServerProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dataIsValid(Object data) {
		
		if(data instanceof LinkedHashMap){
			data = (LinkedHashMap)data;
			
			if(!((LinkedHashMap) data).containsKey("port") || !((LinkedHashMap) data).containsKey("response")){
				return false;
			}
			
			if(((LinkedHashMap) data).containsKey("socketTimeOut") && 
					!((LinkedHashMap) data).get("socketTimeOut").getClass().toString().equals(Integer.class.toString())){
				return false;
			}

			if(!((LinkedHashMap) data).get("port").getClass().toString().equals(Integer.class.toString())
					|| !((LinkedHashMap) data).get("response").getClass().toString().equals(LinkedHashMap.class.toString())){
				return false;
			}
			

			if(!this.validResponse((LinkedHashMap) ((LinkedHashMap) data).get("response"))){
				return false;
			}
			
		}else{
			return false;
		}
		
		return true;
	}
	
	private boolean validResponse(LinkedHashMap response){
		if(!response.containsKey("httpVersion") || !response.containsKey("serverDetail") || !response.containsKey("default")){
			return false;
		}
		
		if(!response.get("httpVersion").getClass().toString().equals(String.class.toString()) ||
				!response.get("serverDetail").getClass().toString().equals(String.class.toString()) ||
				!response.get("default").getClass().toString().equals(LinkedHashMap.class.toString())){
			return false;
		}
		
		LinkedHashMap defaults = (LinkedHashMap) response.get("default");
		if(!defaults.containsKey("code") || !defaults.containsKey("contentType") || !defaults.containsKey("codeText")){
			return false;
		}

		if(!defaults.get("code").getClass().toString().equals(Integer.class.toString()) ||
				!defaults.get("contentType").getClass().toString().equals(String.class.toString()) ||
				!defaults.get("codeText").getClass().toString().equals(String.class.toString())){
			return false;
		}
		
		return true;
	}
	
	@Override
	public Object parse(Object data) throws DuplicateParameterEntryException {
		
		Object[] keys = ((LinkedHashMap) data).keySet().toArray();
		
		for (Object key : keys) {
			if(ConfigurationServer.exist(key)){
				throw new DuplicateParameterEntryException("Duplicate server key "+key.toString()+".");
			}else{
				ConfigurationServer.put((String) key, ((LinkedHashMap) data).get(key));
			}
		}
		
		return null;
	}

	@Override
	public boolean dump(Object data) {
		// TODO Auto-generated method stub
		return false;
	}

}
