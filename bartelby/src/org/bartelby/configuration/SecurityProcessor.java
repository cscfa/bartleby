package org.bartelby.configuration;

import java.io.Console;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.interfaces.Processor;

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
										if(!ip.toString().matches("(\\d{1,3}.){3}\\d{1,3}")){
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
										if(!ip.toString().matches("(\\d{1,3}.){3}\\d{1,3}")){
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
			}

		}else{
			return false;
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
		
		return null;
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
