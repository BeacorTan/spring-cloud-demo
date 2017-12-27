package com.spring.boot.demo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * springboot启动类
 *
 * @author BoSongsh
 * @create 2017-12-13 18:12
 **/
@org.springframework.boot.autoconfigure.SpringBootApplication
/**
 * 扫描指定匹配的包。没有使用该注解，使用@controller，@service是无效的，
 * 除非和*application类放在同包【同目录】下
 */
@ComponentScan(value = "com.spring.boot.demo.*")
// 此注解表示该服务为eureka客户端
@EnableDiscoveryClient
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
