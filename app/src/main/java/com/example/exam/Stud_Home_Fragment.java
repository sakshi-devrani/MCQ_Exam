package com.example.exam;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Stud_Home_Fragment extends Fragment {
    TextView id ,name,email,contact ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_stud__home_, container, false);
        name = view.findViewById(R.id.stud_home_name);
        email = view.findViewById(R.id.stud_home_email);
        id = view.findViewById(R.id.stud_home_id);
        contact = view.findViewById(R.id.stud_home_contact);
        // Inflate the layout for this fragment
        SharedPreferences pref = requireActivity().getSharedPreferences("Pref",MODE_PRIVATE);
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
        return view;
    }
}