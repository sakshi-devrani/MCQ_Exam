package com.example.exam;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Objects;
public class Login extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    EditText loginUsername, loginPassword;
    Button loginButton;
    String auth_email = "sakshi@gmail.com";
    String auth_pass = "S@k$hi121";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.btn_login);
        loginUsername= findViewById(R.id.Email);
        loginPassword = findViewById(R.id.Password);
        SharedPreferences pref = getSharedPreferences("Pref",MODE_PRIVATE);
        String type = pref.getString("type", null);
        if(Objects.equals(type, "admin")){
            String log = pref.getString("logged_in",null);
            if(log!=null){
                Intent intent = new Intent(getApplicationContext(),Admin_Panel.class);
                startActivity(intent); }
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if (!validateUsername() | !validatePassword()) {
                 Toast.makeText(getApplicationContext(),
                         "Enter proper values", Toast.LENGTH_LONG).show();
             } else {
                 checkUser(); }
         }
     });
    }
    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true; }
    }
    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true; }
    }
    public void checkUser(){
        SharedPreferences pref = getSharedPreferences("Pref",MODE_PRIVATE);
        String type = pref.getString("type", null);
        String email = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        // Execute the query and get the result
        mAuth.signInWithEmailAndPassword(auth_email, auth_pass).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {

                            if (Objects.equals(type, "admin"))
                            {
                                CollectionReference collectionRef = db.collection("Admin");
                            // Create a query to find documents that match the values
                            Query query = collectionRef.whereEqualTo("Admin_Email", email)
                                    .whereEqualTo("Admin_Password", password);
                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        // Get the number of documents that match the query
                                        int count = task.getResult().size();
                                        if (count > 0) {
                                            // The values exist in the collection
                                            Toast.makeText(getApplicationContext(),
                                                    "Welcome"
                                                    , Toast.LENGTH_SHORT).show();
                                            DocumentSnapshot document = task.getResult()
                                                    .getDocuments().get(0);
                                            String id = document.getId();
                                            String name = document.getString("Admin_Email");
                                            String age = document.getString("Admin_Password");
                                            SharedPreferences.Editor myEdit = pref.edit();
                                            myEdit.putString("logged_in", (id)) ;
                                            myEdit.apply();
                                            Intent intent = new Intent(getApplicationContext(),Admin_Panel.class);
                                            startActivity(intent);
                                        } else {
                                            // The values do not exist in the collection
                                            Toast.makeText(getApplicationContext(),
                                                    "The User don,t exist ",
                                                    Toast.LENGTH_SHORT).show(); }
                                    } else {
                                        // The query failed
                                        Toast.makeText(getApplicationContext(),
                                                "The query failed: " +
                                                        Objects.requireNonNull(task.getException())
                                                                .getMessage(),
                                                Toast.LENGTH_SHORT).show(); }
                                }
                            });
                        } else
                            {
                                CollectionReference collectionRef = db.collection("Student_Info");
                                // Create a query to find documents that match the values
                                Query query = collectionRef.whereEqualTo("Stud_Email", email)
                                        .whereEqualTo("Stud_pwd", password);
                                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            // Get the number of documents that match the query
                                            int count = task.getResult().size();
                                            if (count > 0) {
                                                // The values exist in the collection
                                                Toast.makeText(getApplicationContext(),
                                                        "welcome"
                                                        , Toast.LENGTH_SHORT).show();
                                                // Get the first document that matches the query
                                                DocumentSnapshot document = task.getResult()
                                                        .getDocuments().get(0);
                                                String id = document.getId();
                                                String name = document.getString("Stud_Name");
                                                String contact = document.getString("Stud_Contact");
                                                String email = document.getString("Stud_Email");
                                                SharedPreferences pref = getSharedPreferences("Pref",MODE_PRIVATE);
                                                SharedPreferences.Editor myEdit = pref.edit();
                                                myEdit.putString("id", id) ;
                                                myEdit.putString("name", name) ;
                                                myEdit.putString("email", email) ;
                                                myEdit.putString("contact", contact) ;
                                                myEdit.apply();
                                                Intent intent = new Intent(getApplicationContext(),Stud_Panel.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getApplicationContext(),
                                                        "User Not Found ",
                                                        Toast.LENGTH_SHORT).show(); }
                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "The query failed: " +
                                                            Objects.requireNonNull(task.getException())
                                                                    .getMessage(),
                                                    Toast.LENGTH_SHORT).show(); }
                                    }
                                });
                            } }
                    }}
        );
    }
}
