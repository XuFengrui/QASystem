package com.qa.system.controller;

import com.qa.system.dao.AnswerDao;
import com.qa.system.entity.Answer;
import com.qa.system.entity.Message;
import com.qa.system.entity.Question;
import com.qa.system.entity.VoComment;
import com.qa.system.service.AnswerService;
import com.qa.system.service.QuestionService;
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

    @Autowired
    QuestionService questionService;

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
    public int answerAdd(@RequestBody Answer answer) throws Exception {
        return answerService.addAnswer(answer);
    }

    /**
     * @Author XuFengrui
     * @Description 修改回答
     * @Date 23:40 2020/4/2
     * @Param [answer] 回答类
     * @return int 若回答编号不存在则返回-1；若回答已被屏蔽返回0；若回答的问题已被屏蔽返回-2，若回答的问题已被终结返回-3，若回答的回答已被屏蔽返回-4，若更改成功则返回1
     **/
    @CrossOrigin
    @PostMapping(value = "/answer/update")
    @ResponseBody
    public int answerUpdate(@RequestBody Answer answer) throws Exception {
        return answerService.updateAnswer(answer);
    }

    /**
     * @Author XuFengrui
     * @Description 删除回答
     * @Date 23:42 2020/4/2
     * @Param [answer] 回答编号
     * @return int 0表示失败，1表示成功
     **/
    @CrossOrigin
    @PostMapping(value = "/answer/delete")
    @ResponseBody
    public int answerDelete(@RequestBody Answer answer) {
        return answerService.deleteAnswerById(answer.getAnswerId());
    }

    /**
    * @Author XuFengrui
    * @Description 根据回答查询回答编号
    * @Date 9:58 2020/5/12
    * @Param [answer] 回答编号
    * @return java.util.List<VoComment> 返回该回答下的所有未被屏蔽的评论
    **/
    @CrossOrigin
    @PostMapping(value = "/answer/comment")
    @ResponseBody
    public List<VoComment> answerToComment(@RequestBody Answer answer) {
        return answerService.findWhiteVoCommentsByAnswerId(answer.getAnswerId());
    }

    /**
    * @Author XuFengrui
    * @Description 通过回答查询对应的问题
    * @Date 10:48 2020/5/28
    * @Param [answer] 回答编号
    * @return com.qa.system.entity.Question 问题类
    **/
    @CrossOrigin
    @PostMapping(value = "/user/answerQ")
    @ResponseBody
    public Question findAnswer_Question(@RequestBody Answer answer) {
        return questionService.findQuestionById(answerService.findAnswerById(answer.getAnswerId()).getaQuestionId());
    }

    /**
    * @Author XuFengrui
    * @Description 通过评论查询回答（回答的回答）
    * @Date 10:48 2020/5/28
    * @Param [answer] 回答编号
    * @return com.qa.system.entity.Answer 回答类
    **/
    @CrossOrigin
    @PostMapping(value = "/user/commentA")
    @ResponseBody
    public Answer findComment_Answer(@RequestBody Answer answer) {
        return answerService.findAnswerById(answerService.findAnswerById(answer.getAnswerId()).getaAnswerId());
    }
}
