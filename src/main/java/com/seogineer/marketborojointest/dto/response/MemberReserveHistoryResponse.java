package com.seogineer.marketborojointest.dto.response;

import com.seogineer.marketborojointest.domain.Reserve;
import com.seogineer.marketborojointest.domain.ReserveStatus;
import java.time.LocalDateTime;

public class MemberReserveHistoryResponse {
    private final Long memberId;
    private final int amount;
    private final int balance;
    private final ReserveStatus status;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    private MemberReserveHistoryResponse(
            Long memberId,
            int amount,
            int balance,
            ReserveStatus status,
            LocalDateTime createdDate,
            LocalDateTime modifiedDate
    ) {
        this.memberId = memberId;
        this.amount = amount;
        this.balance = balance;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static MemberReserveHistoryResponse of(Reserve reserve){
        return new MemberReserveHistoryResponse(
                reserve.getMember().getId(),
                reserve.getAmount(),
                reserve.getBalance(),
                reserve.getStatus(),
                reserve.getCreatedDate(),
                reserve.getModifiedDate());
    }

    public Long getMemberId() {
        return memberId;
    }

    public int getAmount() {
        return amount;
    }

    public int getBalance() {
        return balance;
    }

    public ReserveStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
