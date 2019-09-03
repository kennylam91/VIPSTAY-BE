package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.OrderHouse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface OrderHouseService {
    List<OrderHouse> findAll();

    List<OrderHouse> findOrderHousesByTenantId(long id);

    OrderHouse findById(Long id);

    boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqual(Date checkin, Date checkout);

    boolean existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqual(Date checkin, Date checkout);

    void createOrderHouse(OrderHouse house);

    void updateOrderHouse(OrderHouse house);

    void deleteOrderHouse(Long id);
}
