package com.example.pablo.fau_rock_climbing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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

        editor.putString(KEY_MODE, "guest");
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
                data.getInt(KEY_AGE, 0));
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

}


