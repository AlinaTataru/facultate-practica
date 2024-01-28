package com.example.programare_examene.exam.model;

import com.example.programare_examene.common.jpa.BaseEntity;
import com.example.programare_examene.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class Exam extends BaseEntity {
	@Column(nullable = false)
	private LocalDateTime scheduledDateTime;

	@Column(nullable = false)
	private String curriculum;

	@Column(nullable = false)
	private String studentYear;

	@Column(nullable = false)
	private String room;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ExamStatus examStatus;

	@OneToMany
	@Builder.Default
	@ToString.Exclude
	private Set<Member> teachers = new HashSet<>();

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Exam exam = (Exam) o;
		return getId() != null && Objects.equals(getId(), exam.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
