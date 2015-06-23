/**
 * 
 */
package org.bartelby.security;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bartelby.insideRouter.HTTPResourceContainer;
import org.bartelby.insideRouter.Transient;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Generator for fields mask.
 * 
 * Mask pattern generation class.
 * 
 * @author vallance
 *
 */
public class MaskPattern {

	protected String maskName;
	protected ArrayList<String> ipRules;
	protected ArrayList<String> userRules;
	protected ArrayList<SecuritySystemMask> systemPattern;
	
	/**
	 * 
	 */
	public MaskPattern(LinkedHashMap<String, Object> mask, String maskName) {
		this.maskName = maskName;
		this.ipRules = new ArrayList<String>();
		this.userRules = new ArrayList<String>();
		this.systemPattern = new ArrayList<SecuritySystemMask>();
		
		this.generate(mask);
	}
	
	public void generate(LinkedHashMap<String, Object> mask){
		
		if(mask.containsKey("ip")){
			this.ipRules = (ArrayList<String>) mask.get("ip");
		}
		if(mask.containsKey("user")){
			this.userRules = (ArrayList<String>) mask.get("user");
		}
		
		this.generateSystem((ArrayList) mask.get("system"));
	}
	
	private void generateSystem(ArrayList systems){
		
		for (Object system : systems) {
			LinkedHashMap systemTmp = (LinkedHashMap) system;
			Object[] systemKeys = systemTmp.keySet().toArray();
			
			for (Object systemKey : systemKeys) {
				LinkedHashMap systemTemp = (LinkedHashMap) systemTmp.get(systemKey);
				SecuritySystemMask systemElm = new SecuritySystemMask();
				systemElm.setSystem(systemKey.toString());
				
				if(systemTemp.containsKey("grant")){
					systemElm.setGrant((boolean) systemTemp.get("grant"));
				}

				if(systemTemp.containsKey("container")){
					systemElm.setContainers(this.generateContainer((ArrayList) systemTemp.get("container")));
				}

				this.systemPattern.add(systemElm);
			}
			
		}
		
		return;
		
	}

	private ArrayList<SecurityContainerMask> generateContainer(ArrayList containers) {
		ArrayList<SecurityContainerMask> arrayList = new ArrayList<SecurityContainerMask>();
		
		for (Object container : containers) {
			Object[] containerKeys = ((LinkedHashMap)container).keySet().toArray();
			
			for (Object containerKey : containerKeys) {
				LinkedHashMap containerDef = (LinkedHashMap) ((LinkedHashMap)container).get(containerKey);
				SecurityContainerMask securityContainerTmp;
				if(containerDef.containsKey("child")){
					securityContainerTmp = new SecurityContainerMask<SecurityContainerMask>();
				}else if(containerDef.containsKey("field")){
					securityContainerTmp = new SecurityContainerMask<SecurityFieldMask>();
				}else{
					securityContainerTmp = new SecurityContainerMask();
				}

				securityContainerTmp.setcontainerName(containerKey.toString());
				
				if(containerDef.containsKey("grant")){
					securityContainerTmp.setGrant((boolean) containerDef.get("grant"));
				}
				if(containerDef.containsKey("child")){
					securityContainerTmp.setchilds(this.generateContainer((ArrayList) containerDef.get("child")));
				}
				if(containerDef.containsKey("field")){
					securityContainerTmp.setchilds(this.generateField((ArrayList) containerDef.get("field")));
				}

				arrayList.add(securityContainerTmp);
			}
		}
		
		return arrayList;
	}

	private ArrayList<SecurityFieldMask> generateField(ArrayList fields) {
		ArrayList<SecurityFieldMask> arrayList = new ArrayList<SecurityFieldMask>();
		
		for (Object field : fields) {
			Object[] fieldKeys = ((LinkedHashMap)field).keySet().toArray();
			
			for (Object fieldKey : fieldKeys) {
				SecurityFieldMask fieldTmp = new SecurityFieldMask();
				fieldTmp.setFields(fieldKey.toString());
				
				if(((LinkedHashMap)((LinkedHashMap)field).get(fieldKey)).containsKey("grant")){
					fieldTmp.setGrant((boolean) ((LinkedHashMap)((LinkedHashMap)field).get(fieldKey)).get("grant"));
				}

				if(((LinkedHashMap)((LinkedHashMap)field).get(fieldKey)).containsKey("replace")){
					fieldTmp.setReplace((String) ((LinkedHashMap)((LinkedHashMap)field).get(fieldKey)).get("replace"));
				}
				
				arrayList.add(fieldTmp);
			}
		}
		
		return arrayList;
	}

	public ArrayList<String> getIpRules() {
		return ipRules;
	}

	public ArrayList<String> getUserRules() {
		return userRules;
	}

	public ArrayList<SecuritySystemMask> getSystemPattern() {
		return systemPattern;
	}

}
