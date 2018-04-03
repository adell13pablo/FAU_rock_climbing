package com.example.pablo.fau_rock_climbing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mainDrawer;
    RecyclerView recyclerView;
    String[] dataset = {"Test sample 1","Test sample 2", "Test sample 3", "Test sample4", "Test sample 5", "Test sample 6", "Test sample 7", "Test sample 8" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 //Toolbar definition

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        //Defining the recycler view

        recyclerView = findViewById(R.id.main_recycler_view);
        MainCardAdapter adapter = new MainCardAdapter(dataset);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        //End of recyclerView

//Navigation Drawer definition
        mainDrawer = findViewById(R.id.main_drawer_layout);



        NavigationView mainNavigation = findViewById(R.id.main_nav_view);

        mainNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Set item as selected to persist highlight
                item.setChecked(true);

                //Close drawer when item clicked

                mainDrawer.closeDrawers();

                //Update UI based on selected option

                switch (item.getItemId()){
                    case R.id.student_mode:
                        //Toast.makeText(getApplicationContext(), "Student Mode", Toast.LENGTH_LONG).show();
                       Snackbar snackbar = Snackbar.make(findViewById(R.id.main_relative), "Student Mode", Snackbar.LENGTH_SHORT);
                        snackbar.setAction("Launch", new launchStudent());
                        snackbar.show();


                        break;
                    case R.id.guest_mode:
                        Toast.makeText(getApplicationContext(), "Guest Mode", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.register:
                        Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });




        //End of navigation drawer definition



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Toast.makeText(getApplicationContext(), "Internet permission not granted", Toast.LENGTH_LONG);
        }


    }




    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mainDrawer.openDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "Clicked drawable", Toast.LENGTH_LONG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Creating object to link to snackbar action

    public class launchStudent implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), StudentLogIn.class);
            startActivity(i);
        }
    }
}
