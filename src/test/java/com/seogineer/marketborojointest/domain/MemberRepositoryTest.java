package com.seogineer.marketborojointest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository repository;

    @Test
    void 저장() {
        Member member = new Member();
        repository.save(member);

        List<Member> members = repository.findAll();

        assertThat(members.size()).isEqualTo(1);
    }
}
