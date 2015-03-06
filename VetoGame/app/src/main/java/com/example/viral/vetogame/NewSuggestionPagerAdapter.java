package com.example.viral.vetogame;

import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Viral on 3/5/2015.
 */
public class NewSuggestionPagerAdapter extends FragmentPagerAdapter {

    private int radius;
    private int zipCode;
    private String address;
    private Location location;

    public NewSuggestionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        switch (i) {
            case 0:
               fragment = new LocationSectionFragment();
               return fragment;
            default:
               fragment = new FindSectionFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Location";
        }else{
            return "Find";
        }
    }
}
