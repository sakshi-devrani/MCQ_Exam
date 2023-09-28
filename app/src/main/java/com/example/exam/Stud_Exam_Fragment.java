package com.example.exam;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Stud_Exam_Fragment extends Fragment {
    ArrayList<Stud_Data_Model> studdatamodel= new ArrayList<>();
    ArrayList<Question_data_model> quedatamodel= new ArrayList<>();
    ListView listView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";
    SharedPreferences obj;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_stud__exam_, container, false);
        listView = view.findViewById(R.id.Stud_exam_listview);
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
                                                    studdatamodel.add(new Stud_Data_Model(subj));
                                                }
                                            } else {
                                                Log.d("here", "Error getting documents: ", task.getException());
                                            }
                                            Stud_Sch_List adapter=new Stud_Sch_List(studdatamodel, requireActivity().getApplicationContext());
                                            listView.setAdapter(adapter);
                                        }
                                    });
                        }
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                obj = requireActivity().getSharedPreferences("Pref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = obj.edit();
                myEdit.putString("Subj", listView.getItemAtPosition(position).toString());
                myEdit.apply();
                Intent intent = new Intent(getContext().getApplicationContext(), Exam_Panel.class);
                startActivity(intent);
            }
        });
            return view;
}
}