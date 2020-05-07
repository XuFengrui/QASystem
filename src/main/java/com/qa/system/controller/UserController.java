package com.qa.system.controller;

import com.aliyuncs.exceptions.ClientException;
import com.qa.system.entity.*;
import com.qa.system.service.AnswerService;
import com.qa.system.service.QuestionService;
import com.qa.system.service.RegisterService;
import com.qa.system.service.UserService;
import com.qa.system.utils.TimeSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
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
    public String userLoginByCode(@RequestBody User user) throws ClientException {
        return userService.loginUserByAuthCode(user.getPhone());
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
        List<Question> questionList = questionService.findQuestionByUserName(user.getName());
        TimeSort.questionListSort(questionList);
        return questionList;
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
        List<Answer> answerList = answerService.findAnswerByUserName(user.getName());
        TimeSort.answerListSort(answerList);
        return answerList;
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
    * @Description 根据电话号码查询用户
    * @Date 21:03 2020/4/13
    * @Param [user] 电话号码
    * @return com.qa.system.entity.User 存在则返回该用户类，不存在则返回null
    **/
    @CrossOrigin
    @PostMapping(value = "/user/query")
    @ResponseBody
    public User userFindByPhone(@RequestBody User user) {
        return userService.findUserByPhone(user.getPhone());
    }

    /**
    * @Author XuFengrui
    * @Description 更改用户信息
    * @Date 16:05 2020/4/14
    * @Param [user] 用户类
    * @return int -1表示该用户不存在,1表示成功，0表示失败
    **/
    @CrossOrigin
    @PostMapping(value = "/user/update")
    @ResponseBody
    public int userUpdate(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名查询电话号码（主键）
    * @Date 8:15 2020/4/21
    * @Param [user] 电话号码
    * @return java.lang.String null表示该用户不存在，若存在则返回电话号码（String）
    **/
    @CrossOrigin
    @PostMapping(value = "/user/getPhoneByName")
    @ResponseBody
    public String queryPhoneByName(@RequestBody User user) {
        return userService.queryPhoneByName(user.getName());
    }

    /**
    * @Author XuFengrui
    * @Description 用户上传头像
    * @Date 11:59 2020/4/22
    * @Param [user] 头像图片，电话号码
    * @return int 0表示失败，1表示成功
    **/
    @CrossOrigin
    @PostMapping(value = "/user/uploadIcon")
    @ResponseBody
    public int saveIcon(@RequestBody User user) {
        if (userService.saveIcon(user)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 显示用户头像
    * @Date 20:58 2020/4/23
    * @Param [user] 电话号码
    * @return java.lang.String 头像存储的路径（String）,若用户不存在，则返回null
    **/
    @CrossOrigin
    @PostMapping(value = "/user/downloadIcon")
    @ResponseBody
    public String showIcon(@RequestBody User user) {
        return userService.showIcon(user);
    }

    /**
    * @Author XuFengrui
    * @Description 通过base64编码转化为图片
    * @Date 10:29 2020/4/24
    * @Param [user] 电话号码
    * @return java.awt.image.BufferedImage 图片image（BufferedImage类）
    **/
    @CrossOrigin
    @PostMapping(value = "/user/showIcon")
    @ResponseBody
    public BufferedImage decodeToImage(@RequestBody User user) {
        return userService.decodeToImage(user);
    }

    /**
    * @Author XuFengrui
    * @Description 获取base64编码
    * @Date 11:14 2020/4/24
    * @Param [user] 电话号码
    * @return java.lang.String base编码（String）
    **/
    @CrossOrigin
    @PostMapping(value = "/user/getImageStr")
    @ResponseBody
    public String getImageStr(@RequestBody User user) {
        return userService.getImageStr(user);
    }

    /**
    * @Author XuFengrui
    * @Description 通过用户名查询该用户的所有消息
    * @Date 15:25 2020/5/7
    * @Param [name] 用户名
    * @return java.util.List<com.qa.system.entity.Message>
    **/
    @CrossOrigin
    @RequestMapping(value = "/user/message")
    @ResponseBody
    public List<Message> findMessageByName() {
        return userService.findMessageByName("xxx");
    }
}