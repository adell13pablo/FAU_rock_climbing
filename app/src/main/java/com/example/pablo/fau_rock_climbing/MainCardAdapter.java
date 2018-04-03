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

    final private ListItemClickListener listener;
    private String [] data;

    //Implementing Item click handler

    public interface ListItemClickListener{
        void onListItemClick( int clickedItem);
    }


    //Constructor to create the adapter
    public MainCardAdapter(String[] dataset, ListItemClickListener listItemClickListener){

        data = dataset;
        listener = listItemClickListener;


    }



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
    //Return array elements so system knows when to stop creating views
    public int getItemCount() {
        return data.length;
    }

 //Provide a view holder to get each view info
   class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardview;
        TextView text;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            text = (TextView) v.findViewById(R.id.card_text);
            cardview = (CardView) v.findViewById(R.id.card_view);

        }

     @Override
     public void onClick(View view) {
         int clickedPosition = getAdapterPosition();
        listener.onListItemClick(clickedPosition);

     }
 }





}
