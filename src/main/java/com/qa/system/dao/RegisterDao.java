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
    * @Description 根据用户名查询申请注册的用户
    * @Date 16:57 2020/6/9
    * @Param [phone]
    * @return com.qa.system.entity.Register
    **/
    public Register findRegisterByName(String name){

        List<Register> registerList = jdbcTemplate.query("select * from register where name = ?",new BeanPropertyRowMapper<>(Register.class),name);
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
    public boolean isRegisterExistByPhone(String phone){
        return findRegisterByPhone(phone) != null;
    }

    /**
    * @Author XuFengrui
    * @Description 查询该用户名是否已经申请注册，false表示还未申请，true表示已经申请
    * @Date 17:00 2020/6/9
    * @Param [name]
    * @return boolean
    **/
    public boolean isRegisterExistByName(String name){
        return findRegisterByName(name) != null;
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

        int count = jdbcTemplate.update("update register set name = ?,password = ?,sex = ?,age = ?,mail = ? where phone = ?",register.getName(),register.getPassword(),register.getSex(),register.getAge(),register.getMail(),register.getPhone());
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

        int count = jdbcTemplate.update("insert into register(phone,name,password,sex,age,mail) values(?,?,?,?,?,?)",register.getPhone(),register.getName(),register.getPassword(),register.getSex(),register.getAge(),register.getMail());
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

    /**
    * @Author XuFengrui
    * @Description 模糊查询注册用户
    * @Date 11:35 2020/4/24
    * @Param [strWord]
    * @return java.util.List<com.qa.system.entity.Register>
    **/
    public List<Register> findRegistersByKeyword(String strWord) {
        List<Register> registerList = jdbcTemplate.query("select * from register where phone like ? or name like ? or mail = ?",new Object[]{"%"+strWord+"%","%"+strWord+"%","%"+strWord+"%"},new BeanPropertyRowMapper<>(Register.class));
        return registerList;
    }
}
