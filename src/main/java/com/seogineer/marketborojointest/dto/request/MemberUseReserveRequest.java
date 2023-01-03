package com.seogineer.marketborojointest.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUseReserveRequest {
    private int amount;

    public MemberUseReserveRequest(int amount) {
        this.amount = amount;
    }
}
