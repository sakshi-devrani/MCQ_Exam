package com.example.exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Stud_upd_del extends AppCompatActivity {
    EditText name,email,contact;
    TextView id;
    Button Del , update;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_upd_del);
        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        id = findViewById(R.id.edit_id);
        contact = findViewById(R.id.edit_number);
        Del = findViewById(R.id.Delete);
        update= findViewById(R.id.Update);

        SharedPreferences pref = getSharedPreferences("Myfile",MODE_PRIVATE);
        String name1 = pref.getString("name", null);
        String email1 = pref.getString("email", null);
        String id1 = pref.getString("id", null);
        String contact1 = pref.getString("contact", null);

        if(name1 != null)
        {
            name.setText(name1);
            email.setText(email1);
            id.setText(id1);
            contact.setText(contact1);
        }
        Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Student_Info").document(id1).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(getApplicationContext(),Admin_Panel.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Unable to delete user", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                String contact1 = contact.getText().toString();
                if (!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(email1)) {
                    assert id1 != null;
                    DocumentReference myRef = db.collection("Student_Info").document(id1);
                 //   if(name == db.collection("Student_Info").) {
                        myRef.update("Stud_Name", name1);
                        myRef.update("Stud_Email", email1);
                        myRef.update("Stud_Contact", contact1);
                    Intent intent = new Intent(getApplicationContext(),Admin_Panel.class);
                    startActivity(intent);
                 //   }
                } else
                    Toast.makeText(getApplicationContext(), "Unable to update user", Toast.LENGTH_SHORT).show();
            }

        });
    }
}