package com.seogineer.marketborojointest.service;

import static com.seogineer.marketborojointest.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.seogineer.marketborojointest.domain.Member;
import com.seogineer.marketborojointest.domain.MemberRepository;
import com.seogineer.marketborojointest.domain.ReserveRepository;
import com.seogineer.marketborojointest.dto.request.MemberAddReserveRequest;
import com.seogineer.marketborojointest.dto.request.MemberUseReserveRequest;
import com.seogineer.marketborojointest.dto.response.MemberReserveHistoryResponse;
import com.seogineer.marketborojointest.dto.response.MemberTotalBalanceResponse;
import com.seogineer.marketborojointest.exception.MemberException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final ReserveRepository reserveRepository;

    public Member createMember(){
        return memberRepository.save(new Member());
    }

    @Cacheable(value="members", key="#memberId")
    @Transactional(readOnly = true)
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }

    @CacheEvict(value="members", key="#memberId")
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Caching(
            evict = @CacheEvict(value = "members", key = "#memberId"),
            put = @CachePut(value = "members", key = "#memberId")
    )
    public void addReserve(Long memberId, MemberAddReserveRequest request){
        Member member = findMember(memberId);
        member.addReserve(request.getAmount());
    }

    @Transactional(readOnly = true)
    public MemberTotalBalanceResponse getTotalReserveByMember(Long memberId){
        Member member = findMember(memberId);
        return MemberTotalBalanceResponse.of(member);
    }

    @Transactional(readOnly = true)
    public List<MemberReserveHistoryResponse> getReserveHistoryByMember(Long memberId, Pageable pageable){
        Member member = findMember(memberId);
        return reserveRepository.findAllByMemberId(member.getId(), pageable).stream()
                .map(MemberReserveHistoryResponse::of)
                .collect(Collectors.toList());
    }

    @Caching(
            evict = @CacheEvict(value = "members", key = "#memberId"),
            put = @CachePut(value = "members", key = "#memberId")
    )
    public void useReserve(Long memberId, MemberUseReserveRequest request){
        Member member = findMember(memberId);
        member.useReserve(request.getAmount());
    }
}
