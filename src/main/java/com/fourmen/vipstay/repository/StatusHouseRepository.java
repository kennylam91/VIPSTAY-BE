package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.StatusHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusHouseRepository extends JpaRepository<StatusHouse, Long> {
    List<StatusHouse> findAllByHouseId(Long houseId);
}
