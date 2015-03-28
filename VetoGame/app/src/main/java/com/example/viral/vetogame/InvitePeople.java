package com.example.viral.vetogame;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.HashMap;


public class InvitePeople extends Activity implements SearchView.OnQueryTextListener{
    private ListView listPeople;
    private SearchView searchView;

    private ArrayList<Person> personItems;
    private ArrayList<Person> filteredList;

    private PeopleAdapter adapter;
    private int numberInvited = 0;

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
        personItems.add(new Person("Jack Kim", false));
        personItems.add(new Person("Erica Lee", false));
        personItems.add(new Person("Michelle Davis", false));
        personItems.add(new Person("Terry Min", false));
        personItems.add(new Person("Jessica Denton", false));
        personItems.add(new Person("John Mishkin", false));
        personItems.add(new Person("Seth Orr", false));
        personItems.add(new Person("Leo Yang", false));
        personItems.add(new Person("Sally Watson", false));
        personItems.add(new Person("Adair Liu", false));
        adapter = new PeopleAdapter(this, personItems);
        listPeople.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_invite_people, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_invite_people, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //searchView = (SearchView) findViewById(R.id.search_invite_people);
        searchView = (SearchView) menu.findItem(R.id.search_invite_people).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_save_people) {
            Intent intent = new Intent(InvitePeople.this,
                    NewGame.class);

            numberInvited = 0;
            for(int i=0;i<personItems.size();i++){
                if(personItems.get(i).getChecked()){
                    numberInvited++;
                }
            }
            intent.putExtra("numberInvited",numberInvited);
            //startActivity(intent);
            setResult(RESULT_OK, intent);
            finish();
        }else if(id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // toggle button - show invited people
    public void tbtn_onClick(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            ArrayList<Person> personInvited = new ArrayList<Person>();
            //int j = 0;
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            personInvited.clear();
            for(int i=0; i<personItems.size(); i++){
                if(personItems.get(i).getChecked()){
                    personInvited.add(personItems.get(i));
                    //map.put(j, i);
                    //j++;
                    map.put(personItems.get(i).getId(),i);
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

    @Override
    public boolean onQueryTextChange(String newText)
    {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }
}
