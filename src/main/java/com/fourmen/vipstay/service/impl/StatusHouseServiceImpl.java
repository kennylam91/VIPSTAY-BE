package com.fourmen.vipstay.service.impl;

import com.fourmen.vipstay.model.StatusHouse;
import com.fourmen.vipstay.repository.StatusHouseRepository;
import com.fourmen.vipstay.service.StatusHouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StatusHouseServiceImpl implements StatusHouseService {

    @Autowired
    private StatusHouseRepository statusHouseRepository;
    @Override
    public List<StatusHouse> findAll() {
        return this.statusHouseRepository.findAll();
    }

    @Override
    public StatusHouse findById(Long id) {
        return this.statusHouseRepository.getOne(id);
    }

    @Override
    public void save(StatusHouse statusHouse) {
        this.statusHouseRepository.save(statusHouse);
    }

    @Override
    public void deleteById(Long id) {
        this.statusHouseRepository.deleteById(id);
    }

    @Override
    public List<StatusHouse> findAllByHouseId(Long houseId) {
        return this.statusHouseRepository.findAllByHouseId(houseId);
    }
}
