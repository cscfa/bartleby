package org.bartelby.interfaces;

public interface Processor {

	public boolean dataIsValid(Object data);
	
	public Object parse(Object data);
	
	public boolean dump(Object data);
	
}
