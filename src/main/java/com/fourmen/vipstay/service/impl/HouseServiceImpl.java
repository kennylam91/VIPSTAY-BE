package com.fourmen.vipstay.service.impl;

import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.repository.HouseRepository;
import com.fourmen.vipstay.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Override
    public List<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public List<House> findByUserId(long userId) {
        return houseRepository.findByUserId(userId);
    }

    @Override
    public House findById(Long id) {
        return houseRepository.findById(id).get();
    }

    @Override
    public void createHouse(House house) {
        houseRepository.save(house);
    }

    @Override
    public void updateHouse(House house) {
        houseRepository.save(house);
    }

    @Override
    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
    }
}
