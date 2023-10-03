package com.example.exam;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
public class Admin_Exam_Fragment extends Fragment {
    SharedPreferences obj;
    ArrayList<Subj_Data_Model> studdatamodel= new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String auth_email = "sakshi@gmail.com";
    Button btn;
    String auth_pass = "S@k$hi121";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__exam_, container, false);
        ListView lv = view.findViewById(R.id.craeted_list_admin);
        btn = view.findViewById(R.id.create_exam);
        obj = requireActivity().getSharedPreferences("ArrayList",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = obj.edit();
        mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            db.collection("Subject").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String subj = document.getString("Sub_Name");
                                                    studdatamodel.add(new Subj_Data_Model(subj)); }
                                            } else {
                                                Log.d("here", "Error getting documents: ", task.getException());
                                            }
                                            Stud_Sch_List adapter=new Stud_Sch_List(studdatamodel, requireActivity().
                                                    getApplicationContext());
                                            lv.setAdapter(adapter); }
                                    });
                        } }
                });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), Create_Exam.class);
                startActivity(intent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                obj = requireActivity().getSharedPreferences("Pref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = obj.edit();
                myEdit.putString("Subj", lv.getItemAtPosition(position).toString());
                myEdit.apply();
                Intent intent = new Intent(getContext().getApplicationContext(),Exam_Panel.class);
                startActivity(intent);
            }
        });
        return view;
    }
}