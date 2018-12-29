package com.powderblue.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author powderblue
 * 管理端启动类
 *
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.powderblue.entity"})
public class ManagerApp {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class);
    }
}
