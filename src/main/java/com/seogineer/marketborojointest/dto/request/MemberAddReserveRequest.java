package com.seogineer.marketborojointest.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAddReserveRequest {
    private int amount;

    public MemberAddReserveRequest(int amount) {
        this.amount = amount;
    }
}
