package com.example.pablo.fau_rock_climbing;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MyCoursesFragment extends Fragment {
   private ArrayList<Course> courses;
   private RecyclerView rv;
   private MyCoursesAdapter adapter;
   private RecyclerView.LayoutManager manager;
   private ProgressBar bar;



    public MyCoursesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courses = new ArrayList<>();
        initiateMyCourses();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_courses, container, false);
        rv = v.findViewById(R.id.rv_my_courses);
        bar = v.findViewById(R.id.progress_bar_my_courses);

        adapter = new MyCoursesAdapter(courses);
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


    public void initiateMyCourses(){

        class InitiateMyCourses extends AsyncTask<Void, Void, String> {
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
                    Log.d("CREATED JSON", "JSON");
                    int size = obj.getInt("size");
                    for(int i=0; i<size; i++) {
                        JSONObject obj_2 = obj.getJSONObject("Course "+ i);
                        String [] instructors = obj_2.getString("instructors").split(",");


                        Course course = new Course("Course " + obj_2.getString("name"), obj_2.getString("level"),
                                "$" + obj_2.getString("price"), instructors);

                        c.add(course);
                        Log.d("ADDED COURSE", "ADDED");
                    }
                    courses = c;
                    Log.d("UPDATING", "UPDATING");
                    adapter.updateMyCourses(c);

                    Log.d("GET_MYCOURSES", s);
                }catch (Exception e){

                    Log.d("GET_ERROR", s);
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = "";
                HttpRequestHandler handler = new HttpRequestHandler();
                HashMap<String, String> data = new HashMap<>();
                if(SharedPreferencesManager.getInstance(getContext()).appMode().equals("guest")) {
                    data.put("username", SharedPreferencesManager.getInstance(getContext()).getGuest().getUsername());
                    result = handler.sendPostRequest(URL.getMyCourses, data);
                }
                else if(SharedPreferencesManager.getInstance(getContext()).appMode().equals("student")){
                    data.put("username", String.valueOf(SharedPreferencesManager.getInstance(getContext()).getStudent().getZ_number()));
                    result = handler.sendPostRequest(URL.getMyCourses_Student, data);
                    Log.d("STUDENT", "doInBackground: STUDENT " + String.valueOf(SharedPreferencesManager.getInstance(getContext()).getStudent().getZ_number()) );
                }
                Log.d("EXECUTING", "EXECUTING");

                return result;
            }
        }

        InitiateMyCourses c = new InitiateMyCourses();
        c.execute();

    }
}
