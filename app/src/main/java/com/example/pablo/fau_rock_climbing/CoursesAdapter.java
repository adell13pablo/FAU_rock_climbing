package com.example.pablo.fau_rock_climbing;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {
ArrayList<Course> courses;
final private ListenerItem listener;



    public interface ListenerItem{
     void onItemClicked(View view, int position);
}


public CoursesAdapter(ArrayList<Course> courses, ListenerItem listener){
    this.courses = courses;
    this.listener = listener;
}


    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_rv_layout, null);
        CoursesViewHolder vh = new CoursesViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(CoursesViewHolder holder, final int position) {
        Course course = courses.get(position);

        holder.name.setText(course.getName());
        holder.level.setText(course.getLevel());
        holder.price.setText(course.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OUT VIEWHOLDER CLICK", "onClick: VIEWHOLDER CLICK");
                listener.onItemClicked(view,  position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void updateCourses(ArrayList<Course> course){
            courses = course;
        Log.d("UPDATING_RV", "UPDATED");
            notifyDataSetChanged();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView name, level, price;

        public CoursesViewHolder(View v, ListenerItem item){
            super(v);
            name = v.findViewById(R.id.course_name);
            level = v.findViewById(R.id.course_level);
            price = v.findViewById(R.id.course_price);

        }

        @Override
        public void onClick(View view) {


        }
    }
}
