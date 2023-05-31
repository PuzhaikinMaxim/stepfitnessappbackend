package com.puj.stepfitnessapp;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.guild.GuildService;
import com.puj.stepfitnessapp.guildchallenges.GuildChallenge;
import com.puj.stepfitnessapp.guildchallenges.GuildChallengesRepository;
import com.puj.stepfitnessapp.guildchallenges.GuildChallengesService;
import com.puj.stepfitnessapp.guildchallengesreward.GuildChallengesRewardService;
import com.puj.stepfitnessapp.guildrank.GuildRank;
import com.puj.stepfitnessapp.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(value = SpringJUnit4ClassRunner.class)
class GuildChallengeServiceTests {

	private GuildChallengesService guildChallengesService;

	@MockBean
	private GuildChallengesRepository guildChallengesRepository;

	@MockBean
	private GuildChallengesRewardService guildChallengesRewardService;

	@MockBean
	private GuildService guildService;

	private Guild guild;

	private Player owner;

	@BeforeEach
	public void setup() {
		guildChallengesService = new GuildChallengesService(
				guildChallengesRepository,
				guildChallengesRewardService,
				guildService
		);
		owner = getGuildOwner();
		guild = getGuild();
	}

	@Test
	void challengeCompleteTest() {
		Mockito.when(guildChallengesRepository.findGuildChallengeByGuildAndIsStartedTrue(guild)).thenReturn(
				Optional.of(getGuildChallenge())
		);
		Assertions.assertTrue(guildChallengesService.updateProgress(200,guild));
	}

	@Test
	void challengeUpdateProgressPointsCountTest() {
		var guildChallenge = getGuildChallenge();
		guildChallenge.setPointsMultiplier(2.0);
		Mockito.when(guildChallengesRepository.findGuildChallengeByGuildAndIsStartedTrue(guild)).thenReturn(
				Optional.of(guildChallenge)
		);
		Assertions.assertTrue(guildChallengesService.updateProgress(100,guild));

		guildChallenge.setProgress(0);
		guildChallenge.setPointsMultiplier(1.9);
		Assertions.assertFalse(guildChallengesService.updateProgress(100,guild));

		guildChallenge.setProgress(0);
		guildChallenge.setPointsMultiplier(1.0);
		guildChallenge.setPointsFixed(100);
		Assertions.assertTrue(guildChallengesService.updateProgress(100,guild));

		guildChallenge.setProgress(0);
		guildChallenge.setPointsFixed(99);
		Assertions.assertFalse(guildChallengesService.updateProgress(100,guild));
	}

	@Test
	void challengeUpdateProgressTest() {
		Mockito.when(guildChallengesRepository.findGuildChallengeByGuildAndIsStartedTrue(guild)).thenReturn(
				Optional.of(getGuildChallenge())
		);
		Assertions.assertFalse(guildChallengesService.updateProgress(100,guild));
	}

	@Test
	void generateGuildChallengesTest() {
		Mockito.when(guildChallengesRepository.findGuildChallengeByGuild(guild)).thenReturn(
				Optional.empty()
		);
		var guildChallenges = guildChallengesService.generateGuildChallenges(guild, owner.getUser_id());
		Assertions.assertEquals(3, guildChallenges.size());
	}

	private Guild getGuild() {
		var guild = new Guild(
				owner,
				new GuildRank(1,200),
				"test_guild",
				1
		);
		var players = List.of(owner);
		guild.setPlayers(players);
		return guild;
	}

	private GuildChallenge getGuildChallenge() {
		var guildChallenge = new GuildChallenge(
				100,2,200,guild,1.5
		);
		guildChallenge.setChallengeEndDateTime(LocalDateTime.now().plusHours(2));
		guildChallenge.setPointsFixed(0);
		guildChallenge.setPointsMultiplier(1.0);
		guildChallenge.setProgress(0);
		return guildChallenge;
	}

	private Player getGuildOwner() {
		var player = new Player();
		player.setUser_id(1L);
		return player;
	}

}
