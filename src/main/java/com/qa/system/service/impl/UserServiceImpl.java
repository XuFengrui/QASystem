package com.qa.system.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.qa.system.dao.*;
import com.qa.system.entity.Message;
import com.qa.system.entity.Register;
import com.qa.system.entity.User;
import com.qa.system.service.AnswerService;
import com.qa.system.service.QuestionService;
import com.qa.system.service.UserService;
import com.qa.system.utils.AliyunSmsUtils;
import com.qa.system.utils.Base64Utils;
import com.qa.system.utils.SendMailUtils;
import com.qa.system.utils.Sha256Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
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
    MessageDao messageDao;

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
    * @Description 查询所有未拉黑用户
    * @Date 15:43 2020/6/2
    * @Param []
    * @return java.util.List<com.qa.system.entity.User>
    **/
    @Override
    public List<User> findAllWhiteUsers() {
        return userDao.findAllWhiteUsers();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已拉黑用户
    * @Date 15:43 2020/6/2
    * @Param []
    * @return java.util.List<com.qa.system.entity.User>
    **/
    @Override
    public List<User> findAllBlackUsers() {
        return userDao.findAllBlackUsers();
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
        }else if(userDao.findUserByName(user.getName()).getPassword().equals(Sha256Utils.getSHA256Str(user.getPassword()))){
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
            user.setPassword(Sha256Utils.getSHA256Str(register.getPassword()));
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
            User user1 = userDao.findUserByPhone(user.getPhone());
            if (user1.getShield() == 1) {
                user1.setShield(0);
                userDao.userShield(user1);
                questionService.blacklistQuestions(questionDao.findQuestionsByUserName(user1.getName()));
                answerService.blacklistAnswers(answerDao.findAnswersByUserName(user1.getName()));
                messageDao.userBlackMessage(user1.getName());
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
            User user1 = userDao.findUserByPhone(user.getPhone());
            if (user1.getShield() == 0) {
                user1.setShield(1);
                userDao.userShield(user1);
                questionService.whitelistQuestions(questionDao.findQuestionsByUserName(user1.getName()));
                answerService.whitelistAnswers(answerDao.findAnswersByUserName(user1.getName()));
                messageDao.userWhiteMessage(user1.getName());
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

    /**
    * @Author XuFengrui
    * @Description 查询用户头像
    * @Date 21:01 2020/4/23
    * @Param [user]
    * @return java.lang.String
    **/
    @Override
    public String showIcon(User user) {
        if (!userDao.isUserExistByPhone(user.getPhone())) {
            return null;
        } else {
            return userDao.findUserByPhone(user.getPhone()).getIcon();
        }
    }

    /**
    * @Author XuFengrui
    * @Description base编码转化为图片
    * @Date 10:27 2020/4/24
    * @Param [user]
    * @return java.awt.image.BufferedImage
    **/
    @Override
    public BufferedImage decodeToImage(User user) {
        if (!userDao.isUserExistByPhone(user.getPhone())) {
            return null;
        } else {
            return Base64Utils.decodeToImage(Base64Utils.getImageStr(userDao.findUserByPhone(user.getPhone()).getIcon()));
        }
    }

    /**
    * @Author XuFengrui
    * @Description 获取base64编码
    * @Date 11:08 2020/4/24
    * @Param [user]
    * @return java.lang.String
    **/
    @Override
    public String getImageStr(User user) {
        if (!userDao.isUserExistByPhone(user.getPhone())) {
            return null;
        } else {
            return Base64Utils.getImageStr(userDao.findUserByPhone(user.getPhone()).getIcon());
        }
    }

    /**
    * @Author XuFengrui
    * @Description 模糊查询用户
    * @Date 11:51 2020/4/24
    * @Param [keyWord]
    * @return java.util.List<com.qa.system.entity.User>
    **/
    @Override
    public List<User> findUsersByKeyword(String keyWord) {
        return userDao.findUsersByKeyword(keyWord);
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名查询该用户所接收的所有消息
    * @Date 15:20 2020/5/7
    * @Param [name]
    * @return java.util.List<com.qa.system.entity.Message>
    **/
    @Override
    public List<Message> findMessageByName(String name) {
        return messageDao.findMessageByName(name);
    }

    /**
    * @Author XuFengrui
    * @Description 查询用户未读消息的数量
    * @Date 19:01 2020/5/14
    * @Param [name]
    * @return int
    **/
    @Override
    public int countAvailableMessage(String name) {
        return messageDao.countAvailableByName(name);
    }

    /**
    * @Author XuFengrui
    * @Description 修改消息的已读属性
    * @Date 19:08 2020/5/14
    * @Param [message]
    * @return int
    **/
    @Override
    public int updateMessageStatus(Message message) {
        return messageDao.updateMessageStatus(message);
    }

    /**
    * @Author XuFengrui
    * @Description 修改用户密码
    * @Date 10:54 2020/5/28
    * @Param [user]
    * @return int 成功返回1，不成功返回0
    **/
    @Override
    public int updatePassword(User user) {
        return userDao.updatePassword(user);
    }
}
