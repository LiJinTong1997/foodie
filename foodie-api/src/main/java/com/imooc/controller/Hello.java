package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {
    @GetMapping("/hello")
    public Object hello(){
        return "hello Wrold";
    }
}
