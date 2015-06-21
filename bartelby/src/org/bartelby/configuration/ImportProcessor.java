package org.bartelby.configuration;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.bartelby.console.ConsoleArgument;
import org.bartelby.exception.DirectoryNotFoundException;
import org.bartelby.exception.MalformedYamlFile;
import org.bartelby.exception.PreconditionException;
import org.bartelby.interfaces.Processor;
import org.bartelby.ressources.StringRessource;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;

public class ImportProcessor implements Processor {

	protected LinkedHashMap<String, Object> data;
	
	public ImportProcessor(LinkedHashMap<String, Object> yamlData) {
		super();
		this.data = yamlData;
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
			Logger logger = (Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER);
			logger.error("Fail to import config file from imports.yaml\nStack trace : "+esw.toString());
			logger.info("Fail to import config file from imports.yaml. See error log for stack trace.");
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
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("'import' element must be an ArrayList instance in import.yaml.");
			}
			throw new MalformedYamlFile("The import file is malformed at 'import'. See documentation.");
		}else{
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("'import' element must exist in import.yaml.");
			}
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
				if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
					((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("Each element of 'import' element must be an ArrayList instance in import.yaml.");
				}
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
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("The elements to import must be of only one type.");
			}
			throw new MalformedYamlFile("The import file is malformed in import element. See documentation.");
		}else if(!defaultElementType.containsAll(importElement.keySet())){
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("The elements to import can only be of type directory or file.");
			}
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

	public Object parse() throws FileNotFoundException, DirectoryNotFoundException{
		return this.parse(this.data);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object parse(Object data) throws FileNotFoundException, DirectoryNotFoundException {
		
		ArrayList importElement = (ArrayList) ((LinkedHashMap<String, Object>)data).get("import");
		
		ArrayList<ImportFileElement> fileList = new ArrayList<ImportFileElement>();
		
		for (Object insideElement : importElement) {
			LinkedHashMap element = (LinkedHashMap) insideElement;
			
			if(element.containsKey("directory")){
				ImportDirectoryElement dir = this.parseDirectory((HashMap<String, Object>) element.get("directory"));
				if(dir != null){
					fileList.addAll(dir.getFileList());
				}
			}else if(element.containsKey("file")){
				ImportFileElement file = this.parseFile((HashMap<String, Object>) element.get("file"));
				if(file != null){
					fileList.add(file);
				}
			}
		}
	
		return fileList;
	}
	
	private ImportDirectoryElement parseDirectory(HashMap<String, Object> element) throws FileNotFoundException, DirectoryNotFoundException{
		
		ImportDirectoryElement directory = null;
		
		if(element.containsKey("path")){
			directory = new ImportDirectoryElement((String) element.get("path"));
		}else{
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("A path error exist in import.yaml.");
			}
			return null;
		}
		
		if(element.containsKey("required")){
			directory.setRequired((Boolean) element.get("required"));
		}
		
		if(element.containsKey("recursive")){
			directory.setRequired((Boolean) element.get("recursive"));
		}
		
		try {
			directory.initPathDiscovering();
		} catch (PreconditionException e) {
			if(directory.isRequired()){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("File(s) is(are) required but not exist. Path : "+directory.getPath());
				if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
					((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("File(s) is(are) required but not exist. Path : "+directory.getPath());
				}
				throw new FileNotFoundException();
			}
		} catch (DirectoryNotFoundException e) {
			if(directory.isRequired()){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("File(s) is(are) required but not exist. Path : "+directory.getPath());
				if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
					((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("File(s) is(are) required but not exist. Path : "+directory.getPath());
				}
				
				throw new DirectoryNotFoundException(e);
			}
		}

		return directory;
	}
	
	private ImportFileElement parseFile(HashMap<String, Object> element) throws FileNotFoundException{
		
		ImportFileElement file = null;
		
		if(element.containsKey("path")){
			file = new ImportFileElement((String) element.get("path"));
		}else{
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("A path error exist in import.yaml.");
			}
			return null;
		}
		
		if(element.containsKey("required")){
			file.setRequired((Boolean) element.get("required"));
		}
		
		if(!file.fileExist() && file.isRequired()){
			((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("File(s) is(are) required but not exist. Path : "+file.getPath());
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("File(s) is(are) required but not exist. Path : "+file.getPath());
			}
			throw new FileNotFoundException();
		}
		
		return file;
	}

	@Override
	public boolean dump(Object data) {
		// TODO Auto-generated method stub
		return false;
	}
}
