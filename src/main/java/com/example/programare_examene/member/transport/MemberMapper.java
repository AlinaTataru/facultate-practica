package com.example.programare_examene.member.transport;

import com.example.programare_examene.member.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	UserDto toUserDto(Member member);
}
