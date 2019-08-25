package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.model.StatusHouse;
import com.fourmen.vipstay.security.service.UserPrinciple;
import com.fourmen.vipstay.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    @Autowired
    private HouseService houseService;

    private UserPrinciple getCurrentUser(){
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @GetMapping("/houses")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<House>> listHouse() {
        long userId=getCurrentUser().getId();
        List<House> houses = houseService.findByUserId(userId);
        if (houses.isEmpty()) {
            return new ResponseEntity<List<House>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<House>>(houses, HttpStatus.OK);
    }
    @PostMapping("/houses")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> createHouse(@RequestBody House house){
        house.setStatus(StatusHouse.AVAILABLE);
        this.houseService.createHouse(house);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping("/houses/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> editHouse(@RequestBody House house, @PathVariable Long id){
        House currentHouse = this.houseService.findById(id);

        if(currentHouse == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        currentHouse.setId(house.getId());
        currentHouse.setHouseName(house.getHouseName());
        currentHouse.setCategory(house.getCategory());
        currentHouse.setAddress(house.getAddress());
        currentHouse.setBedroomNumber(house.getBedroomNumber());
        currentHouse.setBathroomNumber(house.getBathroomNumber());
        currentHouse.setDescription(house.getDescription());
        currentHouse.setPrice(house.getPrice());
        currentHouse.setImage(house.getImage());
        currentHouse.setRate(house.getRate());
        currentHouse.setArea(house.getArea());

        this.houseService.updateHouse(currentHouse);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/houses/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id){
        House house = this.houseService.findById(id);

        if(house == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        this.houseService.deleteHouse(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
