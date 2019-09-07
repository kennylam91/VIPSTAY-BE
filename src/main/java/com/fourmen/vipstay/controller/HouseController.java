package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.model.OrderHouse;
import com.fourmen.vipstay.model.StatusHouse;
import com.fourmen.vipstay.model.User;
import com.fourmen.vipstay.security.service.UserPrinciple;
import com.fourmen.vipstay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//need'nt login
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private ImageHouseService imageHouseService;

    @Autowired
    private OrderHouseService orderHouseService;

    @Autowired
    private UserService userService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @Autowired
    private StatusHouseService statusHouseService;

    @RequestMapping(value = "/houses", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> listAllHouse() {
        List<House> houses = this.houseService.findAll();

        if (houses.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        for (House house : houses) {
            List<String> listImageUrlOfHouse = imageHouseService.getListImageUrlOfHouseByHouseId(house.getId());
            house.setImageUrls(listImageUrlOfHouse);
            List<Long> orderHouseIds = orderHouseService.getOrderIdsByHouseId(house.getId());
            house.setOrderHouseIds(orderHouseIds);
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
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        List<String> listImageUrlOfHouse = imageHouseService.getListImageUrlOfHouseByHouseId(house.getId());
        house.setImageUrls(listImageUrlOfHouse);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get detail house", house),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/houses/{id}/booking", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> bookingHouse(@PathVariable Long id, @RequestBody OrderHouse orderHouse) {
        boolean isBooked =
                orderHouseService.existsOrderHouseByCheckinGreaterThanEqualAndCheckinLessThanEqualAndHouseId(
                        orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsOrderHouseByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(
                        orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsOrderHouseByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndHouseId(
                        orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsOrderHouseByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndHouseId(
                        orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsStatusHouseByEndDateGreaterThanEqualAndEndDateLessThanEqual(orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsStatusHouseByStartDateGreaterThanEqualAndEndDateLessThanEqual(orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsStatusHouseByStartDateGreaterThanEqualAndStartDateLessThanEqual(orderHouse.getCheckin(), orderHouse.getCheckout(), id)
                        || orderHouseService.existsStatusHouseByStartDateLessThanEqualAndEndDateGreaterThanEqual(orderHouse.getCheckin(), orderHouse.getCheckout(), id);
        if (isBooked) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Ngày này nhà đã được đặt. Bạn vui lòng đặt vào ngày khác", null),
                    HttpStatus.OK);
        }
        House house = houseService.findById(id);
        orderHouse.setHouse(house);
        User tenant = userService.findById(getCurrentUser().getId());
        orderHouse.setTenant(tenant);
        orderHouseService.createOrderHouse(orderHouse);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Đặt nhà thành công", null),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/house/all-user-order", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> allUserOder() {
        List<OrderHouse> orderHouses = orderHouseService.findAll();
        return new ResponseEntity<StandardResponse>(new StandardResponse(true, "list all order", orderHouses), HttpStatus.OK);
    }


    @RequestMapping(value = "/statusHouses/{houseId}", method = RequestMethod.GET)
    private ResponseEntity<StandardResponse> listStatusHouse(@PathVariable Long houseId) {
        List<StatusHouse> statusHouses = this.statusHouseService.findAllByHouseId(houseId);

        if (statusHouses.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(true, "Successfully but not found data", null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list status houses", statusHouses),
                HttpStatus.OK);
    }

//    @GetMapping("/booking/{id}")
//    public ResponseEntity<StandardResponse> bookingHouse(@PathVariable Long id){
//        House house=houseService.findById(id);
//        if (house.getStatus()== Status.AVAILABLE){
//            house.setStatus(Status.BOOKED);
//            return new ResponseEntity<StandardResponse>(new StandardResponse("Successfully"),HttpStatus.OK);
//        }else {
//            return new ResponseEntity<StandardResponse>(new StandardResponse("Unavailable"),HttpStatus.OK);
//        }
//    }
}

