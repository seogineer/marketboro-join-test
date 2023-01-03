package com.seogineer.marketborojointest.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.seogineer.marketborojointest.domain.Reserve;
import com.seogineer.marketborojointest.domain.ReserveStatus;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberReserveHistoryResponse {
    private Long memberId;
    private int amount;
    private int balance;
    private ReserveStatus status;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDate;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modifiedDate;

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
