package com.puj.stepfitnessapp.guild;

import com.puj.stepfitnessapp.guildchallenges.GuildChallenge;
import com.puj.stepfitnessapp.guildchallengesreward.GuildChallengesReward;
import com.puj.stepfitnessapp.guildenterrequest.GuildEnterRequest;
import com.puj.stepfitnessapp.guildrank.GuildRank;
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
    private Integer amountOfCompletedChallenges = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer xp = 0;

    private String guildName;

    @OneToOne()
    @JoinColumn(unique = true)
    private Player owner;

    @ManyToOne
    @JoinColumn()
    private GuildRank guildRank;

    @Column(columnDefinition = "integer default 0")
    private Integer guildLogoId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "guild_id")
    private List<GuildChallenge> guildChallenges;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "guild_id")
    private List<GuildEnterRequest> guildEnterRequests;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "guild")
    private List<GuildChallengesReward> guildChallengesRewards;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "guild")
    private List<Player> players;

    public Guild(Player owner, GuildRank guildRank, String guildName, Integer guildLogoId) {
        this.owner = owner;
        this.guildRank = guildRank;
        this.guildName = guildName;
        this.guildLogoId = guildLogoId;
    }
}
