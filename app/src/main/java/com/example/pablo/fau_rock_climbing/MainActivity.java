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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainCardAdapter.ListItemClickListener {

    DrawerLayout mainDrawer;
    Intent i; //Main intent to launch subflows
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


        if(SharedPreferencesManager.getInstance(getApplicationContext()).isLoggedIn()){
            String app_mode = SharedPreferencesManager.getInstance(getApplicationContext()).appMode();

            if(app_mode.equalsIgnoreCase("student")){
                startActivity(new Intent(getApplicationContext(), StudentMainActivity.class));}

            else if (app_mode.equalsIgnoreCase("guest")){


                startActivity(new Intent(getApplicationContext(), GuestMainActivity.class));}

        }


        //Defining the recycler view

        recyclerView = findViewById(R.id.main_recycler_view);
        MainCardAdapter adapter = new MainCardAdapter(dataset, this);
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
                       // Toast.makeText(getApplicationContext(), "Student Mode", Toast.LENGTH_LONG).show();
                      // Snackbar snackbar = Snackbar.make(findViewById(R.id.main_relative), "Student Mode", Snackbar.LENGTH_SHORT);
                        //snackbar.setAction("Launch", new launchStudent());
                        //snackbar.show();
                       i = new Intent(getApplicationContext(), StudentLogIn.class);
                        startActivity(i);

                        break;
                    case R.id.guest_mode:
                        i = new Intent(getApplicationContext(), GuestLogIn.class);
                        startActivity(i);
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

    @Override
    public void onListItemClick(int clickedItem) {
        Toast.makeText(getApplicationContext(), "Click on item" + clickedItem, Toast.LENGTH_LONG).show();
    }


    //Creating object to link to snackbar action with an interface (not using it anymore)

    public class launchStudent implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent j = new Intent(getApplicationContext(), StudentLogIn.class);
            startActivity(j);
        }
    }

}
