package com.example.programare_examene.exam;

import com.example.programare_examene.exam.model.ExamStatus;
import com.example.programare_examene.exam.transport.ExamCreateDto;
import com.example.programare_examene.exam.transport.ExamDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamResource {
	private final ExamService examService;
	@GetMapping
	@Operation(summary = "Get all existing Exams")
	public ResponseEntity<List<ExamDto>> getAllExams(){
		var exams = examService.findAllExams(Pageable.unpaged()).getContent();
		return exams.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(exams);
	}

	@GetMapping("/status/{status}")
	@Operation(summary = "Get all existing Exams filtered by a certain ExamStatus")
	public ResponseEntity<List<ExamDto>> getAllExamsByStatus(@Parameter(description = "ExamStatus to filter by")
	                                                             @PathVariable ExamStatus status) {
		var exams = examService.findAllByExamStatus(status, Pageable.unpaged()).getContent();
		return exams.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(exams);
	}

	@PreAuthorize("hasRole('ROLE_GROUP_REPRESENTATIVE')")
	@PostMapping
	public ResponseEntity<ExamDto> proposeExamSchedule(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody ExamCreateDto examCreateDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(examService.scheduleProposedExam(examCreateDto));
	}

	@PreAuthorize("hasRole('ROLE_TEACHER')")
	@PostMapping("/examId/{examId}/status/review")
	public ResponseEntity<ExamDto> setProposedExamToStatusReview(@PathVariable UUID examId){
		return examService.setProposedExamToStatusReview(examId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.noContent().build());
	}

	@PreAuthorize("hasRole('ROLE_HEAD_SECRETARY')")
	@PostMapping("/examId/{examId}/status/approved")
	public ResponseEntity<ExamDto> setReviewExamToStatusApproved(@PathVariable UUID examId){
		return examService.setReviewExamToStatusApproved(examId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.noContent().build());
	}

	@PreAuthorize("hasRole('ROLE_TEACHER') || hasAnyRole('ROLE_HEAD_SECRETARY')")
	@PostMapping("/examId/{examId}/status/rejected")
	public ResponseEntity<ExamDto> setProposedOrReviewExamToStatusRejected(@PathVariable UUID examId){
		return examService.setProposedOrReviewExamToStatusRejected(examId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.noContent().build());
	}
}
