package com.qa.system.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.qa.system.dao.*;
import com.qa.system.entity.Admin;
import com.qa.system.entity.Register;
import com.qa.system.entity.User;
import com.qa.system.service.AnswerService;
import com.qa.system.service.QuestionService;
import com.qa.system.service.UserService;
import com.qa.system.utils.AliyunSmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    RegisterDao registerDao;
    QuestionDao questionDao;
    AnswerDao answerDao;
    AdminDao adminDao;
    QuestionService questionService;
    AnswerService answerService;

    /**
    * @Author XuFengrui
    * @Description 查询所有用户
    * @Date 16:41 2020/3/29
    * @Param []
    * @return java.util.List<com.qa.system.entity.User>
    **/
    @Override
    public List<User> findAllUser() {

        return userDao.findAllUser();
    }

    /**
    * @Author XuFengrui
    * @Description 根据号码查询用户
    * @Date 16:41 2020/3/29
    * @Param [phone]
    * @return com.qa.system.entity.User
    **/
    @Override
    public User findUserByPhone(String phone) {

        return userDao.findUserByPhone(phone);
    }

    /**
    * @Author XuFengrui
    * @Description 更改用户信息
    * @Date 16:41 2020/3/29
    * @Param [phone, user]
    * @return int
    **/
    @Override
    public int updateUser(User user) {
        if(userDao.isUserExist(user.getPhone())){
            return userDao.updateUser(user);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 新增用户信息
    * @Date 16:41 2020/3/29
    * @Param [phone, user]
    * @return int
    **/
    @Override
    public int addUser(User user) {
        if(!userDao.isUserExist(user.getPhone())){
            user.setShield(1);
            return userDao.addUser(user);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 删除用户信息
    * @Date 16:41 2020/3/29
    * @Param [phone]
    * @return int
    **/
    @Override
    public int deleteUserByPhone(String phone) {
        if(userDao.isUserExist(phone)){
            return userDao.deleteUserByPhone(phone);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 用户根据用户名和密码登录
    * @Date 16:41 2020/3/29
    * @Param [user]
    * @return int
    **/
    @Override
    public int loginUserByPassword(User user) {
        if(!userDao.isUserExist(user.getPhone())){
            return -1;
        }else if(userDao.findUserByPhone(user.getPhone()).getPassword().equals(user.getPassword())){
            return 1;
        }else{
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 用户根据手机号码和验证码登录
    * @Date 8:54 2020/4/2
    * @Param [phone]
    * @return int
    **/
    @Override
    public String loginUserByAuthCode(String phone) throws ClientException {
        return AliyunSmsUtils.verificationCode(phone);
    }

    /**
    * @Author XuFengrui
    * @Description 注册用户
    * @Date 16:41 2020/3/29
    * @Param [register]
    * @return int
    **/
    @Override
    public int registerUser(Register register) {
        if (registerDao.isRegisterExist(register.getPhone())) {
            User user = new User();
            user.setPhone(register.getPhone());
            user.setName(register.getName());
            user.setPassword(register.getPassword());
            user.setSex(register.getSex());
            user.setAge(register.getAge());
            return addUser(user);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 拉黑用户
    * @Date 16:41 2020/3/29
    * @Param [user]
    * @return int
    **/
    @Override
    public int blacklistUser(User user) {
        if (user.getShield() == 1) {
            user.setShield(0);
            questionService.blacklistQuestions(questionDao.findQuestionsByUserName(user.getName()));
            answerService.blacklistAnswers(answerDao.findAnswersByUserPhone(user.getPhone()));
            return 1;
        }else {
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 取消用户拉黑
    * @Date 16:41 2020/3/29
    * @Param [user]
    * @return int
    **/
    @Override
    public int whitelistUser(User user) {
        if (user.getShield() == 0) {
            user.setShield(1);
            questionService.whitelistQuestions(questionDao.findQuestionsByUserName(user.getName()));
            answerService.whitelistAnswers(answerDao.findAnswersByUserPhone(user.getPhone()));
            return 1;
        }else {
            return 0;
        }
    }

}
