package com.seogineer.marketborojointest.exception;

public class MemberException extends RuntimeException {
    private final ErrorCode errorCode;

    public MemberException(ErrorCode errorCode){
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
