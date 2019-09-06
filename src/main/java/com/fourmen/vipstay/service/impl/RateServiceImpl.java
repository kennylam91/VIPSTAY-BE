package com.fourmen.vipstay.service.impl;

import com.fourmen.vipstay.model.Rate;
import com.fourmen.vipstay.repository.RateRepository;
import com.fourmen.vipstay.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Override
    public List<Rate> findAllByHouseId(Long houseId) {
        return this.rateRepository.findAllByHouseId(houseId);
    }

    @Override
    public void createRate(Rate rate) {
        this.rateRepository.save(rate);
    }

    @Override
    public boolean existsRateByUserId(Long id) {
        return this.rateRepository.existsRateByUserId(id);
    }
}
