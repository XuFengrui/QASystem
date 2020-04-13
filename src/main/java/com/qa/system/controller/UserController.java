package com.qa.system.controller;

import com.aliyuncs.exceptions.ClientException;
import com.qa.system.entity.Answer;
import com.qa.system.entity.Question;
import com.qa.system.entity.Register;
import com.qa.system.entity.User;
import com.qa.system.service.AnswerService;
import com.qa.system.service.QuestionService;
import com.qa.system.service.RegisterService;
import com.qa.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    RegisterService registerService;

    /**
    * @Author XuFengrui
    * @Description 用户根据用户名和密码登录
    * @Date 20:23 2020/4/2
    * @Param [user] 姓名和密码
    * @return int -1表示用户不存在，1表示登录成功，0表示用户名或密码错误
    **/
    @CrossOrigin
    @PostMapping(value = "/user/loginPwd")
    @ResponseBody
    public int userLoginByPwd(@RequestBody User user) {
        return userService.loginUserByPassword(user);
    }

    /**
    * @Author XuFengrui
    * @Description 用户根据手机号码和验证码登录，发送成功返回验证码
    * @Date 20:27 2020/4/2
    * @Param [phone] 电话号码
    * @return java.lang.String 短信发送成功则返回验证码（String）；发送失败返回空（"")
    **/
    @CrossOrigin
    @PostMapping(value = "/user/loginCode")
    @ResponseBody
    public String userLoginByCode(@RequestBody String phone) throws ClientException {
        return userService.loginUserByAuthCode(phone);
    }

    /**
    * @Author XuFengrui
    * @Description 查询某一用户所发布的所有问题
    * @Date 23:27 2020/4/2
    * @Param [user] 姓名
    * @return java.util.List<com.qa.system.entity.Question> 问题类的数组
    **/
    @CrossOrigin
    @PostMapping(value = "/user/question")
    @ResponseBody
    public List<Question> userAllQuestion(@RequestBody User user) {
        return questionService.findQuestionByUserName(user.getName());
    }

    /**
    * @Author XuFengrui
    * @Description 查询某一用户所发布的所有回答
    * @Date 23:33 2020/4/2
    * @Param [user] 姓名
    * @return java.util.List<com.qa.system.entity.Answer> 回答类的数组
    **/
    @CrossOrigin
    @PostMapping(value = "/user/answer")
    @ResponseBody
    public List<Answer> userAllAnswer(@RequestBody User user) {
        return answerService.findAnswerByUserName(user.getName());
    }

    /**
    * @Author XuFengrui
    * @Description 终结问题
    * @Date 23:37 2020/4/2
    * @Param [question] 问题编号
    * @return int -1表示该问题不存在，0表示该问题已被终结，此次终结失败，1表示成功
    **/
    @CrossOrigin
    @PostMapping(value = "/user/questionEnd")
    @ResponseBody
    public int userQuestionEnd(@RequestBody Question question) {
        return questionService.endQuestion(question);
    }

    /**
    * @Author XuFengrui
    * @Description 修改回答
    * @Date 23:40 2020/4/2
    * @Param [answer] 回答类
    * @return int 0表示失败，1表示成功
    **/
    @CrossOrigin
    @PostMapping(value = "/user/AnswerUpdate")
    @ResponseBody
    public int userAnswerUpdate(@RequestBody Answer answer) {
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
    @PostMapping(value = "/user/AnswerDelete")
    @ResponseBody
    public int userAnswerDelete(@RequestBody Answer answer) {
        return answerService.deleteAnswerById(answer.getAnswerId());
    }

    /**
    * @Author XuFengrui
    * @Description 用户申请注册
    * @Date 23:47 2020/4/2
    * @Param [register] 注册类
    * @return int 用户已注册导致注册失败返回-1；注册成功返回1
    **/
    @CrossOrigin
    @PostMapping(value = "/user/register")
    @ResponseBody
    public int userRegister(@RequestBody Register register) {
        return registerService.applyRegister(register);
    }

    /**
    * @Author XuFengrui
    * @Description 根据号码查询用户
    * @Date 21:03 2020/4/13
    * @Param [user] 电话号码
    * @return com.qa.system.entity.User 用户类
    **/
    @CrossOrigin
    @PostMapping(value = "/user/query")
    @ResponseBody
    public User userFindByPhone(@RequestBody User user) {
        return userService.findUserByPhone(user.getPhone());
    }
//    @RequestMapping(value = "/user/hello")
//    public String  userRegister() {
//        return "hello!";
//    }
}