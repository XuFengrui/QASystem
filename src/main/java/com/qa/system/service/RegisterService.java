package com.qa.system.service;

import com.qa.system.entity.Register;

import java.util.List;

/**
 * @ClassName RegisterService
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
public interface RegisterService {

    public List<Register> findAllRegister();
    public Register findRegisterByPhone(String phone);
    public int updateRegister(Register register);
    public int addRegister(Register register);
    public int deleteRegisterByPhone(String phone);
    public int applyRegister(String phone,Register register);

}
