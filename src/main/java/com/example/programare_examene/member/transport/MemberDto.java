package com.example.programare_examene.member.transport;


import com.example.programare_examene.role.model.Role;
import com.example.programare_examene.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String id;
    private String username;
    private String password;
    private boolean active;
    private Set<Role> roles;

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId() != null ? member.getId().toString() : null, member.getUsername(), "", member.isActive(), member.getRoles());
    }
}
