package com.qa.system.controller;

import com.aliyuncs.exceptions.ClientException;
import com.qa.system.entity.*;
import com.qa.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/2
 * @Version 1.0
 **/
@RestController
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    RegisterService registerService;

    @Autowired
    AdminService adminService;

    /**
    * @Author XuFengrui
    * @Description 查询所有用户
    * @Date 23:53 2020/4/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.User> 用户类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/user")
    @ResponseBody
    public List<User> adminAllUser() {
        return userService.findAllUser();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有未通过的用户注册信息
    * @Date 23:57 2020/4/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Register> 注册类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/register")
    @ResponseBody
    public List<Register> adminAllRegister() {
        return registerService.findNotPassedRegister();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有问题
    * @Date 23:54 2020/4/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Question> 问题类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/question")
    @ResponseBody
    public List<Question> adminAllQuestion() {
        return questionService.findAllQuestion();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有回答
    * @Date 23:55 2020/4/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Answer> 回答类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/answer")
    @ResponseBody
    public List<Answer> adminAllAnswer() {
        return answerService.findAllAnswer();
    }

    /**
    * @Author XuFengrui
    * @Description 拉黑用户
    * @Date 0:00 2020/4/3
    * @Param [user] 电话号码
    * @return int 用户已被拉黑，拉黑失败返回0；拉黑成功返回1,用户不存在返回-1
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/userBlack")
    @ResponseBody
    public int adminBlackUser(@RequestBody User user) {
        return userService.blacklistUser(user);
    }

    /**
    * @Author XuFengrui
    * @Description 取消拉黑用户
    * @Date 0:29 2020/4/3
    * @Param [user] 电话号码
    * @return int 用户未被拉黑，取消拉黑失败返回0；取消拉黑成功返回1,用户不存在返回-1
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/userWhite")
    @ResponseBody
    public int adminWhiteUser(@RequestBody User user) {
        return userService.whitelistUser(user);
    }

    /**
    * @Author XuFengrui
    * @Description 屏蔽问题
    * @Date 0:36 2020/4/3
    * @Param [question] 问题编号
    * @return int 问题已被屏蔽，屏蔽失败返回0；屏蔽成功返回1,问题不存在返回-1
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/questionBlack")
    @ResponseBody
    public int adminBlackQuestion(@RequestBody Question question) {
        return questionService.blacklistQuestion(question);
    }

    /**
    * @Author XuFengrui
    * @Description 取消屏蔽问题
    * @Date 0:37 2020/4/3
    * @Param [question] 问题编号
    * @return int 问题未被屏蔽，取消屏蔽失败返回0；取消屏蔽成功返回1,问题不存在返回-1
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/questionWhite")
    @ResponseBody
    public int adminWhiteQuestion(@RequestBody Question question) {
        return questionService.whitelistQuestion(question);
    }

    /**
    * @Author XuFengrui
    * @Description 屏蔽回答
    * @Date 0:39 2020/4/3
    * @Param [answer] 回答编号
    * @return int 回答已被屏蔽，屏蔽失败返回0；屏蔽成功返回1,回答不存在返回-1
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/answerBlack")
    @ResponseBody
    public int adminBlackAnswer(@RequestBody Answer answer) {
        return answerService.blacklistAnswer(answer);
    }

    /**
    * @Author XuFengrui
    * @Description 取消屏蔽回答
    * @Date 0:40 2020/4/3
    * @Param [answer] 回答编号
    * @return int 回答未被屏蔽，取消屏蔽失败返回0；取消屏蔽成功返回1,回答不存在返回-1
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/answerWhite")
    @ResponseBody
    public int adminWhiteQuestion(@RequestBody Answer answer) {
        return answerService.whitelistAnswer(answer);
    }

    /**
    * @Author XuFengrui
    * @Description 同意用户注册
    * @Date 0:43 2020/4/3
    * @Param [register] 注册类
    * @return int 注册成功返回1；注册失败返回0；注册信息不存在返回-1
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/userRegister")
    @ResponseBody
    public int adminUserRegister(@RequestBody Register register) throws ClientException {
        return userService.registerUser(register);
    }

    /**
     * @Author XuFengrui
     * @Description 管理员登录
     * @Date 20:59 2020/4/13
     * @Param [admin] 管理员的账号和密码
     * @return int -1表示该账号不存在，0表示账号或密码错误，-2表示该账号已被登录，1表示登录成功
     **/
    @CrossOrigin
    @PostMapping(value = "/admin/login")
    @ResponseBody
    public int adminLogin(@RequestBody Admin admin) {
        return adminService.loginAdmin(admin);
    }

    /**
     * @Author XuFengrui
     * @Description 管理员退出登录，登陆状态码归0
     * @Date 11:28 2020/4/14
     * @Param [admin] 管理员的账号
     * @return int -1表示该账号不存在，0表示该账号登录状态码已归0，1表示退出登录成功
     **/
    @CrossOrigin
    @PostMapping(value = "/admin/exit")
    @ResponseBody
    public int adminExit(@RequestBody Admin admin) {
        return adminService.exitAdmin(admin);
    }

    /**
    * @Author XuFengrui
    * @Description 模糊查询用户
    * @Date 11:59 2020/4/24
    * @Param [admin]
    * @return java.util.List<com.qa.system.entity.User>
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/queryUser")
    @ResponseBody
    public List<User> userQuery(@RequestBody Admin admin) {
        return userService.findUsersByKeyword(admin.getName());
    }

    /**
    * @Author XuFengrui
    * @Description 模糊查询注册用户
    * @Date 11:54 2020/4/24
    * @Param [admin]
    * @return java.util.List<com.qa.system.entity.User>
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/queryRegister")
    @ResponseBody
    public List<Register> registerQuery(@RequestBody Admin admin) {
        return registerService.findRegistersByKeyword(admin.getName());
    }

    /**
    * @Author XuFengrui
    * @Description 模糊查询问题
    * @Date 11:59 2020/4/24
    * @Param [admin]
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/queryQuestion")
    @ResponseBody
    public List<Question> questionQuery(@RequestBody Admin admin) {
        return questionService.findQuestionsByKeyword(admin.getName());
    }

    /**
    * @Author XuFengrui
    * @Description 模糊查询回答
    * @Date 12:00 2020/4/24
    * @Param [admin]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/queryAnswer")
    @ResponseBody
    public List<Answer> answerQuery(@RequestBody Admin admin) {
        return answerService.findAnswersByKeyword(admin.getName());
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有未拉黑用户
    * @Date 15:48 2020/6/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.User> 用户类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/whiteUsers")
    @ResponseBody
    public List<User> findAllWhiteUsers() {
        return userService.findAllWhiteUsers();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已拉黑用户
    * @Date 15:49 2020/6/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.User> 用户类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/blackUsers")
    @ResponseBody
    public List<User> findAllBlackUsers() {
        return userService.findAllBlackUsers();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有未屏蔽问题
    * @Date 15:49 2020/6/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Question> 问题类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/whiteQuestions")
    @ResponseBody
    public List<Question> findAllWhiteQuestions() {
        return questionService.findAllWhiteQuestions();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已屏蔽问题
    * @Date 15:50 2020/6/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Question> 问题类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/blackQuestions")
    @ResponseBody
    public List<Question> findAllBlackQuestions() {
        return questionService.findAllBlackQuestions();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有未屏蔽回答
    * @Date 15:50 2020/6/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Answer> 回答类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/whiteAnswers")
    @ResponseBody
    public List<Answer> findAllWhiteAnswers() {
        return answerService.findAllWhiteAnswers();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已屏蔽回答
    * @Date 15:51 2020/6/2
    * @Param [] 无
    * @return java.util.List<com.qa.system.entity.Answer> 回答类数组
    **/
    @CrossOrigin
    @PostMapping(value = "/admin/blackAnswers")
    @ResponseBody
    public List<Answer> findAllBlackAnswers() {
        return answerService.findAllBlackAnswers();
    }
}
