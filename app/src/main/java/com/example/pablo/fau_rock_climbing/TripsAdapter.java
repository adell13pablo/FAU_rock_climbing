package com.example.pablo.fau_rock_climbing;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pablo on 4/5/2018.
 */

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripsViewHolder> {

    ArrayList<Trip> trips;
    ListItemOnClickListener listener;
    Context context;


    public TripsAdapter(ArrayList<Trip> trips, ListItemOnClickListener listeners, Context context){
        this.trips = trips;
        this.listener = listeners;
        this.context = context;
    }
    @Override
    public TripsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_layout_rv, null);
        TripsViewHolder vh = new TripsViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(TripsViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.title.setText(trip.getName());
        String titles[] = trip.getName().split(" ");
        int id = context.getResources().getIdentifier(titles[0].toLowerCase(), "drawable", context.getPackageName());
        holder.im.setImageResource(id);

    }

    public void updatedTrips(ArrayList<Trip> trips){
        this.trips = trips;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public interface ListItemOnClickListener{
        public void onListItemClick(int position);
    }



    public class TripsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView card;
        ImageView im;
        TextView title;

        public TripsViewHolder(View itemView, ListItemOnClickListener l) {
            super(itemView);
            listener = l;
            card = itemView.findViewById(R.id.card_view_trips);
            im = itemView.findViewById(R.id.image_rv_trips);
            title = itemView.findViewById(R.id.card_text_trips);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onListItemClick(position);
        }
    }
}
