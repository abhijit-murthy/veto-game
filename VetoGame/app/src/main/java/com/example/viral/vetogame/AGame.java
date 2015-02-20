package com.example.viral.vetogame;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Viral on 2/19/2015.
 */
public class AGame {

    private String gameName;
    private Suggestion currentSuggestion;
    private Date timeStarted;
    private Date timeEnding;
    private int numberOfMembers;
    private String gameType;
    private int gameId;
    private int eventTime;
    private ArrayList<Suggestion> pastSuggestions;
    private Location location;
    private int locationRadius;


    public AGame(String gameName, Suggestion currentSuggestion, Date timeStarted, Date timeEnding,
                 int numberOfMembers, String gameType, int eventTime, Location location,
                 int locationRadius){
        this.gameName = gameName;
        this.currentSuggestion = currentSuggestion;
        this.timeStarted = timeStarted;
        this.timeEnding = timeEnding;
        this.numberOfMembers = numberOfMembers;
        this.gameType = gameType;
        this.eventTime = eventTime;
        this.location = location;
        this.locationRadius = locationRadius;
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

    public Date getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Date getTimeEnding() {
        return timeEnding;
    }

    public void setTimeEnding(Date timeEnding) {
        this.timeEnding = timeEnding;
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
