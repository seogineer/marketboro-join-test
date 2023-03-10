package com.seogineer.marketborojointest.domain;

import static com.seogineer.marketborojointest.exception.ErrorCode.GREATER_THAN_TOTAL_RESERVES;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seogineer.marketborojointest.exception.MemberException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Column(name = "reserve_id")
    private List<Reserve> reserves = new ArrayList<>();

    public Member() {}

    public Member(Long id){
        this.id = id;
    }

    public void addReserve(int amount){
        reserves.add(Reserve.of(amount, this));
    }

    public void useReserve(int usage) {
        validateGreaterThanTotalReserves(usage);

        int cursor = 0;
        while(true){
            if(reserves.get(cursor).isUse()){
                cursor++;
                continue;
            }

            int changes = reserves.get(cursor).use(usage);
            if(IsZeroChanges(changes)){
                break;
            }
            cursor++;
            usage = changes;
        }
    }

    private boolean IsZeroChanges(int changes){
        return changes == 0;
    }

    private void validateGreaterThanTotalReserves(int amount){
        if(getTotalReserve() < amount){
            throw new MemberException(GREATER_THAN_TOTAL_RESERVES);
        }
    }

    @JsonIgnore
    public int getTotalReserve() {
        return reserves.stream()
                .filter(reserve -> !reserve.isDeleted())
                .map(Reserve::getBalance)
                .reduce(0, Integer::sum);
    }
}
