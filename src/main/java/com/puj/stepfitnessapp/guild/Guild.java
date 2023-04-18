package com.puj.stepfitnessapp.guild;

import com.puj.stepfitnessapp.guildchallenges.GuildChallenge;
import com.puj.stepfitnessapp.guildenterrequest.GuildEnterRequest;
import com.puj.stepfitnessapp.guildranks.GuildRank;
import com.puj.stepfitnessapp.player.Player;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "guilds")
public class Guild {

    @Id
    @SequenceGenerator(
            name = "guild_sequence",
            sequenceName = "guild_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guild_sequence"
    )
    private Long guildId;

    @Column(columnDefinition = "integer default 0")
    private Integer amountOfCompletedChallenges;

    @Column(columnDefinition = "integer default 0")
    private Integer xp;

    @OneToOne()
    @JoinColumn(referencedColumnName = "user_id")
    private Player owner;

    @ManyToOne
    @JoinColumn()
    private GuildRank guildRank;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "guild_id")
    private List<GuildChallenge> guildChallenges;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "guild_id")
    private List<GuildEnterRequest> guildEnterRequests;
}
