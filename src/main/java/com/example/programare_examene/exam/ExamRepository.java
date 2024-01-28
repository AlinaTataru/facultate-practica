package com.example.programare_examene.exam;

import com.example.programare_examene.exam.model.Exam;
import com.example.programare_examene.exam.model.ExamStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamRepository  extends JpaRepository<Exam, UUID> {
	Optional<Exam> findByCurriculumAndScheduledDateTimeLessThanEqual(String subject, LocalDateTime scheduleDateTime);
	Page<Exam> findAllByExamStatus(ExamStatus examStatus, Pageable pageable);
}
