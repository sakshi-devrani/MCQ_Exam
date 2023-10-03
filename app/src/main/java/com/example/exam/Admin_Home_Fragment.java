package com.example.exam;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Admin_Home_Fragment extends Fragment {
    ArrayList<Data_Model> datamodel= new ArrayList<>();
    SharedPreferences obj;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";
    ListView list;

    FloatingActionButton add_stud;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_admin__home_, container, false);
        list= view.findViewById(R.id.admin_listview);
        add_stud = view.findViewById(R.id.add_button);
        mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            db.collection("Student_Info").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String id = document.getId();
                                                    String name = document.getString("Stud_Name");
                                                    String contact = document.getString("Stud_Contact");
                                                    String email = document.getString("Stud_Email");
                                                    datamodel.add(new Data_Model(name,email,contact,id));
                                                }
                                            } else {
                                                Log.d("here", "Error getting documents: ", task.getException());
                                            }
                                            admin_home_listveiw_Adapter adapter=new admin_home_listveiw_Adapter(datamodel, requireActivity().getApplicationContext());
                                            list.setAdapter(adapter);
                                        }
                                    });
                        }
                    }
                });
        add_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), Stud_Add.class);
                startActivity(intent);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Data_Model data = (Data_Model) list.getItemAtPosition(position);
                String[] get = new String[4];
                get[0] = data.getname();
                get[1] = data.getemail();
                get[2] = data.getid();
                get[3] = data.getContact();
               obj = requireActivity().getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = obj.edit();
                editor.putString("name",get[0]);
                editor.putString("email",get[1]);
                editor.putString("id",get[2]);
                editor.putString("contact",get[3]);
                editor.apply();
                Intent intent = new Intent(requireActivity().getApplicationContext(),Stud_upd_del.class);
                startActivity(intent);
            }
        });
        return view;
        }
}
