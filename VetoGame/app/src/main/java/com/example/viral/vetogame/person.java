package com.example.viral.vetogame;

/**
 * Created by eunkikim on 2/19/15.
 */
public class person {
    String name;
    int value; /* 1 = checkbox checked */

    person(String name, int value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }
}
