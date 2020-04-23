package com.qa.system.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.qa.system.dao.*;
import com.qa.system.entity.Admin;
import com.qa.system.entity.Register;
import com.qa.system.entity.User;
import com.qa.system.service.AnswerService;
import com.qa.system.service.QuestionService;
import com.qa.system.service.UserService;
import com.qa.system.utils.AliyunSmsUtils;
import com.qa.system.utils.Base64Utils;
import com.qa.system.utils.SendMailUtils;
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

    @Autowired
    RegisterDao registerDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    AnswerDao answerDao;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    SendMailUtils sendMailUtils;

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
    * @Description 根据号码查询用户，存在则返回该用户类，不存在返回null
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
    * @Description 根据用户名查询用户，存在则返回该用户类，不存在返回null
    * @Date 11:17 2020/4/14
    * @Param [name]
    * @return com.qa.system.entity.User
    **/
    @Override
    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名返回用户号码（主键）
    * @Date 8:13 2020/4/21
    * @Param [name]
    * @return java.lang.String
    **/
    @Override
    public String queryPhoneByName(String name) {
        if (userDao.findUserByName(name) != null) {
            return userDao.findUserByName(name).getPhone();
        } else {
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 更改用户信息,-1表示该用户不存在
    * @Date 16:41 2020/3/29
    * @Param [phone, user]
    * @return int
    **/
    @Override
    public int updateUser(User user) {
        if(userDao.isUserExistByPhone(user.getPhone())){
            return userDao.updateUser(user);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 新增用户信息，-1表示用户已存在
    * @Date 16:41 2020/3/29
    * @Param [phone, user]
    * @return int
    **/
    @Override
    public int addUser(User user) {
        if(!userDao.isUserExistByPhone(user.getPhone())){
            user.setShield(1);
            return userDao.addUser(user);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 删除用户信息，-1表示用户不存在
    * @Date 16:41 2020/3/29
    * @Param [phone]
    * @return int
    **/
    @Override
    public int deleteUserByPhone(String phone) {
        if(userDao.isUserExistByPhone(phone)){
            return userDao.deleteUserByPhone(phone);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 用户根据用户名和密码登录，-1表示用户不存在，1表示登录成功，0表示用户名或密码错误
    * @Date 16:41 2020/3/29
    * @Param [user]
    * @return int
    **/
    @Override
    public int loginUserByPassword(User user) {
        if(!userDao.isUserExistByName(user.getName())){
            return -1;
        }else if(userDao.findUserByName(user.getName()).getPassword().equals(user.getPassword())){
            return 1;
        }else{
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 用户根据手机号码和验证码登录，发送成功返回验证码,失败返回空，非用户号码返回null
    * @Date 8:54 2020/4/2
    * @Param [phone]
    * @return int
    **/
    @Override
    public String loginUserByAuthCode(String phone) throws ClientException {
        if (userDao.isUserExistByPhone(phone)) {
            return AliyunSmsUtils.verificationCode(phone);
        } else {
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 注册用户，-1表示注册表中没有该用户信息
    * @Date 16:41 2020/3/29
    * @Param [register]
    * @return int
    **/
    @Override
    public int registerUser(Register register) throws ClientException {
        if (registerDao.isRegisterExist(register.getPhone())) {
            User user = new User();
            user.setPhone(register.getPhone());
            user.setName(register.getName());
            user.setPassword(register.getPassword());
            user.setSex(register.getSex());
            user.setAge(register.getAge());
            sendMailUtils.sendSuccessRegisterMail(register.getMail(),register.getName());
            return addUser(user);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 拉黑用户，0表示用户已被拉黑，1表示拉黑成功,-1表示用户不存在
    * @Date 16:41 2020/3/29
    * @Param [user]
    * @return int
    **/
    @Override
    public int blacklistUser(User user) {
        if (userDao.isUserExistByPhone(user.getPhone())) {
            if (userDao.findUserByPhone(user.getPhone()).getShield() == 1) {
                user.setShield(0);
                userDao.userShield(user);
                questionService.blacklistQuestions(questionDao.findQuestionsByUserName(user.getName()));
                answerService.blacklistAnswers(answerDao.findAnswersByUserName(user.getName()));
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }

    }

    /**
    * @Author XuFengrui
    * @Description 取消用户拉黑,0表示该用户未被拉黑，1表示取消拉黑成功，-1表示用户不存在
    * @Date 16:41 2020/3/29
    * @Param [user]
    * @return int
    **/
    @Override
    public int whitelistUser(User user) {
        if (userDao.isUserExistByPhone(user.getPhone())) {
            if (userDao.findUserByPhone(user.getPhone()).getShield() == 0) {
                user.setShield(1);
                userDao.userShield(user);
                questionService.whitelistQuestions(questionDao.findQuestionsByUserName(user.getName()));
                answerService.whitelistAnswers(answerDao.findAnswersByUserName(user.getName()));
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 上传用户头像
    * @Date 11:57 2020/4/22
    * @Param [user]
    * @return boolean
    **/
    @Override
    public boolean saveIcon(User user) {
        try {
            String str = user.getIcon().substring((user.getIcon().indexOf(",") + 1));
            String imgPath = "D:/icon/" + System.currentTimeMillis() + ".png";
            Base64Utils.generateImage(str, imgPath);
            user.setIcon(imgPath);
            if (userDao.updateUserIcon(user) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
