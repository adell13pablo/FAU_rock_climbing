package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentProfile extends AppCompatActivity {

    TextView name, l_name, age, level, znumber;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!SharedPreferencesManager.getInstance(getApplicationContext()).isLoggedIn()){
            startActivity(new Intent(getApplicationContext(), StudentLogIn.class));
        }

        FloatingActionButton fab = findViewById(R.id.fab_student_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentMainActivity.class ));
            }
        });

        name = findViewById(R.id.student_profile_name);
        l_name = findViewById(R.id.student_profile_l_name);
        age = findViewById(R.id.student_profile_age);
        level = findViewById(R.id.student_profile_level);
        znumber = findViewById(R.id.student_profile_znumber);



        //Use the SharedPreferences Instance to get the data , therefore we don't need to use the bundle

       Student student = SharedPreferencesManager.getInstance(getApplicationContext()).getStudent();

        name.setText(student.getStudentName());
        l_name.setText(student.getL_name());
        age.setText(String.valueOf(student.getAge()));
        level.setText(student.getLevel());
        znumber.setText(String.valueOf(student.getZ_number()));
        logout = findViewById(R.id.student_profile_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.getInstance(getApplicationContext()).logOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

}
