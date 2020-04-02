package com.qa.system.dao;

import com.qa.system.entity.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RegisterDao
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Repository
public class RegisterDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
    * @Author XuFengrui
    * @Description 根据号码查询申请注册的用户
    * @Date 16:26 2020/3/29
    * @Param [phone]
    * @return com.qa.system.entity.Register
    **/
    public Register findRegisterByPhone(String phone){

        List<Register> registerList = jdbcTemplate.query("select * from register where phone = ?",new BeanPropertyRowMapper<>(Register.class),phone);
        if(registerList.size()>0){
            return registerList.get(0);
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 查询号码是否已经申请注册，false表示还未申请，true表示已经申请
    * @Date 16:26 2020/3/29
    * @Param [phone]
    * @return boolean
    **/
    public boolean isRegisterExist(String phone){
        return findRegisterByPhone(phone) != null;
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已经申请注册的用户
    * @Date 16:26 2020/3/29
    * @Param []
    * @return java.util.List<com.qa.system.entity.Register>
    **/
    public List<Register> findAllRegister(){

        List<Register> registerList = jdbcTemplate.query("select * from register",new Object[]{},new BeanPropertyRowMapper<>(Register.class));
        return registerList;
    }

    /**
    * @Author XuFengrui
    * @Description 更改用户注册信息
    * @Date 16:27 2020/3/29
    * @Param [phone, register]
    * @return int
    **/
    public int updateRegister(Register register){

        int count = jdbcTemplate.update("update register set name = ?,password = ?,sex = ?,age = ? where phone = ?",register.getName(),register.getPassword(),register.getSex(),register.getAge(),register.getPhone());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 添加申请注册的用户信息
    * @Date 16:27 2020/3/29
    * @Param [phone, register]
    * @return int
    **/
    public int addRegister(Register register){

        int count = jdbcTemplate.update("insert into register(phone,name,password,sex,age) values(?,?,?,?,?)",register.getPhone(),register.getName(),register.getPassword(),register.getSex(),register.getAge());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 删除申请注册的用户信息
    * @Date 16:27 2020/3/29
    * @Param [phone]
    * @return int
    **/
    public int deleteRegisterByPhone(String phone){

        int count = jdbcTemplate.update("delete from register where phone = ?",phone);
        return count;
    }
}
