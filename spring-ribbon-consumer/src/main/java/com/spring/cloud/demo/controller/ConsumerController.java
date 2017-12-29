package com.spring.cloud.demo.controller;

import com.spring.cloud.demo.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BoSongsh
 * @create 2017-12-15 11:31
 **/
@RestController
public class ConsumerController {

    @Autowired
    IHelloService helloService;

    /**
     * 不指定method的请求方式，spring对post和get都支持
     * 如果指定了则只支持指定的请求方式
     *
     * @return
     */
    @RequestMapping(value = "/helloConsumer", method = RequestMethod.GET)
    public String helloConsumer() {
        return helloService.helloService();
    }
}
