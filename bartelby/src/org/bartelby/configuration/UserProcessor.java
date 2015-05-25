package org.bartelby.configuration;

import java.util.LinkedHashMap;

import org.bartelby.console.ConsoleUser;
import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.interfaces.Processor;

public class UserProcessor implements Processor {

	public UserProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dataIsValid(Object data) {

		if(data instanceof LinkedHashMap){
			
			Object[] dataKeys = ((LinkedHashMap) data).keySet().toArray();
			
			for (Object key : dataKeys) {
				if(((LinkedHashMap) data).get(key) instanceof LinkedHashMap){
					
					LinkedHashMap user = (LinkedHashMap)((LinkedHashMap) data).get(key);
					
					if(!user.containsKey("role") || !user.containsKey("password") || !user.containsKey("passwordType")){
						return false;
					}
					
					if(!user.get("role").getClass().toString().equals(String.class.toString()) || 
							!user.get("password").getClass().toString().equals(String.class.toString()) || 
							!user.get("passwordType").getClass().toString().equals(String.class.toString())){
						return false;
					}
					
				}else{
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
			if(ConfigurationUser.exist(key)){
				throw new DuplicateParameterEntryException("Duplicate user key "+key.toString()+".");
			}else{
				ConfigurationUser.put((String) key, (new ConsoleUser((String)((LinkedHashMap) ((LinkedHashMap) data).get(key)).get("role"),
						(String)((LinkedHashMap) ((LinkedHashMap) data).get(key)).get("password"),
						(String)((LinkedHashMap) ((LinkedHashMap) data).get(key)).get("passwordType"),
						(String) key)));
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
