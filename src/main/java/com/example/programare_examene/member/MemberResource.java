package com.example.programare_examene.member;


import com.example.programare_examene.role.model.Role;
import com.example.programare_examene.role.model.RoleType;
import com.example.programare_examene.member.transport.MemberDto;
import com.example.programare_examene.common.util.Utils;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MemberResource {
    private final Utils utils;
    private final MemberService memberService;

    public MemberResource(Utils utils, MemberService memberService) {
        this.utils = utils;
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public List<MemberDto> getAll() {
        return memberService
                .getMembers()
                .stream()
                .filter(it -> !it.getRoles()
                        .stream()
                        .map(Role::getType)
                        .collect(Collectors.toSet())
                        .contains(RoleType.ADMIN))
                .map(MemberDto::from).collect(Collectors.toList());
    }

    @PostMapping("/members")
    public MemberDto save(@RequestBody MemberDto dto) {
        return MemberDto.from(memberService.create(dto));
    }

    @PutMapping("/members/{memberId}")
    public MemberDto update(@PathVariable String memberId, @RequestBody MemberDto dto) {
        Optional<Member> member = memberService.findById(memberId);

        if (member.isEmpty()) {
            throw new NotFoundException("Member with id " + memberId + " was not found.");
        }

        return MemberDto.from(memberService.update(member.get(), dto));
    }

    @DeleteMapping("/members/{memberId}")
    public void delete(@PathVariable String memberId) {
        Optional<Member> member = memberService.findById(memberId);

        if (member.isEmpty()) {
            throw new NotFoundException("Member with id " + memberId + " was not found.");
        }

        memberService.delete(member.get());
    }
}
