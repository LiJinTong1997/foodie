package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.StuService;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.JsonResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录",tags = "用户注册登录的相关接口")
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在",notes = "用户名是否存在",httpMethod = "GET")
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

    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public JsonResult register(@RequestBody UserBO userBO,HttpServletRequest request, HttpServletResponse response){
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
        Users result = userService.createUser(userBO);

        result = setNullProperty(result);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(result),true);

        //请求成功
        return JsonResult.ok();
    }

    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public JsonResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        System.out.println(username);
        System.out.println(password);
        //1.判断用户名 密码不为空
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return JsonResult.errorMsg("用户名或密码不能为空");
        }
        //2.实现登录
        Users result = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if(result == null){
            return JsonResult.errorMsg("用户名或密码错误");
        }

        result = setNullProperty(result);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(result),true);
        //请求成功
        return JsonResult.ok(result);
    }

    @ApiOperation(value = "用户注销",notes = "用户注销",httpMethod = "POST")
    @PostMapping("/logout")
    public JsonResult logout(@RequestParam String userId,HttpServletRequest request, HttpServletResponse response){
        //清除用户相关信息cookie
        CookieUtils.deleteCookie(request,response,"user");
        // TODO  用户退出登录需要清空购物车

        // TODO  分布式会话中，需要清除用户数据
        return JsonResult.ok();
    }














    public Users setNullProperty(Users result){
        result.setPassword(null);
        result.setMobile(null);
        result.setEmail(null);
        result.setUpdatedTime(null);
        result.setCreatedTime(null);

        return result;
    }


}
