package com.seogineer.marketborojointest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Column(name = "member_id")
    private Long memberId;

    protected Reserve() {}

    public Reserve(int amount, Long memberId){
        this.amount = amount;
        this.balance = amount;
        this.status = ReserveStatus.SAVE;
        this.memberId = memberId;
    }

    public int use(int money){
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
