package com.seogineer.marketborojointest.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;

@Entity
@Getter
public class Reserve extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    private int balance;
    @Enumerated(value = EnumType.STRING)
    private ReserveStatus status;
    @JoinColumn(name = "member_id")
    private Long memberId;

    protected Reserve() {}

    private Reserve(int amount, Long memberId){
        this.amount = amount;
        this.balance = amount;
        this.status = ReserveStatus.SAVE;
        this.memberId = memberId;
    }

    public static Reserve of(int amount, Long memberId){
        return new Reserve(amount, memberId);
    }

    public int use(int money){
        if(this.status == ReserveStatus.USE){
            return money;
        }

        if(this.amount == money) {
            this.status = ReserveStatus.USE;
            this.balance -= money;
            return 0;
        }
        if(this.amount > money){
            this.balance -= money;
            return 0;
        }
        this.status = ReserveStatus.USE;
        int remain = money - this.balance;
        this.balance = 0;
        return remain;
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

    public Long getMemberId() {
        return memberId;
    }
}
