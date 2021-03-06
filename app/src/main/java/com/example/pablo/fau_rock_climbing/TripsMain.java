package com.example.pablo.fau_rock_climbing;

        import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.view.MenuItem;
import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;
import android.widget.Toolbar;

/**
 * Created by Pablo on 4/4/2018.
 */

public class TripsMain extends AppCompatActivity {

    ViewPager pager;
    private DrawerLayout mDrawerLayout;
    TextView nav_drawer_name, username;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.mytrips_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);


        mDrawerLayout =  findViewById(R.id.mytrips_drawer_layout);

        final NavigationView nav_view =  findViewById(R.id.guest_nav_view);
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

                mDrawerLayout.closeDrawers();

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


        pager = findViewById(R.id.pager_trips);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this); //Created adapter to fit the viewpager


        //Add the fragments to the pager
        adapter.addFragment(new FragmentMyTrips(),"My Trips");
        adapter.addFragment(new FragmentTrips(), "Trips");


        pager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabs_mytrips);
        tabs.setupWithViewPager(pager);





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
