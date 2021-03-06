/**
 * 
 */
package org.bartelby.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bartelby.console.ConsoleArgument;
import org.bartelby.exception.DirectoryNotFoundException;
import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.exception.EmptyFileException;
import org.bartelby.exception.MalformedYamlFile;
import org.bartelby.exception.NotFileException;
import org.bartelby.ressources.BooleanRessource;
import org.bartelby.ressources.StringRessource;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

/**
 * @author vallance
 *
 */
public class ConfigurationLoader {
	
	protected ArrayList<ImportFileElement> fileToImport = new ArrayList<ImportFileElement>();

	public ConfigurationLoader() throws NotDirectoryException, FileNotFoundException, DirectoryNotFoundException, NotFileException, EmptyFileException, MalformedYamlFile{
		super();
		this.getImports();
	}
	
	public void getImports() throws NotDirectoryException, DirectoryNotFoundException, NotFileException, FileNotFoundException, EmptyFileException, MalformedYamlFile {
		
		Logger log = (Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER);
		String defaultConfigurationPath = null;
		
		if(BooleanRessource.USE_DEFAULT_CONFIGURATION_RELATIVE_PATH){
			String classpathEntries = System.getProperty("java.class.path").split(File.pathSeparator)[0];
			String classpathEntry = classpathEntries.substring(0, classpathEntries.lastIndexOf("/"));
			defaultConfigurationPath = new String(classpathEntry + "/" + StringRessource.DEFAULT_CONFIGURATION_RELATIVE_PATH);
		}else{
			defaultConfigurationPath = new String(StringRessource.DEFAULT_CONFIGURATION_ABSOLUTE_PATH);
		}
		
		File defaultConfigurationDirectory = new File(defaultConfigurationPath);
		
		if(defaultConfigurationDirectory.exists() && defaultConfigurationDirectory.isDirectory() && defaultConfigurationDirectory.canRead()){
			
			defaultConfigurationDirectory = null;
			
			String defaultImportFilePath = new String(defaultConfigurationPath + "/" + StringRessource.DEFAULT_CONFIGURATION_IMPORT_FILE_DEFINER);
			File defaultImportFile = new File(defaultImportFilePath);
			
			if(defaultImportFile.exists() && defaultImportFile.isFile() && defaultImportFile.canRead()){
				
			    InputStream ImportFileInput = new FileInputStream(defaultImportFile);
				Yaml yaml = new Yaml();
			    Map data = (Map) yaml.load(ImportFileInput);
			    
			    if(data == null){
			    	throw new EmptyFileException("File "+defaultImportFilePath+" is empty.");
			    }else{
			    	log.info("Start processing import config file.");
					if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
						log.debug("Start processing import config file.");
					}
			    	ImportProcessor importProcessor = new ImportProcessor((LinkedHashMap<String, Object>) data);
			    	
			    	if(importProcessor.dataIsValid()){
			    		try{
			    			this.fileToImport = (ArrayList<ImportFileElement>) importProcessor.parse();
			    		}catch(FileNotFoundException e){
			    			throw new YAMLException("A required file does not exist or an error exist in import.yaml.", e);
			    		}catch(DirectoryNotFoundException e){
			    			throw new YAMLException("A required directory does not exist or an error exist in import.yaml.", e);
			    		}
			    	}else{
			    		log.info("Starting fail. Import processpr return a malformed yaml file schema.");
			    		throw new MalformedYamlFile("File import.yaml can't be parsed.");
			    	}
			    }
				
			    try {
					ImportFileInput.close();
				} catch (IOException e) {}
			}else{
				if(defaultImportFile.exists()){
					throw new NotFileException(defaultImportFilePath+" is not a file.");
				}else{
					throw new FileNotFoundException("File "+defaultImportFilePath+" does not exist.");
				}
			}
			
		}else{
			if(defaultConfigurationDirectory.exists()){
				throw new NotDirectoryException(defaultConfigurationPath+" is not a directory.");
			}else{
				throw new DirectoryNotFoundException("Directory "+defaultConfigurationPath+" does not exist.");
			}
		}
		
	}
	
	public void loadResources() throws DuplicateParameterEntryException, MalformedYamlFile{
		Logger log = (Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER);
    	log.info("Start processing import config file.");
		if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
			log.debug("Start loading files from import config file.");
		}
		
		YamlFileSwitchLoader yamlLoader = new YamlFileSwitchLoader();
		
		try {
			yamlLoader.loadFiles(this.fileToImport);
		} catch (FileNotFoundException e) {
			((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("A file is not found.");
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("A file is not found.");
			}
		} catch (DuplicateParameterEntryException e) {
			((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("Duplicate parameter key.");
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("Duplicate parameter key.");
			}
			
			throw new DuplicateParameterEntryException("A yaml file contain errors.", e);
		} catch (MalformedYamlFile e) {
			((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("A yaml file contain errors.");
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("A yaml file contain errors.");
			}
			
			throw new MalformedYamlFile("A yaml file contain errors.", e);
		}
		
	}
	
}
