package com.example.pablo.fau_rock_climbing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Pablo on 3/29/2018.
 */
//Creating this class to store login session using sharedPreferences and performing some operations with them
    //Using singleton pattern
public class SharedPreferencesManager {

    //Constants

    private final String SHARED_PREF_NAME = "faurockclimbing";
    private final String KEY_ZNUMBER = "keyznumber";
    private final String KEY_NAME = "keyname";
    private final String KEY_L_NAME = "keylname";
    private final String KEY_AGE = "keyage";
    private final String KEY_LEVEL = "keylevel";
    private final String KEY_MODE = "keymode";
    private final String KEY_USERNAME = "keyusername";
    private final String KEY_MEMBERSHIP = "keymembership";
    private final String KEY_DATE = "keydate";

    private static SharedPreferencesManager manager;
    private static Context context;

    private SharedPreferencesManager(Context cxt){
        context = cxt;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context){

        if(manager == null){
            manager = new SharedPreferencesManager(context);
        }
        return manager;

    }

    //Method to allow the user to login and store its data in shared preferences

    public void studentLogin(Student student){
        SharedPreferences data = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE); //Getting preferences for the user with the key we defined
        SharedPreferences.Editor editor = data.edit();

        editor.putString(KEY_MODE, "student");
        editor.putString(KEY_NAME, student.getStudentName());
        editor.putString(KEY_L_NAME, student.getL_name());
        editor.putInt(KEY_AGE, student.getAge());
        editor.putString(KEY_LEVEL, student.getLevel());
        editor.putInt(KEY_ZNUMBER, student.getZ_number());

        editor.apply();
    }

    public void guestLogin(Guest guest){
        SharedPreferences data = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();

        editor.putString(KEY_MODE, "guest");

        editor.putString(KEY_USERNAME, guest.getUsername());
        editor.putString(KEY_NAME, guest.getGuestName());
        editor.putString(KEY_L_NAME, guest.getL_name());
        editor.putString(KEY_LEVEL, guest.getLevel());
        editor.putInt(KEY_AGE, guest.getAge());
        editor.putString(KEY_MEMBERSHIP, guest.getMembership());
        editor.putString(KEY_DATE, guest.getE_date());
        editor.apply();

    }

    //This method will check if the user is logged in or not

    public boolean isLoggedIn(){
        SharedPreferences data = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return data.getString(KEY_NAME, null)!=null;
    }

    //Method to return logged in user

    public Student getStudent(){
        SharedPreferences data = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Student(data.getInt(KEY_ZNUMBER, 0), data.getInt(KEY_AGE, 0)," ", data.getString(KEY_NAME, null), data.getString(KEY_L_NAME,null)
            ,  data.getString(KEY_LEVEL, null));
}

    public Guest getGuest(){
        SharedPreferences data = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Guest(data.getString(KEY_NAME, null),
                         data.getString(KEY_L_NAME, null),
                          data.getString(KEY_USERNAME, null),
                         data.getString(KEY_MEMBERSHIP,null ),
                            data.getString(KEY_LEVEL, null),
                data.getInt(KEY_AGE, 0), data.getString(KEY_DATE, null));
    }

    public String appMode(){
        String mode;
        SharedPreferences data = context.getSharedPreferences(SHARED_PREF_NAME
        , Context.MODE_PRIVATE);
        mode = data.getString(KEY_MODE, null);
        return mode;

    }


    //Clear SharedPreferences so we dont have any info on the student as it has already looged out
    public void logOut(){
        SharedPreferences data = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();

        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public void updateUser(){
        if(isLoggedIn()){
            SharedPreferences data = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            if(data.getString(KEY_MODE, null) == "guest"){
                updateGuest();
            }

            if(data.getString(KEY_MODE, null) == "student"){

            }
        }


    }

    public void updateGuest(){
        class UpdateGuest extends AsyncTask<Void, Void, String>{
            Guest guest;
            @Override
            protected String doInBackground(Void... voids) {
                HttpRequestHandler handler = new HttpRequestHandler();

                guest =  SharedPreferencesManager.getInstance(context).getGuest();
                String username = guest.getUsername();
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);

                return   handler.sendPostRequest(URL.loginGuest, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try{

                    JSONObject obj = new JSONObject(s);

                    if(!obj.getBoolean("error")){
                        //Go to next activity
                        //Create student object to store information about user
                        Guest guest_2 = new Guest(obj.getString("name"), obj.getString("l_name"), obj.getString("g_id"), obj.getString("membership"),
                                obj.getString("level"), obj.getInt("age"), obj.getString("e_date"));
                        if(guest.getMembership() != guest_2.getMembership()) guest.setMembership(guest_2.getMembership());
                        if(guest.getLevel() != guest_2.getLevel()) guest.setLevel(guest_2.getLevel());
                        if(guest.getE_date() != guest_2.getE_date()) guest.setE_date(guest_2.getE_date());
                        SharedPreferencesManager.getInstance(context).guestLogin(guest);

                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }
        UpdateGuest ug = new UpdateGuest();
        ug.execute();
    }

    public void updateStudent(){
        class UpdateStudent extends AsyncTask<Void, Void, String>{
            Student student;
            @Override
            protected String doInBackground(Void... voids) {
                HttpRequestHandler handler = new HttpRequestHandler();

                student =  SharedPreferencesManager.getInstance(context).getStudent();
                String username = String.valueOf(student.getZ_number());
                HashMap<String, String> params = new HashMap<>();
                params.put("z_number", username);

                return   handler.sendPostRequest(URL.loginStudent, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try{

                    JSONObject obj = new JSONObject(s);

                    if(!obj.getBoolean("error")){
                        //Go to next activity
                        //Create student object to store information about user
                        Student student_2  = new Student(Integer.parseInt(obj.getString("znumber")),
                                Integer.parseInt(obj.getString("age")),"",
                                obj.getString("name"),
                                obj.getString("l_name"),
                                obj.getString("level") );
                        if(student.getLevel() != student_2.getLevel()) student.setLevel(student_2.getLevel());

                        SharedPreferencesManager.getInstance(context).studentLogin(student);

                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }
        UpdateStudent us = new UpdateStudent();
        us.execute();
    }

}


