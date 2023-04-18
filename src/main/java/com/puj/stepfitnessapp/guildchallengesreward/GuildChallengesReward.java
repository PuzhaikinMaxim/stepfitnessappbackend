package com.puj.stepfitnessapp.guildchallengesreward;

import com.puj.stepfitnessapp.items.Item;
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
@Table(name = "guild_challenges_reward")
public class GuildChallengesReward {

    @Id
    @SequenceGenerator(
            name = "guild_challenges_reward_sequence",
            sequenceName = "guild_challenges_reward_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guild_challenges_reward_sequence"
    )
    private Long guildChallengesRewardId;

    @OneToOne()
    @JoinColumn(name = "player")
    private Player player;

    @Column(nullable = false)
    private Integer xp;

    @Convert(converter = GuildRewardConverter.class)
    private List<Item> reward;
}
