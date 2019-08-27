package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//must login with guest role
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/me")
public class GuestController {

    @Autowired
    private HouseService houseService;

    @RequestMapping(value = "/houses", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    //not done
    public ResponseEntity<StandardResponse> listHouseOfGuest() {
        List<House> houses = this.houseService.findAll();

        if (houses.isEmpty()) {
            return new ResponseEntity<StandardResponse>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(new StandardResponse(true, "Successfully. Get list house that was booked by guest", houses), HttpStatus.OK);
    }

    @RequestMapping(value = "/houses/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> getGuestHouse(@PathVariable Long id) {
        House house = this.houseService.findById(id);

        if (house == null) {
            return new ResponseEntity<StandardResponse>(new StandardResponse(true, "Successfully but not found data", null), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(new StandardResponse(true, "Successfully. Get detail house that was booked by guest", house), HttpStatus.OK);
    }
}

