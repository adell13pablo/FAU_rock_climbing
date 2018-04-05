package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView membership_status, validity_date, name, username;
    Button renew_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_membership);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        renew_payment = findViewById(R.id.renew_payment);
        membership_status = findViewById(R.id.membership_status);
        validity_date = findViewById(R.id.membership_validity);
        SharedPreferencesManager.getInstance(getApplicationContext()).updateGuest();
        Guest guest = SharedPreferencesManager.getInstance(getApplicationContext()).getGuest();
         name = headerView.findViewById(R.id.guest_nav_drawer_name);
         username = headerView.findViewById(R.id.guest_nav_drawer_username);
        name.setText(guest.getGuestName() + " " + guest.getL_name());
        username.setText(guest.getUsername());




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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.guest_account:
                // Toast.makeText(getApplicationContext(), "User account", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), GuestProfile.class));
                break;
            case R.id.guest_trips:
                Toast.makeText(getApplicationContext(), "Guest trips", Toast.LENGTH_LONG).show();
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
}
