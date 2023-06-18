package com.puj.stepfitnessapp.guildenterrequest;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GuildEnterRequestRepository extends JpaRepository<GuildEnterRequest, Long> {

    Optional<GuildEnterRequest> getGuildEnterRequestByGuildAndPlayer(Guild guild, Player player);

    Optional<List<GuildEnterRequest>> getGuildEnterRequestsByPlayer(Player player);

    @Transactional
    @Modifying
    void deleteByPlayer(Player player);
}
