package com.seogineer.marketborojointest.controller;

import com.seogineer.marketborojointest.domain.Member;
import com.seogineer.marketborojointest.dto.request.MemberAddReserveRequest;
import com.seogineer.marketborojointest.dto.request.MemberUseReserveRequest;
import com.seogineer.marketborojointest.dto.response.MemberReserveHistoryResponse;
import com.seogineer.marketborojointest.dto.response.MemberResponse;
import com.seogineer.marketborojointest.dto.response.MemberTotalBalanceResponse;
import com.seogineer.marketborojointest.service.MemberService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<MemberResponse> createMember(){
        MemberResponse member = memberService.createMember();
        return ResponseEntity.created(URI.create("/member/" + member.getId())).body(member);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long id){
        MemberResponse member = memberService.findMember(id);
        return ResponseEntity.ok().body(member);
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/member/reserve/{id}/add")
    public ResponseEntity<?> addReserve(@PathVariable Long id, @RequestBody @Valid MemberAddReserveRequest request){
        memberService.addReserve(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/member/reserve/{id}/total")
    public ResponseEntity<MemberTotalBalanceResponse> getTotalReserveByMember(@PathVariable Long id){
        MemberTotalBalanceResponse members = memberService.getTotalReserveByMember(id);
        return ResponseEntity.ok().body(members);
    }

    @GetMapping("/member/reserve/{id}/history")
    public ResponseEntity<List<MemberReserveHistoryResponse>> getReserveHistoryByMember(
            @PathVariable Long id, Pageable pageable
    ){
        List<MemberReserveHistoryResponse> members = memberService.getReserveHistoryByMember(id, pageable);
        return ResponseEntity.ok().body(members);
    }

    @PutMapping("/member/reserve/{id}/use")
    public ResponseEntity<?> useReserve(@PathVariable Long id, @RequestBody @Valid MemberUseReserveRequest request){
        memberService.useReserve(id, request);
        return ResponseEntity.ok().build();
    }
}
