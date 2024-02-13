package com.example.programare_examene.exam.transport;

import com.example.programare_examene.member.transport.UserDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ExamCreateDto {
	@NotBlank
	private String curriculum;

	@NotBlank
	private String studentYear;

	@NotBlank
	private String room;

	@FutureOrPresent
	private LocalDateTime scheduledDateTime;

	@Valid
	@Size(min = 1)
	private List<@Valid UserDto> teachers;
}
