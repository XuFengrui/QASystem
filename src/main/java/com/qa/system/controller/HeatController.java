package com.qa.system.controller;

import com.qa.system.entity.Heat;
import com.qa.system.service.HeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HeatController
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/16
 * @Version 1.0
 **/
@RestController
public class HeatController {

    @Autowired
    private HeatService heatService;

    /**
    * @Author XuFengrui
    * @Description 查询该用户之前是否浏览过该问题
    * @Date 9:52 2020/4/16
    * @Param [heat] 电话号码phone，问题编号questionId
    * @return int 若浏览过或未登录用户浏览返回1，未浏览过返回0
    **/
    @CrossOrigin
    @PostMapping(value = "/heat/query")
    @ResponseBody
    public int isUserExistInQuestion(@RequestBody Heat heat) {
        if (heat.getPhone() == null) {
            return 1;
        } else {
            return heatService.isUserExistInQuestion(heat.getPhone(),heat.getQuestionId());
        }
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有Heat类信息，即所有用户浏览过的问题
    * @Date 9:55 2020/4/16
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Heat> Heat类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/heat/all")
    @ResponseBody
    public List<Heat> heatAll() {
        return heatService.findAllHeat();
    }

    /**
    * @Author XuFengrui
    * @Description 修改问题的热度
    * @Date 10:13 2020/4/16
    * @Param [heat] 电话号码phone，问题编号questionId
    * @return int -1表示此用户之前已浏览过该问题，热度无需改变，0表示修改失败，1表示修改成功
    **/
    @CrossOrigin
    @PostMapping(value = "/heat/update")
    @ResponseBody
    public int updateHeatOfQuestion(@RequestBody Heat heat) {
        return heatService.updateHeatOfQuestion(heat);
    }
}
