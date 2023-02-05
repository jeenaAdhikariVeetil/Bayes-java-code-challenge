package gg.bayes.challenge.persistence.repository;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CombatLogEntryRepository extends JpaRepository<CombatLogEntryEntity, Long> {

	@Query("SELECT new gg.bayes.challenge.rest.model.HeroKills(log.actor,COUNT(log.id))"
			+ " FROM CombatLogEntryEntity log join MatchEntity match" +" ON match.id= ?1"
			+ " WHERE log.type = 'HERO_KILLED'"
			+ " GROUP BY log.actor" + " ORDER BY log.actor")
	List<HeroKills> getMatch(Long matcId);

	
	@Query("SELECT new gg.bayes.challenge.rest.model.HeroItem(log.item,log.timestamp)"
			+ " FROM CombatLogEntryEntity log join MatchEntity match" +" ON match.id= ?1"
			+ " WHERE log.type = 'ITEM_PURCHASED'"
			+ " AND log.actor= ?2" )
	List<HeroItem> getHeroItems(Long matchId, String heroName);
	
	
	@Query("SELECT new gg.bayes.challenge.rest.model.HeroSpells(log.ability,COUNT(log.id))"
			+ " FROM CombatLogEntryEntity log join MatchEntity match" +" ON match.id= ?1"
			+ " WHERE log.type = 'SPELL_CAST'"
			+ " AND log.actor= ?2" 
			+ " GROUP BY log.ability")
	List<HeroSpells> getHeroSpells(Long matchId,String heroName);
	
	
	@Query("SELECT new gg.bayes.challenge.rest.model.HeroDamage(log.target,COUNT(log.id),SUM(log.damage))"
			+ " FROM CombatLogEntryEntity log join MatchEntity match" +" ON match.id= ?1"
			+ " WHERE log.type = 'DAMAGE_DONE'"
			+ " AND log.actor= ?2"
			+ " GROUP BY log.target")
	 List<HeroDamage> getHeroDamages(Long matchId,String heroName);
}
