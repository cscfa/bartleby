package org.bartelby.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

import org.bartelby.exception.DirectoryNotFoundException;
import org.bartelby.exception.NotFileException;
import org.bartelby.ressources.BooleanRessource;
import org.bartelby.ressources.StringRessource;
import org.slf4j.LoggerFactory;

import sun.org.mozilla.javascript.internal.regexp.SubString;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class Logger{
	
	protected org.slf4j.Logger logger = null;

	public Logger() throws NotDirectoryException, FileNotFoundException, DirectoryNotFoundException, NotFileException, JoranException{
		super();
		this.logger = (org.slf4j.Logger) LoggerFactory.getLogger(Logger.class);
		this.init();
	}
	
	public void init() throws FileNotFoundException, DirectoryNotFoundException, NotDirectoryException, NotFileException, JoranException {

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
			
			String defaultLoggerFilePath = new String(defaultConfigurationPath + "/" + StringRessource.DEFAULT_LOGGER_CONFIGURATION_FILE_NAME);
			File defaultLoggerFile = new File(defaultLoggerFilePath);
			
			if(defaultLoggerFile.exists() && defaultLoggerFile.isFile()){
				
			    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
			    
			    try {
			      JoranConfigurator configurator = new JoranConfigurator();
			      configurator.setContext(context);
			      context.reset(); 
			      configurator.doConfigure(defaultLoggerFilePath);
			    } catch (JoranException je) {
			    	throw new JoranException("Configuration file error for "+defaultLoggerFilePath, je);
			    }
			    StatusPrinter.printInCaseOfErrorsOrWarnings(context);
				
			}else{
				if(defaultLoggerFile.exists()){
					throw new NotFileException(defaultLoggerFilePath+" is not a file.");
				}else{
					throw new FileNotFoundException("File "+defaultLoggerFilePath+" does not exist.");
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
	
	public org.slf4j.Logger getLogger() {
		return this.logger;
	}
	
	protected void setLogger(org.slf4j.Logger logger){
		this.logger = logger;
	}
}
