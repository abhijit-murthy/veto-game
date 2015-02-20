package com.example.viral.vetogame;

import android.location.Location;

/**
 * Created by Viral on 2/19/2015.
 */
public class Suggestion {

    private String name;
    private int votes;
    private Location location;

    public Suggestion(String name, Location location){
        this.name = name;
        this.location = location;
        votes = 1;
    }

}
