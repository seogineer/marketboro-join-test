package com.seogineer.marketborojointest.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.seogineer.marketborojointest.BaseTest;
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
    private Member 회원6;
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
        회원6 = new Member(6L);
        회원6.addReserve(7);
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
        memberRepository.save(회원2);
        memberRepository.save(회원3);
        memberRepository.save(회원4);
        memberRepository.save(회원5);

        List<Member> members = memberRepository.findAll();

        assertAll(
                () -> assertThat(members).hasSize(5),
                () -> assertThat(members.get(0).getId()).isEqualTo(회원1.getId()),
                () -> assertThat(members.get(0).getReserves()).hasSize(회원1.getReserves().size()),
                () -> assertThat(members.get(0).getTotalReserve()).isEqualTo(회원1.getTotalReserve()),
                () -> assertThat(members.get(1).getId()).isEqualTo(회원2.getId()),
                () -> assertThat(members.get(1).getReserves()).hasSize(회원2.getReserves().size()),
                () -> assertThat(members.get(1).getTotalReserve()).isEqualTo(회원2.getTotalReserve()),
                () -> assertThat(members.get(2).getId()).isEqualTo(회원3.getId()),
                () -> assertThat(members.get(3).getId()).isEqualTo(회원4.getId()),
                () -> assertThat(members.get(4).getId()).isEqualTo(회원5.getId())
        );
    }
}
