package com.puj.stepfitnessapp.guildenterrequest;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.player.Player;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "guild_enter_requests")
public class GuildEnterRequest {

    @Id
    @SequenceGenerator(
            name = "guild_enter_request_sequence",
            sequenceName = "guild_enter_request_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guild_enter_request_sequence"
    )
    private Long guildEnterRequestId;

    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Player player;


    public GuildEnterRequest(Guild guild, Player player) {
        this.guild = guild;
        this.player = player;
    }
}
