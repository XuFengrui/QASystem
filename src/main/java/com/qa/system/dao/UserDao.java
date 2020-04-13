package com.qa.system.dao;

import com.qa.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserDao
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
    * @Author XuFengrui
    * @Description 根据号码查找用户，存在则返回该用户类，不存在返回null
    * @Date 16:25 2020/3/29
    * @Param [phone]
    * @return com.qa.system.entity.User
    **/
    public User findUserByPhone(String phone){

        List<User> userList = jdbcTemplate.query("select * from user where phone = ?",new BeanPropertyRowMapper<>(User.class),phone);
        if(userList.size()>0){
            return userList.get(0);
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 根据号码查找用户是否存在，false表示不存在，true表示存在
    * @Date 16:25 2020/3/29
    * @Param [phone]
    * @return boolean
    **/
    public boolean isUserExistByPhone(String phone){
        return findUserByPhone(phone) != null;
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名查找用户是否存在，false表示不存在，true表示存在
    * @Date 22:41 2020/4/2
    * @Param [name]
    * @return boolean
    **/
    public boolean isUserExistByName(String name){
        return findUserByName(name) != null;
    }

    /**
    * @Author XuFengrui
    * @Description 查找所有用户
    * @Date 16:26 2020/3/29
    * @Param []
    * @return java.util.List<com.qa.system.entity.User>
    **/
    public List<User> findAllUser(){

        List<User> userList = jdbcTemplate.query("select * from user",new Object[]{},new BeanPropertyRowMapper<>(User.class));
        if(userList.size()>0){
            return userList;
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 更改用户信息
    * @Date 16:26 2020/3/29
    * @Param [phone, user]
    * @return int
    **/
    public int updateUser(User user){

        int count = jdbcTemplate.update("update user set name = ?,password = ?,sex = ?,age = ?,shield = ? where phone = ?",user.getName(),user.getPassword(),user.getSex(),user.getAge(),user.getShield(),user.getPhone());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 增加用户
    * @Date 16:26 2020/3/29
    * @Param [phone, user]
    * @return int
    **/
    public int addUser(User user){

        int count = jdbcTemplate.update("insert into user(phone,name,password,sex,age,shield) values(?,?,?,?,?,?)",user.getPhone(),user.getName(),user.getPassword(),user.getSex(),user.getAge(),user.getShield());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 删除用户
    * @Date 16:26 2020/3/29
    * @Param [phone]
    * @return int
    **/
    public int deleteUserByPhone(String phone){

        int count = jdbcTemplate.update("delete from user where phone = ?",phone);
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 查询用户是否被拉黑，0表示拉黑，1表示正常
    * @Date 16:26 2020/3/29
    * @Param [phone]
    * @return int
    **/
    public int isUserBlacklist(String phone) {
        return findUserByPhone(phone).getShield();
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名查找用户
    * @Date 14:26 2020/3/30
    * @Param [name]
    * @return com.qa.system.entity.User
    **/
    public User findUserByName(String name) {
        List<User> userList = jdbcTemplate.query("select * from user where name = ?",new BeanPropertyRowMapper<>(User.class),name);
        if(userList.size()>0){
            return userList.get(0);
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有未被拉黑的用户
    * @Date 16:32 2020/3/30
    * @Param []
    * @return java.util.List<com.qa.system.entity.User>
    **/
    public List<User> findAllWhiteUsers() {
        List<User> userList = jdbcTemplate.query("select * from user where shield = ?",new BeanPropertyRowMapper<>(User.class),1);
        return userList;
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已被拉黑的用户
    * @Date 16:32 2020/3/30
    * @Param []
    * @return java.util.List<com.qa.system.entity.User>
    **/
    public List<User> findAllBlackUsers() {
        List<User> userList = jdbcTemplate.query("select * from user where shield = ?",new BeanPropertyRowMapper<>(User.class),0);
        return userList;
    }

    /**
    * @Author XuFengrui
    * @Description 改变用户的拉黑状态，失败返回0，成功返回1
    * @Date 0:06 2020/4/3
    * @Param [user]
    * @return int
    **/
    public int userShield(User user) {
        int count = jdbcTemplate.update("update user set shield = ? where phone = ?",user.getShield(),user.getPhone());
        return count;
    }


}
