package com.puj.stepfitnessapp.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentialsDto {

    private String username;
    private String password;

    public UserCredentialsDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
