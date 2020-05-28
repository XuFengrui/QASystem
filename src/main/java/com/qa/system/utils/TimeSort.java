package com.qa.system.utils;

import com.qa.system.entity.Answer;
import com.qa.system.entity.Question;
import com.qa.system.entity.VoComment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TimeSort
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/3/30
 * @Version 1.0
 **/
@Component
public class TimeSort {

    /**
    * @Author XuFengrui
    * @Description 回答按照时间降序排列
    * @Date 12:29 2020/3/30
    * @Param [answerList]
    * @return void
    **/
    public static void answerListSort(List<Answer> answerList) {
        Collections.sort(answerList, new Comparator<Answer>() {
            @Override
            public int compare(Answer o1, Answer o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date d1 = format.parse(o1.getTime());
                    Date d2 = format.parse(o2.getTime());
                    if (d1.getTime() > d2.getTime()) {
                        return -1;
                    } else if (d1.getTime() < d2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    /**
    * @Author XuFengrui
    * @Description 问题按照时间降序排列
    * @Date 12:18 2020/3/30
    * @Param [questionList]
    * @return void
    **/
    public static void questionListSort(List<Question> questionList) {
        Collections.sort(questionList, new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date d1 = format.parse(o1.getLastTime());
                    Date d2 = format.parse(o2.getLastTime());
                    if (d1.getTime() > d2.getTime()) {
                        return -1;
                    } else if (d1.getTime() < d2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    /**
    * @Author XuFengrui
    * @Description 将评论按照发布时间降序排序
    * @Date 12:49 2020/5/15
    * @Param [voCommentList]
    * @return void
    **/
    public static void voCommentListSort(List<VoComment> voCommentList) {
        Collections.sort(voCommentList, new Comparator<VoComment>() {
            @Override
            public int compare(VoComment o1, VoComment o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date d1 = format.parse(o1.getStartTime());
                    Date d2 = format.parse(o2.getStartTime());
                    if (d1.getTime() < d2.getTime()) {
                        return -1;
                    } else if (d1.getTime() > d2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
