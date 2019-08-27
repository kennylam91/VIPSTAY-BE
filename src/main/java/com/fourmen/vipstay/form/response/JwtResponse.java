package com.fourmen.vipstay.form.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, String username, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }
}
