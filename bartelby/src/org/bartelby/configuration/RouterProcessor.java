package org.bartelby.configuration;

import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.interfaces.Processor;

public class RouterProcessor implements Processor {

	public RouterProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dataIsValid(Object data) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object parse(Object data) throws DuplicateParameterEntryException {
		// TODO Auto-generated method stub
		
		System.out.println(data.getClass());
		
		return null;
	}

	@Override
	public boolean dump(Object data) {
		// TODO Auto-generated method stub
		return false;
	}

}
