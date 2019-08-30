package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.Category;
import com.fourmen.vipstay.model.House;
import com.fourmen.vipstay.model.ImageOfHouse;
import com.fourmen.vipstay.model.StatusHouse;
import com.fourmen.vipstay.security.service.UserPrinciple;
import com.fourmen.vipstay.service.CategoryService;
import com.fourmen.vipstay.service.HouseService;
import com.fourmen.vipstay.service.ImageHouseService;
import com.fourmen.vipstay.service.UserService;
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
        house.setStatus(StatusHouse.AVAILABLE);
        house.setCategory(category);
        house.setUser(userService.findByUserName(getCurrentUser().getUsername()));
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
//        currentHouse.setImages(house.getImageHouses());

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
                new StandardResponse(true, "Delete the house successfully", null),
                HttpStatus.OK);
    }
}
