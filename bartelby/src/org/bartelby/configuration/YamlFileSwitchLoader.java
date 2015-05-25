package org.bartelby.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import org.bartelby.console.ConsoleArgument;
import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.exception.MalformedYamlFile;
import org.bartelby.service.ServiceContainer;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

public class YamlFileSwitchLoader {

	public YamlFileSwitchLoader() {
		super();
	}

	public void loadFiles(ArrayList<ImportFileElement> fileToImport) throws FileNotFoundException, DuplicateParameterEntryException, MalformedYamlFile {
		
		ParameterProcessor param = new ParameterProcessor();
		ServerProcessor server = new ServerProcessor();
		UserProcessor user = new UserProcessor();
		Yaml yaml = new Yaml();
		
		for (ImportFileElement importFileElement : fileToImport) {
			if(importFileElement.getPath().endsWith(".yaml")){
				try {
					Map data = (Map) yaml.load(new FileInputStream(new File(importFileElement.getPath())));

					if(data.containsKey("parameters")){
						if(param.dataIsValid(data.get("parameters"))){
							try {
								param.parse(data.get("parameters"));
							} catch (DuplicateParameterEntryException e) {
								throw new DuplicateParameterEntryException("Duplicate parameter key in file "+importFileElement.getPath(), e);
							}
						}else{
							((Logger)ServiceContainer.get("logger")).warn("File "+importFileElement.getPath()+" contain parameters loading error.");
							if((boolean) ((ConsoleArgument)ServiceContainer.get("console")).getOption("debug")){
								((Logger)ServiceContainer.get("logger")).debug("File "+importFileElement.getPath()+" contain parameters loading error.");
							}
							
							throw new MalformedYamlFile("File "+importFileElement.getPath()+" contain parameters loading error.");
						}
					}
					
					if(data.containsKey("server")){
						if(server.dataIsValid(data.get("server"))){
							try {
								server.parse(data.get("server"));
							} catch (DuplicateParameterEntryException e) {
								throw new DuplicateParameterEntryException("Duplicate server key in file "+importFileElement.getPath(), e);
							}
						}else{
							((Logger)ServiceContainer.get("logger")).warn("File "+importFileElement.getPath()+" contain server loading error.");
							if((boolean) ((ConsoleArgument)ServiceContainer.get("console")).getOption("debug")){
								((Logger)ServiceContainer.get("logger")).debug("File "+importFileElement.getPath()+" contain server loading error.");
							}
							throw new MalformedYamlFile("File "+importFileElement.getPath()+" contain server loading error.");
						}
					}
					
					if(data.containsKey("user")){
						if(user.dataIsValid(data.get("user"))){
							try {
								user.parse(data.get("user"));
							} catch (DuplicateParameterEntryException e) {
								throw new DuplicateParameterEntryException("Duplicate user key in file "+importFileElement.getPath(), e);
							}
						}else{
							((Logger)ServiceContainer.get("logger")).warn("File "+importFileElement.getPath()+" contain user loading error.");
							if((boolean) ((ConsoleArgument)ServiceContainer.get("console")).getOption("debug")){
								((Logger)ServiceContainer.get("logger")).debug("File "+importFileElement.getPath()+" contain user loading error.");
							}
							throw new MalformedYamlFile("File "+importFileElement.getPath()+" contain user loading error.");
						}
					}
				} catch (FileNotFoundException e) {
					((Logger)ServiceContainer.get("logger")).error("File "+importFileElement.getPath()+" is not found.");
					if((boolean) ((ConsoleArgument)ServiceContainer.get("console")).getOption("debug")){
						((Logger)ServiceContainer.get("logger")).debug("File "+importFileElement.getPath()+" is not found.");
					}
					
					throw new FileNotFoundException("File "+importFileElement.getPath()+" is not found.");
				}
			}else{
				((Logger)ServiceContainer.get("logger")).warn("File "+importFileElement.getPath()+" is not a yaml file pattern.");
				if((boolean) ((ConsoleArgument)ServiceContainer.get("console")).getOption("debug")){
					((Logger)ServiceContainer.get("logger")).debug("File "+importFileElement.getPath()+" is not a yaml file pattern.");
				}
			}
		}
		
	}

}
