package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/houses")
    public ResponseEntity<List<House>> listAllHouse(){
        List<House> houses = this.houseService.findAll();

        if(houses.isEmpty()){
            return new ResponseEntity<List<House>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<House>>(houses, HttpStatus.OK);
    }

    @GetMapping("/houses/{id}")
    public ResponseEntity<House> getHouse(@PathVariable Long id){
        House house = this.houseService.findById(id);

        if (house == null){
            return new ResponseEntity<House>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<House>(house, HttpStatus.OK);
    }

    @PostMapping("/houses")
    public ResponseEntity<Void> createHouse(@RequestBody House house){
        this.houseService.createHouse(house);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping("/houses/{id}")
    public ResponseEntity<Void> editHouse(@RequestBody House house, @PathVariable Long id){
        House currentHouse = this.houseService.findById(id);

        if(currentHouse == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        currentHouse.setId(house.getId());
        currentHouse.setHouseName(house.getHouseName());
        currentHouse.setHouseType(house.getHouseType());
        currentHouse.setAddress(house.getAddress());
        currentHouse.setBedroomNumber(house.getBedroomNumber());
        currentHouse.setBathroomNumber(house.getBathroomNumber());
        currentHouse.setDescription(house.getDescription());
        currentHouse.setPrice(house.getPrice());
        currentHouse.setImage(house.getImage());
        currentHouse.setRate(house.getRate());

        this.houseService.updateHouse(currentHouse);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/houses/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id){
        House house = this.houseService.findById(id);

        if(house == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        this.houseService.deleteHouse(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
