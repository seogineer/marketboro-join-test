package com.seogineer.marketborojointest.domain;

import static com.seogineer.marketborojointest.exception.ErrorCode.GREATER_THAN_TOTAL_RESERVES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.seogineer.marketborojointest.exception.MemberException;
import org.junit.jupiter.api.Test;

class MemberTest {
    @Test
    void 생성() {
        Member member = 회원_생성(1L);

        assertAll(
                () -> assertThat(member).isNotNull(),
                () -> assertThat(member.getId()).isEqualTo(1L)
        );
    }

    @Test
    void 적립금_적립() {
        Member member = 회원_생성(1L);

        적립금_적립(member, 10);

        assertThat(member.getReserves().size()).isEqualTo(1);
    }

    @Test
    void 적립금_합계() {
        Member member = 회원_생성(1L);

        적립금_적립(member, 10);
        적립금_적립(member, 1);
        적립금_적립(member, 15);

        assertThat(member.getTotalReserve()).isEqualTo(26);
    }

    @Test
    void 적립금과_사용_금액이_같은_경우_사용() {
        Member member = 회원_생성(1L);
        적립금_적립(member, 10);
        적립금_적립(member, 1);
        적립금_적립(member, 15);

        member.useReserve(10);

        assertThat(member.getTotalReserve()).isEqualTo(16);
        assertThat(member.getReserves().get(0).getStatus()).isEqualTo(ReserveStatus.USE);
    }

    @Test
    void 적립금이_사용_금액_보다_큰_경우_사용() {
        Member member = 회원_생성(1L);
        적립금_적립(member, 10);
        적립금_적립(member, 1);
        적립금_적립(member, 15);

        member.useReserve(4);

        assertThat(member.getTotalReserve()).isEqualTo(22);
        assertThat(member.getReserves().get(0).getStatus()).isEqualTo(ReserveStatus.SAVE);
    }

    @Test
    void 적립금_보다_사용_금액이_큰_경우_사용() {
        Member member = 회원_생성(1L);
        적립금_적립(member, 10);
        적립금_적립(member, 1);
        적립금_적립(member, 15);

        member.useReserve(12);

        assertThat(member.getTotalReserve()).isEqualTo(14);
        assertThat(member.getReserves().get(0).getStatus()).isEqualTo(ReserveStatus.USE);
        assertThat(member.getReserves().get(1).getStatus()).isEqualTo(ReserveStatus.USE);
        assertThat(member.getReserves().get(2).getStatus()).isEqualTo(ReserveStatus.SAVE);
        assertThat(member.getReserves().get(2).getBalance()).isEqualTo(14);
    }

    @Test
    void 총_적립금액_보다_사용_금액이_큰_경우() {
        Member 회원 = 회원_생성(1L);
        적립금_적립(회원, 10);

        assertThatThrownBy(
                () -> 회원.useReserve(20))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(GREATER_THAN_TOTAL_RESERVES.getDetail());
    }

    private Member 회원_생성(Long memberId) {
        return new Member(memberId);
    }

    private void 적립금_적립(Member member, int amount){
        member.addReserve(amount);
    }
}
