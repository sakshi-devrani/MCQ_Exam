package com.example.exam;

public class Result_Data_Model  {
    String  id ,name, subj, result;


    public Result_Data_Model( String name,  String id,String subj,String result) {

        this.name = name;
        this.id = id;
        this.subj = subj;
        this.result=result;
    }
    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getsubj() {return subj;}
    public String getresult() {
        return result;
    }

}
