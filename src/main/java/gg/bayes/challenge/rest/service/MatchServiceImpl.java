/**
 * 
 */
package gg.bayes.challenge.rest.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gg.bayes.challenge.constant.MatchConstant;
import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.repository.MatchRepository;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.rest.utility.MatchUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeena A V
 *
 */
@Service
@Slf4j
public class MatchServiceImpl implements MatchService {
	
	

	private CombatLogEntryRepository combatRepository;
	private MatchRepository matchRepository;
	private MatchValidation matchValidation;

	public MatchServiceImpl(CombatLogEntryRepository combatRepository, 
			MatchRepository matchRepository,
			MatchValidation matchValidation) {
		
		this.combatRepository = combatRepository;
		this.matchRepository = matchRepository;
		this.matchValidation=matchValidation;
	}

	@Override
	public Long ingestCombatLog(String combatLog) {

		List<String> eventList = combatLog.lines().filter(line -> !line.isBlank()).
				collect(Collectors.toList());
		Set<CombatLogEntryEntity> combatEntry = new HashSet<>();
		eventList.stream().forEach(event -> {
			
			log.info("dota-match- Parsing the event starts");
			CombatLogEntryEntity combat = MatchUtility.getCombatEntry(event);
			if (combat.getType() != null && !combat.getActor().equals(MatchConstant.ACTOR_REMOVE)
					&& !combat.getTarget().equals(MatchConstant.ACTOR_REMOVE)) {
				combatEntry.add(combat);
			}
		});

		MatchEntity match = new MatchEntity();
		match.setCombatLogEntries(combatEntry);
		
		matchRepository.save(match);
		
		return match.getId();
	}

	@Override
	public List<HeroKills> getMatch(Long matchId) {
		matchValidation.validateMatch(matchId);
		return combatRepository.getMatch(matchId);
	}

	@Override
	public List<HeroItem> getHeroItems(Long matchId, String heroName) {
		matchValidation.validateMatch(matchId);
		matchValidation.validateHero(heroName);
		return combatRepository.getHeroItems(matchId, heroName);
	}

	@Override
	public List<HeroSpells> getHeroSpells(Long matchId, String heroName) {
		matchValidation.validateMatch(matchId);
		matchValidation.validateHero(heroName);
		return combatRepository.getHeroSpells(matchId, heroName);
	}

	@Override
	public List<HeroDamage> getHeroDamages(Long matchId, String heroName) {
		matchValidation.validateMatch(matchId);
		matchValidation.validateHero(heroName);
		return combatRepository.getHeroDamages(matchId, heroName);
	}

}
