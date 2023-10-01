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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.regex.Pattern;

public class Stud_upd_del extends AppCompatActivity {
    EditText name,email,contact,pass;
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
        pass = findViewById(R.id.edit_pass);

        SharedPreferences pref = getSharedPreferences("Myfile",MODE_PRIVATE);
        String name1 = pref.getString("name", null);
        String email1 = pref.getString("email", null);
        String id1 = pref.getString("id", null);
        String contact1 = pref.getString("contact", null);
        String pass1= pref.getString("pass",null);

        if(name1 != null)
        {
            name.setText(name1);
            email.setText(email1);
            id.setText(id1);
            contact.setText(contact1);
            pass.setText(pass1);
        }
        Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Student_Info").document(id1).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_SHORT).show();
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
                String pass1 = pass.getText().toString();
                Pattern uppercase = Pattern.compile("[A-Z]");
                Pattern lowercase = Pattern.compile("[a-z]");
                Pattern digit = Pattern.compile("[0-9]");
                Pattern character = Pattern.compile("[!,#,$,%,^,&,*,~]");
                if (TextUtils.isEmpty(email1)) {
                    email.setError("Please Enter Student Name");
                }
                else if (TextUtils.isEmpty(email1)|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    email.setError("Please Enter email Proper Format ");
                }
                else if (!lowercase.matcher(pass1).find()) {
                    pass.setError("please include Lower case also ");
                }
                else if (!uppercase.matcher(pass1).find()) {
                    pass.setError("please include uppercase case also ");
                }
                // if digit is not present
                else if (!digit.matcher(pass1).find()) {
                    pass.setError("please include Numberic digit also ");
                }
                else if (!character.matcher(pass1).find()) {
                    pass.setError("please include special character also ");
                }
                else if (TextUtils.isEmpty(pass1)) {
                    pass.setError("Please Enter Password");
                } else if ( pass1.length() < 6 || pass1.length() > 12) {
                    pass.setError("between 6 and 12 alphanumeric characters");
                }
                else if (TextUtils.isEmpty(contact1)) {
                    contact.setError("Please Enter Contact");
                }
                else if ( contact1.length() != 10) {
                    contact.setError("Only 10 Digit Contact ");
                }
                else  {
                    if (!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(email1)) {
                        assert id1 != null;
                        {
                            CollectionReference collectionRef = db.collection("Student_Info");
                            // Create a query to find documents that match the values
                            Query query = collectionRef.whereEqualTo("Stud_Email", email1);
                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        // Get the number of documents that match the query
                                        int count = task.getResult().size();
                                        if (count > 0) {
                                            // The values exist in the collection
                                            Toast.makeText(getApplicationContext(),
                                                    "Duplicate Email Id"
                                                    , Toast.LENGTH_SHORT).show();
                                            // Get the first document that matches the query
                                        } else {
                                            DocumentReference myRef = db.collection("Student_Info").document(id1);
                                            myRef.update("Stud_Name", name1);
                                            myRef.update("Stud_Email", email1);
                                            myRef.update("Stud_Contact", contact1);
                                            myRef.update("Stud_pwd", pass1);
                                            Toast.makeText(getApplicationContext(), "User updated", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Admin_Panel.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        // The query failed
                                        Toast.makeText(getApplicationContext(),
                                                "The query failed: " +
                                                        Objects.requireNonNull(task.getException())
                                                                .getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
            };
        }
        );}
}
