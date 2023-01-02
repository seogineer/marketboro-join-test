package com.seogineer.marketborojointest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.seogineer.marketborojointest.domain.Member;
import com.seogineer.marketborojointest.domain.MemberRepository;
import com.seogineer.marketborojointest.dto.response.MemberReserveHistoryResponse;
import com.seogineer.marketborojointest.dto.response.MemberTotalAmountResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;
    private final Member 회원 = new Member(1L);
    private final Member 회원2 = new Member(2L);
    private final Member 회원3 = new Member(3L);
    private final Member 회원4 = new Member(4L);
    private final Member 회원5 = new Member(5L);
    private final Member 회원6 = new Member(6L);

    @Test
    void 회원별_적립금_적립() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        memberService.addReserve(회원.getId(), 10);

        assertThat(회원.getReserves().size()).isEqualTo(1);
    }

    @Test
    void 회원별_적립금_합계_조회() {
        회원.addReserve(10);
        회원2.addReserve(50);
        회원2.addReserve(30);
        given(memberRepository.findAll()).willReturn(Arrays.asList(회원, 회원2));

        List<MemberTotalAmountResponse> response = memberService.getTotalReserveByMember();

        assertThat(response.get(0).getMemberId()).isEqualTo(1L);
        assertThat(response.get(0).getTotalAmount()).isEqualTo(10);
        assertThat(response.get(1).getMemberId()).isEqualTo(2L);
        assertThat(response.get(1).getTotalAmount()).isEqualTo(80);
    }

    @Test
    void 회원별_적립금_적립_사용_내역_조회() {
        회원.addReserve(10);
        회원2.addReserve(50);
        회원2.addReserve(30);
        회원3.addReserve(10);
        회원3.addReserve(10);
        회원3.addReserve(50);
        회원4.addReserve(30);
        회원5.addReserve(10);
        회원5.addReserve(10);
        회원5.addReserve(50);
        회원6.addReserve(30);
        List<Member> members = Arrays.asList(회원, 회원2, 회원3, 회원4, 회원5);
        Page<Member> pagedResponse = new PageImpl<>(members);
        given(memberRepository.findAll(PageRequest.of(0, 5))).willReturn(pagedResponse);

        List<MemberReserveHistoryResponse> response = memberService.getReserveHistoryByMember(PageRequest.of(0, 5));

        assertThat(response.size()).isEqualTo(5);
    }

    @Test
    void 회원별_적립금_사용() {
        회원.addReserve(10);
        회원.addReserve(20);
        회원.addReserve(30);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(회원));

        memberService.useReserve(회원.getId(), 10);

        assertThat(회원.getTotalReserve()).isEqualTo(50);
    }
}
