package com.example.exam;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
public class Create_Exam extends AppCompatActivity {
    EditText ans,o1,o2,o3,o4,subj,que;
    Button submit,add;
    String subj1,que1,ans1,op1,op2,op3,op4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);
        submit=findViewById(R.id.Submit);
        add =findViewById(R.id.add);
        subj= findViewById(R.id.edit_subj);
        que=findViewById(R.id.edit_que);
        ans=findViewById(R.id.edit_ans);
        o1=findViewById(R.id.edit_o1);
        o2=findViewById(R.id.edit_o2);
        o3=findViewById(R.id.edit_o3);
        o4=findViewById(R.id.edit_o4);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subj1 = subj.getText().toString();
                que1 = que.getText().toString();
                ans1 = ans.getText().toString();
                op1 = o1.getText().toString();
                op2 = o2.getText().toString();
                op3 = o3.getText().toString();
                op4 = o4.getText().toString();

            }
        });
    }
}