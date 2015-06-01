package org.bartelby.insideRouter;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.bartelby.console.ConsoleArgument;
import org.bartelby.interfaces.Component;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;

public class TransientCarrier {
	
	protected ConcurrentHashMap<TransientIdentifier, Vector<Transient>> transients = new ConcurrentHashMap<TransientIdentifier, Vector<Transient>>();
	protected Vector<TransientIdentifier> parentList = new Vector<TransientIdentifier>();

	public TransientCarrier() {
	}

	public boolean add(Component parent, Transient transientMessage) {
		return this.add((new TransientIdentifier(parent.getName(), parent.hashCode())), transientMessage);
	}

	public boolean add(TransientIdentifier parentId, Transient transientMessage) {
		Vector<TransientIdentifier> tmpParentList = this.parentList;
		
		if(!this.setLast(parentId)){
			return false;
		}
		
		if(this.transients.contains(parentId)){
			return this.transients.get(parentId).add(transientMessage);
		}else{
			Vector<Transient> tmpVector = new Vector<Transient>();
			tmpVector.add(transientMessage);
			this.transients.put(parentId, tmpVector);
			return true;
		}
	}
	
	protected boolean setLast(TransientIdentifier parentId) {
		if(!this.parentList.isEmpty() && ((TransientIdentifier)this.parentList.lastElement()).equals(parentId)){
			return true;
		}else{
			if(this.parentList.contains(parentId)){
				int oldIndex = this.parentList.indexOf(parentId);
				
				if(oldIndex >= 0){
					try{
						this.parentList.remove(oldIndex);
					}catch(ArrayIndexOutOfBoundsException e){
						this.parentList.remove(parentId);
					}
					
					if(this.parentList.add(parentId)){
						return true;
					}
				}
			}else{
				if(this.parentList.add(parentId)){
					return true;
				}
			}
		}
		
		return false;
	}

	public ConcurrentHashMap<TransientIdentifier, Vector<Transient>> getTransients() {
		return transients;
	}

	public Vector<TransientIdentifier> getParentList() {
		return parentList;
	}
	
	public Transient getLast() {
		if(this.parentList.isEmpty()){
			return null;
		}else if(this.transients.containsKey(this.parentList.lastElement())){
			return this.transients.get(this.parentList.lastElement()).lastElement();
		}else{
			return null;
		}
	}
}
