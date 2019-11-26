package com.imooc.controller;

import com.imooc.pojo.bo.UserBO;
import com.imooc.service.StuService;
import com.imooc.service.UserService;
import com.imooc.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public JsonResult usernameIsExist(@RequestParam String username){
        //判断入参是否为空
        if(StringUtils.isBlank(username)){
            return JsonResult.errorMsg("用户名不能为空");
        }

        //判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist){
            return JsonResult.errorMsg("用户名已存在");
        }

        //请求成功
        return JsonResult.ok();
    }

    @PostMapping("/register")
    public JsonResult register(@RequestBody UserBO userBO){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        //0.判断用户名 密码不为空
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)){
            return JsonResult.errorMsg("用户名或密码不能为空");
        }
        //1.查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist){
            return JsonResult.errorMsg("用户名已存在");
        }
        //2.密码长度不能小于6位
        if(password.length()<6){
            return JsonResult.errorMsg("密码长度不能小于6位");
        }
        //3.查询两次密码是否一致
        if(!password.equals(confirmPassword)){
            return JsonResult.errorMsg("两次密码输入不一致");
        }
        //4.实现注册
        userService.createUser(userBO);
        //请求成功
        return JsonResult.ok();
    }
}
