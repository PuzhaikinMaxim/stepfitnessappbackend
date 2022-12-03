package com.puj.stepfitnessapp.user;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    private PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }
    /*
    public UserDto mapUserToUserDto() {

    }

     */

    public User mapUserDtoToUser(UserDto userDto) {
        String encryptedPassword = encoder.encode(userDto.getPassword());
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(encryptedPassword);
        user.setRole(Role.ROLE_USER.toString());
        user.setEnterToken("");
        return user;
    }
}
