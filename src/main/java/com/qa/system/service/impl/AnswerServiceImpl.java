package com.qa.system.service.impl;

import com.qa.system.dao.AnswerDao;
import com.qa.system.dao.QuestionDao;
import com.qa.system.dao.UserDao;
import com.qa.system.entity.Answer;
import com.qa.system.entity.Question;
import com.qa.system.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AnswerServiceImpl
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/3/29
 * @Version 1.0
 **/
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerDao answerDao;
    QuestionDao questionDao;
    UserDao userDao;


    /**
    * @Author XuFengrui
    * @Description 查询所有回答
    * @Date 16:39 2020/3/29
    * @Param []
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    @Override
    public List<Answer> findAllAnswer() {
        return answerDao.findAllAnswer();
    }

    /**
    * @Author XuFengrui
    * @Description 根据回答编号查询回答
    * @Date 16:39 2020/3/29
    * @Param [id]
    * @return com.qa.system.entity.Answer
    **/
    @Override
    public Answer findAnswerById(int id) {
        return answerDao.findAnswerById(id);
    }

    /**
    * @Author XuFengrui
    * @Description 更改回答,若回答编号不存在则返回-1；若回答已被屏蔽返回0；若回答的问题已被屏蔽返回-2，若回答的问题已被终结返回-3，若回答的回答已被屏蔽返回-4，若更改成功则返回1
    * @Date 16:39 2020/3/29
    * @Param [id, answer]
    * @return int
    **/
    @Override
    public int updateAnswer(Answer answer) {
        if (answerDao.isAnswerExist(answer.getaAnswerId())) {
            if (answer.getShield() == 1) {
                if (questionDao.findQuestionById(answer.getaQuestionId()).getShield() == 1) {
                    if (questionDao.findQuestionById(answer.getaQuestionId()).getSignal() == 1) {
                        if (!answerDao.isAAnswerExist(answer.getAnswerId())) {
                            return answerDao.updateAnswer(answer);
                        } else if (findAnswerById(answer.getaAnswerId()).getShield() == 1) {
                            return answerDao.updateAnswer(answer);
                        } else {
                            return -4;
                        }
                    } else {
                        return -3;
                    }
                } else {
                    return -2;
                }
            } else {
                return 0;
            }
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 新增回答
    * @Date 16:39 2020/3/29
    * @Param [id, answer]
    * @return int
    **/
    @Override
    public int addAnswer(Answer answer) {
        if(!answerDao.isAnswerExist(answer.getaAnswerId())){
            if (userDao.findUserByName(answer.getAnswerer()).getShield() == 0) {
                answer.setShield(0);
            } else {
                answer.setShield(1);
            }
            return answerDao.addAnswer(answer);
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 删除回答
    * @Date 16:39 2020/3/29
    * @Param [id]
    * @return int
    **/
    @Override
    public int deleteAnswerById(int id) {
        Answer answer = answerDao.findAnswerById(id);
        if (answerDao.isAnswerExist(answer.getaAnswerId())) {
            if (answer.getShield() == 1) {
                if (questionDao.findQuestionById(answer.getaQuestionId()).getShield() == 1) {
                    if (questionDao.findQuestionById(answer.getaQuestionId()).getSignal() == 1) {
                        if (!answerDao.isAAnswerExist(answer.getAnswerId())) {
                            return answerDao.deleteAnswerById(id);
                        } else if (findAnswerById(answer.getaAnswerId()).getShield() == 1) {
                            return answerDao.deleteAnswerById(id);
                        } else {
                            return -4;
                        }
                    } else {
                        return -3;
                    }
                } else {
                    return -2;
                }
            } else {
                return 0;
            }
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 屏蔽回答
    * @Date 16:39 2020/3/29
    * @Param [answer]
    * @return int
    **/
    @Override
    public int blacklistAnswer(Answer answer) {
        if (answer.getShield() == 1) {
            answer.setShield(0);
            answerDao.updateAnswer(answer);
            return 1;
        }else {
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 解除屏蔽回答
    * @Date 16:40 2020/3/29
    * @Param [answer]
    * @return int
    **/
    @Override
    public int whitelistAnswer(Answer answer) {
        if (answer.getShield() == 0) {
            answer.setShield(1);
            answerDao.updateAnswer(answer);
            return 1;
        }else {
            return 0;
        }
    }

    /**
    * @Author XuFengrui
    * @Description
    * @Date 10:45 2020/3/30
    * @Param [answerList]
    * @return int
    **/
    @Override
    public int blacklistAnswers(List<Answer> answerList) {
        for (int i = answerList.size(); i > 0; i--) {
            if (answerList.get(i - 1).getShield() == 1) {
                answerList.get(i - 1).setShield(0);
                answerDao.updateAnswer(answerList.get(i - 1));
            }
        }
        return answerList.size();
    }

    /**
    * @Author XuFengrui
    * @Description
    * @Date 10:45 2020/3/30
    * @Param [answerList]
    * @return int
    **/
    @Override
    public int whitelistAnswers(List<Answer> answerList) {
        for (int i = answerList.size(); i > 0; i--) {
            if (answerList.get(i - 1).getShield() == 0) {
                answerList.get(i - 1).setShield(1);
                answerDao.updateAnswer(answerList.get(i - 1));
            }
        }
        return answerList.size();
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题编号查找该问题所有的回答
    * @Date 13:29 2020/3/30
    * @Param [id]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    @Override
    public List<Answer> findAnswersByQuestionId(int id) {
        return answerDao.findAnswersByQuestionId(id);
    }
}
