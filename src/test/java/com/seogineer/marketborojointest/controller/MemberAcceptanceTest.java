package com.seogineer.marketborojointest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.seogineer.marketborojointest.AcceptanceTest;
import com.seogineer.marketborojointest.dto.response.MemberResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("회원 관련 기능")
public class MemberAcceptanceTest extends AcceptanceTest {
    private MemberResponse 회원;

    @BeforeEach
    public void setUp() {
        super.setUp();

        회원 = 회원_생성_요청().as(MemberResponse.class);
    }

    @Test
    void 회원_생성() {
        ExtractableResponse<Response> response = 회원_생성_요청();

        회원_생성됨(response);
    }

    @Test
    void 적립금_적립() {
        ExtractableResponse<Response> response = 적립금_적립_요청(회원.getId(), 10);

        적립금_적립됨(response);
    }

    private void 적립금_적립됨(ExtractableResponse<Response> response){
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> 적립금_적립_요청(Long memberId, int amount){
        Map<String, Integer> memberAddReserveParams = new HashMap<>();
        memberAddReserveParams.put("amount", amount);

        return RestAssured.given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(memberAddReserveParams).
                when().
                put("/member/reserve/" + memberId + "/add").
                then().
                log().all().
                extract();
    }

    private void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private ExtractableResponse<Response> 회원_생성_요청() {
        return RestAssured.given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                post("/member").
                then().
                log().all().
                extract();
    }
}
