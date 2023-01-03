package com.seogineer.marketborojointest.dto.response;

import com.seogineer.marketborojointest.domain.Member;
import com.seogineer.marketborojointest.domain.Reserve;
import java.util.List;

public class MemberResponse {
    private Long id;
    private List<Reserve> reserves;

    private MemberResponse(Long id, List<Reserve> reserves) {
        this.id = id;
        this.reserves = reserves;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getReserves());
    }

    public Long getId() {
        return id;
    }

    public List<Reserve> getReserves() {
        return reserves;
    }
}
