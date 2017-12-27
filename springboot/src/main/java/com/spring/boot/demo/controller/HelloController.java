package com.spring.boot.demo.controller;

import com.swetake.util.Qrcode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * @author BoSongsh
 * @create 2017-12-13 18:20
 **/
@RestController
public class HelloController {

    private final Logger logger = Logger.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * url=http:localhost:port/hello
     * springboot demo接口
     * @return
     */
    @RequestMapping("/hello")
    public String hello() {

        discoveryClient.getServices();
        discoveryClient.getInstances("111");
        return "Hello World";
    }

    /**
     * url=http:localhost:port/index.html
     */
    @RequestMapping("/index")
    public ModelMap index(ModelMap modelMap) {
        modelMap.addAttribute("path", "0000000000000000");
        return modelMap;
    }
}
