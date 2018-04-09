package com.example.pablo.fau_rock_climbing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



public class CoursesFragment extends Fragment implements CoursesAdapter.ListenerItem {
    RecyclerView rv;
    CoursesAdapter adapter;
    RecyclerView.LayoutManager manager;
    ProgressBar bar;


    private ArrayList<Course> courses;



    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courses = new ArrayList<>();
        initializeCourses();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_courses, container, false);
        rv = v.findViewById(R.id.rv_courses);
        bar = v.findViewById(R.id.progress_bar_courses);


        adapter = new CoursesAdapter(courses, this);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);

        return v;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public void initializeCourses(){

         class InitiateCourses extends AsyncTask<Void, Void, String>{
             ArrayList<Course> c = new ArrayList<>();
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

                    int size = obj.getInt("size");
                    for(int i=0; i<size; i++) {

                        JSONObject obj_2 = obj.getJSONObject("Course "+ i);
                        String [] instructors = obj_2.getString("instructors").split(",");


                        Course course = new Course("Course " + obj_2.getString("name"), obj_2.getString("level"),
                                "$" + obj_2.getString("price"), instructors);
                        c.add(course);
                    }
                    courses = c;
                    adapter.updateCourses(c);

                    Log.d("POST_COURSES", s);
                }catch (Exception e){
                    Log.d("ERROR", s);
                    e.printStackTrace();

                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpRequestHandler handler = new HttpRequestHandler();
                Log.d("EXECUTING", "EXECUTING");
                return handler.sendGetRequest(URL.getCourses) ;

            }
        }

        InitiateCourses c = new InitiateCourses();
        c.execute();

    }



    @Override
    public void onItemClicked(View view, int position) {
        Log.d("CLICKED ITEM", "onItemClicked: CLICKED");
        Course course = courses.get(position);
        Intent i = new Intent(getActivity(), CoursePayment.class);
        i.putExtra("course", course.getName());
        i.putExtra("level", course.getLevel());
        i.putExtra("price", course.getPrice());
        i.putExtra("instructors", course.getInstructors());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


}
