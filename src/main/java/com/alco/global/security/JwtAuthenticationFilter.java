package com.alco.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTHORIZATION_TYPE = "Bearer ";

    public JwtAuthenticationFilter(String processUrl,
                                   AuthenticationManager authenticationManager) {
        super(processUrl);
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(successHandler());
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK);
    }

    public String extractToken(String header) {
        if (header != null && header.startsWith(AUTHORIZATION_TYPE)) {
            return header.substring(AUTHORIZATION_TYPE.length()).trim();
        }
        else return null;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Authentication authentication = new JwtAuthenticationToken(accessToken);
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws ServletException, IOException {

        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

}
