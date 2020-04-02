package com.qa.system.service.impl;

import com.qa.system.dao.AnswerDao;
import com.qa.system.dao.QuestionDao;
import com.qa.system.dao.UserDao;
import com.qa.system.entity.Answer;
import com.qa.system.entity.Question;
import com.qa.system.service.QuestionService;
import com.qa.system.service.UserService;
import com.qa.system.utils.TimeSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName QuestionServiceImpl
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;
    UserDao userDao;
    AnswerDao answerDao;

    /**
    * @Author XuFengrui
    * @Description 查询所有问题
    * @Date 16:40 2020/3/29
    * @Param []
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @Override
    public List<Question> findAllQuestion() {
        return questionDao.findAllQuestion();
    }

    /**
    * @Author XuFengrui
    * @Description 将问题按最新回答的时间顺序展示
    * @Date 13:45 2020/3/30
    * @Param []
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @Override
    public List<Question> findWhiteQuestionByTimeOrder() {
        List<Answer> answerList = null;
        List<Answer> answers = null;
        List<Question> questions = questionDao.findAllWhiteQuestions();
        for (int i = 0; i < questions.size(); i++) {
            answers = answerDao.findWhiteAnswersByQuestionId(questions.get(i).getQuestionId());
            TimeSort.answerListSort(answers);
            answerList.add(answers.get(0));
        }
        TimeSort.answerListSort(answerList);
        List<Question> questionList = findAllQuestion();
        for (int j = 0; j < questions.size(); j++) {
            questionList.add(findQuestionById(answerList.get(j).getaQuestionId()));
        }
        return questionList;
    }

    @Override
    public List<Question> findQuestionsByKeyword(String string) {
        return findQuestionsByKeyword(string);
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题编号查询问题
    * @Date 16:40 2020/3/29
    * @Param [id]
    * @return com.qa.system.entity.Question
    **/
    @Override
    public Question findQuestionById(int id) {
        return questionDao.findQuestionById(id);
    }

    /**
    * @Author XuFengrui
    * @Description 更改问题,问题不存在返回-1；屏蔽问题不能修改返回0；成功修改返回1
    * @Date 16:40 2020/3/29
    * @Param [id, question]
    * @return int
    **/
    @Override
    public int updateQuestion(Question question) {
        if (questionDao.isQuestionExist(question.getQuestionId())) {
            if (question.getShield() == 0) {
                return 0;
            } else {
                return questionDao.updateQuestion(question);
            }
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 新增问题,问题已存在返回-1，提问人已被拉黑返回0；成功新增返回1
    * @Date 16:40 2020/3/29
    * @Param [id, question]
    * @return int
    **/
    @Override
    public int addQuestion(Question question) {
        if(!questionDao.isQuestionExist(question.getQuestionId())){
            if (userDao.findUserByName(question.getQuestioner()).getShield() == 0) {
                question.setShield(0);
            } else {
                question.setShield(1);
            }
            question.setHeat(0);
            question.setSignal(1);
            return questionDao.addQuestion(question);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 删除问题,问题不存在返回-1；屏蔽问题不能删除返回0；成功删除返回1
    * @Date 16:40 2020/3/29
    * @Param [id]
    * @return int
    **/
    @Override
    public int deleteQuestionById(int id) {
        if(questionDao.isQuestionExist(id)){
            if (findQuestionById(id).getShield() == 0) {
                return 0;
            } else {
                return questionDao.deleteQuestionById(id);
            }
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 屏蔽问题
    * @Date 16:40 2020/3/29
    * @Param [question]
    * @return int
    **/
    @Override
    public int blacklistQuestion(Question question) {
        if (question.getShield() == 1) {
            question.setShield(0);
            questionDao.updateQuestion(question);
            return 1;
        }else {
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 解除屏蔽问题
    * @Date 16:40 2020/3/29
    * @Param [question]
    * @return int
    **/
    @Override
    public int whitelistQuestion(Question question) {
        if (question.getShield() == 0) {
            question.setShield(1);
            questionDao.updateQuestion(question);
            return 1;
        }else {
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description
    * @Date 10:45 2020/3/30
    * @Param [questionList]
    * @return int
    **/
    @Override
    public int blacklistQuestions(List<Question> questionList) {
        for (int i = questionList.size(); i > 0; i--) {
            if (questionList.get(i - 1).getShield() == 1) {
                questionList.get(i - 1).setShield(0);
                questionDao.updateQuestion(questionList.get(i - 1));
            }
        }
        return questionList.size();
    }

    /**
    * @Author XuFengrui
    * @Description
    * @Date 10:45 2020/3/30
    * @Param [questionList]
    * @return int
    **/
    @Override
    public int whitelistQuestions(List<Question> questionList) {
        for (int i = questionList.size(); i > 0; i--) {
            if (questionList.get(i - 1).getShield() == 0) {
                questionList.get(i - 1).setShield(1);
                questionDao.updateQuestion(questionList.get(i - 1));
            }
        }
        return questionList.size();
    }


}
