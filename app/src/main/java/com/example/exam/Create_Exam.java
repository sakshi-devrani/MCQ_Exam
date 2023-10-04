package com.example.exam;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Create_Exam extends AppCompatActivity {
    EditText ans,o1,o2,o3,o4,subj,que;
    Button submit,add;
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
            if(TextUtils.isEmpty(subj1) && TextUtils.isEmpty(que1)&& TextUtils.isEmpty(ans1)&& TextUtils.isEmpty(op1)&& TextUtils.isEmpty(op2)&& TextUtils.isEmpty(op3)&& TextUtils.isEmpty(op4))
            {
                Toast.makeText(getApplicationContext(), "Please Check all Fields " , Toast.LENGTH_SHORT).show();
            }else {
                mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Task<QuerySnapshot> collectionRef = db.collection("Question_Bank")
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int count = task.getResult().size();
                                            if (count > 0) {
                                                Map<String, Object> data = new HashMap<>();
                                                data.put("Sub_Name", subj1);
                                                data.put("que", que1);
                                                data.put("ans", ans1);
                                                data.put("o1", op1);
                                                data.put("o2", op2);
                                                data.put("o3", op3);
                                                data.put("o4", op4);
                                                db.collection("Question_Bank").document(subj1).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(getApplicationContext(), "Question Added", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), Create_Exam.class);
                                                        startActivity(intent); }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Unable to add Question", Toast.LENGTH_SHORT).show(); }
                                                });
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "The query failed: " +
                                                            Objects.requireNonNull(task.getException())
                                                                    .getMessage(),
                                                    Toast.LENGTH_SHORT).show(); }
                                    }
                                });
                            }
                        });
            }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Admin_Panel.class);
                startActivity(intent);
            }
        });
    }
}