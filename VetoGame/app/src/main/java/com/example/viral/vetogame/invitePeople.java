package com.example.viral.vetogame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class InvitePeople extends Activity {
    ListView listPeople;
    Person[] personItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_people);

        listPeople = (ListView) findViewById(R.id.people);
        personItems = new Person[5];
        personItems[0] = new Person("Bryan Mishkin", 0);
        personItems[1] = new Person("Drew Orr", 0);
        personItems[2] = new Person("Mike Jung", 0);
        personItems[3] = new Person("Nini Xia", 0);
        personItems[4] = new Person("Sherry Liu", 0);
        PeopleAdapter adapter = new PeopleAdapter(this, personItems);
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
}
