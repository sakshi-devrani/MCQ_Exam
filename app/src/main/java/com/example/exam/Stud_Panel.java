package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.exam.databinding.ActivityAdminPanelBinding;
import com.example.exam.databinding.ActivityStudPanelBinding;

public class Stud_Panel extends AppCompatActivity {
    ActivityStudPanelBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStudPanelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragments(new Stud_Home_Fragment());
        binding.BottomNavStud.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.stud_exam) {
                replacefragments(new Stud_Exam_Fragment());
            }
            else if (itemId ==R.id.stud_result) {
                replacefragments(new Stud_Result_Fragment());
            }
            else if(itemId==R.id.stud_home)
            {
                replacefragments(new Stud_Home_Fragment());
            }
            return true;
        });
    }
    private void replacefragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}