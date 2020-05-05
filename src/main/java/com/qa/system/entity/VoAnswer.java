package com.qa.system.entity;

/**
 * @ClassName VoAnswer
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/5/5
 * @Version 1.0
 **/
public class VoAnswer {

    private int answerId;
    private String details;
    private String answerer;
    private int aQuestionId;
    private int aAnswerId;
    private int shield;
    private String time;
    private String icon;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAnswerer() {
        return answerer;
    }

    public void setAnswerer(String answerer) {
        this.answerer = answerer;
    }

    public int getaQuestionId() {
        return aQuestionId;
    }

    public void setaQuestionId(int aQuestionId) {
        this.aQuestionId = aQuestionId;
    }

    public int getaAnswerId() {
        return aAnswerId;
    }

    public void setaAnswerId(int aAnswerId) {
        this.aAnswerId = aAnswerId;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
