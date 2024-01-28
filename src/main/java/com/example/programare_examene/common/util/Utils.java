package com.example.programare_examene.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Utils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error converting toJson", e);
        }
        return null;
    }

    public boolean isAuthenticated() {
        try {
            return SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken;
        } catch (Exception e) {
            return false;
        }
    }

    public UsernamePasswordAuthenticationToken getLoggedUser() {
        try {
            return (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception e) {
            log.trace("Error retrieving user from security context", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> toMap(Object... data) {
        if (data.length % 2 == 1) {
            throw new IllegalArgumentException("You must pass an even sized array to the toMap method (size = " + data.length + ")");
        }
        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < data.length;) {
            map.put((K) data[i++], (V) data[i++]);
        }
        return map;
    }
}
