package com.example.pablo.fau_rock_climbing;

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

public class StudentMainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.studentmainactivity);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        mDrawerLayout =  findViewById(R.id.student_drawer_layout);

        NavigationView student_nav_view =  findViewById(R.id.student_nav_view);
        student_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Set item as selected to persist highlight
                item.setChecked(true);

                //Close drawer when item clicked

                mDrawerLayout.closeDrawers();

                //Update UI based on selected option

                switch (item.getItemId()){
                    case R.id.user_acccount:
                        Toast.makeText(getApplicationContext(), "User account", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.user_trips:
                        Toast.makeText(getApplicationContext(), "User trips", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.user_courses:
                        Toast.makeText(getApplicationContext(), "User courses", Toast.LENGTH_LONG).show();
                        break;
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

    public void calltoDB(){
        try{
            URL myurl = new URL("lamp.cse.fau.edu/~padell2017/rock_climbing+phpfile");
            HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();

            if(connection.getResponseCode() == 200){
                //Do something with it
                //Do something with the info we got from the call to the php file
            }

        }catch(MalformedURLException exception){
            Log.i("URL", "URL Exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
