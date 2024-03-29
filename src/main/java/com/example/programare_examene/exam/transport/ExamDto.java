package com.example.programare_examene.exam.transport;

import com.example.programare_examene.exam.model.ExamStatus;
import com.example.programare_examene.member.transport.UserDto;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ExamDto {

	private UUID id;

	@NotBlank
	private String curriculum;

	@NotBlank
	private String studentYear;

	@NotBlank
	private String room;

	@FutureOrPresent
	private LocalDateTime scheduledDateTime;

	private ExamStatus examStatus;

	@Valid
	@Size(min = 1)
	private List<@Valid UserDto> teachers;
}
