package com.puj.stepfitnessapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        mapper = new UserMapper(passwordEncoder);
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByToken(String token) { return userRepository.findUserByToken(token); }

    public void addUser(UserDto user) {
        userRepository.save(mapper.mapUserDtoToUser(user));
    }

    public void addEnterToken(String token, User user) {
        //userRepository.addEnterToken(token);
        user.setEnterToken(token);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var response = getUser(username);
        if(response.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }

        var user = response.get();
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return user.getGrantedAuthorities();
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
