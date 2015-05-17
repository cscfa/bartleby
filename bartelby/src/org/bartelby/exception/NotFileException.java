/**
 * 
 */
package org.bartelby.exception;

/**
 * @author vallance
 *
 */
public class NotFileException extends Exception {

	/**
	 * Serialized UID version.
	 */
	private static final long serialVersionUID = 3226202361905273007L;

	/**
	 * Default constructor.
	 * 
	 * Default NotFileException constructor.
	 */
	public NotFileException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * NotFileException constructor.
	 * 
	 * @param message
	 */
	public NotFileException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * NotFileException constructor.
	 * 
	 * @param cause
	 */
	public NotFileException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 * 
	 * NotFileException constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	public NotFileException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Complete constructor.
	 * 
	 * Complete NotFileException constructor.
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NotFileException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
