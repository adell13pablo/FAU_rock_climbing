package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Pablo on 3/26/2018.
 */

public class GuestMainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    TextView nav_drawer_name;
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.guestmainactivity);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        mDrawerLayout =  findViewById(R.id.guest_drawer_layout);

        NavigationView guest_nav_view =  findViewById(R.id.guest_nav_view);
        View headerView = guest_nav_view.getHeaderView(0);
        nav_drawer_name = headerView.findViewById(R.id.guest_nav_drawer_name);
        Guest guest = SharedPreferencesManager.getInstance(getApplicationContext()).getGuest();
        nav_drawer_name.setText(guest.getGuestName() + " " + guest.getL_name());

        guest_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Set item as selected to persist highlight
                item.setChecked(true);

                //Close drawer when item clicked

                mDrawerLayout.closeDrawers();

                //Update UI based on selected option

                switch (item.getItemId()){
                    case R.id.guest_acccount:
                        // Toast.makeText(getApplicationContext(), "User account", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), GuestProfile.class));
                        break;
                    case R.id.guest_trips:
                        Toast.makeText(getApplicationContext(), "User trips", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.guest_courses:
                        Toast.makeText(getApplicationContext(), "User courses", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.guest_membership:
                        Toast.makeText(getApplicationContext(), "User courses", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.guest_logout:
                        SharedPreferencesManager.getInstance(getApplicationContext()).logOut();
                }
                return true;
            }
        });



    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "Clicked drawable", Toast.LENGTH_LONG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
