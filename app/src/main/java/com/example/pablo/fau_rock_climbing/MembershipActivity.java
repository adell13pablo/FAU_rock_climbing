package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MembershipActivity extends AppCompatActivity
         {

    TextView membership_status, validity_date, name, username, nav_drawer_name;
    Button renew_payment;
    NavigationView nav_view;
    DrawerLayout drawerLayout;
    Guest guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        Toolbar toolbar = findViewById(R.id.courses_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);




        drawerLayout = findViewById(R.id.course_drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.courses_nav_view);
        View headerView = nav_view.getHeaderView(0);
        nav_drawer_name = headerView.findViewById(R.id.guest_nav_drawer_name);
        username = headerView.findViewById(R.id.guest_nav_drawer_username);
        if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("guest")) {
            guest = SharedPreferencesManager.getInstance(getApplicationContext()).getGuest();
            nav_drawer_name.setText(guest.getGuestName() + " " + guest.getL_name());
            username.setText(guest.getUsername());
        }
        else if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("student")){
            Student student = SharedPreferencesManager.getInstance(getApplicationContext()).getStudent();
            nav_drawer_name.setText(student.getStudentName() + " " + student.getL_name());
            username.setText(String.valueOf(student.getZ_number()));
            nav_view.getMenu().findItem(R.id.guest_account).setVisible(false);
            nav_view.getMenu().findItem(R.id.guest_logout).setVisible(false);
            nav_view.getMenu().findItem(R.id.guest_membership).setVisible(false);
            nav_view.getMenu().findItem(R.id.guest_trips).setVisible(false);
            nav_view.getMenu().findItem(R.id.guest_courses).setVisible(false);
            nav_view.inflateMenu(R.menu.user_menu);

        }

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Set item as selected to persist highlight
                item.setChecked(true);

                //Close drawer when item clicked

                drawerLayout.closeDrawers();

                //Update UI based on selected option

                if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("guest")) {

                    switch (item.getItemId()) {
                        case R.id.guest_account:
                            // Toast.makeText(getApplicationContext(), "User account", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), GuestProfile.class));
                            break;
                        case R.id.guest_trips:
                            startActivity(new Intent(getApplicationContext(), TripsMain.class));
                            break;
                        case R.id.guest_courses:
                            startActivity(new Intent(getApplicationContext(), CoursesMain.class));
                            break;
                        case R.id.guest_membership:
                            startActivity(new Intent(getApplicationContext(), MembershipActivity.class));
                            break;

                        case R.id.guest_logout:
                            SharedPreferencesManager.getInstance(getApplicationContext()).logOut();
                    }
                }
                else if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("student")){



                    switch (item.getItemId()) {
                        case R.id.user_acccount:
                            // Toast.makeText(getApplicationContext(), "User account", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), GuestProfile.class));
                            break;
                        case R.id.user_trips:
                            startActivity(new Intent(getApplicationContext(), TripsMain.class));
                            break;
                        case R.id.user_courses:
                            startActivity(new Intent(getApplicationContext(), CoursesMain.class));
                            break;

                        case R.id.student_logout:
                            SharedPreferencesManager.getInstance(getApplicationContext()).logOut();
                    }
                }
                return true;
            }
        });

        renew_payment = findViewById(R.id.renew_payment);
        membership_status = findViewById(R.id.membership_status);
        validity_date = findViewById(R.id.membership_validity);




        if(guest.getMembership().equalsIgnoreCase("") || guest.getMembership().equalsIgnoreCase("null")){
            renew_payment.setVisibility(View.VISIBLE);
            membership_status.setText("No membership");
            validity_date.setVisibility(View.GONE);
            membership_status.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            renew_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PaymentDialog dialog = new PaymentDialog();
                    dialog.show(getFragmentManager(), "renew_payment");
                }
            });

        }else{
            membership_status.setText(guest.getMembership().toUpperCase());
            membership_status.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            validity_date.setText("Valid until: " + guest.getE_date());
        }



    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.membership, menu);
        return true;
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } Not needed*/



    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferencesManager.getInstance(getApplicationContext()).updateGuest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferencesManager.getInstance(getApplicationContext()).updateGuest();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

