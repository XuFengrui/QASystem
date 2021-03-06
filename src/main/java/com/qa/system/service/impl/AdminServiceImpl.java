package com.qa.system.service.impl;

import com.qa.system.dao.AdminDao;
import com.qa.system.entity.Admin;
import com.qa.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AdminServiceImpl
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/3/31
 * @Version 1.0
 **/
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    /**
    * @Author XuFengrui
    * @Description 根据管理员账号查询管理员
    * @Date 20:05 2020/3/31
    * @Param [name]
    * @return com.qa.system.entity.Admin
    **/
    @Override
    public Admin findAdminByName(String name) {
        return adminDao.findAdminByName(name);
    }

    /**
    * @Author XuFengrui
    * @Description 修改管理员信息，-1表示管理员信息不存在
    * @Date 20:05 2020/3/31
    * @Param [admin]
    * @return int
    **/
    @Override
    public int updateAdmin(Admin admin) {
        if(adminDao.isAdminExist(admin.getName())){
            return adminDao.updateAdmin(admin);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 新增管理员，-1表示该管理员已存在
    * @Date 20:05 2020/3/31
    * @Param [admin]
    * @return int
    **/
    @Override
    public int addAdmin(Admin admin) {
        if(!adminDao.isAdminExist(admin.getName())){
            admin.setStatus(0);
            return adminDao.addAdmin(admin);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 删除管理员，-1表示管理员信息不存在
    * @Date 20:05 2020/3/31
    * @Param [name]
    * @return int
    **/
    @Override
    public int deleteAdminByName(String name) {
        if(adminDao.isAdminExist(name)){
            return adminDao.deleteAdminByName(name);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 验证管理员登录,-1表示该账号不存在，0表示账号或密码错误，-2表示该账号已被登录，1表示登录成功
    * @Date 20:07 2020/3/31
    * @Param [admin]
    * @return int
    **/
    @Override
    public int loginAdmin(Admin admin) {
        if (adminDao.isAdminExist(admin.getName())) {
            if (adminDao.findAdminByName(admin.getName()).getPassword().equals(admin.getPassword())) {
                if (adminDao.findAdminByName(admin.getName()).getStatus() == 0) {
                    return 1;
                } else {
                    return -2;
                }
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 管理员退出登录，登陆状态码归0，-1表示该账号不存在，1表示退出登录成功
    * @Date 11:26 2020/4/14
    * @Param [admin]
    * @return int
    **/
    @Override
    public int exitAdmin(Admin admin) {
        if (adminDao.isAdminExist(admin.getName())) {
            return 1;
        } else {
            return -1;
        }
    }
}
