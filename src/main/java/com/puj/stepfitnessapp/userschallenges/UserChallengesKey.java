package com.puj.stepfitnessapp.userschallenges;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserChallengesKey implements Serializable {
    /*

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "challenge_id")
    private Long challengeId;

    public void UserChallengesKey(Long userId, Long challengeId){
        this.userId = userId;
        this.challengeId = challengeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChallengesKey that = (UserChallengesKey) o;
        return userId.equals(that.userId) && challengeId.equals(that.challengeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, challengeId);
    }

     */
}
