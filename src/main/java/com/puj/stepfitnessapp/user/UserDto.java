package com.puj.stepfitnessapp.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDto {
    private String username;
    private String login;
    private String email;
    private String password;

    public UserDto(String username, String login, String email, String password) {
        this.username = username;
        this.login = login;
        this.email = email;
        this.password = password;
    }
}
