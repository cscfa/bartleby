package org.bartelby.component;

import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.insideRouter.Transient;
import org.bartelby.insideRouter.TransientCarrier;
import org.bartelby.interfaces.Component;

public class SecurityComponent implements Component {

	public SecurityComponent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "security";
	}

	@Override
	public Transient process(HTTPResourceContainer container,
			TransientCarrier transients) {
		// TODO Auto-generated method stub
		return null;
	}

}
