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
import android.view.MenuItem;
import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;
import android.widget.Toolbar;

/**
 * Created by Pablo on 4/4/2018.
 */

public class TripsMain extends FragmentActivity {

    ViewPager pager;
    private DrawerLayout mDrawerLayout;
    TextView nav_drawer_name, username, main_info;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_main);



        pager = findViewById(R.id.pager_trips);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this); //Created adapter to fit the viewpager


        //Add the fragments to the pager
        adapter.addFragment(new FragmentTrips(), "Trips");
        adapter.addFragment(new FragmentMyTrips(),"My Trips");

        pager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabs_mytrips);
        tabs.setupWithViewPager(pager);



        mDrawerLayout =  findViewById(R.id.mytrips_drawer_layout);

        NavigationView guest_nav_view =  findViewById(R.id.guest_nav_view);
        View headerView = guest_nav_view.getHeaderView(0);
        nav_drawer_name = headerView.findViewById(R.id.guest_nav_drawer_name);
        username = headerView.findViewById(R.id.guest_nav_drawer_username);
        Guest guest = SharedPreferencesManager.getInstance(getApplicationContext()).getGuest();
        nav_drawer_name.setText(guest.getGuestName() + " " + guest.getL_name());
        username.setText(guest.getUsername());

        guest_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Set item as selected to persist highlight
                item.setChecked(true);

                //Close drawer when item clicked

                mDrawerLayout.closeDrawers();

                //Update UI based on selected option

                switch (item.getItemId()){
                    case R.id.guest_account:
                        // Toast.makeText(getApplicationContext(), "User account", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), GuestProfile.class));
                        break;
                    case R.id.guest_trips:
                        startActivity(new Intent(getApplicationContext(), TripsMain.class));
                        break;
                    case R.id.guest_courses:
                        Toast.makeText(getApplicationContext(), "Guest courses", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.guest_membership:
                        startActivity(new Intent(getApplicationContext(), MembershipActivity.class));
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
