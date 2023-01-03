package com.seogineer.marketborojointest.dto.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberTotalBalanceResponse {
    private Long memberId;
    private int totalBalance;

    private MemberTotalBalanceResponse(Long memberId, int totalBalance) {
        this.memberId = memberId;
        this.totalBalance = totalBalance;
    }

    public static MemberTotalBalanceResponse of(MemberResponse member){
        return new MemberTotalBalanceResponse(member.getId(), member.getTotalReserve());
    }

    public Long getMemberId() {
        return memberId;
    }

    public int getTotalBalance() {
        return totalBalance;
    }
}
