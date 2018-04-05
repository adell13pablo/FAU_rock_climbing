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

public class StudentMainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    TextView nav_drawer_name, nav_drawer_znumber, info_Text;
    String info = "FAU climbing club takes place at the far north west corner of campus, behind the track. We have a 35 foot rock wall and a bouldering shack that we use for climbing. Most of our members are very active and participate in a number of activites and school clubs.\n" +
            "\n" +
            "We've started competing every spring for those members who want to better their skills and travel all around Florida to meet other climbers at other schools.\n" +
            "\n" +
            "Climbing club is a very laid back club and welcomes all members. Living in Florida, the majority of the people we get out can barely even climb a ladder, so don’t be embarrassed to come out, we’ve seen worse. There will be plenty of more experienced climbers to help each person individually. The more people we have, the more fun we can have so we will be happy to see any new members whether they’ve climbed before or not.\n" +
            "\n" +
            "You can find us on Facebook as FAU Climbing Club, most of our updates can be found there.";
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
        View headerView = student_nav_view.getHeaderView(0);
        nav_drawer_name = headerView.findViewById(R.id.student_nav_drawer_name);
        nav_drawer_znumber = headerView.findViewById(R.id.student_nav_drawer_znumber);
        Student student = SharedPreferencesManager.getInstance(getApplicationContext()).getStudent();
        nav_drawer_name.setText(student.getStudentName() + " " + student.getL_name());
        nav_drawer_znumber.setText(String.valueOf(student.getZ_number()));

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
                       // Toast.makeText(getApplicationContext(), "User account", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), StudentProfile.class));
                        break;
                    case R.id.user_trips:
                        Toast.makeText(getApplicationContext(), "User trips", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.user_courses:
                        Toast.makeText(getApplicationContext(), "User courses", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.student_logout:
                        SharedPreferencesManager.getInstance(getApplicationContext()).logOut();
                }
                return true;
            }
        });

        info_Text = findViewById(R.id.main_student_info_text);
        info_Text.setText(info);

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
