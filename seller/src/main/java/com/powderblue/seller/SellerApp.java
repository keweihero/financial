package com.powderblue.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author lkw
 * 销售端启动类
 */
@SpringBootApplication
@EnableCaching
public class SellerApp {
    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }
}