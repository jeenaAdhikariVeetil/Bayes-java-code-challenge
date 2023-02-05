package gg.bayes.challenge.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MatchControllerIntegrationTest {

    private static final String COMBATLOG_FILE_1 = "/data/combatlog_1.log.txt";
    private static final String COMBATLOG_FILE_2 = "/data/combatlog_2.log.txt";

    @Autowired
    private MockMvc mvc;

    private Map<String, Long> matchIds;

    @BeforeAll
    void setup() throws Exception {
        // Populate the database with all events from both sample data files and store the returned
        // match IDS.
        matchIds = Map.of(
                COMBATLOG_FILE_1, ingestMatch(COMBATLOG_FILE_1),
                COMBATLOG_FILE_2, ingestMatch(COMBATLOG_FILE_2));      
    }

    
    @Test
    void ingestCombatLogTest() throws Exception {
    	String combatLog = IOUtils.resourceToString(COMBATLOG_FILE_1, StandardCharsets.UTF_8);
        assertThat(mvc).isNotNull();
        log.info("dota-match unit Tests- processing the combat log file");
        this.mvc.perform(post("/api/match")
                .contentType(MediaType.TEXT_PLAIN)
                .content(combatLog)).andDo(print())
        		.andExpect(status().isCreated())
        		.andExpect(jsonPath("$").isNotEmpty())
        		.andExpect(jsonPath("$").isNumber());
    }
    
    @Test
    void getMatchTest() throws Exception {
    	String combatLog = IOUtils.resourceToString(COMBATLOG_FILE_1, StandardCharsets.UTF_8);
    	log.info("dota-match unit Tests- Fetching the heros and kill counts");
    	Long matchId=matchIds.get(COMBATLOG_FILE_1);
    	this.mvc.perform(get("/api/match/"+matchId)
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(combatLog)).andDo(print())
    			.andDo(print())
    			.andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].hero").value("abyssal_underlord"))
                .andExpect(jsonPath("$[0].kills").value("6"))
                .andExpect(jsonPath("$[1].hero").value("bane"))
                .andExpect(jsonPath("$[1].kills").value("2"));  	
    }
    
    @Test
    void getHeroItemsTest() throws Exception {
    	String combatLog = IOUtils.resourceToString(COMBATLOG_FILE_1, StandardCharsets.UTF_8);
    	log.info("dota-match unit Tests- Fetching the items bought by the named hero");
    	Long matchId=matchIds.get(COMBATLOG_FILE_1);
    	this.mvc.perform(get("/api/match/"+matchId+ "/snapfire/items")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(combatLog)).andDo(print())
    			.andDo(print())
    			.andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
               //issue has to be fixed	
    }
    
    @Test
    void getHeroSpellsTest() throws Exception {
    	String combatLog = IOUtils.resourceToString(COMBATLOG_FILE_1, StandardCharsets.UTF_8);
    	log.info("dota-match unit Tests- Fetching the spells cast by the named hero");
    	Long matchId=matchIds.get(COMBATLOG_FILE_1);
    	this.mvc.perform(get("/api/match/"+matchId+"/bane/spells")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(combatLog)).andDo(print())
    			.andDo(print())
    			.andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].spell").value("bane_brain_sap"))
                .andExpect(jsonPath("$[0].casts").value("16"))
                .andExpect(jsonPath("$[1].spell").value("bane_fiends_grip"))
                .andExpect(jsonPath("$[1].casts").value("5"));  	
    }
    
    @Test
    void getHeroDamagesTest() throws Exception {
    	String combatLog = IOUtils.resourceToString(COMBATLOG_FILE_1, StandardCharsets.UTF_8);
    	log.info("dota-match unit Tests- Fetching the damage done data for the named hero");
    	Long matchId=matchIds.get(COMBATLOG_FILE_1);
    	this.mvc.perform(get("/api/match/"+matchId+"/bane/damage")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(combatLog)).andDo(print())
    			.andDo(print())
    			.andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].target").value("abyssal_underlord"))
                .andExpect(jsonPath("$[0].damage_instances").value("38"))
                .andExpect(jsonPath("$[0].total_damage").value("1712"))
                .andExpect(jsonPath("$[1].target").value("dragon_knight"))
                .andExpect(jsonPath("$[1].damage_instances").value("27"))
                .andExpect(jsonPath("$[1].total_damage").value("1809"));  	
    }
    
    /**
     * Helper method that ingests a combat log file and returns the match id associated with all parsed events.
     *
     * @param file file path as a classpath resource, e.g.: /data/combatlog_1.log.txt.
     * @return the id of the match associated with the events parsed from the given file
     * @throws Exception if an error happens when reading or ingesting the file
     */
    private Long ingestMatch(String file) throws Exception {
        String fileContent = IOUtils.resourceToString(file, StandardCharsets.UTF_8);

        return Long.parseLong(mvc.perform(post("/api/match")
                                         .contentType(MediaType.TEXT_PLAIN)
                                         .content(fileContent))
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString());
    }
}
