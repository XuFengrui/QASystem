package com.qa.system.dao;

import com.qa.system.entity.Question;
import com.qa.system.utils.FormatChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName QuestionDao
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Repository
public class QuestionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
    * @Author XuFengrui
    * @Description 查询所有问题
    * @Date 16:27 2020/3/29
    * @Param []
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    public List<Question> findAllQuestion() {
        List<Question> questionList = jdbcTemplate.query("select * from question",new Object[]{},new BeanPropertyRowMapper<>(Question.class));
        if(questionList != null && questionList.size()>0){
            return questionList;
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题编号查询问题
    * @Date 16:27 2020/3/29
    * @Param [id]
    * @return com.qa.system.entity.Question
    **/
    public Question findQuestionById(int id) {
        List<Question> questionList = jdbcTemplate.query("select * from question where questionId = ?",new BeanPropertyRowMapper<>(Question.class),id);
        if(questionList != null && questionList.size()>0){
            return questionList.get(0);
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 查询问题是否存在，false表示不存在，true表示存在
    * @Date 16:27 2020/3/29
    * @Param [id]
    * @return boolean
    **/
    public boolean isQuestionExist(int id) {
        return findQuestionById(id) != null;
    }

    /**
    * @Author XuFengrui
    * @Description 查询问题表中最大的问题编号
    * @Date 16:27 2020/3/29
    * @Param []
    * @return int
    **/
    public int maxQuestionId() {
        List<Question> questionList = jdbcTemplate.query("select id from question",new Object[]{},new BeanPropertyRowMapper<>(Question.class));
        if(questionList != null && questionList.size()>0){
            return questionList.get(questionList.size() - 1).getQuestionId();
        }else{
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 更改问题
    * @Date 16:27 2020/3/29
    * @Param [id, question]
    * @return int
    **/
    public int updateQuestion(Question question) {
        int count = jdbcTemplate.update("update question set details = ?,questioner = ?,shield = ?,time = ?,heat = ?,label = ?,signal = ? where questionId = ?",question.getDetails(),question.getQuestioner(),question.getShield(),FormatChange.dateTimeChange(new Date()),question.getHeat(),question.getLabel(),question.getSignal(),question.getQuestionId());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 新增问题
    * @Date 16:27 2020/3/29
    * @Param [id, question]
    * @return int
    **/
    public int addQuestion(Question question) {
        int count = jdbcTemplate.update("insert into question(questionId,details,questioner,shield,time,heat,label,signal) values(?,?,?,?,?,?,?,?)",maxQuestionId()+1,question.getDetails(),question.getQuestioner(),question.getShield(),FormatChange.dateTimeChange(new Date()),question.getHeat(),question.getLabel(),question.getSignal());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 删除问题
    * @Date 16:27 2020/3/29
    * @Param [id]
    * @return int
    **/
    public int deleteQuestionById(int id) {
        int count = jdbcTemplate.update("delete from question where questionId = ?",id);
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 查询问题是否被屏蔽，0表示被屏蔽，1表示正常
    * @Date 17:00 2020/3/29
    * @Param [id]
    * @return int
    **/
    public int isQuestionBlacklist(int id) {
        return findQuestionById(id).getShield();
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名查询某指定用户所提出的所有问题
    * @Date 17:16 2020/3/29
    * @Param [name]
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    public List<Question> findQuestionsByUserName(String name) {
        List<Question> questionList = jdbcTemplate.query("select * from question where questioner = ?",new BeanPropertyRowMapper<>(Question.class),name);
        return questionList;
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有未被屏蔽的问题
    * @Date 15:58 2020/3/30
    * @Param []
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    public List<Question> findAllWhiteQuestions() {
        List<Question> questionList = jdbcTemplate.query("select * from question where shield = ?",new BeanPropertyRowMapper<>(Question.class),1);
        return questionList;
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已被屏蔽的问题
    * @Date 15:58 2020/3/30
    * @Param []
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    public List<Question> findAllBlackQuestions() {
        List<Question> questionList = jdbcTemplate.query("select * from question where shield = ?",new BeanPropertyRowMapper<>(Question.class),0);
        return questionList;
    }

    /**
    * @Author XuFengrui
    * @Description 模糊查询，关键词搜索问题（通过问题内容，问题标签，或者提问者用户名）
    * @Date 19:53 2020/3/30
    * @Param [string]
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    public List<Question> findQuestionsByKeyword(String string) {
        List<Question> questionList = jdbcTemplate.query("select * from question where (details like ? or label like ? or questioner like ?) and shield = ?",new Object[]{"%"+string+"%","%"+string+"%","%"+string+"%",1},new BeanPropertyRowMapper<>(Question.class));
        return questionList;
    }
}
