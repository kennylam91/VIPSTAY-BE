package com.fourmen.vipstay.service;

import com.fourmen.vipstay.model.OrderHouse;
import com.fourmen.vipstay.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface OrderHouseService {
    List<OrderHouse> findAll();

    List<OrderHouse> findOrderHousesByTenantId(long id);

    List<OrderHouse> findOrderHousesByHouseId(long id);

    OrderHouse findById(Long id);

    boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqual(Date checkin, Date checkout);

    boolean existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqual(Date checkin, Date checkout);

    void createOrderHouse(OrderHouse house);

    void updateOrderHouse(OrderHouse house);

    void deleteOrderHouse(Long id);

    boolean existsStatusHouseByStartDateGreaterThanEqualAndStartDateLessThanEqual(Date checkin, Date checkout, Long houseId);

    boolean existsStatusHouseByEndDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long houseId);

    boolean existsStatusHouseByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date checkin, Date checkou, Long houseId);

    boolean existsStatusHouseByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long houseId);
}
