/**
 * 
 */
package gg.bayes.challenge.rest.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import gg.bayes.challenge.constant.MatchConstant;
import gg.bayes.challenge.exception.InvalidHeroException;
import gg.bayes.challenge.exception.NoMatchFoundException;
import gg.bayes.challenge.persistence.repository.MatchRepository;
/**
 * @author Jeena A V
 *
 */
@SpringBootTest
public class MatchValidationTest {
	
	@InjectMocks
	MatchValidationImpl matchValidationImpl;
	
	@Mock
	MatchRepository matchRepository;

	@Test
	public void validateMatchTest()
	{
		Mockito.when(matchRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		NoMatchFoundException exception = assertThrows(NoMatchFoundException.class, () -> {
			matchValidationImpl.validateMatch(1L);
		});
		String expectedMessage = MatchConstant.INVALID_MATCHID + 1L;
		String actualMessage = exception.getMessage();

		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void validateHeroTest()
	{
		assertThrows(InvalidHeroException.class, () -> {
			matchValidationImpl.validateHero("");
		});
	}
}
