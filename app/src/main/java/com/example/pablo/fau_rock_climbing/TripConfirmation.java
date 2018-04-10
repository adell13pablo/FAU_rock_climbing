package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class TripConfirmation extends AppCompatActivity {
    TextView title, s_date, e_date, gear;
    ImageView iv;
    Button confirm;
    String id_trip = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_confirmation);
        Intent i = getIntent();
        iv = findViewById(R.id.iv_trip);
        int id = i.getIntExtra("image", 0);
        iv.setImageResource(id);
        id_trip= i.getStringExtra("id");
        title = findViewById(R.id.tv_title_confitmation_trip);
        s_date = findViewById(R.id.s_date_confirmation_trip);
        e_date = findViewById(R.id.e_date_confirmation_trip);
        gear = findViewById(R.id.tv_required_gear);
        confirm = findViewById(R.id.button_coffirm_assistance);
        title.setText(i.getStringExtra("name"));
        s_date.setText(i.getStringExtra("s_date"));
        e_date.setText(i.getStringExtra("e_date"));
        String gear_need = "";
        String [] resources = i.getStringArrayExtra("gear");

        for(int j = 0; j<resources.length;j++){
            if(resources.length < 3){
                gear_need = resources[j].toUpperCase();
            }else {
                gear_need = resources[j].toUpperCase() + " , ";
            }
        }
        gear.setText(gear_need);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerTrip();
            }
        });

    }

    public void registerTrip(){
        class confirmTrip extends AsyncTask<Void, Void, String>{
            @Override
            protected String doInBackground(Void... voids) {
                HttpRequestHandler handler = new HttpRequestHandler();
                HashMap<String, String> params = new HashMap<>();
                if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("guest")){

                    params.put("username", String.valueOf(SharedPreferencesManager.getInstance(getApplicationContext()).getGuest().getUsername()));
                }
                else if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("student")){
                    params.put("username", String.valueOf(SharedPreferencesManager.getInstance(getApplicationContext()).getStudent().getZ_number()));
                }


                params.put("trip", id_trip);
                params.put("mode", SharedPreferencesManager.getInstance(getApplicationContext()).appMode());

                Log.d("SET", "doInBackground: Trip SET");


                return handler.sendPostRequest(URL.paymentTrip, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent i = null;

                try{
                    JSONObject obj = new JSONObject(s);
                    if(!obj.getBoolean("error")){
                        if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("guest")){
                            i = new Intent(getApplicationContext(), GuestMainActivity.class);

                        }
                        else if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("student")){
                            i = new Intent(getApplicationContext(), StudentMainActivity.class);
                        }

                        startActivity(i);

                    }else{
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    Log.d("JSON ERROR TRIP", "onPostExecute: TRIP JSON ERROR");
                }
            }
        }
    }
}
