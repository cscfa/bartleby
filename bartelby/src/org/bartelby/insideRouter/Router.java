package org.bartelby.insideRouter;

import java.util.ArrayDeque;

import org.bartelby.configuration.ConfigurationRouter;
import org.bartelby.interfaces.Component;

public class Router {
	
	protected HTTPResourceContainer container;
	protected ArrayDeque<Component> componentQueue = new ArrayDeque<Component>();
	protected TransientCarrier transientCarrier = new TransientCarrier();

	public Router(HTTPResourceContainer container) {
		
		this.container = container;
		
		this.componentQueue = (ArrayDeque<Component>) ConfigurationRouter.getQueueClone();
		
	}
	
	public TransientCarrier process() {
		
		while(!this.componentQueue.isEmpty()){
			Component currentComponent = this.componentQueue.pollFirst();
			Transient currentTransient = currentComponent.process(this.container, this.transientCarrier);
			this.transientCarrier.add(currentComponent, currentTransient);
			
			if(currentTransient.getCode() >= 200 || currentTransient.getCode() < 100){
				this.componentQueue.clear();
			}
		}

		return this.transientCarrier;
		
	}

}
