package com.spring.cloud.demo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka demo
 *
 * @author BoSongsh
 * @create 2017-12-14 17:14
 **/
//@SpringCloudApplication
@SpringBootApplication
@EnableEurekaServer
public class SpringEurekaDemoApplication {
    public static void main(String[] args) {
        //SpringApplication.run(SpringEurekaDemoApplication.class, args);
        new SpringApplicationBuilder(SpringEurekaDemoApplication.class).web(true).run(args);
    }
}
