package com.example.programare_examene.common.api;


import com.example.programare_examene.member.Member;
import com.example.programare_examene.role.model.Role;
import com.example.programare_examene.role.model.RoleType;
import com.example.programare_examene.member.MemberRepository;
import com.example.programare_examene.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;


import static com.example.programare_examene.common.util.Constants.ADMIN;
import static com.example.programare_examene.common.util.Constants.DEFAULT_PASSWORD;
import static com.example.programare_examene.common.util.Constants.GROUP_REPRESENTATIVE;
import static com.example.programare_examene.common.util.Constants.HEAD_SECRETARY;
import static com.example.programare_examene.common.util.Constants.TEACHER;

@Service
public class AppSetupService implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppSetupService(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {

        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void addDefaultAdmin() {
        if (memberRepository.findByUsername(ADMIN).isEmpty()) {
            Member member = new Member();

            member.setUsername(ADMIN);
            member.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            member.setActive(true);
            member.setRoles(roleRepository.findAll().stream().filter(it -> it.getType().equals(RoleType.ADMIN)).collect(Collectors.toSet()));
            memberRepository.save(member);

            member = new Member();
            member.setUsername(GROUP_REPRESENTATIVE);
            member.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            member.setActive(true);
            member.setRoles(roleRepository.findAll().stream().filter(it -> it.getType().equals(RoleType.GROUP_REPRESENTATIVE)).collect(Collectors.toSet()));
            memberRepository.save(member);

            member = new Member();
            member.setUsername(TEACHER);
            member.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            member.setActive(true);
            member.setRoles(roleRepository.findAll().stream().filter(it -> it.getType().equals(RoleType.TEACHER)).collect(Collectors.toSet()));
            memberRepository.save(member);

            member = new Member();
            member.setUsername(HEAD_SECRETARY);
            member.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            member.setActive(true);
            member.setRoles(roleRepository.findAll().stream().filter(it -> it.getType().equals(RoleType.HEAD_SECRETARY)).collect(Collectors.toSet()));
            memberRepository.save(member);
        }
    }

    public void addRoles() {
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.saveAll(Arrays.stream(RoleType.values()).map(Role::new).collect(Collectors.toSet()));
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.addRoles();
        this.addDefaultAdmin();
    }
}
