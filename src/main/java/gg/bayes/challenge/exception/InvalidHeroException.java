/**
 * 
 */
package gg.bayes.challenge.exception;

/**
 * @author Jeena A V
 *
 */
public class InvalidHeroException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7028274431261134554L;

	public InvalidHeroException(String message, Throwable cause) {
		super(message, cause);

	}

	public InvalidHeroException(Throwable cause) {
		super(cause);

	}

	public InvalidHeroException(String message) {
		super(message);

	}

}
