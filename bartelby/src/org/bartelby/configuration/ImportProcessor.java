package org.bartelby.configuration;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.bartelby.exception.MalformedYamlFile;
import org.bartelby.interfaces.Processor;
import org.slf4j.Logger;

public class ImportProcessor implements Processor {

	protected LinkedHashMap<String, Object> data;
	private Logger logger;
	
	public ImportProcessor(LinkedHashMap<String, Object> yamlData, Logger log) {
		super();
		this.data = yamlData;
		this.logger = log;
	}
	
	/**
	 * Valid data structure.
	 * 
	 * Valid the YAML import file schema.
	 * 
	 * @return Boolean
	 */
	public boolean dataIsValid(){
		return this.dataIsValid(this.data);
	}
	
	/**
	 * Valid data structure.
	 * 
	 * Valid the YAML import file schema.
	 * 
	 * @return Boolean
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean dataIsValid(Object data){
		try {
		
			this.dataValidationSchema((LinkedHashMap<String, Object>) data);
			return true;
		
		} catch (MalformedYamlFile e) {
			StringWriter esw = new StringWriter();
			PrintWriter epw = new PrintWriter(esw);
			e.printStackTrace(epw);
			this.logger.error("Fail to import config file from imports.yaml\nStack trace : "+esw.toString());
			this.logger.info("Fail to import config file from imports.yaml. See error log for stack trace.");
			return false;
		}
	}

	/**
	 * Valid root element.
	 * 
	 * Valid root element is define.
	 * 
	 * @param yamlData
	 * @throws MalformedYamlFile
	 */
	@SuppressWarnings("rawtypes")
	public void dataValidationSchema(LinkedHashMap<String, Object> yamlData) throws MalformedYamlFile{
		
		if(yamlData.containsKey("import") && yamlData.get("import") instanceof ArrayList){
			try {
				this.validImportSet((ArrayList) yamlData.get("import"));
			} catch (MalformedYamlFile e) {
				throw new MalformedYamlFile("The import file is malformed in import content. See documentation.", e);
			}
		}else if(yamlData.containsKey("import")){
			throw new MalformedYamlFile("The import file is malformed at 'import'. See documentation.");
		}
	}

	/**
	 * Valid imports elements.
	 * 
	 * Valid all imports elements.
	 * 
	 * @param importSet
	 * @throws MalformedYamlFile
	 */
	@SuppressWarnings("rawtypes")
	protected void validImportSet(ArrayList importSet) throws MalformedYamlFile{
		for (Object importElement : importSet) {
			if(importElement instanceof LinkedHashMap){
				try {
					this.validImportElement((LinkedHashMap) importElement);
				} catch (MalformedYamlFile e) {
					throw new MalformedYamlFile("The import file is malformed in import content. See documentation.", e);
				}
			}else{
				throw new MalformedYamlFile("The import file is malformed, wait for 'LinkedHashMap' into import. "+importElement.getClass()+" given . See documentation.");
			}
		}
	}
	
	/**
	 * Valid imports element schema.
	 * 
	 * Valid all imports element schema.
	 * 
	 * @param importElement
	 * @throws MalformedYamlFile
	 */
	@SuppressWarnings("rawtypes")
	protected void validImportElement(LinkedHashMap importElement) throws MalformedYamlFile {
		
		HashSet<String> defaultElementType = new HashSet<String>();
		defaultElementType.add("directory");
		defaultElementType.add("file");
		
		if(importElement.size() != 1){
			throw new MalformedYamlFile("The import file is malformed in import element. See documentation.");
		}else if(!defaultElementType.containsAll(importElement.keySet())){
			throw new MalformedYamlFile("The import file is malformed in import element. An element type is forbidden. See documentation.");
		}else{
			if(importElement.containsKey("directory")){
				this.validImportElementContent(importElement);
			}else{
				this.validImportElementContent(importElement, true);
			}
		}
	}
	
	/**
	 * Valid element definition.
	 * 
	 * Valid element definition. Used for directory element.
	 * 
	 * @param element
	 * @throws MalformedYamlFile
	 */
	@SuppressWarnings("rawtypes")
	protected void validImportElementContent(LinkedHashMap element) throws MalformedYamlFile{
		this.validImportElementContent(element, false);
	}
	
	/**
	 * Valid element definition.
	 * 
	 * Valid element definition. Used for each element.
	 * 
	 * @param element
	 * @param isFile
	 * @throws MalformedYamlFile
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void validImportElementContent(LinkedHashMap element, boolean isFile) throws MalformedYamlFile {

		HashSet<String> defaultElementContent = new HashSet<String>();
		defaultElementContent.add("path");
		
		String selector;
		if(!isFile){
			defaultElementContent.add("recursive");
			selector = new String("directory");
		}else{
			selector = new String("file");
		}
		
		defaultElementContent.add("required");

		HashSet<Object> recursiveDefaultType = new HashSet<Object>();
		recursiveDefaultType.add(true);
		recursiveDefaultType.add(false);

		HashSet<Object> requiredDefaultType = new HashSet<Object>();
		requiredDefaultType.add(true);
		requiredDefaultType.add(false);
		
		HashMap<String, HashSet<Object>> defaultElementContentType = new HashMap<String, HashSet<Object>>();
		
		if(!isFile){
			defaultElementContentType.put("recursive", recursiveDefaultType);
		}
		
		defaultElementContentType.put("required", requiredDefaultType);

		if(element.size() != 1){
			throw new MalformedYamlFile("The import file is malformed in import element content. See documentation.");
		}else if(!defaultElementContent.containsAll(((HashMap<String, Object>) element.get(selector)).keySet()) 
				|| !element.get(selector).getClass().equals(LinkedHashMap.class)){
			throw new MalformedYamlFile("The import file is malformed in import element content. An element content type is forbidden. See documentation.");
		}else{

			LinkedHashMap<String, String> entries = (LinkedHashMap<String, String>)element.get(selector);
			int entriesCount = entries.keySet().size();
			Object[] entriesArray = entries.keySet().toArray();
			
			for (Integer i = 0; i < entriesCount; i ++) {
				if(defaultElementContentType.containsKey(entriesArray[i])){
					if(!defaultElementContentType.get(entriesArray[i]).contains(entries.get(entriesArray[i]))){
						throw new MalformedYamlFile("The import file is malformed in import element content. Value "+entries.get(entriesArray[i])+" is not allowed for "+entriesArray[i]+". See documentation.");
					}
				}
			}
		}
	}


	@Override
	public Object parse(Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dump(Object data) {
		// TODO Auto-generated method stub
		return false;
	}
}
