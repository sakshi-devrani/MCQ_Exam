package com.example.exam;
import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Admin_Exam_Fragment extends Fragment {
    SharedPreferences obj;
    ArrayList<Subj_Data_Model> studdatamodel= new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String auth_email = "sakshi@gmail.com";
    Button btn ;
    EditText create ;
    String auth_pass = "S@k$hi121";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__exam_, container, false);
        ListView lv = view.findViewById(R.id.craeted_list_admin);
        create = view.findViewById(R.id.create);
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

                String edit = create.getText().toString();

                if (TextUtils.isEmpty(edit)) {
                    create.setError("Enter Subject Name ");
                }
                else {
                    mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    CollectionReference collectionRef = db.collection("Subject");
                                    Query query = collectionRef.whereEqualTo("Sub_Name", edit);
                                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                int count = task.getResult().size();
                                                if (count > 0) {
                                                    Toast.makeText(getContext().getApplicationContext(),
                                                            "Duplicate values "
                                                            , Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Map<String, Object> data = new HashMap<>();
                                                    data.put("Sub_Name", edit);
                                                    db.collection("Subject").document(edit).set(data)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(getContext().getApplicationContext(), "Subject Added", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(getContext().getApplicationContext(), Create_Exam.class);
                                                                    startActivity(intent); }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getContext().getApplicationContext(), "Unable to add subject", Toast.LENGTH_SHORT).show(); }
                                                            });
                                                }
                                            } else {
                                                Toast.makeText(getContext().getApplicationContext(),
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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(Admin_Exam_Fragment.this.getContext());
                builder.setMessage("Do you want to Delete");
                builder.setTitle("Alert!");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    Subj_Data_Model data = (Subj_Data_Model) arg0.getItemAtPosition(position);
                    String item = data.getsubj();
                    db.collection("Subject").document(item).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext().getApplicationContext(), "Subject Deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext().getApplicationContext(), Admin_Panel.class);
                                    startActivity(intent); }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext().getApplicationContext(), "Unable to delete subject", Toast.LENGTH_SHORT).show(); }
                            });
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