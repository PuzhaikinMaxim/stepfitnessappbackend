package com.puj.stepfitnessapp.guildchallenges;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.guild.GuildService;
import com.puj.stepfitnessapp.guildchallengesreward.GuildChallengesRewardService;
import com.puj.stepfitnessapp.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GuildChallengesService {

    private final GuildChallengesRepository guildChallengesRepository;

    private final GuildChallengesRewardService guildChallengesRewardService;

    private final GuildService guildService;
    
    private final Double DIFFICULTY_MULTIPLIER_EASY = 1.0;
    private final Double DIFFICULTY_MULTIPLIER_NORMAL = 1.2;
    private final Double DIFFICULTY_MULTIPLIER_HARD = 1.5;

    private final Double[] difficultyMultiplierList = {
            DIFFICULTY_MULTIPLIER_EASY, DIFFICULTY_MULTIPLIER_NORMAL, DIFFICULTY_MULTIPLIER_HARD
    };

    @Autowired
    public GuildChallengesService(
            GuildChallengesRepository guildChallengesRepository,
            GuildChallengesRewardService guildChallengesRewardService,
            GuildService guildService
    ) {
        this.guildChallengesRepository = guildChallengesRepository;
        this.guildChallengesRewardService = guildChallengesRewardService;
        this.guildService = guildService;
    }

    public GuildChallenge getGuildChallengeByOwnerId(Long userId) {
        return guildChallengesRepository.findGuildChallengeByGuild_Owner_User_id(userId).orElse(null);
    }

    public GuildChallenge getGuildChallengeById(Long guildChallengeId) {
        return guildChallengesRepository.findById(guildChallengeId).orElse(null);
    }

    public Boolean updateProgress(int amountOfSteps, Guild guild) {
        var response = guildChallengesRepository
                .findGuildChallengeByGuildAndIsStartedTrue(guild);

        if(response.isEmpty()) return false;

        var guildChallenge = response.get();

        if(guildChallenge.getChallengeEndDateTime().isBefore(LocalDateTime.now())){
            guildChallengesRepository.delete(guildChallenge);
            return false;
        }

        var points = guildChallenge.getProgress();
        var pointsFixed = (amountOfSteps * guildChallenge.getPointsFixed())/100;
        points = points + (int) (pointsFixed*guildChallenge.getPointsMultiplier());
        points = Math.min(guildChallenge.getAmountOfPointsToFinish(), points);
        guildChallenge.setProgress(points);

        if(points < guildChallenge.getAmountOfPointsToFinish()){
            guildChallengesRepository.save(guildChallenge);
            return false;
        }

        guildService.incrementAmountOfCompletedChallenges(guild);

        guildChallengesRewardService.generateReward(guild, guildChallenge);

        guildChallengesRepository.delete(guildChallenge);

        return true;
    }

    public void startGuildChallenge(GuildChallenge guildChallenge, Long userId) {
        if(!guildChallenge.getGuild().getOwner().getUser_id().equals(userId)) return;

        guildChallenge.setIsStarted(true);

        var guild = guildChallenge.getGuild();
        var players = guild.getPlayers();
        var guildParticipants = guild.getPlayers();
        var pointsMultiplier = 0.0;
        var pointsFixed = 0;
        var additionalMinutes = 0;
        var timeMultiplier = 0.0;

        for(Player player : players){
            pointsMultiplier += player.calculatePointMultiplier();
            pointsFixed += player.calculateAmountOfAdditionalPoints();
            additionalMinutes += player.calculateAdditionalMinutes();
            timeMultiplier += player.calculateTimeMultiplier();
        }

        pointsMultiplier = pointsMultiplier / guildParticipants.size();
        pointsFixed = pointsFixed / guildParticipants.size();
        additionalMinutes = additionalMinutes / guildParticipants.size();
        timeMultiplier = timeMultiplier / guildParticipants.size();

        var minutes = (int) ((guildChallenge.getBaseHoursToFinish() * 60 + additionalMinutes) * timeMultiplier);

        var guildChallengeFinishDateTime = LocalDateTime.now().plusMinutes(minutes);

        guildChallenge.setChallengeEndDateTime(guildChallengeFinishDateTime);
        guildChallenge.setPointsFixed(pointsFixed);
        guildChallenge.setPointsMultiplier(pointsMultiplier);
        guildChallenge.setProgress(0);
        guildChallengesRepository.save(guildChallenge);
        guildChallengesRepository.deleteByIsStartedFalse();
    }

    public List<GuildChallenge> generateGuildChallenges(Guild guild, Long userId) {
        var response = guildChallengesRepository
                .findGuildChallengeByGuild(guild);

        if(response.isPresent()) return response.get();

        if(!guild.getOwner().getUser_id().equals(userId)) return null;

        var amountOfPlayers = guild.getPlayers().size();

        var collectiveLevel = guild.getPlayers().stream().mapToInt(Player::getLevel).sum();

        var guildChallenges = new ArrayList<GuildChallenge>();

        for(var difficultyMultiplier : difficultyMultiplierList){
            var hoursToFinish = (int) (240/difficultyMultiplier);
            var pointsToFinish = (int) (40000 * amountOfPlayers * difficultyMultiplier);
            var xp = (int) ((100 + collectiveLevel)*difficultyMultiplier);
            var guildChallenge = new GuildChallenge(
                xp, hoursToFinish, pointsToFinish, guild
            );
            guildChallenges.add(guildChallenge);
        }

        guildChallengesRepository.saveAll(guildChallenges);

        return guildChallenges;
    }

    public GuildChallenge getCurrentGuildChallenge(Long userId) {
        var guild = guildService.findGuildByUserId(userId);
        var guildChallenge = guildChallengesRepository.findGuildChallengeByGuildAndIsStartedTrue(guild);
        return guildChallenge.orElse(null);
    }

    public List<GuildChallenge> getGuildChallenges(Long userId) {
        var guild = guildService.findGuildByUserId(userId);
        if(!guild.getOwner().getUser_id().equals(userId)) return null;
        return guild.getGuildChallenges();
    }
}
