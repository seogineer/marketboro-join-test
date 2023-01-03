package com.seogineer.marketborojointest.dto.request;

import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUseReserveRequest {
    @Min(1)
    private int amount;

    public MemberUseReserveRequest(int amount) {
        this.amount = amount;
    }
}
