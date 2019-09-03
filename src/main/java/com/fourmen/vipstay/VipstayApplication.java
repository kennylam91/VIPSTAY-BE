package com.fourmen.vipstay;

import com.fourmen.vipstay.service.StatusHouseService;
import com.fourmen.vipstay.service.impl.StatusHouseServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VipstayApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VipstayApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(VipstayApplication.class);
    }

    @Bean
    public StatusHouseService statusHouseService(){
        return  new StatusHouseServiceImpl();
    }
}
