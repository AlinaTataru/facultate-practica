package com.example.programare_examene.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByUsername(String username);

    @Query("from Member m left join fetch m.roles where m.username = :username")
    Optional<Member> findByUsernameWithRoles(String username);
}
