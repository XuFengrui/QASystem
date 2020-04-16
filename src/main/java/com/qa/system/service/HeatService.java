package com.qa.system.service;

import com.qa.system.entity.Heat;
import com.qa.system.entity.Question;

import java.util.List;

/**
 * @ClassName HeatService
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/4/16
 * @Version 1.0
 **/
public interface HeatService {

    public List<Heat> findAllHeat();
    public int isUserExistInQuestion(String phone,int questionId);
    public int updateHeat(Heat heat);
    public int addHeat(Heat heat);
    public int deleteHeat(int id);
    public int updateHeatOfQuestion(Heat heat);
}
