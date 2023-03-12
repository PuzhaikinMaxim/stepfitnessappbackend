package com.puj.stepfitnessapp.userschallenges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveChallengesService {

    private final ActiveChallengesRepository activeChallengesRepository;

    private final ActiveChallengesMapper activeChallengesMapper = new ActiveChallengesMapper();

    @Autowired
    public ActiveChallengesService(ActiveChallengesRepository activeChallengesRepository) {
        this.activeChallengesRepository = activeChallengesRepository;
    }

    public UserChallengesDto getUserChallengesByUser(long userId) {
        final var result = activeChallengesRepository.getUserChallengesByUser(userId);
        if(result.isEmpty()){
            return null;
        }
        else {
            return activeChallengesMapper.mapUserChallengesToDto(result.get());
        }
    }

    public void addUserChallenge(UserChallenges userChallenges) {
        activeChallengesRepository.save(userChallenges);
    }

    public void deleteUserChallenge(long userId) {
        final var userChallenges = activeChallengesRepository.getUserChallengesByUser(userId).get();
        activeChallengesRepository.deleteById(userChallenges.getId());
    }

    public void setChallengeCompleted(long userId) {
        final var userChallenges = activeChallengesRepository.getUserChallengesByUser(userId).get();
        userChallenges.setCompleted(true);
        activeChallengesRepository.save(userChallenges);
    }

    public void updateUserChallengesProgress(int newProgress, int amountOfSteps, long userId) {
        activeChallengesRepository.updateUserChallengesProgress(newProgress, amountOfSteps, userId);
    }
}
