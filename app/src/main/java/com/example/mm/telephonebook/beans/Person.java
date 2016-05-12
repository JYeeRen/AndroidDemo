package com.example.mm.telephonebook.beans;

import java.io.Serializable;

/**
 * Name:  Person
 * Author:  Lambo
 * Function:  联系人实体类,用于封装联系人信息
 */

public class Person implements Serializable {

    //  Fields

    private String name;            //姓名
    private String connection;      //关系
    private String phoneNumber;     //联系电话

    // Constructor

    public Person() {
        super();
    }

    public Person(String name, String connection, String phoneNumber) {
        this.name = name;
        this.connection = connection;
        this.phoneNumber = phoneNumber;
    }

    //  Get && Set

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}