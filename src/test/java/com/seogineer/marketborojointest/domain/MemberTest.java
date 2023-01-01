package com.seogineer.marketborojointest.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class MemberTest {
    @Test
    void 생성() {
        Member member = new Member(1L);

        assertAll(
                () -> assertThat(member).isNotNull(),
                () -> assertThat(member.getId()).isEqualTo(1L)
        );
    }
}
