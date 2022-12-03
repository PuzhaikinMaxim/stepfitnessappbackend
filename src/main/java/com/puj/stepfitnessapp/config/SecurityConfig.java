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
        /*
        http = http.csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/authorized").hasRole("USER");

        http.addFilterBefore(
                tokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
        return http.build();

         */

        return http
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/authorized").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .httpBasic(Customizer.withDefaults())
                .build();
        /*
        return http
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/registerUser").permitAll()
                .antMatchers("/authorized").hasRole("USER")
                .and()
                .httpBasic(Customizer.withDefaults())
                .build();

         */

    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return service;
    }

    /*
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationManagerBuilder authenticationManagerBuilder
    ) throws Exception {
        return authenticationManagerBuilder.userDetailsService(service).and().build();
    }

     */
}
