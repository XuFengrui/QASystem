package com.qa.system.service.impl;

import com.qa.system.dao.RegisterDao;
import com.qa.system.dao.UserDao;
import com.qa.system.entity.Register;
import com.qa.system.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RegisterServiceImpl
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    RegisterDao registerDao;

    @Autowired
    UserDao userDao;

    /**
    * @Author XuFengrui
    * @Description 查询所有申请注册的用户
    * @Date 16:40 2020/3/29
    * @Param []
    * @return java.util.List<com.qa.system.entity.Register>
    **/
    @Override
    public List<Register> findAllRegister() {
        return registerDao.findAllRegister();
    }

    /**
    * @Author XuFengrui
    * @Description 根据号码查询申请注册的用户
    * @Date 16:40 2020/3/29
    * @Param [phone]
    * @return com.qa.system.entity.Register
    **/
    @Override
    public Register findRegisterByPhone(String phone) {
        return registerDao.findRegisterByPhone(phone);
    }

    /**
    * @Author XuFengrui
    * @Description 更改申请注册用户的信息,-1表示注册表中无该用户信息
    * @Date 16:40 2020/3/29
    * @Param [phone, register]
    * @return int
    **/
    @Override
    public int updateRegister(Register register) {
        if (registerDao.isRegisterExist(register.getPhone())) {
            return registerDao.updateRegister(register);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 新增申请注册用户的信息，-1表示该用户已申请注册
    * @Date 16:40 2020/3/29
    * @Param [phone, register]
    * @return int
    **/
    @Override
    public int addRegister(Register register) {
        if(!registerDao.isRegisterExist(register.getPhone())){
            return registerDao.addRegister(register);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 删除申请注册用户的信息，-1表示注册表中无该用户信息
    * @Date 16:40 2020/3/29
    * @Param [phone]
    * @return int
    **/
    @Override
    public int deleteRegisterByPhone(String phone) {
        if(registerDao.isRegisterExist(phone)){
            return registerDao.deleteRegisterByPhone(phone);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 用户申请注册，若注册表里没有该号码绑定的信息则直接注册，若注册表里有但用户表里没有则更改注册信息，若用户表里存在则注册失败并返回-1
    * @Date 16:41 2020/3/29
    * @Param [phone, register]
    * @return int
    **/
    @Override
    public int applyRegister(Register register) {
        if (!registerDao.isRegisterExist(register.getPhone())) {
            return registerDao.addRegister(register);
        } else if (!userDao.isUserExistByPhone(register.getPhone())) {
            return registerDao.updateRegister(register);
        } else {
            return -1;
        }
    }

    @Override
    public List<Register> findNotPassedRegister() {
        List<Register> registerList = null;
        List<Register> registers = registerDao.findAllRegister();
        for (int i = registers.size(); i > 0; i--) {
            if (!userDao.isUserExistByPhone(registers.get(i - 1).getPhone())) {
                registerList.add(registers.get(i - 1));
            }
        }
        return registerList;
    }
}
