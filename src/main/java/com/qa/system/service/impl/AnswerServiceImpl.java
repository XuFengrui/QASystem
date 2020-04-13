package com.qa.system.service.impl;

import com.qa.system.dao.AnswerDao;
import com.qa.system.dao.QuestionDao;
import com.qa.system.dao.UserDao;
import com.qa.system.entity.Answer;
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

    @Autowired
    QuestionDao questionDao;

    @Autowired
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
    * @Description 根据用户名查询该用户所发布的所有回答
    * @Date 23:32 2020/4/2
    * @Param [name]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    @Override
    public List<Answer> findAnswerByUserName(String name) {
        return answerDao.findAnswersByUserName(name);
    }

    /**
    * @Author XuFengrui
    * @Description 根据问题编号查询该问题下未被屏蔽的所有回答
    * @Date 0:57 2020/4/3
    * @Param [id]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    @Override
    public List<Answer> findWhiteAnswerByQuestionId(int id) {
        if (questionDao.isQuestionExist(id)) {
            return answerDao.findWhiteAnswersByQuestionId(id);
        } else {
            return null;
        }
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
                    if (questionDao.findQuestionById(answer.getaQuestionId()).getEnd() == 1) {
                        if (!answerDao.isAAnswerExist(answer.getAnswerId())) {
                            questionDao.updateQuestion(questionDao.findQuestionById(answer.getaQuestionId()));
                            return answerDao.updateAnswer(answer);
                        } else if (findAnswerById(answer.getaAnswerId()).getShield() == 1) {
                            questionDao.updateQuestion(questionDao.findQuestionById(answer.getaQuestionId()));
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
    * @Description 新增回答，-1表示该回答已存在,-2表示该回答回答的回答已被屏蔽，-3表示问题已被终结，-4表示该回答回答的问题已被屏蔽，返回1表示成功添加
    * @Date 16:39 2020/3/29
    * @Param [id, answer]
    * @return int
    **/
    @Override
    public int addAnswer(Answer answer) {
        if(!answerDao.isAnswerExist(answer.getAnswerId())){
            if (questionDao.findQuestionById(answer.getaQuestionId()).getShield() == 1) {
                if (questionDao.findQuestionById(answer.getaQuestionId()).getEnd() == 1) {
                    if (answer.getaAnswerId() == 0) {
                        if (userDao.findUserByName(answer.getAnswerer()).getShield() == 1) {
                            answer.setShield(1);
                            questionDao.updateQuestion(questionDao.findQuestionById(answer.getaQuestionId()));
                        } else {
                            answer.setShield(0);
                        }
                        return answerDao.addAnswer(answer);

                    } else if (answerDao.findAnswerById(answer.getaAnswerId()).getShield() == 1) {
                        if (userDao.findUserByName(answer.getAnswerer()).getShield() == 1) {
                            answer.setShield(1);
                        } else {
                            answer.setShield(0);
                        }
                        return answerDao.addAnswer(answer);
                    } else {
                        return -2;
                    }
                } else {
                    return -3;
                }
            } else {
                return -4;
            }
        }else {
            return -1;
        }
    }

    /**
    * @Author XuFengrui
    * @Description 删除回答，若回答编号不存在则返回-1；若回答已被屏蔽返回0；若回答的问题已被屏蔽返回-2，若回答的问题已被终结返回-3，若回答的回答已被屏蔽返回-4，若更改成功则返回1
    * @Date 16:39 2020/3/29
    * @Param [id]
    * @return int
    **/
    @Override
    public int deleteAnswerById(int id) {
        if (answerDao.isAnswerExist(id)) {
            Answer answer = answerDao.findAnswerById(id);
            if (answer.getShield() == 1) {
                if (questionDao.findQuestionById(answer.getaQuestionId()).getShield() == 1) {
                    if (questionDao.findQuestionById(answer.getaQuestionId()).getEnd() == 1) {
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
    * @Description 屏蔽回答,0表示该回答已被屏蔽，1表示成功屏蔽,-1表示该回答不存在
    * @Date 16:39 2020/3/29
    * @Param [answer]
    * @return int
    **/
    @Override
    public int blacklistAnswer(Answer answer) {
        if (answerDao.isAnswerExist(answer.getAnswerId())) {
            if (answerDao.findAnswerById(answer.getAnswerId()).getShield() == 1) {
                answer.setShield(0);
                answerDao.answerShield(answer);
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
    * @Description 解除屏蔽回答，0表示该回答未被屏蔽，1表示成功解除屏蔽,-1表示该回答不存在
    * @Date 16:40 2020/3/29
    * @Param [answer]
    * @return int
    **/
    @Override
    public int whitelistAnswer(Answer answer) {
        if (answerDao.isAnswerExist(answer.getAnswerId())) {
            if (answerDao.findAnswerById(answer.getAnswerId()).getShield() == 0) {
                answer.setShield(1);
                answerDao.answerShield(answer);
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
    * @Description 返回值为成功屏蔽的回答数量
    * @Date 10:45 2020/3/30
    * @Param [answerList]
    * @return int
    **/
    @Override
    public int blacklistAnswers(List<Answer> answerList) {
        for (int i = answerList.size(); i > 0; i--) {
            if (answerDao.findAnswerById(answerList.get(i - 1).getAnswerId()).getShield() == 1) {
                answerList.get(i - 1).setShield(0);
                answerDao.answerShield(answerList.get(i - 1));
            }
        }
        return answerList.size();
    }

    /**
    * @Author XuFengrui
    * @Description 返回值为成功解除屏蔽的回答数量
    * @Date 10:45 2020/3/30
    * @Param [answerList]
    * @return int
    **/
    @Override
    public int whitelistAnswers(List<Answer> answerList) {
        for (int i = answerList.size(); i > 0; i--) {
            if (answerDao.findAnswerById(answerList.get(i - 1).getAnswerId()).getShield() == 0) {
                answerList.get(i - 1).setShield(1);
                answerDao.answerShield(answerList.get(i - 1));
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
