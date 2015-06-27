package org.bartelby.cache;

import java.util.LinkedHashMap;

import org.bartelby.configuration.ConfigurationCache;
import org.spleen.Spleen;
import org.spleen.collection.NamespaceInterface;
import org.spleen.config.SpleenConfigurator;
import org.spleen.type.CacheObject;

public class CacheService{

	protected static Spleen spleenCache = new Spleen();

	public static NamespaceInterface get(String key) {
		return spleenCache.get(key);
	}
	
	public static void createNamespace(String key, LinkedHashMap data){
		
		
		SpleenConfigurator tmpConfig = new SpleenConfigurator();
		
		tmpConfig.setRemoverSheduleTime(Long.valueOf(data.get("schedule").toString()));
		tmpConfig.setCacheTimeout(Long.valueOf(data.get("cacheTime").toString()));
		tmpConfig.setMaxSize(Double.valueOf(data.get("memory").toString()));
		tmpConfig.setMaxCount(Integer.valueOf(data.get("count").toString()));
		
		if(data.get("type").toString().equals("thread")){
			CacheService.spleenCache.createNamespace(Integer.valueOf(data.get("count").toString()), key, tmpConfig);
		}else{
			CacheService.spleenCache.createNamespace(key, tmpConfig);
		}
		
	}

}
