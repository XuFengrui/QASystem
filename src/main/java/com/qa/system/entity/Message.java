package com.qa.system.entity;

/**
 * @ClassName Message
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/5/7
 * @Version 1.0
 **/
public class Message {

    private int id;
    private String recipient;
    private String sender;
    private String details;
    private int questionId;
    private int answerId;
    private int commentId;
    private String time;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int aAnswerId) {
        this.commentId = aAnswerId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
