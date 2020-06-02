package com.qa.system.service;

import com.qa.system.entity.Question;

import java.util.List;

/**
 * @ClassName QuestionService
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
public interface QuestionService {

    public List<Question> findAllQuestion();
    public List<Question> findAllWhiteQuestions();
    public List<Question> findAllBlackQuestions();
    public List<Question> findWhiteQuestionByTimeOrder();
    public List<Question> showQuestionsByKeyword(String string);
    public List<Question> findQuestionByUserName(String name);
    public Question findQuestionById(int id);
    public int updateQuestion(Question question);
    public int addQuestion(Question question) throws Exception;
    public int deleteQuestionById(int id);
    public int blacklistQuestion(Question question);
    public int whitelistQuestion(Question question);
    public int blacklistQuestions(List<Question> questionList);
    public int whitelistQuestions(List<Question> questionList);
    public int endQuestion(Question question);
    public List<Question> sortQuestionByHeat();
    public List<Question> findQuestionsByKeyword(String keyWord);
}
