package com.example.viral.vetogame;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Viral on 2/19/2015.
 */
public class Game implements Serializable {//implements Parcelable {

    private String gameId;

    // current game DB
    // needs to think about suggestionTTL
    private String userId;
    private Calendar eventTime;
    private String eventType;
    private int suggestionTTL;
    private Calendar timeEnding;

    //private Location center;
    private String center;
    private int radius;


    // maybe need to add to DB
    private String gameName;
    private Suggestion currentSuggestion;
    private int numberOfMembers;

    private ArrayList<Suggestion> pastSuggestions;

    // need for past game?
    private String winner;

    // temporary constructor for matching DB
    public Game(String gameId, String gameName, Calendar eventTime, String eventType, Calendar timeEnding,
                int suggestionTTL, String center, int radius, Suggestion currentSuggestion, int numberOfMembers){
        this.gameId = gameId;
        this.gameName = gameName;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.timeEnding = timeEnding;
        this.suggestionTTL = suggestionTTL;
        this.center = center;
        this.radius = radius;
        this.currentSuggestion = currentSuggestion;
        this.numberOfMembers = numberOfMembers;
    }

    /*
    public Game(String userId, String eventType,
                int suggestionTTL, String center, int radius){
        this.userId = userId;
        this.eventType = eventType;
        this.suggestionTTL = suggestionTTL;
        this.center = center;
        this.radius = radius;
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

    //Temporary constructor to test searching
    public Game(String gameName, Suggestion currentSuggestion, int numberOfMembers, String winner){
        this.gameName = gameName;
        this.currentSuggestion = currentSuggestion;
        this.numberOfMembers = numberOfMembers;
        this.winner = winner;
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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public ArrayList<Suggestion> getPastSuggestions() {
        return pastSuggestions;
    }

    public void setPastSuggestions(ArrayList<Suggestion> pastSuggestions) {
        this.pastSuggestions = pastSuggestions;
    }

    /*public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }*/

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Calendar getEventTime() {
        return eventTime;
    }

    public void setEventTime(Calendar eventTime) {
        this.eventTime = eventTime;
    }

    public int getSuggestionTTL() {
        return suggestionTTL;
    }

    public void setSuggestionTTL(int suggestionTTL) {
        this.suggestionTTL = suggestionTTL;
    }

    // temporary until DB is fixed
    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
