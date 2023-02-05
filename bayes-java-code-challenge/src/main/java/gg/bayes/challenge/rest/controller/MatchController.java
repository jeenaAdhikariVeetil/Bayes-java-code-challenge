package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.rest.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequestMapping("/api/match")
@Validated
public class MatchController {

	private MatchService matchService;

	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	/**
	 * Ingests a DOTA combat log file, parses and persists relevant events data. All
	 * events are associated with the same match id.
	 *
	 * @param combatLog the content of the combat log file
	 * @return the match id associated with the parsed events
	 */
	
	@PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Long> ingestCombatLog(@RequestBody @NotBlank String combatLog) {
		
		log.info("dota-match ingestCombatLog");
		Long matchId = matchService.ingestCombatLog(combatLog);
		
		return new ResponseEntity<Long>(matchId, HttpStatus.CREATED);

	}

	/**
	 * Fetches the heroes and their kill counts for the given match.
	 *
	 * @param matchId the match identifier
	 * @return a collection of heroes and their kill counts
	 */
	@GetMapping(path = "{matchId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HeroKills>> getMatch(@PathVariable("matchId") Long matchId) {

		log.info("dota-match getMatch");
		throw new NotImplementedException("TODO: implement");
	}

	/**
	 * For the given match, fetches the items bought by the named hero.
	 *
	 * @param matchId  the match identifier
	 * @param heroName the hero name
	 * @return a collection of items bought by the hero during the match
	 */
	@GetMapping(path = "{matchId}/{heroName}/items", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HeroItem>> getHeroItems(@PathVariable("matchId") Long matchId,
			@PathVariable("heroName") String heroName) {
		log.info("dota-match getHeroItems");
		throw new NotImplementedException("TODO: implement");
	}

	/**
	 * For the given match, fetches the spells cast by the named hero.
	 *
	 * @param matchId  the match identifier
	 * @param heroName the hero name
	 * @return a collection of spells cast by the hero and how many times they were
	 *         cast
	 */
	@GetMapping(path = "{matchId}/{heroName}/spells", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HeroSpells>> getHeroSpells(@PathVariable("matchId") Long matchId,
			@PathVariable("heroName") String heroName) {

		throw new NotImplementedException("TODO: implement");
	}

	/**
	 * For a given match, fetches damage done data for the named hero.
	 *
	 * @param matchId  the match identifier
	 * @param heroName the hero name
	 * @return a collection of "damage done" (target, number of times and total
	 *         damage) elements
	 */
	@GetMapping(path = "{matchId}/{heroName}/damage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HeroDamage>> getHeroDamages(@PathVariable("matchId") Long matchId,
			@PathVariable("heroName") String heroName) {

		throw new NotImplementedException("TODO: implement");
	}
}
