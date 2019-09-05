package com.fourmen.vipstay.service.impl;

import com.fourmen.vipstay.model.OrderHouse;
import com.fourmen.vipstay.model.StatusHouse;
import com.fourmen.vipstay.repository.OrderHouseRepository;
import com.fourmen.vipstay.repository.StatusHouseRepository;
import com.fourmen.vipstay.service.OrderHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderHouseServiceImpl implements OrderHouseService {
    @Autowired
    private OrderHouseRepository orderHouseRepository;

    @Autowired
    private StatusHouseRepository statusHouseRepository;

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
    public boolean existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqual(Date checkin, Date checkout) {
        return orderHouseRepository.existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqual(checkin, checkout);
    }

    @Override
    public boolean existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqual(Date checkin, Date checkout) {
        return orderHouseRepository.existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqual(checkin, checkout);
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

    @Override
    public boolean existsStatusHouseByStartDateGreaterThanEqualAndStartDateLessThanEqual(Date checkin, Date checkout, Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseRepository.findAllByHouseId(houseId);
        for (Integer i = 0; i < statusHouses.size(); i++) {
            if (statusHouses.get(i).getStartDate().after(checkin) && statusHouses.get(i).getEndDate().before(checkout)) {
                return true;
            }
            ;
        }
        return false;
    }

    @Override
    public boolean existsStatusHouseByEndDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseRepository.findAllByHouseId(houseId);
        for (Integer i = 0; i < statusHouses.size(); i++) {
            if (statusHouses.get(i).getEndDate().before(checkin) && statusHouses.get(i).getEndDate().after(checkout)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsStatusHouseByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date checkin, Date checkout, Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseRepository.findAllByHouseId(houseId);
        for (Integer i = 0; i < statusHouses.size(); i++) {
            if(statusHouses.get(i).getStartDate().before(checkin) && statusHouses.get(i).getEndDate().after(checkout)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsStatusHouseByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseRepository.findAllByHouseId(houseId);
        for (Integer i = 0; i < statusHouses.size(); i++) {
            if(statusHouses.get(i).getStartDate().after(checkin) && statusHouses.get(i).getEndDate().before(checkout)){
                return true;
            }
        }
        return false;
    }
}
