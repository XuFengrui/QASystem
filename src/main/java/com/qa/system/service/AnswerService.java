package com.qa.system.service;

import com.qa.system.entity.Answer;

import java.util.List;

/**
 * @ClassName AnswerService
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
public interface AnswerService {

    public List<Answer> findAllAnswer();
    public Answer findAnswerById(int id);
    public List<Answer> findAnswersByQuestionId(int id);
    public List<Answer> findAnswerByUserName(String name);
    public List<Answer> findWhiteAnswerByQuestionId(int id);
    public int updateAnswer(Answer answer);
    public int addAnswer(Answer answer);
    public int deleteAnswerById(int id);
    public int blacklistAnswer(Answer answer);
    public int whitelistAnswer(Answer answer);
    public int blacklistAnswers(List<Answer> answerList);
    public int whitelistAnswers(List<Answer> answerList);
}
