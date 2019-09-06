package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByHouseId(Long id);

    boolean existsRateByUserId (Long id);
}
