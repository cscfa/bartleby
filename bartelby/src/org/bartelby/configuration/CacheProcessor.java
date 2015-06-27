package org.bartelby.configuration;

import java.util.LinkedHashMap;

import org.bartelby.cache.CacheService;
import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.interfaces.Processor;

public class CacheProcessor implements Processor {

	public CacheProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dataIsValid(Object data) {

		if(data instanceof LinkedHashMap){
			Object[] keySet = ((LinkedHashMap) data).keySet().toArray();
			
			for (Object key : keySet) {
				if(((LinkedHashMap) data).get(key) instanceof LinkedHashMap){
					LinkedHashMap cache = (LinkedHashMap) ((LinkedHashMap) data).get(key);

					if(cache.containsKey("memory") && 
							cache.containsKey("count") && 
							cache.containsKey("cacheTime") && 
							cache.containsKey("schedule") && 
							cache.containsKey("type") && 
							cache.containsKey("thread")){

						if((cache.get("memory").getClass().toString().equals(Integer.class.toString()) || 
								cache.get("memory").getClass().toString().equals(Long.class.toString())) && 
								cache.get("count").getClass().toString().equals(Integer.class.toString()) && (
								cache.get("cacheTime").getClass().toString().equals(Integer.class.toString()) || 
								cache.get("cacheTime").getClass().toString().equals(Long.class.toString())) && (
								cache.get("schedule").getClass().toString().equals(Integer.class.toString()) || 
								cache.get("schedule").getClass().toString().equals(Long.class.toString())) && 
								cache.get("thread").getClass().toString().equals(Integer.class.toString()) && 
								cache.get("type").getClass().toString().equals(String.class.toString())){
						
							if(cache.get("type").toString().equals(("simple").toString()) || 
									cache.get("type").toString().equals(("thread").toString())){
								continue;
							}else{
								return false;
							}
						}else{
							return false;
						}
						
					}else{
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

		Object[] keySet = ((LinkedHashMap) data).keySet().toArray();
		
		for (Object key : keySet) {
			LinkedHashMap cache = (LinkedHashMap) ((LinkedHashMap) data).get(key);
			
			CacheService.createNamespace(key.toString(), cache);
		}
		
		return null;
	}

	@Override
	public boolean dump(Object data) {
		// TODO Auto-generated method stub
		return false;
	}

}
