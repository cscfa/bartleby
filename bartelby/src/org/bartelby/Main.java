package org.bartelby;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

import org.bartelby.configuration.ConfigurationLoader;
import org.bartelby.console.ConsoleArgument;
import org.bartelby.exception.DirectoryNotFoundException;
import org.bartelby.exception.EmptyFileException;
import org.bartelby.exception.MalformedYamlFile;
import org.bartelby.exception.NotFileException;
import org.bartelby.logger.Logger;
import org.bartelby.service.ServiceContainer;
import org.yaml.snakeyaml.error.YAMLException;

import ch.qos.logback.core.joran.spi.JoranException;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		args = new String[1];
		args[0] = "--debug";
		
		ConfigurationLoader conf = null;
		
		try {
			Logger logger = new Logger();
			ConsoleArgument console = new ConsoleArgument(args);

			ServiceContainer.set("logger", logger.getLogger());
			ServiceContainer.set("console", console);
			
			conf = new ConfigurationLoader();
			
			
		} catch (NotDirectoryException | FileNotFoundException
				| DirectoryNotFoundException | NotFileException
				| JoranException | EmptyFileException | YAMLException | MalformedYamlFile e) { 
			System.out.println(e);
			return;
		}
		
		conf.loadResources();
		
	}

}
