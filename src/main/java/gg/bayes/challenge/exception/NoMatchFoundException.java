/**
 * 
 */
package gg.bayes.challenge.exception;

/**
 * @author Jeena A V
 *
 */
public class NoMatchFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1404159973428874645L;

	public NoMatchFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public NoMatchFoundException(String message) {
		super(message);

	}

	public NoMatchFoundException(Throwable cause) {
		super(cause);

	}

}
