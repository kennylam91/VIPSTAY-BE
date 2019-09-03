package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.OrderHouse;
import com.fourmen.vipstay.security.service.UserPrinciple;
import com.fourmen.vipstay.service.HouseService;
import com.fourmen.vipstay.service.OrderHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//must login with guest role
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/me")
public class GuestController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private OrderHouseService orderHouseService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    //not done
    public ResponseEntity<StandardResponse> listOrderOfGuest() {
        List<OrderHouse> orderHouses = this.orderHouseService.findOrderHousesByTenantId(getCurrentUser().getId());

        if (orderHouses.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list orders that was booked by guest", orderHouses),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> getDetailOrder(@PathVariable Long id) {
        OrderHouse orderHouse = this.orderHouseService.findById(id);

        if (orderHouse == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get detail order that was booked by guest", orderHouse),
                HttpStatus.OK);
    }
}

