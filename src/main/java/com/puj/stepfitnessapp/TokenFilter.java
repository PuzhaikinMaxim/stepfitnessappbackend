package com.puj.stepfitnessapp;

import com.puj.stepfitnessapp.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {

    private static final String HEADER_TOKEN = "token";

    private final UserService service;

    public TokenFilter(UserService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(HEADER_TOKEN);

        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.replace("\"","");
        var result = service.getUserByToken(token);
        if(result.isEmpty()){
            response.setStatus(401);
            return;
        }
        var user = result.get();

        final var authentication =
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        user.getGrantedAuthorities());

        authentication.setDetails(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
