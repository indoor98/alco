package com.alco.global.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String jwt;

    public JwtAuthenticationToken(String jwt) {
        super(null);
        this.jwt = jwt;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return jwt;
    }
}
