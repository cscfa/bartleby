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
		RouterProcessor router = new RouterProcessor();
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
							this.reportError(importFileElement, "parameters");
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
							this.reportError(importFileElement, "server");
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
							this.reportError(importFileElement, "user");
						}
					}
					
					if(data.containsKey("router")){
						if(router.dataIsValid(data.get("router"))){
							try {
								router.parse(data.get("router"));
							} catch (DuplicateParameterEntryException e) {
								throw new DuplicateParameterEntryException("Duplicate router key in file "+importFileElement.getPath(), e);
							}
						}else{
							this.reportError(importFileElement, "router");
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
	
	public void reportError(ImportFileElement file, String type) throws MalformedYamlFile{
		((Logger)ServiceContainer.get("logger")).warn("File "+file.getPath()+" contain "+type+" loading error.");
		if((boolean) ((ConsoleArgument)ServiceContainer.get("console")).getOption("debug")){
			((Logger)ServiceContainer.get("logger")).debug("File "+file.getPath()+" contain "+type+" loading error.");
		}
		throw new MalformedYamlFile("File "+file.getPath()+" contain "+type+" loading error.");
	}

}
