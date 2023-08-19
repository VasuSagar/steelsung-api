package com.vasu.steelsungapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class SteelsungApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteelsungApiApplication.class, args);
    }

}
