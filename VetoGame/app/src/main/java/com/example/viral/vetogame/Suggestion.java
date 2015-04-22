package com.example.viral.vetogame;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Viral on 2/19/2015.
 */
public class Suggestion implements Serializable{


    private String suggestionId;
    private String name;
    private int votes;
    private String location_string;
    //private int rating;
    //private double distance;
    private boolean checked;
    private String suggestedBy;
    private LatLng latLng;
    private String ratingImg;
    private String image;
    private String mobileURL;
    private Date createdAt;
    private int numReviews;

    public Suggestion(String name, String location_string, String suggestionId, Date createdAt){
        this.name = name;
        this.location_string = location_string;
        this.suggestionId = suggestionId;
        this.createdAt = createdAt;
        votes = 1;
    }

    public Suggestion(String name, String location_string, double distance){
        this.name = name;
        this.location_string = location_string;
        //this.distance = distance;
        votes = 1;
    }


    public Suggestion(String name, String location_string, int rating, double distance){
        this.name = name;
        this.location_string = location_string;
        //this.rating = rating;
        //this.distance = distance;
        votes = 1;
        checked = false;
    }

    public Suggestion(String name){
        this.name = name;
        votes = 1;
        checked = false;
    }

    @Override
    public String toString(){
        return name;
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
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

    public String getLocation_string() {
        return location_string;
    }

    public void setLocation_string(String location_string) {
        this.location_string = location_string;
    }

    public void setLocation_string(String address, String city, String state) {
        this.location_string = address+", "+city+", "+state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    /*public int getRating() {
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
    }*/

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

    public void setLatLng(double lat, double lng){
        this.latLng = new LatLng(lat, lng);
    }

    public LatLng getLatLng(){
        return this.latLng;
    }

    public String getRatingImg() {
        return ratingImg;
    }

    public void setRatingImg(String ratingImg) {
        this.ratingImg = ratingImg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobileURL() {
        return mobileURL;
    }

    public void setMobileURL(String mobileURL) {
        this.mobileURL = mobileURL;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }
}
