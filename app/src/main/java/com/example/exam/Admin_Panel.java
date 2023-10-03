package com.example.exam;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.exam.databinding.ActivityAdminPanelBinding;
public class Admin_Panel extends AppCompatActivity {
    ActivityAdminPanelBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminPanelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragments(new Admin_Home_Fragment());
        binding.BottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.admin_exam) {
                replacefragments(new Admin_Exam_Fragment()); }
            else if (itemId ==R.id.admin_result) {
                replacefragments(new Admin_Result_Fragment()); }
            else if(itemId==R.id.admin_home) {
                replacefragments(new Admin_Home_Fragment()); }
            return true;
        });
    }
    private void replacefragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit(); }
}