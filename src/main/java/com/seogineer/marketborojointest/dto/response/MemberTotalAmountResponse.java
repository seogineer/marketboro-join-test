package com.seogineer.marketborojointest.dto.response;

public class MemberTotalAmountResponse {
    private final Long memberId;
    private final int totalAmount;

    public MemberTotalAmountResponse(Long memberId, int totalAmount) {
        this.memberId = memberId;
        this.totalAmount = totalAmount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
