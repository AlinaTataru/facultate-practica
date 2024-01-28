package com.example.programare_examene.member;


import com.example.programare_examene.member.transport.MemberDto;
import com.example.programare_examene.role.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static com.example.programare_examene.common.util.Validators.isEmpty;
import static com.example.programare_examene.common.util.Validators.isNotEmpty;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public MemberService(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            RoleService roleService) {

        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(String memberId) {
        return memberRepository.findById(UUID.fromString(memberId));
    }

    public Member create(MemberDto dto) {
        if (isEmpty(dto.getUsername())) {
            throw new ValidationException("Username is null.");
        }

        if (isEmpty(dto.getPassword())) {
            throw new ValidationException("Password is null.");
        }

        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ValidationException("Username is already taken.");
        }

        return memberRepository.save(
                Member.builder()
                        .username(dto.getUsername())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .active(dto.isActive())
                        .roles(Stream.of(roleService.getUserRole()).collect(Collectors.toSet()))
                        .build()
        );
    }

    public Member update(Member member, MemberDto dto) {
        if (isNotEmpty(dto.getUsername()) && !member.getUsername().equals(dto.getUsername())) {
            if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
                throw new ValidationException("Username is already taken.");
            }
            member.setUsername(dto.getUsername());
        }

        if (isNotEmpty(dto.getPassword())) {
            member.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        member.setActive(dto.isActive());
        member.setRoles(dto.getRoles());

        return memberRepository.save(member);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }
}
