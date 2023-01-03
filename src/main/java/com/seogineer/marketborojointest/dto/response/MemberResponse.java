package com.seogineer.marketborojointest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seogineer.marketborojointest.domain.Member;
import com.seogineer.marketborojointest.domain.Reserve;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberResponse {
    private Long id;
    private List<ReserveResponse> reserves;

    private MemberResponse(Long id, List<Reserve> reserves) {
        this.id = id;
        this.reserves = reserves.stream().map(ReserveResponse::of).collect(Collectors.toList());
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getReserves());
    }

    public Long getId() {
        return id;
    }

    public List<ReserveResponse> getReserves() {
        return reserves;
    }

    @JsonIgnore
    public int getTotalReserve() {
        return reserves.stream()
                .map(ReserveResponse::getBalance)
                .reduce(0, Integer::sum);
    }
}
