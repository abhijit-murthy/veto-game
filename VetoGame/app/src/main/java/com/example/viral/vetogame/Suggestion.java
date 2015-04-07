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
    private String location_string;
    private int rating;
    private double distance;
    private boolean checked;
    private String suggestedBy;

    public Suggestion(String name, Location location){
        this.name = name;
        this.location = location;
        votes = 1;
    }


    public Suggestion(String name, String location_string, int rating, double distance){
        this.name = name;
        this.location_string = location_string;
        this.rating = rating;
        this.distance = distance;
        votes = 1;
        checked = false;
    }

    public Suggestion(String name){
        this.name = name;
        votes = 1;
    }

    @Override
    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocation_string() {
        return location_string;
    }

    public void setLocation_string(String location_string) {
        this.location_string = location_string;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSuggestedBy() {
        return suggestedBy;
    }

    public void setSuggestedBy(String suggestedBy) {
        this.suggestedBy = suggestedBy;
    }
}
