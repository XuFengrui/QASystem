package com.qa.system.dao;

import com.qa.system.entity.Heat;
import com.qa.system.entity.Question;
import com.qa.system.entity.User;
import com.qa.system.utils.FormatChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName HeatDao
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/15
 * @Version 1.0
 **/
@Repository
public class HeatDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
    * @Author XuFengrui
    * @Description 查询所有heat类
    * @Date 21:14 2020/4/15
    * @Param []
    * @return java.util.List<com.qa.system.entity.Heat>
    **/
    public List<Heat> findAllHeat(){
        List<Heat> heatList = jdbcTemplate.query("select * from heat",new Object[]{},new BeanPropertyRowMapper<>(Heat.class));
        if(heatList.size()>0){
            return heatList;
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户号码查询该用户浏览过的问题
    * @Date 21:14 2020/4/15
    * @Param [phone]
    * @return com.qa.system.entity.Heat
    **/
    public Heat findHeatByPhone(String phone) {
        List<Heat> heatList = jdbcTemplate.query("select * from heat where phone = ?", new BeanPropertyRowMapper<>(Heat.class), phone);
        if (heatList.size() > 0) {
            return heatList.get(0);
        } else {
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题编号查询浏览过该问题的用户
    * @Date 21:14 2020/4/15
    * @Param [id]
    * @return com.qa.system.entity.Heat
    **/
    public Heat findHeatByQuestionId(int id) {
        List<Heat> heatList = jdbcTemplate.query("select * from heat where questionId = ?", new BeanPropertyRowMapper<>(Heat.class), id);
        if (heatList.size() > 0) {
            return heatList.get(0);
        } else {
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 查询当前heat表中最大id
    * @Date 21:14 2020/4/15
    * @Param []
    * @return int
    **/
    public int maxId() {
        List<Heat> heatList = jdbcTemplate.query("select * from heat",new Object[]{},new BeanPropertyRowMapper<>(Heat.class));
        if(heatList.size()>0){
            return heatList.get(heatList.size() - 1).getId();
        }else{
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 修改heat类信息
    * @Date 21:15 2020/4/15
    * @Param [heat]
    * @return int
    **/
    public int updateHeat(Heat heat) {
        int count = jdbcTemplate.update("update heat set phone = ?,questionId = ? where id = ?",heat.getPhone(),heat.getQuestionId(),heat.getId());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 新增heat类信息
    * @Date 21:15 2020/4/15
    * @Param [heat]
    * @return int
    **/
    public int addHeat(Heat heat) {
        int count = jdbcTemplate.update("insert into heat(id,phone,questionId) values(?,?,?)",maxId()+1,heat.getPhone(),heat.getQuestionId());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 删除heat类信息
    * @Date 21:15 2020/4/15
    * @Param [id]
    * @return int
    **/
    public int deleteHeat(int id) {
        int count = jdbcTemplate.update("delete from heat where id = ?",id);
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 查询某用户是否浏览过该问题，1表示浏览过，0表示未浏览过
    * @Date 21:23 2020/4/15
    * @Param [phone, questionId]
    * @return int
    **/
    public int isUserExistInQuestion(String phone,int questionId) {
        List<Heat> heatList = jdbcTemplate.query("select * from heat where phone = ? and questionId = ?",new BeanPropertyRowMapper<>(Heat.class),phone,questionId);
        if(heatList.size()>0){
            return 1;
        }else{
            return 0;
        }
    }
}
