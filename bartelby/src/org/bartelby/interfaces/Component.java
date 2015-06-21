package org.bartelby.interfaces;

import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.insideRouter.Transient;
import org.bartelby.insideRouter.TransientCarrier;

public interface Component extends Cloneable{

	public String getName();
	public Transient process(HTTPResourceContainer container, TransientCarrier transients);
	
}
