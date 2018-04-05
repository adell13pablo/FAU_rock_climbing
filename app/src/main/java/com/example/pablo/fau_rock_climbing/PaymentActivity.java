package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {
        TextInputEditText card, account, cvc, exp;

        Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent i = getIntent();
        final String payment_mode = i.getStringExtra("payment");

        card = findViewById(R.id.card_number);

        account = findViewById(R.id.account_name);
        cvc = findViewById(R.id.cvc);
        exp = findViewById(R.id.expiration_date);
        accept = findViewById(R.id.accept_payment);


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment(payment_mode);
            }
        });
    }


    private void makePayment(String mode){



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
                    if(!object.getBoolean("error")){

                        Intent intent = new Intent(getApplicationContext(), GuestMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "An error has ocurred, please check your data", Toast.LENGTH_LONG).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... strings) {

                HttpRequestHandler handler = new HttpRequestHandler();

                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("username", guest.getUsername());
                parameters.put("payment", strings[0]);
                return handler.sendPostRequest(URL.makePayment, parameters);
            }
        }

        Payment payment = new Payment();
        payment.execute(mode);

    }
}
