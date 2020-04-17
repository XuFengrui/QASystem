package com.qa.system.controller;

import com.qa.system.entity.Answer;
import com.qa.system.entity.Question;
import com.qa.system.service.AnswerService;
import com.qa.system.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName QuestionController
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/2
 * @Version 1.0
 **/
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    /**
    * @Author XuFengrui
    * @Description 查询主页的所有问题
    * @Date 0:49 2020/4/3
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Question> 问题类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/question/all")
    @ResponseBody
    public List<Question> questionAll() {
        return questionService.findWhiteQuestionByTimeOrder();
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题编号查询该问题下未被屏蔽的所有回答
    * @Date 0:58 2020/4/3
    * @Param [question] 问题编号
    * @return java.util.List<com.qa.system.entity.Answer> 回答类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/question/answer")
    @ResponseBody
    public List<Answer> questionToAnswer(@RequestBody Question question) {
        return answerService.findWhiteAnswerByQuestionId(question.getQuestionId());
    }

    /**
    * @Author XuFengrui
    * @Description 新增问题
    * @Date 22:17 2020/4/7
    * @Param [question] question类
    * @return int -1表示问题已存在创建失败，0表示创建成功但是该提问人已被拉黑；成功新增返回1（提问人未被拉黑的情况）
    **/
    @CrossOrigin
    @PostMapping(value = "/question/add")
    @ResponseBody
    public int questionAdd(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题Id查询问题
    * @Date 11:18 2020/4/8
    * @Param [question] 问题编号
    * @return com.qa.system.entity.Question 问题类
    **/
    @CrossOrigin
    @PostMapping(value = "/question/query")
    @ResponseBody
    public Question questionFindById(@RequestBody Question question) {
        return questionService.findQuestionById(question.getQuestionId());
    }

    /**
    * @Author XuFengrui
    * @Description 将问题按照热度的倒序排列
    * @Date 10:39 2020/4/16 
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Question> 问题类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/question/heat")
    @ResponseBody
    public List<Question> sortQuestionByHeat() {
        return questionService.sortQuestionByHeat();
    }

    /**
    * @Author XuFengrui
    * @Description 问题的关键词搜索
    * @Date 9:37 2020/4/17
    * @Param [] 关键词（String）
    * @return java.util.List<com.qa.system.entity.Question> 问题类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/question/search")
    @ResponseBody
    public List<Question> findQuestionsByKeyword(@RequestParam(value = "strWord", required = false) String strWord) {
        System.out.println(strWord);
        return questionService.findQuestionsByKeyword(strWord);
    }
}
