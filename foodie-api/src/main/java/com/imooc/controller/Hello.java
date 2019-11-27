package com.imooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class Hello {

    final static Logger logger= LoggerFactory.getLogger(Hello.class);
    @GetMapping("/hello")
    public Object hello(){
        logger.info("hello");
        logger.debug("hello");
        logger.warn("hello");
        logger.error("hello");
        return "hello Wrold";
    }
}
