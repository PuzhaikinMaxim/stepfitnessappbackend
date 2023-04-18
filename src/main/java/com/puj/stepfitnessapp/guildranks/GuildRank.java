package com.puj.stepfitnessapp.guildranks;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "guild_ranks")
public class GuildRank {

    @Id
    @SequenceGenerator(
            name = "guild_rank_sequence",
            sequenceName = "guild_rank_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guild_rank_sequence"
    )
    private Integer guildRank;

    private Integer xpToNextRank;
}
