package com.example.pablo.fau_rock_climbing;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 4/5/2018.
 */

public class MyTrips_Adapter extends RecyclerView.Adapter<MyTrips_Adapter.ViewHolder> {

   private ArrayList<Trip> mytrips;
    final private ListItemClickListener listener;

    public MyTrips_Adapter(ArrayList<Trip> trip, ListItemClickListener listener){
        mytrips = trip;
        this.listener = listener;
    }

    public interface ListItemClickListener{
        void onLitemItemClick(View view, int position);
    }

    public MyTrips_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytrips_adapter_layout, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Trip mytrip = mytrips.get(position);
        holder.name.setText(mytrip.getName());
        holder.s_date.setText(mytrip.getS_date());
        holder.e_date.setText(mytrip.getE_date());


    }

    public void updateTripsChange(ArrayList<Trip> trips){
        mytrips = trips;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return mytrips.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, s_date, e_date;
        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            name = v.findViewById(R.id.mytrip_name);
            s_date = v.findViewById(R.id.s_date_mytrip);
            e_date = v.findViewById(R.id.e_date_mytrip);

        }


        @Override

        public void onClick(View view) {

        }
    }
}
