package com.example.programare_examene.common.config.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.programare_examene.common.exception.model.ErrorResponse;
import com.example.programare_examene.common.util.Utils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Utils utils;

    public JwtAuthenticationEntryPoint(Utils utils) {
        this.utils = utils;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        response.getWriter().write(utils.toJson(new ErrorResponse(e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED)));
    }
}

