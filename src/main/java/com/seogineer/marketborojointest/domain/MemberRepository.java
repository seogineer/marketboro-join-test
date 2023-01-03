package com.seogineer.marketborojointest.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.reserves")
    Optional<Member> findById(Long memberId);
}
