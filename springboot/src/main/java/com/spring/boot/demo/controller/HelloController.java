package com.spring.boot.demo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BoSongsh
 * @create 2017-12-13 18:20
 **/
@RestController
public class HelloController {

    private final Logger logger = Logger.getLogger(HelloController.class);


    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping("/hello")
    public String hello() {

        discoveryClient.getServices();
        discoveryClient.getInstances("111");
        return "Hello World";
    }

    @RequestMapping("/index")
    public ModelMap index(ModelMap modelMap) {
        return modelMap;
    }
}
