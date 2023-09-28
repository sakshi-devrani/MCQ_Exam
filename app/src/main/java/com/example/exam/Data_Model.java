package com.example.exam;

import android.app.Activity;

public class Data_Model extends Stud_Home_Fragment {
    String  id ,name, email, contact,pwd;


    public Data_Model( String name, String email, String contact, String id,String pwd) {

        this.name = name;
        this.email = email;
        this.contact = contact;
        this.id = id;
        this.pwd =pwd;
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

    public String getpwd() {
        return pwd;
    }

}

