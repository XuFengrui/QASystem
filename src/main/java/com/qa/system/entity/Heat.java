package com.qa.system.entity;

/**
 * @ClassName heat
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/15
 * @Version 1.0
 **/
public class Heat {

    private int id;
    private String phone;
    private int questionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
