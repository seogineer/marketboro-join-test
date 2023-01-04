package com.seogineer.marketborojointest.domain;

import java.util.List;

public interface ReserveRepositoryCustom {
    List<Reserve> findCreatedDateOneYearsAgo();
}
