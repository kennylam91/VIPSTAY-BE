package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.*;
import com.fourmen.vipstay.security.service.UserPrinciple;
import com.fourmen.vipstay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/host")
public class HostController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageHouseService imageHouseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderHouseService orderHouseService;

    @Autowired
    private StatusHouseService statusHouseService;

    @RequestMapping(method = RequestMethod.GET, value = "/statusHouses/{id}")
    public ResponseEntity<StandardResponse> getStatusHouseById(@PathVariable Long id) {
        StatusHouse statusHouse = this.statusHouseService.findById(id);

        if (statusHouse == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"Get the status house successfully",statusHouse),
                HttpStatus.OK);
    }

    @PutMapping("/statusHouses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> editStatusHouse(@RequestBody StatusHouse statusHouse, @PathVariable Long id) {
        StatusHouse currentStatusHouse = this.statusHouseService.findById(id);

        if (currentStatusHouse == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

        //no update id for StatusHouse
        currentStatusHouse.setStartDate(statusHouse.getStartDate());
        currentStatusHouse.setEndDate(statusHouse.getEndDate());

        this.statusHouseService.save(currentStatusHouse);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Update the status house successfully", null),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/statusHouses")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> createStatusHouse(@RequestBody StatusHouse statusHouse) {
        this.statusHouseService.save(statusHouse);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Post a new status house successfully", null),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/statusHouses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> deleteStatusHouse(@PathVariable Long id) {
        StatusHouse statusHouse = this.statusHouseService.findById(id);

        if (statusHouse == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

        this.statusHouseService.deleteById(id);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"Delete the status house successfully",null),
                HttpStatus.OK);
    }

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @GetMapping("/houses")
    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> listHouseOfHost() {
        long userId = getCurrentUser().getId();
        List<House> houses = houseService.findByUserId(userId);
        if (houses.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list house of host", houses),
                HttpStatus.OK);
    }

    @PostMapping("/houses")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> createHouse(@RequestBody List<ImageOfHouse> imageOfHouses) {
        //find category
        String typeName = imageOfHouses.get(0).getHouse().getCategory().getName();
        Category category = categoryService.findByName(typeName);
        //save house
        House house = imageOfHouses.get(0).getHouse();
        house.setStatus(Status.AVAILABLE);
        house.setCategory(category);
        house.setUser(userService.findByUsername(getCurrentUser().getUsername()));
        this.houseService.createHouse(house);
        //save image of house
        for (ImageOfHouse imageOfHouse : imageOfHouses) {
            imageOfHouse.setHouse(house);
            this.imageHouseService.createImageHouse(imageOfHouse);
        }
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Post a new house successfully", null),
                HttpStatus.CREATED);
    }

    @PutMapping("/houses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> editHouse(@RequestBody House house, @PathVariable Long id) {
        House currentHouse = this.houseService.findById(id);

        if (currentHouse == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }
        //no update id for house
        currentHouse.setHouseName(house.getHouseName());
        currentHouse.setCategory(house.getCategory());
        currentHouse.setAddress(house.getAddress());
        currentHouse.setBedroomNumber(house.getBedroomNumber());
        currentHouse.setBathroomNumber(house.getBathroomNumber());
        currentHouse.setDescription(house.getDescription());
        currentHouse.setPrice(house.getPrice());
        currentHouse.setRate(house.getRate());
        currentHouse.setArea(house.getArea());

        this.houseService.updateHouse(currentHouse);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Update the house successfully", null),
                HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/houses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> deleteHouse(@PathVariable Long id) {
        House house = this.houseService.findById(id);

        if (house == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

        this.houseService.deleteHouse(id);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"Delete the house successfully",null),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/house/orderOfUser/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasRole('HOST')")
    public  ResponseEntity<StandardResponse> getHouseOrderByUser(@PathVariable("id") Long id){
        List<OrderHouse> orderHouses = orderHouseService.findOrderHousesByHouseId(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(true,"list all order",orderHouses),HttpStatus.OK);
    }
}
