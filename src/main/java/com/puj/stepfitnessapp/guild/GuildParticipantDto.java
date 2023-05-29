package com.puj.stepfitnessapp.guild;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GuildParticipantDto {

    private Long participantId;

    private String participantName;

    private Integer participantProfileImageId;

    private Integer participantLevel;
}
