package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.*;
import com.fourmen.vipstay.security.service.UserPrinciple;
import com.fourmen.vipstay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private UserService userService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN') or hasRole('HOST')")
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

    @RequestMapping(value = "/orders/{id}/house-of-order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> getHouseOfOrder(@PathVariable long id) {
        OrderHouse orderHouse = this.orderHouseService.findById(id);

        if (orderHouse == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        House house=orderHouse.getHouse();

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get the house of order", house),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> getDetailOrder(@PathVariable Long id) {
        OrderHouse orderHouse = this.orderHouseService.findById(id);

        if (orderHouse == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get detail order that was booked by guest", orderHouse),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> deleteOrderHouse(@PathVariable Long id) {
        OrderHouse orderHouse = this.orderHouseService.findById(id);
        Date checkin = orderHouse.getCheckin();
        Date now = new Date();
        int day = 86400 * 1000;
        double nowToCheckinByDay = (double) (checkin.getTime() - now.getTime()) / day;
        if (nowToCheckinByDay < 1.0) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Không thể hủy đơn hàng", null),
                    HttpStatus.OK);
        }
        orderHouse.setStatusOrder(StatusOrder.CANCELED);
        this.orderHouseService.updateOrderHouse(orderHouse);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Hủy đơn hàng thành công", null),
                HttpStatus.OK);
    }

    @PostMapping("/comments")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> createComment(@RequestBody Comment comment) {
        comment.setUser(this.userService.findById(getCurrentUser().getId()));
        this.commentService.createComment(comment);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Phản hỏi dịch vụ thành công", null),
                HttpStatus.CREATED);
    }

    @PostMapping("/rates")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> createRate(@RequestBody Rate rate) {
        rate.setUser(this.userService.findById(getCurrentUser().getId()));
        if (this.rateService.existsRateByUserIdAndHouseId(rate.getUser().getId(), rate.getHouse().getId() ) ){
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(true, "Bạn chỉ được đánh giá một lần", null),
                    HttpStatus.CREATED);
        }
        this.rateService.createRate(rate);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Đánh giá thành công", null),
                HttpStatus.CREATED);
    }

    @GetMapping("/rates/{houseId}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> getRateByUserIdAndHouseId(@PathVariable Long houseId){
        Rate rate = this.rateService.findByUserIdAndHouseId(getCurrentUser().getId(), houseId);
        if(rate == null){
            return new ResponseEntity<StandardResponse>(new StandardResponse(false, "Bạn chưa đánh giá cho dịch vụ này!", null), HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<StandardResponse>(new StandardResponse(true, "Lấy đánh gía thành công", rate), HttpStatus.OK);
    }
}

