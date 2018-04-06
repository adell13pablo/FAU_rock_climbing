package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Pablo on 3/26/2018.
 */
public class GuestLogIn extends AppCompatActivity {
    EditText user, password;
    Button login, cancel, register;
    TextView resultTextView;
    ProgressBar progressBar;


    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.guest_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.guest_password);
        progressBar = findViewById(R.id.guest_progressBar);

        login = (Button) findViewById(R.id.guest_login);
        cancel = (Button) findViewById(R.id.guest_cancel);
        register = findViewById(R.id.guest_register);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getApplicationContext(), GuestRegister.class);
                startActivity(i);
            }
        });
    }

    //Create Async Task for user verification -- Show loader until task is finished.

    public void guestLogin(){
        final String username = user.getText().toString();
        final String pword = password.getText().toString();


        //Add Integrity methods


        //Define inner class to perform the actions, Params are Void because we can access to them as it is an inner class and we got them before

        class User_GuestLogin extends AsyncTask<Void, Void, String>{


            //So progressBar for as long as the connection with the server goes
            protected void onPreExecute(){
                super.onPreExecute();

                progressBar.setVisibility(View.VISIBLE);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                // Toast.makeText(getApplicationContext(), "Connection Finished", Toast.LENGTH_LONG).show();

                //Do something with the message received


                try{

                    //We receive a JSON object with the messages we have passed on the php script
                    resultTextView = findViewById(R.id.guest_result_text);

                    JSONObject obj = new JSONObject(s);

                    if(obj.getBoolean("error")){
                        resultTextView.setText(obj.getString("message"));

                    }else{
                        //Go to next activity
                        //Create student object to store information about user
                        Guest guest = new Guest(obj.getString("name"), obj.getString("l_name"), obj.getString("g_id"), obj.getString("membership"),
                                                obj.getString("level"), obj.getInt("age"), obj.getString("e_date"), obj.getString("password"));
                        SharedPreferencesManager.getInstance(getApplicationContext()).guestLogin(guest);

                        startActivity(new Intent(getApplicationContext(), GuestMainActivity.class));

                    }

                }catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                //Create Request Handler Object where we have defined the methods to connect with the server

                HttpRequestHandler handler = new HttpRequestHandler();

                //Create hashmap with pairs key-value

                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", pword);

                //Call the URL with the parameters
                return handler.sendPostRequest(URL.loginGuest, params);


            }
        }
        //Execute the AsyncClass

        User_GuestLogin guest = new User_GuestLogin();
        guest.execute();
    }
}
