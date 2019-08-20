package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.House;

import java.util.List;

public interface HouseService {

    List<House> findAll();

    House findById(Long id);

    void createHouse(House house);

    void updateHouse(House house);

    void deleteHouse(Long id);
}
