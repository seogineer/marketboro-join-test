package com.seogineer.marketborojointest.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.seogineer.marketborojointest.BaseTest;
import com.seogineer.marketborojointest.dto.response.MemberResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class MemberRepositoryTest extends BaseTest {
    @Autowired
    private MemberRepository memberRepository;
    private Member 회원1;
    private Member 회원2;
    private Member 회원3;
    private Member 회원4;
    private Member 회원5;
    @BeforeEach
    void before() {
        회원1 = new Member(1L);
        회원1.addReserve(10);
        회원1.addReserve(11);
        회원1.addReserve(1);
        회원2 = new Member(2L);
        회원2.addReserve(3);
        회원3 = new Member(3L);
        회원3.addReserve(5);
        회원3.addReserve(8);
        회원4 = new Member(4L);
        회원4.addReserve(1);
        회원4.addReserve(3);
        회원5 = new Member(5L);
        회원5.addReserve(2);
    }

    @Test
    void 저장() {
        memberRepository.save(회원1);

        Member members = memberRepository.findById(회원1.getId()).get();

        assertAll(
                () -> assertThat(members).isNotNull(),
                () -> assertThat(members.getId()).isEqualTo(1L)
        );
    }

    @Test
    @Transactional
    void 조회() {
        memberRepository.save(회원1);

        Member member = memberRepository.findById(회원1.getId()).get();

        assertAll(
                () -> assertThat(member.getId()).isEqualTo(회원1.getId()),
                () -> assertThat(member.getTotalReserve()).isEqualTo(회원1.getTotalReserve()),
                () -> assertThat(member.getReserves()).hasSize(회원1.getReserves().size())
        );
    }
}
