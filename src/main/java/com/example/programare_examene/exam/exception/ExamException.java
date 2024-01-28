package com.example.programare_examene.exam.exception;

import com.example.programare_examene.exam.model.Exam;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExamException {

	public static final String AN_EXAM_SCHEDULE_ALREADY_EXISTS_FOR_THE_SUBJECT_IN_QUESTION = "An exam schedule already exists for the subject in question <%s>";

	public static HttpClientErrorException examScheduleAlreadyExists(String curriculum) {
		return new HttpClientErrorException(HttpStatus.BAD_REQUEST, AN_EXAM_SCHEDULE_ALREADY_EXISTS_FOR_THE_SUBJECT_IN_QUESTION.formatted(curriculum));
	}
}
