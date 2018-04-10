package com.example.pablo.fau_rock_climbing;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pablo on 4/4/2018.
 */

public class FragmentTrips extends Fragment implements TripsAdapter.ListItemOnClickListener {

    protected ArrayList<Trip> mytrips ;
    protected RecyclerView list;
    protected ProgressBar bar;
    protected TripsAdapter.ListItemOnClickListener listener;
    protected TripsAdapter adapter;

    public FragmentTrips(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mytrips = new ArrayList<>();
        initializeTrips();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trips, container, false);
        bar = v.findViewById(R.id.progress_bar_trips);
        list = v.findViewById(R.id.trips_RV);

        adapter = new TripsAdapter(mytrips, listener, this.getContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setAdapter(adapter);
        list.setLayoutManager(manager);
        return v;
    }



    public void initializeTrips(){

        class Trips extends AsyncTask<Void, Void, String>{
            protected ArrayList<Trip> tp = new ArrayList<>();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                bar.setVisibility(View.GONE);

                try{
                    JSONObject obj = new JSONObject(s);
                    Log.d("POST_RESULT", s);

                    int size = obj.getInt("size");

                    for(int i= 0; i<size; i++){
                        JSONObject obj_2 = obj.getJSONObject("Trip " + i);
                        Trip trip = new Trip(obj_2.getString("name"), obj_2.getString("s_date"), obj_2.getString("e_date"),
                                obj_2.getInt("id"), obj_2.getString("gear"));
                        tp.add(trip);
                    }
                    mytrips = tp;
                    adapter.updatedTrips(tp);
                }catch (JSONException e){
                    Log.d("POST_ERROR",s );
                    Log.e("JSON", e.getMessage());

                    bar.setVisibility(View.GONE);
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpRequestHandler handler = new HttpRequestHandler();


                return handler.sendGetRequest(URL.getTrips);
            }


        }
        Trips mt = new Trips();
        mt.execute();
    }



    @Override
    public void onListItemClick(View view, int position) {
        Log.d("CLICKED ON TRIPS", "onListItemClick: CLICKED ON TRIPS");
    }
}
