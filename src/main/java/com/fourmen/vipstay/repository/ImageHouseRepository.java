package com.fourmen.vipstay.repository;

import com.fourmen.vipstay.model.ImageOfHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageHouseRepository extends JpaRepository<ImageOfHouse,Long> {
}
