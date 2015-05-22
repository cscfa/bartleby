package org.bartelby.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ImportFileElement {

	String path = null;
	Boolean required = true;
	
	public ImportFileElement() {
		super();
	}
	
	public ImportFileElement(String filePath) {
		super();
		this.setPath(filePath);
	}
	
	public ImportFileElement(String filePath, Boolean required) {
		super();
		this.required = required;
		this.setPath(filePath);
	}

	/**
	 * Return path.
	 * 
	 * Return file path.
	 * 
	 * @return String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Set path.
	 * 
	 * Set up file path.
	 * 
	 * @param path
	 * @return 
	 * @throws FileNotFoundException 
	 */
	public ImportFileElement setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Return required.
	 * 
	 * Return file is required.
	 * 
	 * @return Boolean
	 */
	public Boolean isRequired() {
		return required;
	}

	/**
	 * Set required.
	 * 
	 * Set up if file is required.
	 * 
	 * @param required
	 * @return 
	 */
	public ImportFileElement setRequired(Boolean required)  {
		this.required = required;
		return this;
	}
	
	/**
	 * Check if file exist.
	 * 
	 * Check if file defined by path exist.
	 * 
	 * @return
	 */
	public boolean fileExist() {
		File testFile = new File(this.path);
		
		if(testFile.exists() && testFile.isFile()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Return file.
	 * 
	 * Return the file closed with path in a File instance.
	 * 
	 * @return
	 */
	public File getFile() {
		if(this.fileExist()){
			return new File(this.path);
		}else{
			return null;
		}
	}

}
