package com.example.viral.vetogame;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Viral on 2/19/2015.
 */
public class Game implements Serializable {//implements Parcelable {

    // current game DB
    // needs to think about suggestionTTL
    private int gameId;
    private Calendar eventTime;
    private String eventType;
    private Calendar timeEnding;
    private Location center;
    private int radius;

    // maybe need to add to DB
    private String gameName;
    private Suggestion currentSuggestion;
    private int numberOfMembers;

    private ArrayList<Suggestion> pastSuggestions;

    // need for past game?
    private String winner;

    /*// 99.9% of the time you can just ignore this
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(gameName);
        out.writeValue(currentSuggestion);
        out.writeValue(eventTime);
        out.writeValue(timeEnding);
        out.writeInt(numberOfMembers);
        out.writeString(eventType);
        out.writeInt(gameId);
        out.writeInt(eventTime);
        out.writeValue(pastSuggestions);
        out.writeValue(center);
        out.writeInt(radius);
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
        eventTime = (Calendar)in.readValue(Calendar.class.getClassLoader());
        timeEnding = (Calendar)in.readValue(Calendar.class.getClassLoader());
        numberOfMembers = in.readInt();
        eventType = in.readString();
        gameId = in.readInt();
        eventTime = in.readInt();
        pastSuggestions = (ArrayAdapter<Suggestion>)in.readValue(ArrayList<Suggestion>.class.getClassLoader());
        center = (Location)in.readValue(Location.class.getClassLoader());
        radius = in.readInt();
    }*/

    public Game(String gameName, Suggestion currentSuggestion, Calendar eventTime, Calendar timeEnding,
                  int numberOfMembers, String eventType){
        this.gameName = gameName;
        this.currentSuggestion = currentSuggestion;
        this.eventTime = eventTime;
        this.timeEnding = timeEnding;
        this.numberOfMembers = numberOfMembers;
        this.eventType = eventType;
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
        return eventTime;
    }

    public void setTimeStarted(Calendar startTime) {
        this.eventTime = startTime;
    }

    public Calendar getTimeEnding() {
        return timeEnding;
    }

    public void setTimeEnding(Calendar endTime) {
        this.timeEnding = endTime;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public ArrayList<Suggestion> getPastSuggestions() {
        return pastSuggestions;
    }

    public void setPastSuggestions(ArrayList<Suggestion> pastSuggestions) {
        this.pastSuggestions = pastSuggestions;
    }

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
