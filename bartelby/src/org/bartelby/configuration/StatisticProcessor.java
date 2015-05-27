package org.bartelby.configuration;

import java.util.LinkedHashMap;

import org.bartelby.interfaces.Processor;

public class StatisticProcessor implements Processor {

	public StatisticProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dataIsValid(Object data) {
		if(data instanceof LinkedHashMap){
			data = (LinkedHashMap)data;
			
			if(((LinkedHashMap) data).containsKey("sniffer") &&
					((LinkedHashMap) data).get("sniffer") instanceof LinkedHashMap){
				LinkedHashMap sniffer = (LinkedHashMap) ((LinkedHashMap) data).get("sniffer");
				return true;
			}
		}
		return false;
	}

	@Override
	public Object parse(Object data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dump(Object data) {
		// TODO Auto-generated method stub
		return false;
	}

}
