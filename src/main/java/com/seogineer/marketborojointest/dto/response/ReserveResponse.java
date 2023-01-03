package com.seogineer.marketborojointest.dto.response;

import com.seogineer.marketborojointest.domain.Reserve;
import com.seogineer.marketborojointest.domain.ReserveStatus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReserveResponse {
    private Long id;
    private int amount;
    private int balance;
    private ReserveStatus status;

    private ReserveResponse(Long id, int amount, int balance, ReserveStatus status) {
        this.id = id;
        this.amount = amount;
        this.balance = balance;
        this.status = status;
    }

    public static ReserveResponse of(Reserve reserve){
        return new ReserveResponse(reserve.getId(), reserve.getAmount(), reserve.getBalance(), reserve.getStatus());
    }

    public Long getId() {
        return id;
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
}
