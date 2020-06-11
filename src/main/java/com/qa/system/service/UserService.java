package com.qa.system.service;

import com.aliyuncs.exceptions.ClientException;
import com.qa.system.entity.*;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @ClassName UserService
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
public interface UserService {

    public List<User> findAllUser();
    public List<User> findAllWhiteUsers();
    public List<User> findAllBlackUsers();
    public User findUserByPhone(String phone);
    public User findUserByName(String name);
    public String queryPhoneByName(String name);
    public int updateUser(User user);
    public int addUser(User user);
    public int deleteUserByPhone(String phone);
    public int loginUserByPassword(User user);
    public String loginUserByAuthCode(String phone) throws ClientException;
    public int registerUser(Register register) throws ClientException;
    public int registerRefuse(Register register) throws ClientException;
    public int blacklistUser(User user);
    public int whitelistUser(User user);
    public boolean saveIcon(User user);
    public String showIcon(User user);
    public BufferedImage decodeToImage(User user);
    public String getImageStr(User user);
    public List<User> findUsersByKeyword(String keyWord);
    public List<Message> findMessageByName(String name);
    public int countAvailableMessage(String name);
    public int updateMessageStatus(Message message);
    public int updatePassword(User user);
    public int messageType(Message message);
    public int updateUserPhoneByName(User user);
    public String sendCode(User user) throws ClientException;

}
