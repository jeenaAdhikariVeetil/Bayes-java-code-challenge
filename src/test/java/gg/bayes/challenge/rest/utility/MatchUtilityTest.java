/**
 * 
 */
package gg.bayes.challenge.rest.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;

/**
 * @author Jeena A V
 *
 */
@SpringBootTest
public class MatchUtilityTest {

	@Test
	public void getCombatEntryTest() {
		
		String event = "[00:10:42.031] npc_dota_hero_bane hits npc_dota_hero_abyssal_underlord with dota_unknown for 51 damage (740->689)";
		CombatLogEntryEntity combat = MatchUtility.getCombatEntry(event);
		System.out.println(combat);
		Assertions.assertEquals(642031L, combat.getTimestamp());
		Assertions.assertEquals("DAMAGE_DONE", combat.getType().toString());
		Assertions.assertEquals("bane", combat.getActor());
		Assertions.assertEquals("abyssal_underlord", combat.getTarget());
		Assertions.assertEquals(51, combat.getDamage());
	}
}
