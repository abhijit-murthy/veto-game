package com.example.viral.vetogame;

/**
 * Created by eunkikim on 2/19/15.
 */
public class Person {
    String name;
    String userId;
    boolean checked; /* 1 = checkbox checked */

    Person(String name, String userId, Boolean checked){
        this.name = name;
        this.userId = userId;
        this.checked = checked;
    }

    public String getName(){
        return this.name;
    }
    public String getUserId() { return userId; }
    public Boolean getChecked(){
        return this.checked;
    }
    public void setChecked(boolean checked){
        this.checked = checked;
    }
}
