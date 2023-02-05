/**
 * 
 */
package gg.bayes.challenge.rest.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import gg.bayes.challenge.constant.MatchConstant;
import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.CombatLogEntryEntity.Type;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeena A V
 *
 */
@Slf4j
public class MatchUtility {

	public static CombatLogEntryEntity getCombatEntry(String event)  {

		String[] eventArray = event.split(MatchConstant.SPACE);

		CombatLogEntryEntity combat = new CombatLogEntryEntity();

		int arr_len = eventArray.length;
		int i = 1;

		Long timeStamp = getTimeStamp(eventArray[0].trim());
		String ability = "";
		int abilityLevel = 0;
		String actor = "";
		int damage = 0;
		String item = "";
		String target = "";

		boolean isKilled = false;
		boolean isTarget = false;
		boolean isCast = false;

		while (i < arr_len) {
			if (eventArray[i].equals(MatchConstant.HITS)) {

				target = getHero(eventArray[i + 1]);
				isTarget = true;
			}

			if (eventArray[i].equals(MatchConstant.ITEM)) {
				item = eventArray[i + 1].replace(MatchConstant.ITEM_REPLACE, "");
			}
			if (eventArray[i].equals(MatchConstant.DAMAGE)) {
				damage = Integer.parseInt(eventArray[i - 1]);
				combat.setType(Type.DAMAGE_DONE);
			}
			if (eventArray[i].equals(MatchConstant.ABILITY)) {
				ability = eventArray[i + 1];
				abilityLevel = Integer.parseInt(eventArray[i + 3].replace(")", ""));
			}
			if (eventArray[i].equals(MatchConstant.BUYS)) {
				combat.setType(Type.ITEM_PURCHASED);
			}
			if (eventArray[i].equals(MatchConstant.KILLED)) {

				actor = getHero(eventArray[i + 2]);
				target = getHero(eventArray[i - 2]);
				combat.setType(Type.HERO_KILLED);
				isKilled = true;
			}
			if (eventArray[i].startsWith(MatchConstant.ACTOR) && !isKilled && !isTarget && !isCast) {
				actor = getHero(eventArray[i]);
			}
			if (eventArray[i].equals(MatchConstant.CASTS)) {
				target = getHero(eventArray[i + 6]);
				combat.setType(Type.SPELL_CAST);
				isCast = true;
			}
			i = i + 1;
		}

		if (combat.getType() != null) {
			combat.setAbility(ability);
			combat.setAbilityLevel(abilityLevel);
			combat.setActor(actor);
			combat.setDamage(damage);
			combat.setItem(item);
			combat.setTarget(target);
			combat.setTimestamp(timeStamp);
		}

		return combat;
	}

	public static Long getTimeStamp(String timestamp) {
		
		timestamp = timestamp.replaceAll("\\[", "").replaceAll("\\]", "");

		SimpleDateFormat sdf = new SimpleDateFormat(MatchConstant.DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = new Date();
		try {
			date = sdf.parse("1970-01-01 " + timestamp);
		} catch (ParseException e) {
			log.error("dota-match Error- Parsing timestamp has been failed");
		}
		return date.getTime();
	}
	
	
	public static String getHero(String input) {

		if (input.startsWith(MatchConstant.ACTOR)) {
			return input.replace(MatchConstant.ACTOR, "");
		} else {
			return MatchConstant.ACTOR_REMOVE;
		}

	}

}
