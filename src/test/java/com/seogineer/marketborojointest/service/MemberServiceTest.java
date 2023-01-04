package com.seogineer.marketborojointest.service;

import static com.seogineer.marketborojointest.exception.ErrorCode.GREATER_THAN_TOTAL_RESERVES;
import static com.seogineer.marketborojointest.exception.ErrorCode.RESERVE_SAVE_MUST_POSITIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.seogineer.marketborojointest.domain.Member;
import com.seogineer.marketborojointest.domain.MemberRepository;
import com.seogineer.marketborojointest.domain.Reserve;
import com.seogineer.marketborojointest.domain.ReserveRepository;
import com.seogineer.marketborojointest.domain.ReserveStatus;
import com.seogineer.marketborojointest.dto.request.MemberAddReserveRequest;
import com.seogineer.marketborojointest.dto.request.MemberUseReserveRequest;
import com.seogineer.marketborojointest.dto.response.MemberReserveHistoryResponse;
import com.seogineer.marketborojointest.dto.response.MemberTotalBalanceResponse;
import com.seogineer.marketborojointest.exception.MemberException;
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
class MemberServiceTest {
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

    @Test
    void 회원별_적립금_적립() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(10));

        assertThat(회원.getReserves().size()).isEqualTo(1);
    }

    @Test
    void 음수_적립금_적립() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        assertThatThrownBy(
                () -> memberService.addReserve(회원.getId(), new MemberAddReserveRequest(-1))
        )
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(RESERVE_SAVE_MUST_POSITIVE.getDetail());
    }

    @Test
    void 회원별_적립금_합계_조회() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));
        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(10));
        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(10));
        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(10));

        MemberTotalBalanceResponse response = memberService.getTotalReserveByMember(회원.getId());

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(1L),
                () -> assertThat(response.getTotalBalance()).isEqualTo(30)
        );
    }

    @Test
    void 적립금_적립하지_않고_조회() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        MemberTotalBalanceResponse response = memberService.getTotalReserveByMember(회원.getId());

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(1L),
                () -> assertThat(response.getTotalBalance()).isEqualTo(0)
        );
    }

    @Test
    void 적립금_사용후_합계_조회() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));
        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(10));
        memberService.useReserve(회원.getId(), new MemberUseReserveRequest(10));

        MemberTotalBalanceResponse response = memberService.getTotalReserveByMember(회원.getId());

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(1L),
                () -> assertThat(response.getTotalBalance()).isEqualTo(0)
        );
    }

    @Test
    void 적립금_부분_사용후_합계_조회() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));
        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(10));
        memberService.useReserve(회원.getId(), new MemberUseReserveRequest(5));

        MemberTotalBalanceResponse response = memberService.getTotalReserveByMember(회원.getId());

        assertAll(
                () -> assertThat(response.getMemberId()).isEqualTo(1L),
                () -> assertThat(response.getTotalBalance()).isEqualTo(5)
        );
    }

    @Test
    void 회원별_적립금_적립_사용_내역_조회() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));
        given(reserveRepository.findAllByMemberId(anyLong(), any())).willReturn(Arrays.asList(적립금1, 적립금2, 적립금3));

        List<MemberReserveHistoryResponse> response =
                memberService.getReserveHistoryByMember(회원.getId(), PageRequest.of(0, 5));

        assertAll(
                () -> assertThat(response).hasSize(3),
                () -> assertThat(response.get(0).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(0).getAmount()).isEqualTo(10),
                () -> assertThat(response.get(0).getBalance()).isEqualTo(10),
                () -> assertThat(response.get(0).getStatus()).isEqualTo(ReserveStatus.SAVE),
                () -> assertThat(response.get(1).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(1).getAmount()).isEqualTo(5),
                () -> assertThat(response.get(1).getBalance()).isEqualTo(5),
                () -> assertThat(response.get(1).getStatus()).isEqualTo(ReserveStatus.SAVE),
                () -> assertThat(response.get(2).getMemberId()).isEqualTo(회원.getId()),
                () -> assertThat(response.get(2).getAmount()).isEqualTo(1),
                () -> assertThat(response.get(2).getBalance()).isEqualTo(1),
                () -> assertThat(response.get(2).getStatus()).isEqualTo(ReserveStatus.SAVE)
        );
    }

    @Test
    void 회원별_적립금_사용() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));
        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(10));
        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(20));
        memberService.addReserve(회원.getId(), new MemberAddReserveRequest(30));

        memberService.useReserve(회원.getId(), new MemberUseReserveRequest(10));

        assertThat(회원.getTotalReserve()).isEqualTo(50);
    }

    @Test
    void 적립금_보다_큰_금액_사용() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        assertThatThrownBy(
                () -> memberService.useReserve(회원.getId(), new MemberUseReserveRequest(10))
        )
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(GREATER_THAN_TOTAL_RESERVES.getDetail());
    }

}
