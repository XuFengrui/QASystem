package com.qa.system.dao;

import com.qa.system.entity.Message;
import com.qa.system.utils.FormatChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName MessageDao
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/5/7
 * @Version 1.0
 **/
@Repository
public class MessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
    * @Author XuFengrui
    * @Description 查询所有Message类
    * @Date 15:06 2020/5/7
    * @Param []
    * @return java.util.List<com.qa.system.entity.Message>
    **/
    public List<Message> findAllMessage(){
        List<Message> messageList = jdbcTemplate.query("select * from message",new Object[]{},new BeanPropertyRowMapper<>(Message.class));
        if(messageList.size()>0){
            return messageList;
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名查询该用户被操作过的所有信息
    * @Date 15:06 2020/5/7
    * @Param [name]
    * @return com.qa.system.entity.Message
    **/
    public List<Message> findMessageByName(String name) {
        List<Message> messageList = jdbcTemplate.query("select * from message where recipient = ? order by time desc", new BeanPropertyRowMapper<>(Message.class), name);
        if (messageList.size() > 0) {
            return messageList;
        } else {
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 查询当前message表中最大id
    * @Date 15:08 2020/5/7
    * @Param []
    * @return int
    **/
    public int maxId() {
        List<Message> messageList = jdbcTemplate.query("select * from message",new Object[]{},new BeanPropertyRowMapper<>(Message.class));
        if(messageList.size()>0){
            return messageList.get(messageList.size() - 1).getId();
        }else{
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 修改message类信息
    * @Date 15:14 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int updateMessage(Message message) {
        int count = jdbcTemplate.update("update message set questionId = ?,answerId = ?,details = ?,time = ? where id = ?",message.getDetails(),FormatChange.dateTimeChange(new Date()),message.getId());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 新增message类信息
    * @Date 15:14 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int addMessage(Message message) {
        int count = jdbcTemplate.update("insert into message(id,recipient,sender,details,time) values(?,?,?,?,?)",maxId()+1,message.getRecipient(),message.getSender(),message.getDetails(),FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 删除message类信息
    * @Date 15:16 2020/5/7
    * @Param [id]
    * @return int
    **/
    public int deleteMessage(int id) {
        int count = jdbcTemplate.update("delete from message where id = ?",id);
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 拉黑提醒
    * @Date 15:48 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int userBlackMessage(String name) {
        int count = jdbcTemplate.update("insert into message(id,recipient,details,questionId,answerId,commentId,time) values(?,?,?,?,?,?,?)",maxId()+1,name,"您已被管理员拉黑",0,0,0,FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 解除拉黑提醒
    * @Date 15:48 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int userWhiteMessage(String name) {
        int count = jdbcTemplate.update("insert into message(id,recipient,details,questionId,answerId,commentId,time) values(?,?,?,?,?,?,?)",maxId()+1,name,"您已被解除拉黑",0,0,0,FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 问题屏蔽提醒
    * @Date 15:48 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int questionBlackMessage(Message message) {
        int count = jdbcTemplate.update("insert into message(id,recipient,details,questionId,answerId,commentId,time) values(?,?,?,?,?,?,?)",maxId()+1,message.getRecipient(),"您有一个问题已被管理员屏蔽",message.getQuestionId(),0,0,FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 问题解除屏蔽提醒
    * @Date 15:52 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int questionWhiteMessage(Message message) {
        int count = jdbcTemplate.update("insert into message(id,recipient,details,questionId,answerId,commentId,time) values(?,?,?,?,?,?,?)",maxId()+1,message.getRecipient(),"您有一个问题已被解除屏蔽",message.getQuestionId(),0,0,FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 回答屏蔽提醒
    * @Date 15:53 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int answerBlackMessage(Message message) {
        int count = jdbcTemplate.update("insert into message(id,recipient,details,questionId,answerId,commentId,time) values(?,?,?,?,?,?,?)",maxId()+1,message.getRecipient(),"您有一个回答已被管理员屏蔽",0,message.getAnswerId(),0,FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 回答解除屏蔽提醒
    * @Date 15:53 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int answerWhiteMessage(Message message) {
        int count = jdbcTemplate.update("insert into message(id,recipient,details,questionId,answerId,commentId,time) values(?,?,?,?,?,?,?)",maxId()+1,message.getRecipient(),"您有一个回答已被解除屏蔽",0,message.getAnswerId(),0,FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 新的回答提醒
    * @Date 15:48 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int byAnswerMessage(Message message) {
        int count = jdbcTemplate.update("insert into message(id,recipient,sender,details,questionId,answerId,commentId,time) values(?,?,?,?,?,?,?,?)",maxId()+1,message.getRecipient(),message.getSender(),"您有一个新的回复",message.getQuestionId(),message.getAnswerId(),0,FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 新的评论提醒
    * @Date 15:48 2020/5/7
    * @Param [message]
    * @return int
    **/
    public int byCommentMessage(Message message) {
        int count = jdbcTemplate.update("insert into message(id,recipient,sender,details,questionId,answerId,commentId,time) values(?,?,?,?,?,?,?,?)",maxId()+1,message.getRecipient(),message.getSender(),"您有一个新的评论",0,message.getAnswerId(),message.getCommentId(),FormatChange.dateTimeChange(new Date()));
        return count;
    }
}
