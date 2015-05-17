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
import java.util.HashSet;
import java.util.List;
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
		
		if(defaultConfigurationDirectory.exists() && defaultConfigurationDirectory.isDirectory()){
			defaultConfigurationDirectory = null;
			
			String defaultImportFilePath = new String(defaultConfigurationPath + "/" + StringRessource.DEFAULT_CONFIGURATION_IMPORT_FILE_DEFINER);
			File defaultImportFile = new File(defaultImportFilePath);
			
			if(defaultImportFile.exists() && defaultImportFile.isFile()){
				
			    InputStream ImportFileInput = new FileInputStream(defaultImportFile);
				Yaml yaml = new Yaml();
			    Map data = (Map) yaml.load(ImportFileInput);
			    
			    if(data == null){
			    	throw new EmptyFileException("File "+defaultImportFilePath+" is empty.");
			    }else{
			    	System.out.println(data);
			    	System.out.println(((ArrayList)data.get("import")).get(1));
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
