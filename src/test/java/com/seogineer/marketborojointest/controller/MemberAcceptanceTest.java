package com.seogineer.marketborojointest.controller;

import static com.seogineer.marketborojointest.controller.MemberAcceptanceSupport.적립금_사용_요청;
import static com.seogineer.marketborojointest.controller.MemberAcceptanceSupport.적립금_적립_사용_내역_조회_요청;
import static com.seogineer.marketborojointest.controller.MemberAcceptanceSupport.적립금_적립_요청;
import static com.seogineer.marketborojointest.controller.MemberAcceptanceSupport.적립금_적립됨;
import static com.seogineer.marketborojointest.controller.MemberAcceptanceSupport.적립금_합계_조회_요청;
import static com.seogineer.marketborojointest.controller.MemberAcceptanceSupport.회원_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.seogineer.marketborojointest.BaseTest;
import com.seogineer.marketborojointest.domain.ReserveStatus;
import com.seogineer.marketborojointest.dto.response.MemberReserveHistoryResponse;
import com.seogineer.marketborojointest.dto.response.MemberResponse;
import com.seogineer.marketborojointest.dto.response.MemberTotalBalanceResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("회원 관련 기능")
class MemberAcceptanceTest extends BaseTest {
    private MemberResponse 회원;

    @BeforeEach
    public void setUp() {
        super.setUp();

        회원 = 회원_생성_요청().as(MemberResponse.class);
    }

    @Test
    void 적립금_적립() {
        ExtractableResponse<Response> response = 적립금_적립_요청(회원.getId(), 10);

        적립금_적립됨(response);
    }

    @Test
    void 적립금_적립전_합계_조회() {
        MemberTotalBalanceResponse response = 적립금_합계_조회_요청(회원.getId()).as(MemberTotalBalanceResponse.class);

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.getTotalBalance()).isEqualTo(0)
        );
    }

    @Test
    void 적립금_적립후_합계_조회() {
        적립금_적립_요청(회원.getId(), 10);
        적립금_적립_요청(회원.getId(), 5);
        MemberTotalBalanceResponse response = 적립금_합계_조회_요청(회원.getId()).as(MemberTotalBalanceResponse.class);

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.getTotalBalance()).isEqualTo(15)
        );
    }

    @Test
    void 적립금_사용후_합계_조회() {
        적립금_적립_요청(회원.getId(), 10);
        적립금_적립_요청(회원.getId(), 5);
        적립금_사용_요청(회원.getId(), 10);
        MemberTotalBalanceResponse response = 적립금_합계_조회_요청(회원.getId()).as(MemberTotalBalanceResponse.class);

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.getTotalBalance()).isEqualTo(5)
        );
    }

    @Test
    void 적립금_일부만_사용후_합계_조회() {
        적립금_적립_요청(회원.getId(), 10);
        적립금_사용_요청(회원.getId(), 5);
        MemberTotalBalanceResponse response = 적립금_합계_조회_요청(회원.getId()).as(MemberTotalBalanceResponse.class);

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.getTotalBalance()).isEqualTo(5)
        );
    }

    @Test
    void 적립금_적립_사용_내역_조회() {
        적립금_적립_요청(회원.getId(), 10);
        적립금_적립_요청(회원.getId(), 5);
        List<MemberReserveHistoryResponse> response = 적립금_적립_사용_내역_조회_요청(회원.getId())
                .jsonPath().getList(".", MemberReserveHistoryResponse.class);

        assertAll(
                () -> assertThat(response).hasSize(2),
                () -> assertThat(response.get(0).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(0).getAmount()).isEqualTo(10),
                () -> assertThat(response.get(0).getStatus()).isEqualTo(ReserveStatus.SAVE),
                () -> assertThat(response.get(1).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(1).getAmount()).isEqualTo(5),
                () -> assertThat(response.get(1).getStatus()).isEqualTo(ReserveStatus.SAVE)
        );
    }

    @Test
    void 적립금_부분_사용후_적립_사용_내역_조회() {
        적립금_적립_요청(회원.getId(), 10);
        적립금_적립_요청(회원.getId(), 5);
        적립금_사용_요청(회원.getId(), 5);
        List<MemberReserveHistoryResponse> response = 적립금_적립_사용_내역_조회_요청(회원.getId())
                .jsonPath().getList(".", MemberReserveHistoryResponse.class);

        assertAll(
                () -> assertThat(response).hasSize(2),
                () -> assertThat(response.get(0).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(0).getAmount()).isEqualTo(10),
                () -> assertThat(response.get(0).getBalance()).isEqualTo(5),
                () -> assertThat(response.get(0).getStatus()).isEqualTo(ReserveStatus.SAVE),
                () -> assertThat(response.get(1).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(1).getAmount()).isEqualTo(5),
                () -> assertThat(response.get(1).getBalance()).isEqualTo(5),
                () -> assertThat(response.get(1).getStatus()).isEqualTo(ReserveStatus.SAVE)
        );
    }

    @Test
    void 적립금_사용후_적립_사용_내역_조회() {
        적립금_적립_요청(회원.getId(), 10);
        적립금_적립_요청(회원.getId(), 5);
        적립금_사용_요청(회원.getId(), 10);
        적립금_사용_요청(회원.getId(), 5);
        List<MemberReserveHistoryResponse> response = 적립금_적립_사용_내역_조회_요청(회원.getId())
                .jsonPath().getList(".", MemberReserveHistoryResponse.class);

        assertAll(
                () -> assertThat(response).hasSize(2),
                () -> assertThat(response.get(0).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(0).getAmount()).isEqualTo(10),
                () -> assertThat(response.get(0).getBalance()).isEqualTo(0),
                () -> assertThat(response.get(0).getStatus()).isEqualTo(ReserveStatus.USE),
                () -> assertThat(response.get(1).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(1).getAmount()).isEqualTo(5),
                () -> assertThat(response.get(1).getBalance()).isEqualTo(0),
                () -> assertThat(response.get(1).getStatus()).isEqualTo(ReserveStatus.USE)
        );
    }

    @Test
    void 적립금_사용() {
        적립금_적립_요청(회원.getId(), 10);
        ExtractableResponse<Response> response = 적립금_사용_요청(회원.getId(), 10);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
