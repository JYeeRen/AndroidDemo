package com.example.mm.telephonebook.beans;

import java.io.Serializable;

/**
 *   Name:  CallRecords
 *   Author:  Lambo
 *   Function: 通话记录实体类
 */
public class CallRecords implements Serializable {

    //  Fields

    private String address;     //通话号码
    private String name;        //通话名称
    private String date;        //通话时间
    private String type;        //通化类型

    // Constructor


    public CallRecords() {
    }

    public CallRecords(String address, String name, String date, String type) {
        this.address = address;
        this.name = name;
        this.date = date;
        this.type = type;
    }

    //  Get && Set

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
