package com.example.pablo.fau_rock_climbing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Pablo on 3/26/2018.
 */
public class StudentLogIn extends AppCompatActivity {
    EditText user, password;
    Button login, cancel;
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.student_login);

        user = (EditText) findViewById(R.id.znumber);
        password = (EditText) findViewById(R.id.student_password);

        login = (Button) findViewById(R.id.login);
        cancel = (Button) findViewById(R.id.student_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Create Async Task for user verification -- Show loader until task is finished.
}
