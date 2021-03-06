package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.House;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HouseService {

    List<House> findAll();

    List<House> findByUserId(long userId);

    House findById(Long id);

    void createHouse(House house);

    void updateHouse(House house);

    void deleteHouse(Long id);
}
