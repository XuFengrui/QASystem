package com.qa.system.controller;

import com.qa.system.entity.User;
import com.qa.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<User> findAllUser(){
        return userService.findAllUser();
    }

    @ResponseBody
    @RequestMapping(value = "/helloWorld")
    public String helloWorld(){
        return "hello world";
    }

    @ResponseBody
    @RequestMapping(value = "/query")
    public User findUserByPhone(String phone){
        phone = "1";
        return userService.findUserByPhone(phone);
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public int updateUser(User user){
        user.setPhone("1"); ;
        user.setName("xxx");
        user.setPassword("123456");
        user.setSex(0);
        user.setAge(12);
        user.setShield(1);
        return userService.updateUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public int addUser(User user){
        user.setPhone("fff");
        user.setName("fff");
        user.setPassword("123456");
        user.setSex(0);
        user.setAge(50);
        user.setShield(0);
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public int deleteUserByPhone(String phone){
        phone = "3";
        return userService.deleteUserByPhone(phone);
    }

}
