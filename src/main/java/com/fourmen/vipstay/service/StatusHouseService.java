package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.StatusHouse;

import java.util.List;

public interface StatusHouseService {
    List<StatusHouse> findAll();

    StatusHouse findById(Long id);

    void save(StatusHouse statusHouse);

    void deleteById(Long id);

    List<StatusHouse> findAllByHouseId(Long houseId);
}
