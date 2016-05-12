package com.example.mm.telephonebook.beans;


import java.io.Serializable;

/**
 *   Name:  ContactNumber
 *   Author:  Lambo
 *   Function: 联系号码实体类
 */
public class ContactNumber implements Serializable {

    //  Fields

    private String name;            //联系人姓名
    private String contactnumber;   //通话号码


    // Constructor

    public ContactNumber() {
    }

    public ContactNumber(String name, String contactnumber) {
        this.name = name;
        this.contactnumber = contactnumber;
    }

    //  Get && Set

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }
}
