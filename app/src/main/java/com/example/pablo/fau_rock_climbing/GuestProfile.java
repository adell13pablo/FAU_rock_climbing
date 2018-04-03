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

public class GuestProfile extends AppCompatActivity {

    TextView name, l_name, age, level, user;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!SharedPreferencesManager.getInstance(getApplicationContext()).isLoggedIn()){
            startActivity(new Intent(getApplicationContext(), GuestLogIn.class));
        }

        FloatingActionButton fab = findViewById(R.id.fab_guest_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GuestMainActivity.class ));
            }
        });

        name = findViewById(R.id.guest_profile_name);
        l_name = findViewById(R.id.guest_profile_l_name);
        age = findViewById(R.id.guest_profile_age);
        level = findViewById(R.id.guest_profile_level);
        user = findViewById(R.id.guest_profile_username);



        //Use the SharedPreferences Instance to get the data , therefore we don't need to use the bundle

        Guest guest = SharedPreferencesManager.getInstance(getApplicationContext()).getGuest();

        name.setText(guest.getGuestName());
        l_name.setText(guest.getL_name());
        age.setText(String.valueOf(guest.getAge()));
        level.setText(guest.getLevel());
        user.setText(String.valueOf(guest.getUsername()));
        logout = findViewById(R.id.guest_profile_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.getInstance(getApplicationContext()).logOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

}
