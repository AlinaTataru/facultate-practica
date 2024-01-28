package com.example.programare_examene.common.config.security;


import com.example.programare_examene.member.Member;
import com.example.programare_examene.member.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByUsernameWithRoles(username);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .disabled(!member.isActive())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .authorities(getGrantedAuthorities(member))
                    .build();
        } else {
            throw new UsernameNotFoundException(username + " was not found.");
        }
    }

    private Set<GrantedAuthority> getGrantedAuthorities(Member member) {
        return member.getRoles().stream().map(it -> new SimpleGrantedAuthority("ROLE_" + it.getType())).collect(Collectors.toSet());
    }
}
