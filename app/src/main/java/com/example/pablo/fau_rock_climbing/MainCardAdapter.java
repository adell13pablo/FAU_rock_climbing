package com.example.pablo.fau_rock_climbing;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Pablo on 3/27/2018.
 */

public class MainCardAdapter extends RecyclerView.Adapter<MainCardAdapter.ViewHolder> {

    private String [] data;

    @Override
    //Create viewholder to host the views
    public MainCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create view

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cards_layout, parent, false);
        //Create viewholder with the view
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    //Bind every position with an item in the viewholder
    public void onBindViewHolder(MainCardAdapter.ViewHolder holder, int position) {
        //Replace every element with its value

        holder.text.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
 //Provide a view holder to get each view info
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardview;
        TextView text;

        public ViewHolder(View v){
            super(v);
            text = (TextView) v.findViewById(R.id.card_text);
            cardview = (CardView) v.findViewById(R.id.card_view);

        }
    }
//Constructor to create the adapter
    public MainCardAdapter(String[] dataset){

        data = dataset;
        Log.i("Length", "Length is "+ data.length);

    }





}
