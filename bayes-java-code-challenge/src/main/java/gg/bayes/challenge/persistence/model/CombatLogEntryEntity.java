package gg.bayes.challenge.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dota_combat_log")
public class CombatLogEntryEntity {

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dota_combat_log_sequence_generator"
    )
    @SequenceGenerator(
            name = "dota_combat_log_sequence_generator",
            sequenceName = "dota_combat_log_sequence",
            allocationSize = 1
    )
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    public MatchEntity match;

    @NotNull
    @Column(name = "entry_timestamp")
    private Long timestamp;

    @NotNull
    @Column(name = "entry_type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "actor")
    private String actor;

    @Column(name = "target")
    private String target;

    @Column(name = "ability")
    private String ability;

    @Column(name = "ability_level")
    private Integer abilityLevel;

    @Column(name = "item")
    private String item;

    @Column(name = "damage")
    private Integer damage;

    public enum Type {
        ITEM_PURCHASED,
        HERO_KILLED,
        SPELL_CAST,
        DAMAGE_DONE
    }

	
    
}
