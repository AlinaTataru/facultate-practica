package com.example.programare_examene.exam;

import com.example.programare_examene.exam.exception.ExamException;
import com.example.programare_examene.exam.model.Exam;
import com.example.programare_examene.exam.model.ExamStatus;
import com.example.programare_examene.exam.transport.ExamCreateDto;
import com.example.programare_examene.exam.transport.ExamDto;
import com.example.programare_examene.exam.transport.ExamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamService {
	private final ExamRepository examRepository;
	private final ExamMapper examMapper;

	public Page<ExamDto> findAllExams(Pageable pageable){
		return examRepository.findAll(pageable).map(examMapper::toDto);
	}

	public ExamDto scheduleProposedExam(ExamCreateDto examDto) {
		validateExamIsNotAlreadyProposedOrScheduled(examDto);
		var exam = examMapper.toEntity(examDto);
		exam.setExamStatus(ExamStatus.PROPOSED);
		exam = this.save(exam);
		return examMapper.toDto(exam);
	}

	private void validateExamIsNotAlreadyProposedOrScheduled(ExamCreateDto examDto) {
		examRepository.findByCurriculumAndScheduledDateTimeLessThanEqual(examDto.getCurriculum(), examDto.getScheduledDateTime())
				.ifPresent(exam -> {throw ExamException.examScheduleAlreadyExists(exam.getCurriculum());} )
			;
	}

	private Exam save(Exam exam){
		return examRepository.save(exam);
	}

	public Page<ExamDto> findAllByExamStatus(ExamStatus status, Pageable pageable) {
		return examRepository.findAllByExamStatus(status, pageable)
				.map(examMapper::toDto)
				;
	}

	public Optional<ExamDto> setProposedExamToStatusReview(UUID examId) {
		return examRepository.findById(examId)
				.filter(exam -> exam.getExamStatus() == ExamStatus.PROPOSED)
				.map(exam -> {
					exam.setExamStatus(ExamStatus.REVIEW);
					return exam;
				})
				.map(this::save)
				.map(examMapper::toDto);
	}

	public Optional<ExamDto> setReviewExamToStatusApproved(UUID examId) {
		return examRepository.findById(examId)
				.filter(exam -> exam.getExamStatus() == ExamStatus.REVIEW)
				.map(exam -> {
					exam.setExamStatus(ExamStatus.APPROVED);
					return exam;
				})
				.map(this::save)
				.map(examMapper::toDto);
	}

	public Optional<ExamDto> setProposedOrReviewExamToStatusRejected(UUID examId) {
		return examRepository.findById(examId)
				.filter(exam -> exam.getExamStatus() == ExamStatus.PROPOSED || exam.getExamStatus() == ExamStatus.REVIEW)
				.map(exam -> {
					exam.setExamStatus(ExamStatus.REJECTED);
					return exam;
				})
				.map(this::save)
				.map(examMapper::toDto);
	}
}
