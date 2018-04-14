package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {
        TextInputEditText card, account, cvc, exp;
         String course = "";
         String mode = "";
        Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent i = getIntent();
        final String payment_mode = i.getStringExtra("payment");
        if(i.hasExtra("course")){
            if(i.hasExtra("mode")) {

                course = i.getStringExtra("course");
                mode = i.getStringExtra("mode");
            }
        }

        card = findViewById(R.id.card_number);

        account = findViewById(R.id.account_name);
        cvc = findViewById(R.id.cvc);
        exp = findViewById(R.id.expiration_date);
        accept = findViewById(R.id.accept_payment);


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment(payment_mode, course);
            }
        });
    }


    private void makePayment(final String mode, final String course){



        class Payment extends AsyncTask<String, Void, String>{
Guest guest = SharedPreferencesManager.getInstance(getApplicationContext()).getGuest() ;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try{
                    JSONObject object = new JSONObject(s);
                    Intent intent = null;
                    if(!object.getBoolean("error")){
                        if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("guest")){
                             intent = new Intent(getApplicationContext(), GuestMainActivity.class);
                        }
                        else if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("student")){
                            intent = new Intent(getApplicationContext(), StudentMainActivity.class);
                        }

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "An error has ocurred, please check your data", Toast.LENGTH_LONG).show();
                        Log.d("PAYMENT ERROR_JSON ", "onPostExecute: PAYMENT ERROR" + s);
                    }
                }catch(JSONException e){
                    Log.d("PAYMENT ERROR ", "onPostExecute: PAYMENT ERROR" + s);
                    e.printStackTrace();

                }
            }

            @Override
            protected String doInBackground(String... strings) {

                HttpRequestHandler handler = new HttpRequestHandler();
                String url = "";
                Log.d("MODE", "doInBackground: MODE " + strings[0]);
                HashMap<String, String> parameters = new HashMap<>();
                if(strings[0].equals("course")){
                    if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("guest")){
                        parameters.put("username", guest.getUsername());
                    }
                    else if(SharedPreferencesManager.getInstance(getApplicationContext()).appMode().equals("student")){
                        parameters.put("username", String.valueOf(SharedPreferencesManager.getInstance(getApplicationContext()).getStudent().getZ_number()));
                    }


                    parameters.put("course", course);
                    parameters.put("mode", mode);
                    parameters.put("payment", strings[0]);
                    url = URL.paymentCourses;
                    Log.d("SET", "doInBackground: COURSE SET");

                }else if(strings[0].equals("monthly") || strings[0].equals("daily")) {
                    parameters.put("username", guest.getUsername());
                    parameters.put("payment", strings[0]);
                    url = URL.makePayment;
                }
                return handler.sendPostRequest(url, parameters);
            }
        }

        Payment payment = new Payment();
        payment.execute(mode);

    }
}
