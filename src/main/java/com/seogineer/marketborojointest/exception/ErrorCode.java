package com.seogineer.marketborojointest.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDetail() {
        return detail;
    }
}
