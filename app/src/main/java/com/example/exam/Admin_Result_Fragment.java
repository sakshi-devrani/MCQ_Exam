package com.example.exam;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Admin_Result_Fragment extends Fragment {
    ArrayList<Result_Data_Model> resultdatamodel = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";
    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_admin__result_, container, false);
        list= view.findViewById(R.id.admin_result_listview);
        mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            db.collection("Result").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String id = document.getString("Stud_id");
                                                    String name = document.getString("Stud_Name");
                                                    String result = document.getString("Result");
                                                    String subj = document.getString("Subj_Name");
                                                    resultdatamodel.add(new Result_Data_Model(name,id,result,subj));
                                                }
                                            } else {
                                                Log.d("here", "Error getting documents: ", task.getException());
                                            }
                                            Result_Data_Adapter adapter=new Result_Data_Adapter(resultdatamodel, requireActivity().getApplicationContext());
                                            list.setAdapter(adapter);
                                        }
                                    });
                        }
                    }
                });
        return view;
    }
}