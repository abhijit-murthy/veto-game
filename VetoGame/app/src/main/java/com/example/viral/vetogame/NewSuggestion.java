package com.example.viral.vetogame;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class NewSuggestion extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    NewSuggestionPagerAdapter newSuggestionPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    String TabFragmentL;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_suggestion);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        newSuggestionPagerAdapter = new NewSuggestionPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(true);


        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.suggestion_pager);
        mViewPager.setAdapter(newSuggestionPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < newSuggestionPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(newSuggestionPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_new_suggestion, menu);
        //super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Toast.makeText(getApplicationContext(), "clicked",Toast.LENGTH_SHORT).show();
        if (id == R.id.action_save_suggestion) {
            /*gameType = spinner.getSelectedItem().toString();
            String gameName = textGameName.getText().toString();
            Suggestion tempSuggestion = new Suggestion("The Muffin Bakery");
            Game game = new Game(gameName, tempSuggestion, startTime, endTime, (numberInvited+1), gameType);
            Intent intent = new Intent(NewGame.this,GameList.class);
            System.out.println("put game in intent");
            intent.putExtra("gameInfo",game);
            //startActivity(intent);
            setResult(RESULT_OK, intent);
            finish();*/
            //Toast.makeText(getApplicationContext(),"pushed",Toast.LENGTH_SHORT);
            //return true;
            return false;
        }else if(id == android.R.id.home) {
            finish();
            return true;
        }

        return false;
        //return super.onOptionsItemSelected(item);
    }

    public String getTabFragmentL() {
        return TabFragmentL;
    }

    public void setTabFragmentL(String tabFragmentL) {
        TabFragmentL = tabFragmentL;
    }
}
