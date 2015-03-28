package com.example.viral.vetogame;

import java.util.Random;

/**
 * Created by eunkikim on 2/19/15.
 */
public class Person {
    private String name;
    private boolean checked; /* 1 = checkbox checked */
    private int id;

    Person(String name, Boolean checked){
        this.name = name;
        this.checked = checked;
        Random rand = new Random();
        id = rand.nextInt();
    }

    Person(String name, Boolean checked, int id){
        this.name = name;
        this.checked = checked;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
