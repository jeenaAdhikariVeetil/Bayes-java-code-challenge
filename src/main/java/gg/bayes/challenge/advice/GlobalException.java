/**
 * 
 */
package gg.bayes.challenge.advice;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import gg.bayes.challenge.constant.MatchConstant;
import gg.bayes.challenge.exception.InvalidHeroException;
import gg.bayes.challenge.exception.NoMatchFoundException;



/**
 * @author Jeena A V
 *
 */

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoMatchFoundException.class)
	public ResponseEntity<String> handleException(NoMatchFoundException ex) {
		return new ResponseEntity<String>(MatchConstant.INVALID_MATCHID, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidHeroException.class)
	public ResponseEntity<String> handleException(InvalidHeroException ex) {
		return new ResponseEntity<String>(MatchConstant.INVALID_HERO, HttpStatus.BAD_REQUEST);
	}

}
