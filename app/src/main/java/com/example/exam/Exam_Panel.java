package com.example.exam;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Exam_Panel extends AppCompatActivity {
    ArrayList<Question_data_model> quedatamodel= new ArrayList<>();
    ListView lv;
    Button btn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";
    Exam_Question_Adapter adapter;
    int result;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_panel);
        lv  = findViewById(R.id.question_listview_show);
        btn = findViewById(R.id.btn_submit);
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
                                            adapter=new Exam_Question_Adapter(quedatamodel,
                                                    getApplicationContext());
                                            lv.setAdapter(adapter); }
                                    });
                        } }
                });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Pref",MODE_PRIVATE);
                String name1 = pref.getString("name", null);
                String id1 = pref.getString("id", null);
                ArrayList<String> selectedItemsText = adapter.getSelectedItemsText();
                ArrayList<String> correctItemsText = adapter.getCorrectItemsText();
                result =0;
                for (int i = 0; i < selectedItemsText.size(); i++) {
                    String item1 = selectedItemsText.get(i);
                    String item2 = correctItemsText.get(i);
                    if(item1.equals(item2)){
                        result += 1;
                    }
                }
                mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Task<QuerySnapshot> collectionRef = db.collection("Result")
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    int count = task.getResult().size();
                                                    if (count > 0) {
                                                        Map<String, Object> data = new HashMap<>();
                                                        data.put("Subj_Name", subj);
                                                        data.put("Stud_Name",name1);
                                                        data.put("Result",result);
                                                        data.put("Stud_id",id1);
                                                        db.collection("Result").document(id1).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(getApplicationContext(), "Result Added", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getApplicationContext(), Stud_Panel.class);
                                                                startActivity(intent); }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getApplicationContext(), "Unable to add Result", Toast.LENGTH_SHORT).show(); }
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
        });
    }
}