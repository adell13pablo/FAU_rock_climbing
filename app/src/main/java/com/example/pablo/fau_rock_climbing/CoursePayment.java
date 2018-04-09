package com.example.pablo.fau_rock_climbing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class CoursePayment extends AppCompatActivity {

    TextView course, level;
    Button payment;
    RadioButton i_1, i_2, i_3, i_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_payment);
        Intent i = getIntent();
        course = findViewById(R.id.course_title_payment);
        level = findViewById(R.id.course_level_payment);
        payment = findViewById(R.id.pay_button);
        i_1 = findViewById(R.id.instructor_1);
        i_2 = findViewById(R.id.instructor_2);
        i_3 = findViewById(R.id.instructor_3);
        i_4 = findViewById(R.id.instructor_4);

        course.setText(i.getStringExtra("course"));
        level.setText(i.getStringExtra("level"));
        payment.setText("Pay " + i.getStringExtra("price"));

        String [] instructors = i.getStringArrayExtra("instructors");
        if(instructors.length == 4){
            i_1.setText(instructors[0]);
            i_2.setText(instructors[1]);
            i_3.setText(instructors[2]);
            i_4.setText(instructors[3]);
        }
       else if(instructors.length == 3){
            i_1.setText(instructors[0]);
            i_2.setText(instructors[1]);
            i_3.setText(instructors[2]);
            i_4.setVisibility(View.GONE);

        }
        else if(instructors.length == 2) {
            i_1.setText(instructors[0]);
            i_2.setText(instructors[1]);
            i_3.setVisibility(View.GONE);
            i_4.setVisibility(View.GONE);


        } else if(instructors.length == 1){
            i_1.setText(instructors[0]);
            i_3.setVisibility(View.GONE);
            i_4.setVisibility(View.GONE);
            i_2.setVisibility(View.GONE);

        }

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                i.putExtra("payment","course");
                String [] course_sel = course.getText().toString().split(" ");
                i.putExtra("course", course_sel[1]);
                i.putExtra("mode", SharedPreferencesManager.getInstance(getApplicationContext()).appMode());
                startActivity(i);
            }
        });
    }

    public void RadioButtonOnClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.instructor_1:
                if(checked){
                    //Something
                }
                //Something
                break;

            case R.id.instructor_2:
                if(checked){
                    //Something
                }
                //Something
                break;

            case R.id.instructor_3:
                if(checked){
                    //Something
                }
                //Something
                break;

            case R.id.instructor_4:
                if(checked){
                    //Something
                }
                //Something
                break;
        }

    }
}
