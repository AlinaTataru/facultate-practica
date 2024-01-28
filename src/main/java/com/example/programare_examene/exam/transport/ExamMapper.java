package com.example.programare_examene.exam.transport;

import com.example.programare_examene.exam.model.Exam;
import com.example.programare_examene.member.transport.MemberMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
		uses = MemberMapper.class
)
public interface ExamMapper {
	ExamDto toDto(Exam exam);
	Exam toEntity(ExamDto examDto);
}
