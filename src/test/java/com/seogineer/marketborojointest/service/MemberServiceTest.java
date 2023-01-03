package com.seogineer.marketborojointest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.seogineer.marketborojointest.domain.Member;
import com.seogineer.marketborojointest.domain.MemberRepository;
import com.seogineer.marketborojointest.domain.Reserve;
import com.seogineer.marketborojointest.domain.ReserveRepository;
import com.seogineer.marketborojointest.dto.request.MemberAddReserveRequest;
import com.seogineer.marketborojointest.dto.request.MemberUseReserveRequest;
import com.seogineer.marketborojointest.dto.response.MemberReserveHistoryResponse;
import com.seogineer.marketborojointest.dto.response.MemberTotalBalanceResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ReserveRepository reserveRepository;
    @InjectMocks
    private MemberService memberService;
    private final Member 회원 = new Member(1L);
    private final Reserve 적립금1 = Reserve.of(10, 회원);
    private final Reserve 적립금2 = Reserve.of(5, 회원);
    private final Reserve 적립금3 = Reserve.of(1, 회원);
    private MemberAddReserveRequest request = new MemberAddReserveRequest(10);

    @Test
    void 회원별_적립금_적립() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        memberService.addReserve(회원.getId(), request);

        assertThat(회원.getReserves().size()).isEqualTo(1);
    }

    @Test
    void 회원별_적립금_합계_조회() {
        회원.addReserve(10);
        회원.addReserve(10);
        회원.addReserve(10);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        MemberTotalBalanceResponse response = memberService.getTotalReserveByMember(회원.getId());

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(1L),
                () -> assertThat(response.getTotalBalance()).isEqualTo(30)
        );
    }

    @Test
    void 회원별_적립금_적립_사용_내역_조회() {
        회원.addReserve(10);
        회원.addReserve(5);
        회원.addReserve(1);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));
        given(reserveRepository.findAllByMemberId(anyLong(), any())).willReturn(Arrays.asList(적립금1, 적립금2, 적립금3));

        List<MemberReserveHistoryResponse> response =
                memberService.getReserveHistoryByMember(회원.getId(), PageRequest.of(0, 5));

        assertThat(response).hasSize(3);
    }

    @Test
    void 회원별_적립금_사용() {
        회원.addReserve(10);
        회원.addReserve(20);
        회원.addReserve(30);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        memberService.useReserve(회원.getId(), new MemberUseReserveRequest(10));

        assertThat(회원.getTotalReserve()).isEqualTo(50);
    }
}
