package com.seogineer.marketborojointest.domain;

import static com.seogineer.marketborojointest.exception.ErrorCode.RESERVE_SAVE_MUST_POSITIVE;

import com.seogineer.marketborojointest.exception.MemberException;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Reserve() {}

    private Reserve(int amount, Member member){
        validateAmountLessThanZero(amount);
        this.amount = amount;
        this.balance = amount;
        this.status = ReserveStatus.SAVE;
        this.member = member;
    }

    public static Reserve of(int amount, Member member){
        return new Reserve(amount, member);
    }

    public int use(int usage){
        if(isUse()){
            return usage;
        }

        if(this.balance == usage) {
            this.status = ReserveStatus.USE;
            this.balance -= usage;
            return 0;
        }
        if(this.balance > usage){
            this.balance -= usage;
            return 0;
        }
        this.status = ReserveStatus.USE;
        int changes = usage - this.balance;
        this.balance = 0;

        return changes;
    }

    private void validateAmountLessThanZero(int amount){
        if(amount <= 0){
            throw new MemberException(RESERVE_SAVE_MUST_POSITIVE);
        }
    }

    public boolean isUse() {
        return this.status == ReserveStatus.USE;
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

    public Member getMember() {
        return member;
    }
}
