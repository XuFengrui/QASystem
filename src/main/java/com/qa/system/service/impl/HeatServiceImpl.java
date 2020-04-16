package com.qa.system.service.impl;

import com.qa.system.dao.HeatDao;
import com.qa.system.dao.QuestionDao;
import com.qa.system.entity.Heat;
import com.qa.system.entity.Question;
import com.qa.system.service.HeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin2.main.server.HeartbeatThread;

import java.util.List;

/**
 * @ClassName HeatServiceImpl
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/16
 * @Version 1.0
 **/
@Service
public class HeatServiceImpl implements HeatService {

    @Autowired
    private HeatDao heatDao;

    @Autowired
    private QuestionDao questionDao;


    /**
    * @Author XuFengrui
    * @Description 查询所有Heat类信息
    * @Date 9:47 2020/4/16
    * @Param []
    * @return java.util.List<com.qa.system.entity.Heat>
    **/
    @Override
    public List<Heat> findAllHeat() {
        return heatDao.findAllHeat();
    }

    /**
    * @Author XuFengrui
    * @Description 查询某用户是否浏览过该问题，1表示浏览过，0表示未浏览过
    * @Date 9:47 2020/4/16
    * @Param [phone, questionId]
    * @return int
    **/
    @Override
    public int isUserExistInQuestion(String phone, int questionId) {
        return heatDao.isUserExistInQuestion(phone,questionId);
    }

    /**
    * @Author XuFengrui
    * @Description 修改Heat类信息
    * @Date 9:47 2020/4/16
    * @Param [heat]
    * @return int
    **/
    @Override
    public int updateHeat(Heat heat) {
        return heatDao.updateHeat(heat);
    }

    /**
    * @Author XuFengrui
    * @Description 新增Heat类信息
    * @Date 9:47 2020/4/16
    * @Param [heat]
    * @return int
    **/
    @Override
    public int addHeat(Heat heat) {
        return heatDao.addHeat(heat);
    }

    /**
    * @Author XuFengrui
    * @Description 删除Heat类信息
    * @Date 9:47 2020/4/16
    * @Param [id]
    * @return int
    **/
    @Override
    public int deleteHeat(int id) {
        return heatDao.deleteHeat(id);
    }

    /**
    * @Author XuFengrui
    * @Description 修改问题的热度属性，-1表示已浏览过，无需修改，1表示修改成功，0表示修改失败
    * @Date 10:12 2020/4/16
    * @Param [heat]
    * @return int
    **/
    @Override
    public int updateHeatOfQuestion(Heat heat) {
        if (heatDao.isUserExistInQuestion(heat.getPhone(), heat.getQuestionId()) == 1) {
            return -1;
        } else {
            heatDao.addHeat(heat);
            Question question = questionDao.findQuestionById(heat.getQuestionId());
            question.setHeat(question.getHeat()+1);
            return questionDao.updateQuestion(question);
        }
    }
}
