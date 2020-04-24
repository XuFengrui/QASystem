package com.qa.system.dao;

import com.qa.system.entity.Answer;
import com.qa.system.entity.Question;
import com.qa.system.utils.FormatChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AnswerDao
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Repository
public class AnswerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
    * @Author XuFengrui
    * @Description 查询所有回答
    * @Date 16:38 2020/3/29
    * @Param []
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    public List<Answer> findAllAnswer() {
        List<Answer> answerList = jdbcTemplate.query("select * from answer",new Object[]{},new BeanPropertyRowMapper<>(Answer.class));
        return answerList;
    }

    /**
    * @Author XuFengrui
    * @Description 根据回答编号查询回答
    * @Date 16:38 2020/3/29
    * @Param [id]
    * @return com.qa.system.entity.Answer
    **/
    public Answer findAnswerById(int id) {
        List<Answer> answerList = jdbcTemplate.query("select * from answer where answerId = ?",new BeanPropertyRowMapper<>(Answer.class),id);
        if(answerList.size()>0){
            return answerList.get(0);
        }else{
            return null;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 查询回答是否存在
    * @Date 16:38 2020/3/29
    * @Param [id]
    * @return boolean
    **/
    public boolean isAnswerExist(int id) {
        return findAnswerById(id) != null;
    }

    /**
    * @Author XuFengrui
    * @Description 查询回答表中最大的回答编号
    * @Date 16:38 2020/3/29
    * @Param []
    * @return int
    **/
    public int maxAnswerId() {
        List<Answer> answerList = jdbcTemplate.query("select * from answer",new Object[]{},new BeanPropertyRowMapper<>(Answer.class));
        if(answerList.size()>0){
            return answerList.get(answerList.size() - 1).getAnswerId();
        }else{
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 更改回答
    * @Date 16:38 2020/3/29
    * @Param [id, answer]
    * @return int
    **/
    public int updateAnswer(Answer answer) {
        int count = jdbcTemplate.update("update answer set details = ?,time = ? where answerId = ?",answer.getDetails(),FormatChange.dateTimeChange(new Date()),answer.getAnswerId());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 新增回答
    * @Date 16:38 2020/3/29
    * @Param [id, answer]
    * @return int
    **/
    public int addAnswer(Answer answer) {
        int count = jdbcTemplate.update("insert into answer(answerId,details,answerer,aQuestionId,aAnswerId,shield,time) values(?,?,?,?,?,?,?)",maxAnswerId()+1,answer.getDetails(),answer.getAnswerer(),answer.getaQuestionId(),answer.getaAnswerId(),answer.getShield(), FormatChange.dateTimeChange(new Date()));
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 删除回答
    * @Date 16:38 2020/3/29
    * @Param [id]
    * @return int
    **/
    public int deleteAnswerById(int id) {
        int count = jdbcTemplate.update("delete from answer where answerId = ?",id);
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名查询某指定用户的所有回答
    * @Date 17:20 2020/3/29
    * @Param [name]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    public List<Answer> findAnswersByUserName(String name) {
        List<Answer> answerList = jdbcTemplate.query("select * from answer where answerer = ?",new BeanPropertyRowMapper<>(Answer.class),name);
        return answerList;
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题编号查找该问题所有的回答
    * @Date 13:28 2020/3/30
    * @Param [id]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    public List<Answer> findAnswersByQuestionId(int id) {
        List<Answer> answerList = jdbcTemplate.query("select * from answer where aQuestionId = ?",new BeanPropertyRowMapper<>(Answer.class),id);
        return answerList;
    }

    /**
    * @Author XuFengrui
    * @Description 根据回答编号查询该回答下所有的评论
    * @Date 18:09 2020/4/2
    * @Param [id]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    public List<Answer> findAnswersByAAnswerId(int id) {
        List<Answer> answerList = jdbcTemplate.query("select * from answer where aAnswerId = ?",new BeanPropertyRowMapper<>(Answer.class),id);
        return answerList;
    }
    /**
    * @Author XuFengrui
    * @Description 判断该回答是否是对问题的回答，false表示对问题的回答，true表示对回答的回答
    * @Date 15:17 2020/3/30
    * @Param [id]
    * @return boolean
    **/
    public boolean isAAnswerExist(int id) {
        return findAnswerById(id).getaAnswerId() != 0;
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有未被屏蔽的回答
    * @Date 15:59 2020/3/30
    * @Param []
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    public List<Answer> findAllWhiteAnswers() {
        List<Answer> answerList = jdbcTemplate.query("select * from answer where shield = ?",new BeanPropertyRowMapper<>(Answer.class),1);
        return answerList;
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已被屏蔽的回答
    * @Date 15:59 2020/3/30
    * @Param []
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    public List<Answer> findAllBlackAnswers() {
        List<Answer> answerList = jdbcTemplate.query("select * from answer where shield = ?",new BeanPropertyRowMapper<>(Answer.class),0);
        return answerList;
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题查询所有未被屏蔽的回答
    * @Date 16:12 2020/3/30
    * @Param [id]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    public List<Answer> findWhiteAnswersByQuestionId(int id) {
        List<Answer> answerList = jdbcTemplate.query("select * from answer where aQuestionId = ? and shield = ? ",new BeanPropertyRowMapper<>(Answer.class),id,1);
        return answerList;
    }

    /**
    * @Author XuFengrui
    * @Description 改变回答的屏蔽状态，失败返回0，成功返回1
    * @Date 0:10 2020/4/3
    * @Param [answer]
    * @return int
    **/
    public int answerShield(Answer answer) {
        int count = jdbcTemplate.update("update answer set shield = ? where answerId = ?",answer.getShield(),answer.getAnswerId());
        return count;
    }

    /**
    * @Author XuFengrui
    * @Description 模糊查询回答
    * @Date 11:45 2020/4/24
    * @Param [strWord]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    public List<Answer> findAnswersByKeyword(String strWord) {
        List<Answer> answerList = jdbcTemplate.query("select * from question where answerId like ? or details like ? or answerer like ?",new Object[]{"%"+strWord+"%","%"+strWord+"%","%"+strWord+"%"},new BeanPropertyRowMapper<>(Answer.class));
        return answerList;
    }
}
