package com.example.exam;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Exam_Panel extends AppCompatActivity {
    ArrayList<Question_data_model> quedatamodel= new ArrayList<>();
    ListView lv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_panel);
        lv  = findViewById(R.id.question_listview_show);
        SharedPreferences pref = getSharedPreferences("Pref",MODE_PRIVATE);
        String subj = pref.getString("Subj", null);
        mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            db.collection("Question_Bank").whereEqualTo("Sub_Name",subj).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String que = document.getString("que");
                                                    String o1 = document.getString("o1");
                                                    String o2 = document.getString("o2");
                                                    String o3 = document.getString("o3");
                                                    String o4 = document.getString("o4");
                                                    String ans = document.getString("ans");
                                                    quedatamodel.add(new Question_data_model(que, ans, o1, o2, o3, o4)); }
                                            } else {
                                                Log.d("here", "Error getting documents: ", task.getException());
                                            }
                                            Exam_Question_Adapter adapter=new Exam_Question_Adapter(quedatamodel,
                                                    getApplicationContext());
                                            lv.setAdapter(adapter); }
                                    });
                        } }
                });
    }
}