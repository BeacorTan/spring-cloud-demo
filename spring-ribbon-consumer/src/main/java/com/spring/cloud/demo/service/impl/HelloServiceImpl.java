package com.spring.cloud.demo.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.hystrix.FallbackHandler;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.spring.cloud.demo.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.Map;

/**
 * @author BoSongsh
 * @create 2017-12-29 16:06
 **/
@Service
public class HelloServiceImpl implements IHelloService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @HystrixCommand(fallbackMethod = "helloFallback")
     * 断路由后回调helloFallback函数
     * @return
     */
    @HystrixCommand(fallbackMethod = "helloFallback")
    public String helloService() {
        return restTemplate.getForEntity("http://BOOT-DEMO/hello", String.class).getBody();
    }


    public String helloFallback() {
        return "errror";
    }


}
