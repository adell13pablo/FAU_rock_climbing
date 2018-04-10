package com.example.pablo.fau_rock_climbing;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pablo on 4/4/2018.
 */

public class FragmentMyTrips extends Fragment implements MyTrips_Adapter.ListItemClickListener{
     ArrayList<Trip> mytrips ;
     RecyclerView list;
     ProgressBar bar;

     MyTrips_Adapter adapter;

    public FragmentMyTrips(){

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mytrips = new ArrayList<>();


        getMyTrips(SharedPreferencesManager.getInstance(getContext()).getGuest().getUsername());
        Log.d("ArrayList", String.valueOf(mytrips.size()));



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_mytrips, parent,false);
        list = view.findViewById(R.id.mytrips_RV);
        bar = view.findViewById(R.id.progress_bar_mytrips);

        adapter= new MyTrips_Adapter(mytrips, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setAdapter(adapter);
        list.setLayoutManager(manager);


        Log.d("ArrayList_2", mytrips.toString());

        return view;
    }




    public void getMyTrips(final String username){


        class Mytrips extends AsyncTask<Void, Void, String>{

            protected ArrayList<Trip> trips = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                bar.setVisibility(View.GONE);

                try{
                   // Log.d("POST",s );Log.d("USERNAME", username );

                    JSONObject obj = new JSONObject(s);

                    int size = obj.getInt("size");
                    for(int i = 0; i<size; i++){
                        JSONObject obj_2 = obj.getJSONObject("Trip " + i);
                        Trip trip = new Trip(obj_2.getString("name"), obj_2.getString("s_date"), obj_2.getString("e_date"), obj_2.getInt("id"));
                        trips.add(trip);
                    }
                    //Log.d("ArrayList_ASYNC", String.valueOf(mytrips.size()));
                    mytrips = trips;
                    adapter.updateTripsChange(trips);



                }catch (JSONException e){
                    Log.e("JSON", e.getMessage());
                    Log.d("POST_ERROR",s );
                    bar.setVisibility(View.GONE);

                }


            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = "";
                HttpRequestHandler handler  = new HttpRequestHandler();
                HashMap<String, String> data = new HashMap<>();
                if(SharedPreferencesManager.getInstance(getContext()).appMode().equals("guest")) {
                    data.put("username", SharedPreferencesManager.getInstance(getContext()).getGuest().getUsername());
                    result = handler.sendPostRequest(URL.getMyTrips, data);
                }
                else if(SharedPreferencesManager.getInstance(getContext()).appMode().equals("student")){
                    data.put("username", String.valueOf(SharedPreferencesManager.getInstance(getContext()).getStudent().getZ_number()));
                    result = handler.sendPostRequest(URL.getMyTrips_Student, data);
                }

                return result;
            }
        }

        Mytrips mt = new Mytrips();
        mt.execute();



    }




    @Override
    public void onLitemItemClick(View view, int position) {
        Log.d("CLICKA", "onLitemItemClick: CLICCCC");
    }
}
