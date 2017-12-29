package com.spring.cloud.demo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon负载均衡
 * 通过@EnableDiscoveryClient注解让该应用注册为Eureka 客户端应用， 以获得服务发现的能力。
 * 同时， 在该主类中创建 RestTemplate 的 Spring Bean 实例，并通过 @LoadBalanced 注解开启客户端负载均衡。
 *
 * @author BoSongsh
 * @create 2017-12-15 11:28
 **/
@EnableDiscoveryClient
// 开启断路器功能，也可以使用 Spring Cloud 应用中的@SpringCloudApplication注解
@EnableCircuitBreaker
@SpringBootApplication
@ComponentScan(value = "com.spring.cloud.demo.*")
public class RibbonConsumerApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(RibbonConsumerApplication.class, args);
    }
}
