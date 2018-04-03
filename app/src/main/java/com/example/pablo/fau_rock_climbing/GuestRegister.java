package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Pablo on 3/27/2018.
 */

public class GuestRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText user, password, name, l_name, age;
    Spinner level;
    TextView result;
    String level_selected;
    Button next,cancel;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.guest_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = findViewById(R.id.username_register);
        password = findViewById(R.id.password_guest_register);
        name = findViewById(R.id.name_guest_register);
        l_name = findViewById(R.id.l_name_guest_register);
        age = findViewById(R.id.age_guest_register);
        next = findViewById(R.id.guest_register_next);
        cancel = findViewById(R.id.guest_register_cancel);
        level = findViewById(R.id.climbing_level_student);

        //Result TextView to Show results
        result = findViewById(R.id.register_guest_result);
        //Create ArrayAdapter for the spinner plus a default choice

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.climbing_level, android.R.layout.simple_spinner_item);
        //Specify dropdown layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply adapter
        level.setAdapter(adapter);
        level.setOnItemSelectedListener(this);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentRegister();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        //What to do when the element has been selected
        level_selected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        level_selected = "Beginner";

    }

    public void studentRegister(){
        final String username = user.getText().toString();
        final String pword = password.getText().toString();
        final String student_name = name.getText().toString();
        final String student_l_name = l_name.getText().toString();
        final String student_age = age.getText().toString();
        final String student_level = level_selected;

        class RegisterGuest extends AsyncTask<Void, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(getApplicationContext(), "Starting Connection", Toast.LENGTH_LONG).show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try{
                    JSONObject obj = new JSONObject(s);

                    if(obj.getBoolean("error")){
                        result.setText("Error: "  + obj.getString("message"));
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(), GuestLogIn.class));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //Create handler object
                HttpRequestHandler handler = new HttpRequestHandler();

                //Create Hashmap
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", pword);
                params.put("name", student_name);
                params.put("l_name", student_l_name);
                params.put("age", student_age);
                params.put("level", student_level);

                return handler.sendPostRequest(URL.registerGuest, params);




            }
        }

        RegisterGuest gr = new RegisterGuest();
        gr.execute();

    }
}
