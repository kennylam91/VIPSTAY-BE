package com.fourmen.vipstay;

import com.fourmen.vipstay.service.HouseService;
import com.fourmen.vipstay.service.UserService;
import com.fourmen.vipstay.service.impl.HouseServiceImpl;
import com.fourmen.vipstay.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VipstayApplication {

    public static void main(String[] args) {
        SpringApplication.run(VipstayApplication.class, args);
    }
    @Bean
    public HouseService houseService(){
        return new HouseServiceImpl();
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }
}
