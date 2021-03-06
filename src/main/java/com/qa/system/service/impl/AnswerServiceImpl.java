package com.qa.system.service.impl;

import com.qa.system.dao.AnswerDao;
import com.qa.system.dao.MessageDao;
import com.qa.system.dao.QuestionDao;
import com.qa.system.dao.UserDao;
import com.qa.system.entity.*;
import com.qa.system.service.AnswerService;
import com.qa.system.utils.Base64Utils;
import com.qa.system.utils.SensitiveWordUtils;
import com.qa.system.utils.TimeSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    MessageDao messageDao;


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
    * @Description 查询所有未屏蔽回答
    * @Date 15:40 2020/6/2
    * @Param []
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    @Override
    public List<Answer> findAllWhiteAnswers() {
        return answerDao.findAllWhiteAnswers();
    }

    /**
    * @Author XuFengrui
    * @Description 查看所有已屏蔽回答
    * @Date 15:41 2020/6/2
    * @Param []
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    @Override
    public List<Answer> findAllBlackAnswers() {
        return answerDao.findAllBlackAnswers();
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
    * @Description 根据问题查询面向对象的回答数组voAnswer
    * @Date 9:35 2020/5/12
    * @Param [id]
    * @return java.util.List<com.qa.system.entity.VoAnswer>
    **/
    @Override
    public List<VoAnswer> findWhiteVoAnswersByQuestionId(int id) {
        List<VoAnswer> voAnswerList= answerDao.findWhiteVoAnswersByQuestionId(id);
        for (int i = 0; i < voAnswerList.size(); i++) {
            VoAnswer voAnswer = voAnswerList.get(i);
            voAnswer.setIcon(Base64Utils.getImageStr(userDao.findUserByName(voAnswer.getAnswerer()).getIcon()));
            voAnswerList.set(i,voAnswer);
        }
        return voAnswerList;
    }

    /**
    * @Author XuFengrui
    * @Description 根据回答查询面向对象的评论数组voComment
    * @Date 9:35 2020/5/12
    * @Param [id]
    * @return java.util.List<com.qa.system.entity.VoComment>
    **/
    @Override
    public List<VoComment> findWhiteVoCommentsByAnswerId(int id) {
        List<VoComment> commentList = new ArrayList<>();
        if (answerDao.findWhiteVoCommentsByAnswerId(id).size() > 0) {
            List<VoComment> comments = answerDao.findWhiteVoCommentsByAnswerId(id);
            for (int i = 0; i < comments.size(); i++) {
                commentList.addAll(findWhiteVoCommentsByAnswerId(comments.get(i).getAnswerId()));
            }
            VoComment voComment = answerDao.findWhiteVoCommentByCommentId(id);
            if (voComment.getaAnswerId() != 0) {
                User user = userDao.findUserByName(answerDao.findAnswerById(voComment.getaAnswerId()).getAnswerer());
                voComment.setIcon(user.getIcon());
                voComment.setByAnswerer(user.getName());
                commentList.add(voComment);
            }
        } else if (answerDao.findAnswerById(id).getaAnswerId() != 0) {
            VoComment voComment = answerDao.findWhiteVoCommentByCommentId(id);
            User user = userDao.findUserByName(answerDao.findAnswerById(voComment.getaAnswerId()).getAnswerer());
            voComment.setIcon(user.getIcon());
            voComment.setByAnswerer(user.getName());
            commentList.add(voComment);
        }
        TimeSort.voCommentListSort(commentList);
        return commentList;
    }

    /**
    * @Author XuFengrui
    * @Description 更改回答,若回答编号不存在则返回-1；若回答已被屏蔽返回0；若回答的问题已被屏蔽返回-2，若回答的问题已被终结返回-3，若回答的回答已被屏蔽返回-4，若更改成功则返回1
    * @Date 16:39 2020/3/29
    * @Param [id, answer]
    * @return int
    **/
    @Override
    public int updateAnswer(Answer answer) throws Exception {
        if (answerDao.isAnswerExist(answer.getAnswerId())) {
            if (answerDao.findAnswerById(answer.getAnswerId()).getShield() == 1) {
                if (questionDao.findQuestionById(answerDao.findAnswerById(answer.getAnswerId()).getaQuestionId()).getShield() == 1) {
                    if (questionDao.findQuestionById(answerDao.findAnswerById(answer.getAnswerId()).getaQuestionId()).getEnd() == 1) {
                        if (!answerDao.isAAnswerExist(answer.getAnswerId())) {
                            questionDao.updateQuestion(questionDao.findQuestionById(answerDao.findAnswerById(answer.getAnswerId()).getaQuestionId()));
                            answer.setDetails(SensitiveWordUtils.sensitiveHelper(answer.getDetails()));
                            return answerDao.updateAnswer(answer);
                        } else if ( answerDao.findAnswerById(answer.getAnswerId()).getaAnswerId() == 0 || answerDao.findAnswerById(answerDao.findAnswerById(answer.getAnswerId()).getaAnswerId()).getShield() == 1) {
                            questionDao.updateQuestion(questionDao.findQuestionById(answerDao.findAnswerById(answer.getAnswerId()).getaQuestionId()));
                            answer.setDetails(SensitiveWordUtils.sensitiveHelper(answer.getDetails()));
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
    public int addAnswer(Answer answer) throws Exception {
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
                        Message message = new Message();
                        message.setRecipient(questionDao.findQuestionById(answer.getaQuestionId()).getQuestioner());
                        message.setSender(answer.getAnswerer());
                        message.setQuestionId(answer.getaQuestionId());
                        message.setAnswerId(answerDao.maxAnswerId()+1);
                        messageDao.byAnswerMessage(message);
                        answer.setDetails(SensitiveWordUtils.sensitiveHelper(answer.getDetails()));
                        return answerDao.addAnswer(answer);

                    } else if (answerDao.findAnswerById(answer.getaAnswerId()).getShield() == 1) {
                        if (userDao.findUserByName(answer.getAnswerer()).getShield() == 1) {
                            answer.setShield(1);
                        } else {
                            answer.setShield(0);
                        }
                        Message message = new Message();
                        message.setRecipient(answerDao.findAnswerById(answer.getaAnswerId()).getAnswerer());
                        message.setSender(answer.getAnswerer());
                        message.setAnswerId(answer.getaAnswerId());
                        message.setCommentId(answerDao.maxAnswerId()+1);
                        messageDao.byCommentMessage(message);
                        answer.setDetails(SensitiveWordUtils.sensitiveHelper(answer.getDetails()));
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
            if (answerDao.findAnswerById(answer.getAnswerId()).getShield() == 1) {
                if (questionDao.findQuestionById(answer.getaQuestionId()).getShield() == 1) {
                    if (questionDao.findQuestionById(answer.getaQuestionId()).getEnd() == 1) {
                        if (!answerDao.isAAnswerExist(answer.getAnswerId()) || answerDao.findAnswerById(answer.getaAnswerId()).getShield() == 1) {
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
                Message message = new Message();
                message.setRecipient(answerDao.findAnswerById(answer.getAnswerId()).getAnswerer());
                message.setAnswerId(answer.getAnswerId());
                messageDao.answerBlackMessage(message);
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
                Message message = new Message();
                message.setRecipient(answerDao.findAnswerById(answer.getAnswerId()).getAnswerer());
                message.setAnswerId(answer.getAnswerId());
                messageDao.answerWhiteMessage(message);
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
    * @Description 屏蔽一组回答
    * @Date 10:45 2020/3/30
    * @Param [answerList]
    * @return int 返回值为成功屏蔽的回答数量
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
    * @Description 解除屏蔽一组回答
    * @Date 10:45 2020/3/30
    * @Param [answerList]
    * @return int 返回值为成功解除屏蔽的回答数量
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

    /**
    * @Author XuFengrui
    * @Description 模糊查询回答
    * @Date 11:52 2020/4/24
    * @Param [keyWord]
    * @return java.util.List<com.qa.system.entity.Answer>
    **/
    @Override
    public List<Answer> findAnswersByKeyword(String keyWord) {
        return answerDao.findAnswersByKeyword(keyWord);
    }
}
