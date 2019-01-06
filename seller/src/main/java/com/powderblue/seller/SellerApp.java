package com.powderblue.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author lkw
 * 销售端启动类
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class SellerApp {
    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }
}
/*
Cannot determine embedded database driver class for database type NONE

If you want an embedded database please put a supported one on the classpath.
If you have database settings to be loaded from a particular profile you may need to active it (no profiles are currently active).

 */