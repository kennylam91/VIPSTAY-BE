package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.ResponseMessage;
import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.model.StatusHouse;
import com.fourmen.vipstay.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/houses")
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<List<House>> listAllHouse(){
        List<House> houses = this.houseService.findAll();

        if(houses.isEmpty()){
            return new ResponseEntity<List<House>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<House>>(houses, HttpStatus.OK);
    }

    @GetMapping("/houses/{id}")
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<House> getHouse(@PathVariable Long id){
        House house = this.houseService.findById(id);

        if (house == null){
            return new ResponseEntity<House>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<House>(house, HttpStatus.OK);
    }
    @GetMapping("/booking/{id}")
    public ResponseEntity<ResponseMessage> bookingHouse(@PathVariable Long id){
        House house=houseService.findById(id);
        if (house.getStatus()== StatusHouse.AVAILABLE){
            house.setStatus(StatusHouse.BOOKED);
            return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successfully"),HttpStatus.OK);
        }else {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage("Unavailable"),HttpStatus.OK);
        }
    }
}

