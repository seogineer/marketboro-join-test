package com.seogineer.marketborojointest.domain;

import static com.seogineer.marketborojointest.exception.ErrorCode.RESERVE_SAVE_MUST_POSITIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.seogineer.marketborojointest.exception.MemberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

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
        int remain = reserve.use(10);

        assertAll(
                () -> assertThat(remain).isEqualTo(0),
                () -> assertThat(reserve.getAmount()).isEqualTo(10),
                () -> assertThat(reserve.getBalance()).isEqualTo(0),
                () -> assertThat(reserve.getStatus()).isEqualTo(ReserveStatus.USE)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10, -100, -500})
    void 적립금으로_입력된_금액이_0_또는_음수인_경우(int amount){
        assertThatThrownBy(() -> 적립금_생성(amount))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(RESERVE_SAVE_MUST_POSITIVE.getDetail());
    }

    @Test
    void 사용할_적립금이_적립된_금액보다_작은_경우() {
        Reserve 적립금 = 적립금_생성(10);
        int remain = 적립금.use(4);

        assertAll(
                () -> assertThat(remain).isEqualTo(0),
                () -> assertThat(적립금.getAmount()).isEqualTo(10),
                () -> assertThat(적립금.getBalance()).isEqualTo(6),
                () -> assertThat(적립금.getStatus()).isEqualTo(ReserveStatus.SAVE)
        );
    }

    @Test
    void 사용할_적립금이_적립된_금액보다_큰_경우() {
        Reserve 적립금 = 적립금_생성(10);
        int remain = 적립금.use(15);

        assertAll(
                () -> assertThat(remain).isEqualTo(5),
                () -> assertThat(적립금.getAmount()).isEqualTo(10),
                () -> assertThat(적립금.getBalance()).isEqualTo(0),
                () -> assertThat(적립금.getStatus()).isEqualTo(ReserveStatus.USE)
        );
    }

    private Reserve 적립금_생성(int money){
        return Reserve.of(money, null);
    }
}
