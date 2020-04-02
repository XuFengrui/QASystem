package com.qa.system.controller;

import com.aliyuncs.exceptions.ClientException;
import com.qa.system.entity.User;
import com.qa.system.result.Result;
import com.qa.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
    * @Author XuFengrui
    * @Description 用户根据用户名和密码登录
    * @Date 20:23 2020/4/2
    * @Param [user] 姓名和密码
    * @return int -1表示用户不存在，1表示登录成功，0表示用户名或密码错误
    **/
    @CrossOrigin
    @PostMapping(value = "/user/loginPwd")
    @ResponseBody
    public Result userLoginByPwd(@RequestBody User user) {
        if (userService.loginUserByPassword(user) == 0) {
            System.out.println("test");
            return new Result(400);
        } else if (userService.loginUserByPassword(user) == 1) {
            System.out.println("1");
            return new Result(200);
        } else {
            System.out.println("2");
            return new Result(300);
        }
//        return userService.loginUserByPassword(user);
//            return new Result(400);
    }

    /**
    * @Author XuFengrui
    * @Description 用户根据手机号码和验证码登录，发送成功返回验证码
    * @Date 20:27 2020/4/2
    * @Param [phone] 电话号码
    * @return java.lang.String 短信发送成功则返回验证码（String）；发送失败返回空（"")
    **/
    @CrossOrigin
    @PostMapping(value = "/user/loginCode")
    @ResponseBody
    public String userLoginByCode(@RequestBody String phone) throws ClientException {
        return userService.loginUserByAuthCode(phone);
    }

}
