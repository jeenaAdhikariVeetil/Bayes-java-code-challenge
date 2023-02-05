/**
 * 
 */
package gg.bayes.challenge.rest.service;

import java.util.List;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

/**
 * @author Jeena A V
 *
 */


public interface MatchService {
	
	 Long ingestCombatLog(String combatLog);
	
	 List<HeroKills> getMatch(Long matchId);
	
	 List<HeroItem> getHeroItems(Long matchId,String heroName);
	 
	 List<HeroSpells> getHeroSpells(Long matchId,String heroName);
	 
	 List<HeroDamage> getHeroDamages(Long matchId,String heroName);
}
