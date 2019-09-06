package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.Comment;
import com.fourmen.vipstay.model.OrderHouse;
import com.fourmen.vipstay.model.Rate;
import com.fourmen.vipstay.model.StatusHouse;
import com.fourmen.vipstay.security.service.UserPrinciple;
import com.fourmen.vipstay.service.CommentService;
import com.fourmen.vipstay.service.HouseService;
import com.fourmen.vipstay.service.OrderHouseService;
import com.fourmen.vipstay.service.RateService;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private RateService rateService;

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

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> deleteOrderHouse(@PathVariable Long id) {
        this.orderHouseService.deleteOrderHouse(id);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Hủy đơn hàng thành công", null),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{houseId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> listCommentsbyHouseId(@PathVariable Long houseId) {
        List<Comment> comments = this.commentService.findAllByHouseId(houseId);

        if (comments.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list comment that was booked by guest", comments),
                HttpStatus.OK);
    }

    @PostMapping("/comments")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> createComment(@RequestBody Comment comment) {
        this.commentService.createComment(comment);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Post a new commnent successfully", null),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/rates/{houseId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> listRatesbyHouseId(@PathVariable Long houseId) {
        List<Rate> rates = this.rateService.findAllByHouseId(houseId);

        if (rates.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list comment that was booked by guest", rates),
                HttpStatus.OK);
    }

    @PostMapping("/rates")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> createRate(@RequestBody Rate rate) {
        if (this.rateService.existsRateByUserId(rate.getUser().getId())){
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(true, "Bạn chỉ được đánh giá một lần", null),
                    HttpStatus.CREATED);
        }
        this.rateService.createRate(rate);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Đánh giá thành công", null),
                HttpStatus.CREATED);
    }
}

