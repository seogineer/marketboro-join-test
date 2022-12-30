package com.seogineer.marketborojointest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MemberTest {
    @Test
    void 생성() {
        Member member = new Member();
        assertThat(member).isNotNull();
    }
}
