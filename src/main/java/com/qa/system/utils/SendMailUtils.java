package com.qa.system.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @ClassName Mail
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/16
 * @Version 1.0
 **/
@Component
public class SendMailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    /**
    * @Author XuFengrui
    * @Description 发送邮件的模板
    * @Date 11:23 2020/4/16
    * @Param [toMail, subject, text]
    * @return void
    **/
    public void sendTextMail(String toMail, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        try {
            javaMailSender.send(simpleMailMessage);
            System.out.println("邮件已发送");
        } catch (Exception e) {
            System.out.println("邮件发送失败。"+e.getMessage());
        }
    }

    /**
    * @Author XuFengrui
    * @Description 发送注册成功的通知邮件
    * @Date 11:26 2020/4/16
    * @Param [toMail, name]
    * @return void
    **/
    public void sendSuccessRegisterMail(String toMail,String name) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject("开心论坛注册成功通知");
        simpleMailMessage.setText("恭喜"+name+"小主，您在开心论坛的注册申请已通过，相信您的到来一定会让开心论坛蓬荜生辉！");
        try {
            javaMailSender.send(simpleMailMessage);
            System.out.println("邮件已发送");
        } catch (Exception e) {
            System.out.println("邮件发送失败。"+e.getMessage());
        }
    }

    /**
    * @Author XuFengrui
    * @Description 发送注册失败的通知邮件
    * @Date 17:15 2020/6/9
    * @Param [toMail, name]
    * @return void
    **/
    public void sendFailedRegisterMail(String toMail,String name) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject("开心论坛注册失败通知");
        simpleMailMessage.setText("很遗憾，"+name+"小主，您在开心论坛的注册申请被拒绝，期待与您的下一次相遇！");
        try {
            javaMailSender.send(simpleMailMessage);
            System.out.println("邮件已发送");
        } catch (Exception e) {
            System.out.println("邮件发送失败。"+e.getMessage());
        }
    }
}
