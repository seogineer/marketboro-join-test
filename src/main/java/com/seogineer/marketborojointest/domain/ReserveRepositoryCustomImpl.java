package com.seogineer.marketborojointest.domain;

import static com.seogineer.marketborojointest.domain.QReserve.reserve;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReserveRepositoryCustomImpl implements ReserveRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Reserve> findCreatedDateOneYearsAgo() {
        return queryFactory
                .selectFrom(reserve)
                .where(
                        reserve.isDeleted.eq(false),
                        reserve.createdDate.before(LocalDateTime.now().minusDays(365))
                )
                .fetch();
    }
}
