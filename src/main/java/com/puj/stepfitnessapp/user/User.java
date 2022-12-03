package com.puj.stepfitnessapp.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Id
    private Long userId;

    private @NotBlank @Column(unique = true) String username;
    private @NotBlank @Column(unique = true) String login;
    private @NotBlank @Column(unique = true) String email;
    private @NotBlank String password;
    private @NotNull @Column(unique = true) String enterToken;
    private @NotNull String role;

    public User(String username,
                String login,
                String email,
                String password) {
        this.username = username;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enterToken='" + enterToken + '\'' +
                '}';
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
