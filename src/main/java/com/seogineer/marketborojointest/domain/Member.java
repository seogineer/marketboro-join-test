package com.seogineer.marketborojointest.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
    @OneToMany(cascade = CascadeType.ALL)
    private final List<Reserve> reserves = new ArrayList<>();

    protected Member() {}

    public Member(Long id){
        this.id = id;
    }

    public void addReserve(int amount){
        reserves.add(Reserve.of(amount, this.getId()));
    }

    public void useReserve(int amount) {
        if(getTotalReserve() < amount){
            throw new IllegalArgumentException();
        }

        int cursor = 0;
        while(true){
            if(cursor > reserves.size() - 1){
                break;
            }
            Reserve reserve = reserves.get(cursor);
            if(reserve.getStatus() == ReserveStatus.USE){
                cursor++;
                continue;
            }
            int remain = reserve.use(amount);
            if(remain == 0){
                break;
            }
            cursor++;
            amount = remain;
        }
    }

    public int getTotalReserve() {
        return reserves.stream()
                .map(Reserve::getBalance)
                .reduce(0, Integer::sum);
    }
}
