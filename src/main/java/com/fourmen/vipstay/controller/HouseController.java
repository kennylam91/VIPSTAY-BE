package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//need'nt login
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping(value = "/houses", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> listAllHouse() {
        List<House> houses = this.houseService.findAll();

        if (houses.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(true, "Successfully but not found data", null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list all house", houses),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/houses/{id}", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> getHouse(@PathVariable Long id) {
        House house = this.houseService.findById(id);

        if (house == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(true, "Successfully but not found data", null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get detail house", house),
                HttpStatus.OK);
    }
//    @GetMapping("/booking/{id}")
//    public ResponseEntity<StandardResponse> bookingHouse(@PathVariable Long id){
//        House house=houseService.findById(id);
//        if (house.getStatus()== StatusHouse.AVAILABLE){
//            house.setStatus(StatusHouse.BOOKED);
//            return new ResponseEntity<StandardResponse>(new StandardResponse("Successfully"),HttpStatus.OK);
//        }else {
//            return new ResponseEntity<StandardResponse>(new StandardResponse("Unavailable"),HttpStatus.OK);
//        }
//    }
}

