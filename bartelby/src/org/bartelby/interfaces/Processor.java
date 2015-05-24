package org.bartelby.interfaces;

import java.io.FileNotFoundException;

public interface Processor {

	public boolean dataIsValid(Object data);
	
	public Object parse(Object data) throws Exception;
	
	public boolean dump(Object data);
	
}
