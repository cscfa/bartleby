package org.bartelby.configuration;

import java.io.Console;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.regex.Pattern;

import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.interfaces.Processor;
import org.bartelby.security.MaskPattern;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Security processor.
 * 
 * Processor for security configuration parameters.
 * 
 * @author vallance
 *
 */
public class SecurityProcessor implements Processor {

	/**
	 * Default constructor.
	 * 
	 * Default security processor constructor with
	 * parent constructor calling.
	 */
	public SecurityProcessor() {
		super();
	}

	/**
	 * Data validation.
	 * 
	 * Validate if given data are valid.
	 * 
	 * @param data	the data to validate.
	 * @see 		org.bartelby.interfaces.Processor#dataIsValid(java.lang.Object)
	 */
	@Override
	public boolean dataIsValid(Object data) {
		
		if(data instanceof LinkedHashMap){
			if(((LinkedHashMap) data).containsKey("ipAccess") && ((LinkedHashMap) data).get("ipAccess").getClass().toString().equals(LinkedHashMap.class.toString())){
				LinkedHashMap<String, Object> ipAccess = (LinkedHashMap<String, Object>) ((LinkedHashMap) data).get("ipAccess");

				if(ipAccess.containsKey("default")){
					Object accessDefault = ipAccess.get("default");
					if(accessDefault.getClass().toString().equals(String.class.toString())){
						if(accessDefault.equals("grant") || accessDefault.equals("deny")){
							if(ipAccess.containsKey("grant") && ipAccess.get("grant").getClass().toString().equals(ArrayList.class.toString())){
								ArrayList grant = (ArrayList) ipAccess.get("grant");
								
								for (Object ip : grant) {
									if(ip.getClass().toString().equals(String.class.toString())){
										if(!ip.toString().matches("^(\\d{1,3}.){3}\\d{1,3}$")){
											return false;
										}
									}else{
										return false;
									}
								}
							}
							if(ipAccess.containsKey("deny") && ipAccess.get("deny").getClass().toString().equals(ArrayList.class.toString())){
								ArrayList deny = (ArrayList) ipAccess.get("deny");
								
								for (Object ip : deny) {
									if(ip.getClass().toString().equals(String.class.toString())){
										if(!ip.toString().matches("^(\\d{1,3}.){3}\\d{1,3}$")){
											return false;
										}
									}else{
										return false;
									}
								}
							}
						}else{
							return false;
						}
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else if(((LinkedHashMap) data).containsKey("ipAccess")){
				return false;
			}

			if(((LinkedHashMap) data).containsKey("userAccess") && ((LinkedHashMap) data).get("userAccess").getClass().toString().equals(LinkedHashMap.class.toString())){
				LinkedHashMap<String, Object> userAccess = (LinkedHashMap<String, Object>) ((LinkedHashMap) data).get("userAccess");

				if(userAccess.containsKey("default")){
					Object accessDefault = userAccess.get("default");
					if(accessDefault.getClass().toString().equals(String.class.toString())){
						if(accessDefault.equals("grant") || accessDefault.equals("deny")){
							
							Object[] keys = userAccess.keySet().toArray();
							
							for (Object key : keys) {
								if(key.toString().equals("default")){
									continue;
								}else{
									Object user = userAccess.get(key.toString());
									
									if(user.getClass().toString().equals(LinkedHashMap.class.toString())){
										user = (LinkedHashMap<String, Object>)userAccess.get(key);
										if(((LinkedHashMap<String, Object>)user).containsKey("ip")){
											if(((LinkedHashMap<String, Object>)user).get("ip").getClass().toString().equals(ArrayList.class.toString())){
												ArrayList ipList = (ArrayList) ((LinkedHashMap<String, Object>)user).get("ip");
												
												for (Object ip : ipList) {
													if(!ip.getClass().toString().equals(String.class.toString()) || !ip.toString().matches("^(\\d{1,3}.){3}\\d{1,3}$")){
														return false;
													}
												}
											}else{
												return false;
											}
										}
										
										if(((LinkedHashMap<String, Object>)user).containsKey("key") && ((LinkedHashMap<String, Object>)user).get("key").getClass().toString().equals(String.class.toString())){
											//nothing
										}else{
											return false;
										}
									}else{
										return false;
									}
								}
							}
							
						}else{
							return false;
						}
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else if(((LinkedHashMap) data).containsKey("userAccess")){
				return false;
			}

			if(((LinkedHashMap) data).containsKey("mask") && ((LinkedHashMap) data).get("mask").getClass().toString().equals(LinkedHashMap.class.toString())){
				LinkedHashMap<String, Object> maskRoot = (LinkedHashMap<String, Object>) ((LinkedHashMap) data).get("mask");
				Object[] keys = maskRoot.keySet().toArray();
				for (Object key : keys) {

					if(maskRoot.get(key).getClass().toString().equals(LinkedHashMap.class.toString())){
						LinkedHashMap mask = (LinkedHashMap) maskRoot.get(key);
						
						if(mask.containsKey("ip") && (!mask.get("ip").getClass().toString().equals(ArrayList.class.toString()))){
							return false;
						}else if(mask.containsKey("ip")){
							for(Object ipValue : ((ArrayList)mask.get("ip"))){
								if(!ipValue.getClass().toString().equals(String.class.toString()) || !ipValue.toString().matches("^(\\d{1,3}.){3}\\d{1,3}$")){
									return false;
								}
							}
						}
						
						if(mask.containsKey("user") && (!mask.get("user").getClass().toString().equals(ArrayList.class.toString()))){
							return false;
						}else if(mask.containsKey("user")){
							for(Object userValue : ((ArrayList)mask.get("user"))){
								if(!userValue.getClass().toString().equals(String.class.toString()) || !userValue.toString().matches("^[a-zA-Z0-9]+$")){
									return false;
								}
							}
						}
						
						if(!mask.containsKey("system") || !mask.get("system").getClass().toString().equals(ArrayList.class.toString())){
							return false;
						}else{
							ArrayList systems = (ArrayList) mask.get("system");
							
							for (Object system : systems) {
								if(!system.getClass().toString().equals(LinkedHashMap.class.toString())){
									return false;
								}
								
								Object[] systemKeys = ((LinkedHashMap)system).keySet().toArray();
								
								for (Object systemKey : systemKeys) {
									//TODO continue to perform validation for each systems. at this state, one of the systemKey will be "mongodb"
									//System.out.println(((LinkedHashMap)systems.get(0)).get("mongodb"));
									
									//throw new NotImplementedException();
									
									if(((LinkedHashMap)system).get(systemKey).getClass().toString().equals(LinkedHashMap.class.toString())){
										LinkedHashMap systemDef = (LinkedHashMap) ((LinkedHashMap)system).get(systemKey);
										
										if(systemDef.containsKey("grant") && (systemDef.get("grant").getClass().toString().equals(Boolean.class.toString()))){
											Boolean grantTmp = (Boolean) systemDef.get("grant");
										}else if(systemDef.containsKey("grant")){
											return false;
										}

										if(systemDef.containsKey("container") && systemDef.get("container").getClass().toString().equals(ArrayList.class.toString())){
											if(!this.validContainer((ArrayList) systemDef.get("container"))){
												return false;
											}
										}else if(systemDef.containsKey("container")){
											return false;
										}else if(systemDef.containsKey("field")){
											return false;
										}
									}else{
										return false;
									}
								}
							}
						}
					}else{
						return false;
					}
				}
				
			}else if(((LinkedHashMap) data).containsKey("mask")){
				return false;
			}

		}else{
			return false;
		}
		
		return true;
	}
	
	private boolean validContainer(ArrayList containers){
		for (Object container : containers) {
			if(container.getClass().toString().equals(LinkedHashMap.class.toString())){
				Object[] containerKeys = ((LinkedHashMap)container).keySet().toArray();
				
				for (Object containerKey : containerKeys) {
					if(((LinkedHashMap)container).get(containerKey).getClass().toString().equals(LinkedHashMap.class.toString())){
						LinkedHashMap containerDef = (LinkedHashMap) ((LinkedHashMap)container).get(containerKey);
						if(containerDef.containsKey("grant") && (containerDef.get("grant").getClass().toString().equals(Boolean.class.toString()))){
							Boolean grantTmp = (Boolean) containerDef.get("grant");
						}else if(containerDef.containsKey("grant")){
							return false;
						}
						
						if(containerDef.containsKey("child") && containerDef.get("child").getClass().toString().equals(ArrayList.class.toString())){
							if(!this.validContainer((ArrayList) containerDef.get("child"))){
								return false;
							}
						}else if(containerDef.containsKey("child")){
							return false;
						}else if(containerDef.containsKey("field") && containerDef.get("field").getClass().toString().equals(ArrayList.class.toString())){
							if(!this.validField((ArrayList) containerDef.get("field"))){
								return false;
							}
						}
						
					}else{
						return false;
					}
				}
			}else{
				return false;
			}
		}
		
		return true;
	}
	
	private boolean validField(ArrayList fields){
		for (Object field : fields) {
			if(field.getClass().toString().equals(LinkedHashMap.class.toString())){
				Object[] fieldKeys = ((LinkedHashMap)field).keySet().toArray();
				
				for (Object fieldKey : fieldKeys) {
					if(((LinkedHashMap)field).get(fieldKey).getClass().toString().equals(LinkedHashMap.class.toString())){
						LinkedHashMap fieldDesc = (LinkedHashMap) ((LinkedHashMap)field).get(fieldKey);
						
						if(fieldDesc.containsKey("grant") && !fieldDesc.get("grant").getClass().toString().equals(Boolean.class.toString())){
							return false;
						}

						if(fieldDesc.containsKey("replace") && !fieldDesc.get("replace").getClass().toString().equals(String.class.toString())){
							return false;
						}
					}else{
						return false;
					}
				}
			}else{
				return false;
			}
		}
		return true;
	}

	/**
	 * parse data.
	 * 
	 * Parse given data into the ConfigurationSecurity object.
	 * 
	 * @param data	the data to parse.
	 * @see 		org.bartelby.interfaces.Processor#parse(java.lang.Object)
	 */
	@Override
	public Object parse(Object data) throws DuplicateParameterEntryException {

		if(((LinkedHashMap) data).containsKey("ipAccess")){
			if(ConfigurationSecurity.exist("ipAccess")){
				throw new DuplicateParameterEntryException("Duplicate security key ipAccess.");
			}else{
				ConfigurationSecurity.put("ipAccess", ((LinkedHashMap<String, Object>)data).get("ipAccess"));
			}
		}
		if(((LinkedHashMap) data).containsKey("userAccess")){
			if(ConfigurationSecurity.exist("userAccess")){
				throw new DuplicateParameterEntryException("Duplicate security key userAccess.");
			}else{
				ConfigurationSecurity.put("userAccess", ((LinkedHashMap<String, Object>)data).get("userAccess"));
			}
		}
		if(((LinkedHashMap) data).containsKey("mask")){
			if(ConfigurationSecurity.exist("mask")){
				throw new DuplicateParameterEntryException("Duplicate security key mask.");
			}else{
				ConfigurationSecurity.put("mask", this.parseMask((LinkedHashMap) ((LinkedHashMap) data).get("mask")));
			}
		}
		
		return null;
	}
	
	public ArrayList<MaskPattern> parseMask(LinkedHashMap data){
		ArrayList<MaskPattern> masks = new ArrayList<MaskPattern>();
		Object[] dataKeys = data.keySet().toArray();
		
		for (Object dataKey : dataKeys) {
			LinkedHashMap mask = (LinkedHashMap) data.get(dataKey);
			masks.add(new MaskPattern(mask, (String) dataKey));
		}
		
		return masks;
	}

	/**
	 * Dump data.
	 * 
	 * Dump data. Currently not implemented.
	 * 
	 * @param data	Unknow parameter.
	 * @see 		org.bartelby.interfaces.Processor#dump(java.lang.Object)
	 */
	@Override
	public boolean dump(Object data) {
		// TODO Auto-generated method stub
		return false;
	}

}
