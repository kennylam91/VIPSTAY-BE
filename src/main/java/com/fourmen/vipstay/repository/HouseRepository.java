package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.model.User;
import com.fourmen.vipstay.security.service.UserPrinciple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByUserId (long userId);
    House findByHouseName (String name);


}
