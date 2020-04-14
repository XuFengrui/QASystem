package com.qa.system.dao;

import com.qa.system.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName AdminDao
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Repository
public class AdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
    * @Author XuFengrui
    * @Description 根据管理员名称查询管理员
    * @Date 11:19 2020/3/31
    * @Param [name]
    * @return com.qa.system.entity.Admin
    **/
    public Admin findAdminByName(String name){

        List<Admin> adminList = jdbcTemplate.query("select * from admin where name = ?",new BeanPropertyRowMapper<>(Admin.class),name);
        if(adminList.size()>0){
            return adminList.get(0);
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 更改管理员信息
    * @Date 11:20 2020/3/31
    * @Param [user]
    * @return int
    **/
    public int updateAdmin(Admin admin) {
        int count = jdbcTemplate.update("update admin set password = ?,status = ? where name = ?",admin.getPassword(),admin.getStatus(),admin.getName());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 新增管理员
    * @Date 11:26 2020/3/31
    * @Param [admin]
    * @return int
    **/
    public int addAdmin(Admin admin) {
        int count = jdbcTemplate.update("insert into admin(name,password,status) values(?,?,?)", admin.getName(),admin.getPassword(),admin.getStatus());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 删除管理员
    * @Date 11:26 2020/3/31
    * @Param [name]
    * @return int
    **/
    public int deleteAdminByName(String name) {
        int count = jdbcTemplate.update("delete from admin where name = ?",name);
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 根据管理员名称查询该管理员是否存在
    * @Date 11:37 2020/3/31
    * @Param [name]
    * @return boolean
    **/
    public boolean isAdminExist(String name) {
        return findAdminByName(name) != null;
    }

    /**
    * @Author XuFengrui
    * @Description 根据管理者账号改变登录状态
    * @Date 11:11 2020/4/14
    * @Param [admin]
    * @return int
    **/
    public int updateAdminStatus(Admin admin) {
        int count = jdbcTemplate.update("update admin set status = ? where name = ?",admin.getStatus(),admin.getName());
        return count;
    }


}
