package com.example.exam;

import android.app.Activity;

public class Data_Model {
    String  id ,name, email, contact;


    public Data_Model( String name, String email, String contact, String id) {

        this.name = name;
        this.email = email;
        this.contact = contact;
        this.id = id;
    }

    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getemail() {
        return email;
    }

    public String getContact() {
        return contact;
    }
}

