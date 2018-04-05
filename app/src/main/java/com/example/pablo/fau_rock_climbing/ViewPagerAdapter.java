package com.example.pablo.fau_rock_climbing;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 4/5/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;


    //Adapter created fot the viewpager

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter (FragmentManager manager, Context context)
    {
        super(manager);
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    public CharSequence getPageTitle(int position){
        return fragmentTitleList.get(position);
    }
}