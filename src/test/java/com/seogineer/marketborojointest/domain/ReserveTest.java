package com.seogineer.marketborojointest.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class ReserveTest {
    @Test
    void 생성() {
        Reserve reserve = 적립금_생성(10);

        assertAll(
                () -> assertThat(reserve.getAmount()).isEqualTo(10),
                () -> assertThat(reserve.getBalance()).isEqualTo(10)
        );
    }

    @Test
    void 사용할_적립금과_금액이_같은_경우() {
        Reserve reserve = 적립금_생성(10);
        reserve.use(10);

        assertAll(
                () -> assertThat(reserve.getAmount()).isEqualTo(10),
                () -> assertThat(reserve.getBalance()).isEqualTo(0),
                () -> assertThat(reserve.getStatus()).isEqualTo(ReserveStatus.USE)
        );
    }

    @Test
    void 사용할_적립금이_적립된_금액보다_작은_경우() {
        Reserve 적립금 = 적립금_생성(10);
        적립금.use(4);

        assertAll(
                () -> assertThat(적립금.getAmount()).isEqualTo(10),
                () -> assertThat(적립금.getBalance()).isEqualTo(6),
                () -> assertThat(적립금.getStatus()).isEqualTo(ReserveStatus.SAVE)
        );
    }

    @Test
    void 사용할_적립금이_적립된_금액보다_큰_경우() {
        Reserve 적립금 = 적립금_생성(10);
        적립금.use(15);

        assertAll(
                () -> assertThat(적립금.getAmount()).isEqualTo(10),
                () -> assertThat(적립금.getBalance()).isEqualTo(0),
                () -> assertThat(적립금.getStatus()).isEqualTo(ReserveStatus.USE)
        );
    }

    private Reserve 적립금_생성(int money){
        return new Reserve(money, null);
    }
}
