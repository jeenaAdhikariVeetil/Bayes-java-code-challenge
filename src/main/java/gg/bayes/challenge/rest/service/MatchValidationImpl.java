/**
 * 
 */
package gg.bayes.challenge.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.bayes.challenge.constant.MatchConstant;
import gg.bayes.challenge.exception.InvalidHeroException;
import gg.bayes.challenge.exception.NoMatchFoundException;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.repository.MatchRepository;

/**
 * @author Jeena A V
 *
 */

@Service
public class MatchValidationImpl implements MatchValidation{
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	CombatLogEntryRepository comEntryRepository;

	@Override
	public void validateMatch(Long matchId) {
		matchRepository.findById(matchId).orElseThrow(
				() -> new NoMatchFoundException(MatchConstant.INVALID_MATCHID + matchId)
						);
	}

	@Override
	public void validateHero(String hero) {
		if (hero.isEmpty() || hero == null) {
			throw new InvalidHeroException(MatchConstant.INVALID_HERO);
		}

	}

}
