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
import gg.bayes.challenge.rest.utility.MatchUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeena A V
 *
 */
@Service
@Slf4j
public class MatchServiceImpl implements MatchService {
	
	

	private CombatLogEntryRepository entryRepository;
	private MatchRepository matchRepository;

	public MatchServiceImpl(CombatLogEntryRepository entryRepository, MatchRepository matchRepository) {
		this.entryRepository = entryRepository;
		this.matchRepository = matchRepository;
	}

	@Override
	public Long ingestCombatLog(String combatLog) {

		List<String> eventList = combatLog.lines().filter(line -> !line.isBlank()).collect(Collectors.toList());

		Set<CombatLogEntryEntity> combatEntry = new HashSet<>();

		eventList.stream().forEach(event -> {

			CombatLogEntryEntity combat = MatchUtility.getCombatEntry(event);
			
			if (combat.getType() != null && !combat.getActor().equals(MatchConstant.ACTOR_REMOVE) 
					&& !combat.getTarget().equals(MatchConstant.ACTOR_REMOVE)) {
				
				combatEntry.add(combat);
			}

		});

		MatchEntity match = new MatchEntity();

		match.setCombatLogEntries(combatEntry);
		matchRepository.save(match);
		System.out.println(eventList);
		return match.getId();
	}

}
