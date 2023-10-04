package com.example.exam;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Stud_Add extends AppCompatActivity {
    EditText name;
    EditText id;
    EditText email;
    EditText contact ;

    EditText pwd;
    Button submit;
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";

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
        pwd = findViewById(R.id.stud_add_pwd);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n1 = name.getText().toString();
                id1= id.getText().toString();
                e1 = email.getText().toString();
                c1 =  contact.getText().toString();
                p1 = pwd.getText().toString();
                Pattern digit = Pattern.compile("[0-9]");
                Pattern character = Pattern.compile("[!,#,$,%,^,&,*,~]");

                 if (TextUtils.isEmpty(id1)) {
                    id.setError("Please Enter Student Id ");
                }
                else if (!digit.matcher(id1).find()) {
                    id.setError("please include Numberic digit only ");
                }
                else if (TextUtils.isEmpty(n1)) {
                    name.setError("Please Enter Student Name");
                }
                else if (TextUtils.isEmpty(e1)|| !android.util.Patterns.EMAIL_ADDRESS.matcher(e1).matches()) {
                    email.setError("Please Enter email Proper Format ");
                }
               else if (!digit.matcher(p1).find()) {
                    pwd.setError("please include Numberic digit also ");
                }
                else if (!character.matcher(p1).find()) {
                    pwd.setError("please include special character also ");
                }
                else if (TextUtils.isEmpty(p1)) {
                    pwd.setError("Please Enter Password");
                } else if ( pwd.length() < 6 || pwd.length() > 12) {
                    pwd.setError("between 6 and 12 alphanumeric characters");

                }
                else if (TextUtils.isEmpty(c1)) {
                    contact.setError("Please Enter Contact");
                }
                else if ( c1.length() != 10) {
                    contact.setError("Only 10 Digit Contact ");
                }
                else {
                    mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                                task -> {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        CollectionReference collectionRef = db.collection("Student_Info");
                                        Query query = collectionRef.whereEqualTo("Stud_Email", e1).whereEqualTo("Stud_id", id1);
                                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    int count = task.getResult().size();
                                                    if (count > 0) {
                                                        Toast.makeText(getApplicationContext(),
                                                                "Duplicate values "
                                                                , Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Map<String, Object> data = new HashMap<>();
                                                        data.put("Stud_pwd", p1);
                                                        data.put("Stud_Name", n1);
                                                        data.put("Stud_Contact", c1);
                                                        data.put("Stud_Email", e1);
                                                        data.put("Stud_id", id1);
                                                        db.collection("Student_Info").document(id1).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getApplicationContext(), Admin_Panel.class);
                                                                startActivity(intent); }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getApplicationContext(), "Unable to add user", Toast.LENGTH_SHORT).show(); }
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
            }}
        );

    }
}