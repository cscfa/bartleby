package org.bartelby.exception;

public class DirectoryNotFoundException extends Exception {

	/**
	 * Serialized UID version.
	 */
	private static final long serialVersionUID = -2148739388444624615L;

	/**
	 * Default constructor.
	 * 
	 * Default DirectoryNotFoundException constructor.
	 */
	public DirectoryNotFoundException() {
		super();
	}

	/**
	 * Complete constructor.
	 * 
	 * Complete DirectoryNotFoundException constructor.
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DirectoryNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor.
	 * 
	 * DirectoryNotFoundException constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	public DirectoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * DirectoryNotFoundException constructor.
	 * 
	 * @param message
	 */
	public DirectoryNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * DirectoryNotFoundException constructor.
	 * 
	 * @param cause
	 */
	public DirectoryNotFoundException(Throwable cause) {
		super(cause);
	}

	
}
