package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/me")
public class GuestController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/houses")
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<List<House>> listAllHouse(){
        List<House> houses = this.houseService.findAll();

        if(houses.isEmpty()){
            return new ResponseEntity<List<House>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<House>>(houses, HttpStatus.OK);
    }

    @GetMapping("/houses/{id}")
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<House> getHouse(@PathVariable Long id){
        House house = this.houseService.findById(id);

        if (house == null){
            return new ResponseEntity<House>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<House>(house, HttpStatus.OK);
    }
}

