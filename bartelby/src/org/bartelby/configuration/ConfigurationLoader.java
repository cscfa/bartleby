/**
 * 
 */
package org.bartelby.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bartelby.exception.DirectoryNotFoundException;
import org.bartelby.exception.EmptyFileException;
import org.bartelby.exception.NotFileException;
import org.bartelby.ressources.BooleanRessource;
import org.bartelby.ressources.StringRessource;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 * @author vallance
 *
 */
public class ConfigurationLoader {

	public ConfigurationLoader(Logger log) throws NotDirectoryException, FileNotFoundException, DirectoryNotFoundException, NotFileException, EmptyFileException{
		super();
		this.getImports(log);
	}
	
	public void getImports(Logger log) throws NotDirectoryException, DirectoryNotFoundException, NotFileException, FileNotFoundException, EmptyFileException {
		
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
			    	ImportProcessor importProcessor = new ImportProcessor((LinkedHashMap<String, Object>) data, log);
			    	importProcessor.dataIsValid();
			    	/*System.out.println(data);
			    	
			    	Object keys[] = ((LinkedHashMap<String, Object>)data).keySet().toArray();
			    	
			    	for (int i = 0; i < keys.length; i++) {
			    		ArrayList imports = (ArrayList) ((LinkedHashMap)data).get(keys[i]);
			    		for(int j = 0; j < imports.size(); j++){
					    	System.out.println(((LinkedHashMap)imports.get(j)).keySet().toArray()[0]);
			    		}
					}*/
			    }
				
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
	
}
