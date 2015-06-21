package org.bartelby.component;

import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.insideRouter.Transient;
import org.bartelby.insideRouter.TransientCarrier;
import org.bartelby.interfaces.Component;

public class SecurityComponent implements Component {
	
	public static final String NAME = "security";

	public SecurityComponent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return SecurityComponent.NAME;
	}

	@Override
	public Transient process(HTTPResourceContainer container,
			TransientCarrier transients) {
		// TODO Auto-generated method stub
		
		Transient result = new Transient(403, "Forbiden");
		result.setContentType("text/plain");
		result.setResponseText("Error 403 : Permission denied");
		
		return result;
	}

}
