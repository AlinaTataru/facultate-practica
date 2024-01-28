package com.example.programare_examene.member.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class StatusDto {
    private String username;
    private Collection<GrantedAuthority> authorities;
}
