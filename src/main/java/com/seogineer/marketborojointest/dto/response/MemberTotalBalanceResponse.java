package com.seogineer.marketborojointest.dto.response;

import com.seogineer.marketborojointest.domain.Member;

public class MemberTotalBalanceResponse {
    private final Long memberId;
    private final int totalBalance;

    private MemberTotalBalanceResponse(Long memberId, int totalBalance) {
        this.memberId = memberId;
        this.totalBalance = totalBalance;
    }

    public static MemberTotalBalanceResponse of(Member member){
        return new MemberTotalBalanceResponse(member.getId(), member.getTotalReserve());
    }

    public Long getMemberId() {
        return memberId;
    }

    public int getTotalBalance() {
        return totalBalance;
    }
}
