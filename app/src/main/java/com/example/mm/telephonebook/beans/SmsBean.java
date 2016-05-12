package com.example.mm.telephonebook.beans;

import java.io.Serializable;

/**
 * Name:  SmsBean
 * Author:  Lambo
 * Function:  短信实体类,用于封装短信内容信息
 */
public class SmsBean implements Serializable{

    //  Fields

    private String type;            //短信类型
    private String date;            //短信时间
    private String address;         //电话号码
    private String body;            //短信内容
    private String read;            //阅读标记
    private String threadId;        //执行线程

    // Constructor


    public SmsBean() {
    }

    public SmsBean(String type, String date, String address, String body, String read, String threadId) {
        this.type = type;
        this.date = date;
        this.address = address;
        this.body = body;
        this.read = read;
        this.threadId = threadId;
    }

    //  Get && Set

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
