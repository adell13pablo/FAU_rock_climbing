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
public class StudentLogIn extends AppCompatActivity {
    EditText user, password;
    Button login, cancel, register;
    TextView resultTextView;
    ProgressBar progressBar;


    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.student_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = (EditText) findViewById(R.id.znumber);
        password = (EditText) findViewById(R.id.student_password);
        progressBar = findViewById(R.id.student_progressBar);
        progressBar.setVisibility(View.GONE);
        login = (Button) findViewById(R.id.login);
        cancel = (Button) findViewById(R.id.student_cancel);
        register = findViewById(R.id.student_register);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getApplicationContext(), RegisterStudent.class);
                startActivity(i);
            }
        });
    }

    //Create Async Task for user verification -- Show loader until task is finished.

    public void userLogin(){
        final String username = user.getText().toString();
        final String pword = password.getText().toString();


        //Add Integrity methods


        //Define inner class to perform the actions, Params are Void because we can access to them as it is an inner class and we got them before

        class UserLogin extends AsyncTask<Void, Void, String>{


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
                    resultTextView = findViewById(R.id.result_text);

                    JSONObject obj = new JSONObject(s);

                    if(obj.getBoolean("error")){
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }else{
                        //Go to next activity
                        //Create student object to store information about user
                        Student student = new Student(Integer.parseInt(obj.getString("znumber")),
                                Integer.parseInt(obj.getString("age")),"",
                                obj.getString("name"),
                                obj.getString("l_name"),
                                obj.getString("level") );

                        SharedPreferencesManager.getInstance(getApplicationContext()).studentLogin(student);
                        finish();
                        startActivity(new Intent(getApplicationContext(), StudentProfile.class));

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
                params.put("z_number", username);
                params.put("password", pword);

                //Call the URL with the parameters
                return handler.sendPostRequest(URL.loginStudent, params);


            }
        }
        //Execute the AsyncClass

        UserLogin userLogin = new UserLogin();
        userLogin.execute();
    }
}
