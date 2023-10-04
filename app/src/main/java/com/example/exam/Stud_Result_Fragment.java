package com.example.exam;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
public class Stud_Result_Fragment extends Fragment {
    ArrayList<Result_Data_Model> resultdatamodel = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";
    ListView list;
    AlertDialog.Builder  builder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_stud__result_, container, false);
        list= view.findViewById(R.id.stud_result_listview);
        Button btn = view.findViewById(R.id.stud_logout);
        SharedPreferences pref = requireActivity().getSharedPreferences("Pref",MODE_PRIVATE);
        String id = pref.getString("id", null);
        mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            db.collection("Result").whereEqualTo("Stud_id",id).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String id = document.getString("Stud_id");
                                                    String name = document.getString("Stud_Name");
                                                    String result = document.getString("Result");
                                                    String subj = document.getString("Subj_Name");
                                                    resultdatamodel.add(new Result_Data_Model(name,id,subj,result));
                                                }
                                            } else {
                                                Log.d("here", "Error getting documents: ", task.getException());
                                            }
                                            Result_Data_Adapter adapter=new Result_Data_Adapter(resultdatamodel,requireActivity().getApplicationContext());
                                            list.setAdapter(adapter); }
                                    });
                        } }
                });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(Stud_Result_Fragment.this.getContext());
                builder.setMessage("Do you want to exit?");
                builder.setTitle("Alert!");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    SharedPreferences preferences = getContext().getSharedPreferences("YourAppPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(getContext().getApplicationContext(),User_Selection.class);
                    startActivity(intent);
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }
}