package org.spleen;

import org.spleen.collection.NamespaceMap;
import org.spleen.config.SpleenConfigurator;
import org.spleen.tool.SizeConverter;
import org.spleen.type.SimpleCacheObject;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SpleenConfigurator config = new SpleenConfigurator();
		config.setCacheTimeout(500);
		config.setRemoverSheduleTime(10);
		config.setMaxSize(SizeConverter.convert(1, SizeConverter.M));
		config.setMaxCount(1000);
		
		Spleen spleen = new Spleen();
		spleen.createNamespace("security", config);

		long start = System.currentTimeMillis();
		for(int i = 0;i < 1_000;i++){
			spleen.put("security", ((Double)Math.random()).toString(), (new SimpleCacheObject(Math.random())));
		}
		System.out.println("time : "+(System.currentTimeMillis() - start));
		
		while(!spleen.get("security").isEmpty());
		System.out.println("done");
	}

}
