package com.seogineer.marketborojointest.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReserveRepository extends JpaRepository<Reserve, Long>, ReserveRepositoryCustom {
    @Query("SELECT r FROM Reserve r WHERE r.member.id = ?1")
    List<Reserve> findAllByMemberId(Long memberId, Pageable pageable);
}
