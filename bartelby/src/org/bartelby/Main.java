package org.bartelby;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.NotDirectoryException;
import java.util.concurrent.ConcurrentHashMap;

import org.bartelby.cache.CacheService;
import org.bartelby.configuration.ConfigurationLoader;
import org.bartelby.configuration.ConfigurationParameters;
import org.bartelby.console.ConsoleArgument;
import org.bartelby.exception.DirectoryNotFoundException;
import org.bartelby.exception.DuplicateParameterEntryException;
import org.bartelby.exception.EmptyFileException;
import org.bartelby.exception.MalformedYamlFile;
import org.bartelby.exception.NotFileException;
import org.bartelby.inputServer.BartelbyServerListener;
import org.bartelby.logger.Logger;
import org.bartelby.ressources.StringRessource;
import org.bartelby.service.ServiceContainer;
import org.bartelby.tools.MaxValueMap;
import org.bartelby.unixServer.BartelbyUnixServerListener;
import org.yaml.snakeyaml.error.YAMLException;

import ch.qos.logback.core.joran.spi.JoranException;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		args = new String[1];
		args[0] = "--debug";
		
		ConfigurationParameters.put(StringRessource.DEFAULT_SERVER_SPACE, (new ConcurrentHashMap<String, Object>()));
		((ConcurrentHashMap)ConfigurationParameters.get(StringRessource.DEFAULT_SERVER_SPACE)).put(StringRessource.DEFAULT_STATUS_SPACE, StringRessource.DEFAULT_STATUS_SPACE_STARTED);
		
		ConfigurationLoader conf = null;
		
		try {
			Logger logger = new Logger();
			ConsoleArgument console = new ConsoleArgument(args);

			ServiceContainer.set(StringRessource.SERVICE_LOGGER, logger.getLogger());
			ServiceContainer.set(StringRessource.SERVICE_CONSOLE, console);
			
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("\n\n\n--- Starting ---");
			}
			
			conf = new ConfigurationLoader();
			
			
		} catch (NotDirectoryException | FileNotFoundException
				| DirectoryNotFoundException | NotFileException
				| JoranException | EmptyFileException | YAMLException | MalformedYamlFile e) { 
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).trace(sw.toString());
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("Starting fail.\n"+sw.toString());
			}
			pw.close();
			try {sw.close();} catch (IOException e1) {}
			((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("Starting fail");
			((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("Starting fail");
			return;
		}
		
		try {
			conf.loadResources();
			
			BartelbyServerListener server = new BartelbyServerListener();
			if(server.validServerConfiguration()){
				server.start();
			}else{
				System.out.println("error");
			}
			
			BartelbyUnixServerListener unixServer = new BartelbyUnixServerListener();
			unixServer.setDaemon(true);
			unixServer.start();
			
			
		} catch (DuplicateParameterEntryException | MalformedYamlFile e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).trace(sw.toString());
			if((boolean) ((ConsoleArgument)ServiceContainer.get(StringRessource.SERVICE_CONSOLE)).getOption(ConsoleArgument.ARG_DEBUG)){
				((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).debug("Starting fail.\n"+sw.toString());
			}
			pw.close();
			try {sw.close();} catch (IOException e1) {}
			((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).info("Starting fail");
			((org.slf4j.Logger)ServiceContainer.get(StringRessource.SERVICE_LOGGER)).error("Starting fail");
			return;
		}
	}
}
