package com.seogineer.marketborojointest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberAcceptanceSupport {
    public static ExtractableResponse<Response> 적립금_적립_사용_내역_조회_요청(Long memberId){
        return RestAssured.given().log().all()
                .when().get("/member/reserve/" + memberId + "/history")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 적립금_합계_조회_요청(Long memberId){
        return RestAssured.given().log().all()
                .when().get("/member/reserve/" + memberId + "/total")
                .then().log().all()
                .extract();
    }

    public static void 적립금_적립됨(ExtractableResponse<Response> response){
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 적립금_적립_요청(Long memberId, int amount){
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

    public static ExtractableResponse<Response> 회원_생성_요청() {
        return RestAssured.given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                post("/member").
                then().
                log().all().
                extract();
    }

    public static ExtractableResponse<Response> 적립금_사용_요청(Long memberId, int amount) {
        Map<String, Integer> memberUseReserveParams = new HashMap<>();
        memberUseReserveParams.put("amount", amount);

        return RestAssured.given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(memberUseReserveParams).
                when().
                put("/member/reserve/" + memberId + "/use").
                then().
                log().all().
                extract();
    }
}
