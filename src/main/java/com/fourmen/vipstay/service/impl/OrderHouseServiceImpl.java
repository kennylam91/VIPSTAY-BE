package com.fourmen.vipstay.service.impl;

import com.fourmen.vipstay.model.OrderHouse;
import com.fourmen.vipstay.repository.OrderHouseRepository;
import com.fourmen.vipstay.service.OrderHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderHouseServiceImpl implements OrderHouseService {
    @Autowired
    private OrderHouseRepository orderHouseRepository;

    @Override
    public List<OrderHouse> findAll() {
        return orderHouseRepository.findAll();
    }

    @Override
    public List<OrderHouse> findOrderHousesByTenantId(long id) {
        return orderHouseRepository.findOrderHousesByTenantId(id);
    }

    @Override
    public List<OrderHouse> findOrderHousesByHouseId(long id) {
        return orderHouseRepository.findOrderHousesByHouseId(id);
    }

    @Override
    public OrderHouse findById(Long id) {
        return orderHouseRepository.findById(id).get();
    }

    @Override
    public boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId) {
        return orderHouseRepository.existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqualAndHouseId(checkin, checkout, houseId);
    }

    @Override
    public boolean existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId) {
        return orderHouseRepository.existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(checkin, checkout, houseId);
    }

    @Override
    public boolean existsOrderHouseByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndHouseId(Date checkin, Date checkout, Long houseId) {
        return orderHouseRepository.existsOrderHouseByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndHouseId(checkin, checkout, houseId);
    }

    @Override
    public boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(Date checkin, Date checkout, Long houseId) {
        return orderHouseRepository.existsOrderHouseByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(checkin, checkout, houseId);
    }

    @Override
    public void createOrderHouse(OrderHouse orderHouse) {
        orderHouseRepository.save(orderHouse);
    }

    @Override
    public void updateOrderHouse(OrderHouse orderHouse) {
        orderHouseRepository.save(orderHouse);

    }

    @Override
    public void deleteOrderHouse(Long id) {
        orderHouseRepository.deleteById(id);
    }
}
