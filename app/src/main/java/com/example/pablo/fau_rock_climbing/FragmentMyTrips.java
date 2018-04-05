package com.example.pablo.fau_rock_climbing;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pablo on 4/4/2018.
 */

public class FragmentMyTrips extends Fragment {
    private ArrayList<Trip> mytrips;
    private RecyclerView list;
    private ProgressBar bar;

    public FragmentMyTrips(){

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mytrips = getMyTrips(SharedPreferencesManager.getInstance(getContext()).getGuest().getUsername());


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_mytrips, parent,false);
        list = view.findViewById(R.id.mytrips_RV);
        bar = view.findViewById(R.id.progress_bar_mytrips);
        return view;
    }


    public ArrayList<Trip> getMyTrips(String username){
     final ArrayList<Trip> trips = new ArrayList<>();

        class Mytrips extends AsyncTask<String, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                list.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);

                try{
                    Log.d("POST",s );
                    JSONObject obj = new JSONObject(s);

                    int size = obj.getInt("size");
                    for(int i = 0; i<size; i++){
                        JSONObject obj_2 = obj.getJSONObject("Trip " + i);
                        Trip trip = new Trip(obj_2.getString("name"), obj_2.getString("s_date"), obj_2.getString("e_date"), obj_2.getInt("id"));
                        trips.add(trip);
                    }

                }catch (JSONException e){
                    Log.e("JSON", e.getMessage());
                    Log.d("POST",s );
                }


            }

            @Override
            protected String doInBackground(String... strings) {
                HttpRequestHandler handler  = new HttpRequestHandler();
                HashMap<String, String> data = new HashMap<>();
                data.put("username", SharedPreferencesManager.getInstance(getContext()).getGuest().getUsername());


                return handler.sendPostRequest(URL.getMyTrips, data);
            }
        }


        return trips;

    }
}
