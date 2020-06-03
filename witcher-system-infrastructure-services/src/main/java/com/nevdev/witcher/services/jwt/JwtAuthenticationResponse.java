package com.nevdev.witcher.services.jwt;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    @Getter
    private final String token;
    @Getter
    private final List authorities;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
        this.authorities = null;
    }

    public JwtAuthenticationResponse(String token, List authorities) {
        this.token = token;
        this.authorities = authorities;
    }

}
