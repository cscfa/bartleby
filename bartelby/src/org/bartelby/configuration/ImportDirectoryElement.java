package org.bartelby.configuration;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import org.bartelby.exception.PreconditionException;

public class ImportDirectoryElement {

	ArrayList<ImportFileElement> fileList = new ArrayList<ImportFileElement>();
	String path = null;
	Boolean required = true;
	Boolean recursive = false;
	protected Boolean wasInit = false;

	public ImportDirectoryElement() {
		super();
	}
	
	/**
	 * @param path
	 */
	public ImportDirectoryElement(String path) {
		super();
		this.path = path;
	}
	
	/**
	 * @param fileList
	 * @param path
	 */
	public ImportDirectoryElement(ArrayList<ImportFileElement> fileList,
			String path) {
		super();
		this.fileList = fileList;
		this.path = path;
	}
	
	/**
	 * @param fileList
	 * @param path
	 * @param required
	 */
	public ImportDirectoryElement(ArrayList<ImportFileElement> fileList,
			String path, Boolean required) {
		super();
		this.fileList = fileList;
		this.path = path;
		this.required = required;
	}
	
	/**
	 * @param fileList
	 * @param path
	 * @param required
	 * @param recursive
	 */
	public ImportDirectoryElement(ArrayList<ImportFileElement> fileList,
			String path, Boolean required, Boolean recursive) {
		super();
		this.fileList = fileList;
		this.path = path;
		this.required = required;
		this.recursive = recursive;
	}

	/**
	 * Return fileList.
	 * 
	 * Return files list of the directory element.
	 * 
	 * @return
	 */
	public ArrayList<ImportFileElement> getFileList() {
		return fileList;
	}

	/**
	 * Set fileList.
	 * 
	 * Set file list of the directory element.
	 * 
	 * @param fileList
	 * @return
	 */
	public ImportDirectoryElement setFileList(ArrayList<ImportFileElement> fileList) {
		this.fileList = fileList;
		return this;
	}

	/**
	 * Return path.
	 * 
	 * Return the directory path.
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Set path.
	 * 
	 * Set up the directory path.
	 * 
	 * @param path
	 * @return
	 */
	public ImportDirectoryElement setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Return is required.
	 * 
	 * Return the directory is require state.
	 * 
	 * @return
	 */
	public Boolean isRequired() {
		return required;
	}

	/**
	 * Set required.
	 * 
	 * Set up the direcctory to be required.
	 * 
	 * @param required
	 * @return
	 */
	public ImportDirectoryElement setRequired(Boolean required) {
		this.required = required;
		return this;
	}

	/**
	 * Return recursive.
	 * 
	 * Return the recursive state of the directory.
	 * 
	 * @return
	 */
	public Boolean isRecursive() {
		return recursive;
	}

	/**
	 * Set recursive.
	 * 
	 * Set the directory recursive.
	 * 
	 * @param recursive
	 * @return
	 */
	public ImportDirectoryElement setRecursive(Boolean recursive) {
		this.recursive = recursive;
		return this;
	}
	
	/**
	 * Return init state.
	 * 
	 * Return the directory init state.
	 * 
	 * @return
	 */
	public Boolean isInit() {
		return wasInit;
	}

	/**
	 * Set init state.
	 * 
	 * Set up the init state.
	 * 
	 * @param wasInit
	 */
	private void setWasInit(Boolean wasInit) {
		this.wasInit = wasInit;
	}
	
	private void initPathDiscovering() {
		ArrayList<String> tmpFileList = this.initPathDiscovering(this.getDirectory());
	}
	
	private ArrayList<String> initPathDiscovering(File dir) {

		ArrayList<String> tmpFileList = new ArrayList<String>();
		
		if(dir.exists() && dir.canRead() && dir.canExecute()){
			for (File containedFile : dir.listFiles()) {
				if(containedFile.canRead() && containedFile.isFile()){
					tmpFileList.add(containedFile.getAbsolutePath());
				}else if(containedFile.canRead() && containedFile.isDirectory() && containedFile.canExecute() && this.isRecursive()){
					tmpFileList.addAll(this.initPathDiscovering(containedFile));
				}else if((!containedFile.canRead() || (!containedFile.isDirectory() && !containedFile.canExecute())) && this.isRequired()){
					throw new PreconditionException("A file or directory that must be require is not accessible.");
				}
			}
		}
		
		return tmpFileList;
	}
	
	public boolean pathExist() {
		File testDir = new File(this.path);
		
		if(testDir.exists() && testDir.isDirectory()){
			return true;
		}else{
			return false;
		}
	}
	
	public File getDirectory(){
		if(this.pathExist()){
			return new File(this.path);
		}else{
			return null;
		}
	}
}
