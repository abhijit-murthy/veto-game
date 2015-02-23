package com.example.viral.vetogame;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Viral on 2/19/2015.
 */
public class Game implements Serializable {//implements Parcelable {

    private String gameName;
    private Suggestion currentSuggestion;
    private Calendar startTime;
    private Calendar endTime;
    private int numberOfMembers;
    private String gameType;
    private int gameId;
    private int eventTime;
    private ArrayList<Suggestion> pastSuggestions;
    private Location location;
    private int locationRadius;


    /*// 99.9% of the time you can just ignore this
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(gameName);
        out.writeValue(currentSuggestion);
        out.writeValue(startTime);
        out.writeValue(endTime);
        out.writeInt(numberOfMembers);
        out.writeString(gameType);
        out.writeInt(gameId);
        out.writeInt(eventTime);
        out.writeValue(pastSuggestions);
        out.writeValue(location);
        out.writeInt(locationRadius);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private MyParcelable(Parcel in) {
        mData = in.readInt();
        gameName = in.readString();
        currentSuggestion = (Suggestion)in.readValue(Suggestion.class.getClassLoader());
        startTime = (Calendar)in.readValue(Calendar.class.getClassLoader());
        endTime = (Calendar)in.readValue(Calendar.class.getClassLoader());
        numberOfMembers = in.readInt();
        gameType = in.readString();
        gameId = in.readInt();
        eventTime = in.readInt();
        pastSuggestions = (ArrayAdapter<Suggestion>)in.readValue(ArrayList<Suggestion>.class.getClassLoader());
        location = (Location)in.readValue(Location.class.getClassLoader());
        locationRadius = in.readInt();
    }*/


    public Game(String gameName, Suggestion currentSuggestion, Calendar startTime, Calendar endTime,
                int numberOfMembers, String gameType, int eventTime, Location location,
                int locationRadius){
        this.gameName = gameName;
        this.currentSuggestion = currentSuggestion;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfMembers = numberOfMembers;
        this.gameType = gameType;
        this.eventTime = eventTime;
        this.location = location;
        this.locationRadius = locationRadius;
        pastSuggestions = new ArrayList<Suggestion>();
    }

    public Game(String gameName, Suggestion currentSuggestion, Calendar startTime, Calendar endTime,
                int numberOfMembers, String gameType){
        this.gameName = gameName;
        this.currentSuggestion = currentSuggestion;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfMembers = numberOfMembers;
        this.gameType = gameType;
        pastSuggestions = new ArrayList<Suggestion>();
    }




    //---------------------- Getters and Setters ---------------------------------------------------

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Suggestion getCurrentSuggestion() {
        return currentSuggestion;
    }

    public void setCurrentSuggestion(Suggestion currentSuggestion) {
        this.currentSuggestion = currentSuggestion;
    }

    public Calendar getTimeStarted() {
        return startTime;
    }

    public void setTimeStarted(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getTimeEnding() {
        return endTime;
    }

    public void setTimeEnding(Calendar endTime) {
        this.endTime = endTime;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getEventTime() {
        return eventTime;
    }

    public void setEventTime(int eventTime) {
        this.eventTime = eventTime;
    }

    public ArrayList<Suggestion> getPastSuggestions() {
        return pastSuggestions;
    }

    public void setPastSuggestions(ArrayList<Suggestion> pastSuggestions) {
        this.pastSuggestions = pastSuggestions;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getLocationRadius() {
        return locationRadius;
    }

    public void setLocationRadius(int locationRadius) {
        this.locationRadius = locationRadius;
    }
}
