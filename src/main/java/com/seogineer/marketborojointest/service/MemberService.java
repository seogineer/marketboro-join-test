package com.seogineer.marketborojointest.service;

import com.seogineer.marketborojointest.domain.Member;
import com.seogineer.marketborojointest.domain.MemberRepository;
import com.seogineer.marketborojointest.dto.response.MemberReserveHistoryResponse;
import com.seogineer.marketborojointest.dto.response.MemberTotalAmountResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void addReserve(Long memberId, int amount){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.addReserve(amount);
    }

    @Transactional(readOnly = true)
    public List<MemberTotalAmountResponse> getTotalReserveByMember(){
        return memberRepository.findAll().stream()
                .map(member -> new MemberTotalAmountResponse(member.getId(), member.getTotalReserve()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberReserveHistoryResponse> getReserveHistoryByMember(Pageable pageable){
        return memberRepository.findAll(pageable).stream()
                .map(member -> new MemberReserveHistoryResponse(member.getId(), member.getReserves()))
                .collect(Collectors.toList());
    }

    public void useReserve(Long memberId, int amount){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.useReserve(amount);
    }
}
