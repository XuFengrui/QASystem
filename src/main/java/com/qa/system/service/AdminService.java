package com.qa.system.service;

import com.qa.system.entity.Admin;
import com.qa.system.entity.User;

/**
 * @ClassName AdminService
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/31
 * @Version 1.0
 **/
public interface AdminService {

    public Admin findAdminByName(String name);
    public int updateAdmin(Admin admin);
    public int addAdmin(Admin admin);
    public int deleteAdminByName(String name);
    public int loginAdmin(Admin admin);
    public int exitAdmin(Admin admin);

}
