package com.example.exam;

public class Question_data_model {
    String  id,que ,ans, o1, o2,o3,o4;
    int selectedanswer ;
        public Question_data_model(String que, String ans, String o1, String o2,String o3,String o4) {
        this.que=que;
        this.ans=ans;
        this.o1=o1;
        this.o2=o2;
        this.o3=o3;
        this.o4=o4;
        }
    public String getque() {
        return que;
    }
    public String geto1() {
        return o1;
    }
    public String geto2() {
        return o2;
    }
    public String geto3() {
        return o3;
    }
    public String geto4() {
        return o4;
    }
    public String getans(){return ans;}
}

