package com.puj.stepfitnessapp.guildenterrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GuildEnterRequestDto {

    private Long requestId;

    private Long userId;

    private String userName;

    private Integer userLevel;
}
