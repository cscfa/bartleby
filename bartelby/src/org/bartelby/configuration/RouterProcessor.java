package org.bartelby.configuration;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.interfaces.Component;
import org.bartelby.interfaces.Processor;

public class RouterProcessor implements Processor {

	public RouterProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dataIsValid(Object data) {

		if(data instanceof ArrayList){
			for(int i = 0;i < ((ArrayList)data).size();i++){
				if(((ArrayList) data).get(i) instanceof String){
					try{
						Class tmpClass = Class.forName((String) ((ArrayList) data).get(i), false, this.getClass().getClassLoader());
						Constructor tmpConst = tmpClass.getConstructor();
						Object tmpObj = tmpConst.newInstance();
						
						if(tmpObj instanceof Component){
							continue;
						}else{
							return false;
						}
					}catch(Exception e){
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
		// TODO Auto-generated method stub
		
		for(int i = 0;i < ((ArrayList)data).size();i++){
			try{
				Class tmpClass = Class.forName((String) ((ArrayList) data).get(i));
				Constructor tmpConst = tmpClass.getConstructor();
				ConfigurationRouter.add((Component) tmpConst.newInstance());
			}catch(Exception e){}
		}
		
		return null;
	}

	@Override
	public boolean dump(Object data) {
		// TODO Auto-generated method stub
		return false;
	}

}
