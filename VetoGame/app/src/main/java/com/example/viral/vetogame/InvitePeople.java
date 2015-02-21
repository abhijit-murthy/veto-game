package com.example.viral.vetogame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.HashMap;


public class InvitePeople extends Activity {
    ListView listPeople;
    ArrayList<Person> personItems;
    PeopleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_people);

        listPeople = (ListView) findViewById(R.id.people);

        personItems = new ArrayList<Person>();
        personItems.add(new Person("Bryan Mishkin", false));
        personItems.add(new Person("Drew Orr", false));
        personItems.add(new Person("Mike Jung", false));
        personItems.add(new Person("Nini Xia", false));
        personItems.add(new Person("Sherry Liu", false));
        personItems.add(new Person("Jack Mishkin", false));
        personItems.add(new Person("Drew Orr", false));
        personItems.add(new Person("Mike Jung", false));
        personItems.add(new Person("Nini Xia", false));
        personItems.add(new Person("Sherry Liu", false));
        personItems.add(new Person("Bryan Mishkin", false));
        personItems.add(new Person("Drew Orr", false));
        personItems.add(new Person("Mike Jung", false));
        personItems.add(new Person("Nini Xia", false));
        personItems.add(new Person("Sherry Liu", false));
        adapter = new PeopleAdapter(this, personItems);
        listPeople.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invite_people, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tbtn_onClick(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            ArrayList<Person> personInvited = new ArrayList<Person>();
            int j = 0;
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            personInvited.clear();
            for(int i=0; i<personItems.size(); i++){
                if(personItems.get(i).getChecked()){
                    personInvited.add(personItems.get(i));
                    map.put(j, i);
                    j++;
                }
            }

            PeopleAdapter adapter = new PeopleAdapter(this, personItems, personInvited);
            adapter.setMap(map);
            listPeople.setAdapter(adapter);
        } else {
            PeopleAdapter adapter = new PeopleAdapter(this, personItems);
            listPeople.setAdapter(adapter);
        }
    }
}
