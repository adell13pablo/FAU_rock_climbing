package com.example.pablo.fau_rock_climbing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyCoursesViewHolder> {
    ArrayList<Course> courses;

    public MyCoursesAdapter(ArrayList<Course> courses){
        this.courses = courses;
    }

    @Override
    public MyCoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_rv_layout, null);
        MyCoursesViewHolder vh = new MyCoursesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyCoursesViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.name.setText(course.getName());
        holder.level.setText(course.getLevel());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void updateMyCourses(ArrayList<Course> courses){
        this.courses = courses;
        notifyDataSetChanged();
    }

    class MyCoursesViewHolder extends RecyclerView.ViewHolder{
        TextView name, level, price;

        public MyCoursesViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.course_name);
            level = v.findViewById(R.id.course_level);
            price = v.findViewById(R.id.course_price);

            price.setVisibility(View.GONE);
        }
    }
}
