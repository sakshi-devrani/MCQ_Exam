package com.example.exam.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exam.Admin_Home_Fragment;
import com.example.exam.Admin_Panel;
import com.example.exam.Data_Model;
import com.example.exam.R;
import com.example.exam.Stud_upd_del;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Stud_Add extends AppCompatActivity {
    EditText name;
    EditText id;
    EditText email;
    EditText contact ;

    EditText pwd;
    Button clear,submit;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private  String n1 , e1, c1, id1,p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_add);
        name = findViewById(R.id.stud_add_name);
        id= findViewById(R.id.stud_add_id);
        email = findViewById(R.id.stud_add_email);
        contact = findViewById(R.id.stud_add_number);
        submit= findViewById(R.id.stud_add_submit);
        clear = findViewById(R.id.stud_add_clear);
        pwd = findViewById(R.id.stud_add_pwd);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n1 = name.getText().toString();
                id1= id.getText().toString();
                e1 = email.getText().toString();
                c1 =  contact.getText().toString();
                p1 = pwd.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(n1)) {
                    name.setError("Please enter Course Name");
                } else if (TextUtils.isEmpty(id1)) {
                    id.setError("Please enter Course id");
                } else if (TextUtils.isEmpty(e1)) {
                    email.setError("Please enter email");
                }
                else if (TextUtils.isEmpty(c1)) {
                    contact.setError("Please enter contact");
                }else if (TextUtils.isEmpty(p1)) {
                    pwd.setError("Please enter password");
                }else {
                   // CollectionReference dbo = db.collection("Student_Info");

                 //  Data_Model dataModel= new Data_Model(n1,id1,e1,c1,p1);
                    Map<String, Object> data = new HashMap<>();
                    data.put("Stud_pwd", p1);
                    data.put("Stud_Name", n1);
                    data.put("Stud_Contact", c1);
                    data.put("Stud_Email", e1);
                    data.put("Stud_id", id1);
                    db.collection("Student_Info").document(n1).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Admin_Panel.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Unable to add user", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(" ");
                email.setText(" ");
                id.setText(" ");
                contact.setText(" ");
                pwd.setText(" ");
            }
        });
    }
}