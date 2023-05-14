package com.puj.stepfitnessapp.config;

import com.puj.stepfitnessapp.TokenFilter;
import com.puj.stepfitnessapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenFilter tokenFilter;

    private final UserService service;

    @Autowired
    public SecurityConfig(@Lazy UserService service) {
        this.service = service;
        tokenFilter = new TokenFilter(service);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/authorized").hasRole("USER")
                .antMatchers("/user_challenges").hasRole("USER")
                .antMatchers("/daily_challenges").hasRole("USER")
                .antMatchers("/player").hasRole("USER")
                .antMatchers("/duel").hasRole("USER")
                .antMatchers("/guild").hasRole("USER")
                .antMatchers("/guild_enter_request").hasRole("USER")
                .antMatchers("/guild_challenge").hasRole("USER")
                .antMatchers("/guild_challenges_reward").hasRole("USER")
                .antMatchers("/players_rating").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    authException.getMessage()
                            );
                        })
                )
                .and()
                .build();
    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return service;
    }

}
