package com.seogineer.marketborojointest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.seogineer.marketborojointest.BaseTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class ReserveRepositoryTest extends BaseTest {
    @Autowired
    ReserveRepository reserveRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 조회() {
        Member 회원 = new Member();
        회원.addReserve(10);
        회원.addReserve(5);
        회원.addReserve(1);
        memberRepository.save(회원);

        List<Reserve> reserves = reserveRepository.findAllByMemberId(회원.getId(), PageRequest.of(0, 2));

        assertThat(reserves).hasSize(2);
    }
}
