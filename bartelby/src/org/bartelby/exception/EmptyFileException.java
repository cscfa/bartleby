/**
 * 
 */
package org.bartelby.exception;

/**
 * @author vallance
 *
 */
public class EmptyFileException extends Exception {

	/**
	 * Serialized UID version.
	 */
	private static final long serialVersionUID = 3849466967170915353L;

	/**
	 * Default constructor.
	 * 
	 * Default EmptyFileException constructor.
	 */
	public EmptyFileException() {
		super();
	}

	/**
	 * Complete constructor.
	 * 
	 * Complete EmptyFileException constructor.
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EmptyFileException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor.
	 * 
	 * EmptyFileException constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	public EmptyFileException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor.
	 * 
	 * EmptyFileException constructor.
	 * 
	 * @param message
	 */
	public EmptyFileException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor.
	 * 
	 * EmptyFileException constructor.
	 * 
	 * @param cause
	 */
	public EmptyFileException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
