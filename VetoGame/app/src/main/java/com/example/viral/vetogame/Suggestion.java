package com.example.viral.vetogame;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by Viral on 2/19/2015.
 */
public class Suggestion implements Serializable{

    private String name;
    private int votes;
    private Location location;

    public Suggestion(String name, Location location){
        this.name = name;
        this.location = location;
        votes = 1;
    }

    public Suggestion(String name){
        this.name = name;
        votes = 1;
    }

    @Override
    public String toString(){
        return name;
    }

}
