package com.qa.system.service;

import com.aliyuncs.exceptions.ClientException;
import com.qa.system.entity.Admin;
import com.qa.system.entity.Register;
import com.qa.system.entity.User;

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
    public User findUserByPhone(String phone);
    public User findUserByName(String name);
    public String queryPhoneByName(String name);
    public int updateUser(User user);
    public int addUser(User user);
    public int deleteUserByPhone(String phone);
    public int loginUserByPassword(User user);
    public String loginUserByAuthCode(String phone) throws ClientException;
    public int registerUser(Register register) throws ClientException;
    public int blacklistUser(User user);
    public int whitelistUser(User user);
    public boolean saveIcon(User user);


}
