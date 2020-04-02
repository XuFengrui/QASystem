package com.qa.system.controller;

import com.qa.system.entity.Admin;
import com.qa.system.result.Result;
import com.qa.system.service.AdminService;
import com.qa.system.service.UserService;
import com.qa.system.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qa.system.entity.User;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @CrossOrigin
    @PostMapping(value = "/login")
    @ResponseBody
    public Result loginUser(@RequestBody User user) {
        user.setPhone("888");
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
    }

    @CrossOrigin
    @PostMapping(value = "/add")
    @ResponseBody
    public Result loginAdmin(@RequestBody Admin admin) {
        System.out.println(admin.getName());
        System.out.println(admin.getPassword());
        System.out.println(admin.getStatus());
        if (adminService.loginAdmin(admin) == 1) {
            System.out.println("test");
            return new Result(200);
        } else if (adminService.loginAdmin(admin) == 0) {
            System.out.println("0");
            return new Result(400);
        } else {
            System.out.println("-1");
            return new Result(300);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/query")
    public List<User> queryUsers() {
        return userService.findAllUser();
    }


    public void out() {
        System.out.println("xxx");
    }
}


