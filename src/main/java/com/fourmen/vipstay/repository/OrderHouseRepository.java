package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.OrderHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderHouseRepository extends JpaRepository<OrderHouse, Long> {
    boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqual(Date checkin, Date checkout);

    boolean existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqual(Date checkin, Date checkout);

    List<OrderHouse> findOrderHousesByTenantId(long id);
}
