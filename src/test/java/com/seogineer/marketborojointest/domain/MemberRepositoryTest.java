package com.seogineer.marketborojointest.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 저장() {
        Member member = new Member(1L);
        memberRepository.save(member);

        List<Member> members = memberRepository.findAll();

        assertAll(
                () -> assertThat(members.size()).isEqualTo(1),
                () -> assertThat(members.get(0).getId()).isEqualTo(1L)
        );
    }
}
