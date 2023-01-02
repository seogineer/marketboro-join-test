package com.seogineer.marketborojointest.dto.response;

import com.seogineer.marketborojointest.domain.Reserve;
import java.util.List;

public class MemberReserveHistoryResponse {
    private final Long memberId;
    private final List<Reserve> reserves;

    public MemberReserveHistoryResponse(Long memberId, List<Reserve> reserves) {
        this.memberId = memberId;
        this.reserves = reserves;
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<Reserve> getReserves() {
        return reserves;
    }
}
