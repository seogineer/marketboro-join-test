package com.seogineer.marketborojointest.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @JoinColumn(name = "reserve_id")
    private final List<Reserve> reserves = new ArrayList<>();

    protected Member() {}

    public Member(Long id){
        this.id = id;
    }
}
