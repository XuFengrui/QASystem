package com.qa.system.entity;

/**
 * @ClassName VoComment
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/5/12
 * @Version 1.0
 **/
public class VoComment {

    private int answerId;
    private String details;
    private String byAnswerer;
    private String answerer;
    private int aAnswerId;
    private int shield;
    private String startTime;
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

    public String getByAnswerer() {
        return byAnswerer;
    }

    public void setByAnswerer(String byAnswerer) {
        this.byAnswerer = byAnswerer;
    }

    public String getAnswerer() {
        return answerer;
    }

    public void setAnswerer(String answerer) {
        this.answerer = answerer;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
