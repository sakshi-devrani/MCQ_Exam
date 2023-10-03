package com.example.exam;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class User_Selection extends AppCompatActivity {
  Button admin,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);
        admin=findViewById(R.id.admin);
        user = findViewById(R.id.user);
        isOnline();
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Pref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = pref.edit();
                myEdit.putString("type", "admin");
                myEdit.apply();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Pref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = pref.edit();
                myEdit.putString("type", "user") ;
                myEdit.apply();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });}
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false; }
        return true; } }