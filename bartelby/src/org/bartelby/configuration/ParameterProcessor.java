package org.bartelby.configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.bartelby.console.ConsoleArgument;
import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.interfaces.Processor;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;

public class ParameterProcessor implements Processor {

	public ParameterProcessor() {
		super();
	}

	@Override
	public boolean dataIsValid(Object data) {
		
		if(data instanceof LinkedHashMap){
			
			Object[] keys = ((LinkedHashMap) data).keySet().toArray();
			ArrayList<Object> acceptedType = new ArrayList<Object>();
			acceptedType.add(Integer.class.toString());
			acceptedType.add(Double.class.toString());
			acceptedType.add(Float.class.toString());
			acceptedType.add(String.class.toString());
			acceptedType.add(Boolean.class.toString());
			acceptedType.add(Long.class.toString());
			
			for (Object key : keys) {
				
				if(!acceptedType.contains(((LinkedHashMap) data).get(key).getClass().toString())){
					return false;
				}
			}
		}else{
			return false;
		}
		
		return true;
	}

	@Override
	public Object parse(Object data) throws DuplicateParameterEntryException {
		
		Object[] keys = ((LinkedHashMap) data).keySet().toArray();
		
		for (Object key : keys) {
			if(ConfigurationParameters.exist(key)){
				throw new DuplicateParameterEntryException("Duplicate parameter key "+key.toString()+".");
			}else{
				ConfigurationParameters.put((String) key, ((LinkedHashMap) data).get(key));
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
