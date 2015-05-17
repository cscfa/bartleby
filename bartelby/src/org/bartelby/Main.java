package org.bartelby;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

import org.bartelby.configuration.ConfigurationLoader;
import org.bartelby.exception.DirectoryNotFoundException;
import org.bartelby.exception.EmptyFileException;
import org.bartelby.exception.NotFileException;
import org.bartelby.logger.Logger;

import ch.qos.logback.core.joran.spi.JoranException;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Logger logger = new Logger();
			ConfigurationLoader conf = new ConfigurationLoader(logger.getLogger());
			
		} catch (NotDirectoryException | FileNotFoundException
				| DirectoryNotFoundException | NotFileException
				| JoranException | EmptyFileException e) {
			System.out.println(e);
		}
		
	}

}
