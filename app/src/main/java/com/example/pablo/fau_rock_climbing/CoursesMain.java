package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CoursesMain extends AppCompatActivity implements CoursesAdapter.ListenerItem{
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ViewPager pager;
    TabLayout tab;
    TextView nav_drawer_name, username;
    CoursesFragment fragment_courses = new CoursesFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_main);

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
            Guest guest = SharedPreferencesManager.getInstance(getApplicationContext()).getGuest();
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
                            startActivity(new Intent(getApplicationContext(), StudentProfile.class));
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

        pager = findViewById(R.id.pager_courses);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        //Add fragments

        adapter.addFragment(new MyCoursesFragment(), "My Courses");
        adapter.addFragment(fragment_courses, "Courses");


        pager.setAdapter(adapter);

        tab = findViewById(R.id.tabs_courses);
        tab.setupWithViewPager(pager);


    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Log.d("INTERFACE", "onItemClicked: HERE");
        fragment_courses.onItemClicked(view, position);
    }
}
