package com.example.viral.vetogame;

/**
 * Created by eunkikim on 2/19/15.
 */
public class Person {
    String name;
    boolean checked; /* 1 = checkbox checked */

    Person(String name, Boolean checked){
        this.name = name;
        this.checked = checked;
    }
    public String getName(){
        return this.name;
    }
    public Boolean getChecked(){
        return this.checked;
    }
    public void setChecked(boolean checked){
        this.checked = checked;
    }
}
