package com.qa.system.service.impl;

import com.qa.system.dao.*;
import com.qa.system.entity.Message;
import com.qa.system.entity.Question;
import com.qa.system.service.QuestionService;
import com.qa.system.utils.SensitiveWordUtils;
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

    @Autowired
    UserDao userDao;

    @Autowired
    MessageDao messageDao;

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
    * @Description 查询所有未屏蔽问题
    * @Date 15:42 2020/6/2
    * @Param []
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @Override
    public List<Question> findAllWhiteQuestions() {
        return questionDao.findAllWhiteQuestions();
    }

    /**
    * @Author XuFengrui
    * @Description 查询所有已屏蔽问题
    * @Date 15:42 2020/6/2
    * @Param []
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @Override
    public List<Question> findAllBlackQuestions() {
        return questionDao.findAllBlackQuestions();
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
        List<Question> questionList = questionDao.findAllWhiteQuestions();
        TimeSort.questionListSort(questionList);
        return questionList;
    }

    /**
    * @Author XuFengrui
    * @Description 根据关键词查询问题
    * @Date 18:38 2020/4/2
    * @Param [string]
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @Override
    public List<Question> showQuestionsByKeyword(String strWord) {
        return questionDao.findQuestionsByKeyword(strWord);
    }

    /**
    * @Author XuFengrui
    * @Description 根据用户名查找该用户发布的所有问题
    * @Date 23:26 2020/4/2
    * @Param [name]
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @Override
    public List<Question> findQuestionByUserName(String name) {
        return questionDao.findQuestionsByUserName(name);
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
            if (questionDao.findQuestionById(question.getQuestionId()).getShield() == 0) {
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
    public int addQuestion(Question question) throws Exception {
        if(!questionDao.isQuestionExist(question.getQuestionId())){
            if (userDao.findUserByName(question.getQuestioner()).getShield() == 0) {
                question.setShield(0);
            } else {
                question.setShield(1);
            }
            question.setHeat(0);
            question.setEnd(1);
            question.setDetails(SensitiveWordUtils.sensitiveHelper(question.getDetails()));
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
            if (questionDao.findQuestionById(id).getShield() == 0) {
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
    * @Description 屏蔽问题，0表示问题已被屏蔽，1表示成功屏蔽该问题,-1表示该问题不存在
    * @Date 16:40 2020/3/29
    * @Param [question]
    * @return int
    **/
    @Override
    public int blacklistQuestion(Question question) {
        if (questionDao.isQuestionExist(question.getQuestionId())) {
            if (questionDao.findQuestionById(question.getQuestionId()).getShield() == 1) {
                question.setShield(0);
                questionDao.questionShield(question);
                Message message = new Message();
                message.setRecipient(questionDao.findQuestionById(question.getQuestionId()).getQuestioner());
                message.setQuestionId(question.getQuestionId());
                messageDao.questionBlackMessage(message);
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 解除屏蔽问题，0表示问题未被屏蔽，1表示成功取消屏蔽该问题,-1表示该问题不存在
    * @Date 16:40 2020/3/29
    * @Param [question]
    * @return int
    **/
    @Override
    public int whitelistQuestion(Question question) {
        if (questionDao.isQuestionExist(question.getQuestionId())) {
            if (questionDao.findQuestionById(question.getQuestionId()).getShield() == 0) {
                question.setShield(1);
                questionDao.questionShield(question);
                Message message = new Message();
                message.setRecipient(questionDao.findQuestionById(question.getQuestionId()).getQuestioner());
                message.setQuestionId(question.getQuestionId());
                messageDao.questionWhiteMessage(message);
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 屏蔽一串问题，返回值为成功屏蔽问题的数量
    * @Date 10:45 2020/3/30
    * @Param [questionList]
    * @return int
    **/
    @Override
    public int blacklistQuestions(List<Question> questionList) {
        for (int i = questionList.size(); i > 0; i--) {
            if (questionDao.findQuestionById(questionList.get(i - 1).getQuestionId()).getShield() == 1) {
                questionList.get(i - 1).setShield(0);
                questionDao.questionShield(questionList.get(i - 1));
            }
        }
        return questionList.size();
    }

    /**
    * @Author XuFengrui
    * @Description 取消屏蔽一串问题，返回值为成功取消屏蔽的问题数量
    * @Date 10:45 2020/3/30
    * @Param [questionList]
    * @return int
    **/
    @Override
    public int whitelistQuestions(List<Question> questionList) {
        for (int i = questionList.size(); i > 0; i--) {
            if (questionDao.findQuestionById(questionList.get(i - 1).getQuestionId()).getShield() == 0) {
                questionList.get(i - 1).setShield(1);
                questionDao.questionShield(questionList.get(i - 1));
            }
        }
        return questionList.size();
    }

    /**
    * @Author XuFengrui
    * @Description 终结问题，问题不存在返回-1，问题已被终结返回0，成功终结问题返回1
    * @Date 0:16 2020/4/3
    * @Param [question]
    * @return int
    **/
    @Override
    public int endQuestion(Question question) {
        if (questionDao.isQuestionExist(question.getQuestionId())) {
            if (questionDao.findQuestionById(question.getQuestionId()).getShield() == 1) {
                question.setEnd(0);
                return questionDao.questionSignal(question);
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 将所有问题按照热度降序排列
    * @Date 10:34 2020/4/16
    * @Param []
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @Override
    public List<Question> sortQuestionByHeat() {
        return questionDao.sortQuestionByHeat();
    }

    /**
    * @Author XuFengrui
    * @Description 模糊查询问题
    * @Date 11:48 2020/4/24
    * @Param [keyWord]
    * @return java.util.List<com.qa.system.entity.Question>
    **/
    @Override
    public List<Question> findQuestionsByKeyword(String keyWord) {
        return questionDao.findQuestionsByKeyword(keyWord);
    }
}
