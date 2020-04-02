package com.qa.system.controller;

import com.qa.system.entity.Answer;
import com.qa.system.entity.Question;
import com.qa.system.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName AnswerController
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/2
 * @Version 1.0
 **/
@RestController
public class AnswerController {

    @Autowired
    AnswerService answerService;

    /**
    * @Author XuFengrui
    * @Description 新增回答
    * @Date 1:02 2020/4/3
    * @Param [answer] 回答类
    * @return int -1表示该回答已存在,-2表示该回答回答的回答已被屏蔽，-3表示问题已被终结，-4表示该回答回答的问题已被屏蔽，返回1表示成功添加
    **/
    @CrossOrigin
    @PostMapping(value = "/answer/add")
    @ResponseBody
    public int answerAdd(@RequestBody Answer answer) {
        return answerService.addAnswer(answer);
    }
}
