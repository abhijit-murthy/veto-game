package com.example.viral.vetogame;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import api.RestClient;
import api.model.GameResponse;
import api.model.SuggestionResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PastGameList extends Activity implements SearchView.OnQueryTextListener{

    private PastGameAdapter adapter;
    private ArrayList<Game> games = new ArrayList<Game>();
    private SearchView searchView;
    private RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_game_list);

        /*ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
        suggestions.add(new Suggestion("Muffin Shop","2.2",5,1.5));
        suggestions.add(new Suggestion("The Swamp","2.2",4,10));
        suggestions.add(new Suggestion("Good Burger","2.2",3,15));
        suggestions.add(new Suggestion("The Shore Shack","2.2",4,45));
        suggestions.add(new Suggestion("Krusty Krab","2.2",1,50));

        games.add(new Game("monday lunch", suggestions.get(0), 5,"viral"));
        games.add(new Game("feast", suggestions.get(1), 5,"Shrek"));
        games.add(new Game("dinner", suggestions.get(4), 5,"Patrick"));
        games.add(new Game("snacktime", suggestions.get(3), 5,"Squid"));
        games.add(new Game("monday lunch2", suggestions.get(2), 5,"Kel"));*/

        // Configure device list.
        adapter = new PastGameAdapter(this,games);

        initGame();
        /*Button button = (Button) findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_past_game_list, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_past_game).getActionView();
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

        if(id == android.R.id.home) {
            finish();
            return true;
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(data == null){
                System.out.println("null");
            }else{
                System.out.println("not null");
                Game game = (Game) data.getSerializableExtra("gameInfo");
                adapter.addGame(game);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private AdapterView.OnItemClickListener createOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Need to go to game info screen");
            }
        };
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

    public void initGame(){
        restClient = new RestClient();
        restClient.getGameInfo().getPastGames("ABMURTHY", new Callback<List<GameResponse>>() {
            @Override
            public void success(List<GameResponse> gameResponses, Response response) {
                ListView list = (ListView) findViewById(R.id.past_game_list);
                list.setAdapter(adapter);
                list.setOnItemClickListener(createOnItemClickListener());
                TextView emptyText = (TextView)findViewById(R.id.emptyPastGamesView);

                for(int i=0; i < gameResponses.size(); i++){
                    Calendar eventTime = Calendar.getInstance(TimeZone.getDefault());
                    eventTime.setTime(gameResponses.get(i).getEventTime());
                    Calendar endingTime = Calendar.getInstance();
                    endingTime.setTime(gameResponses.get(i).getEventTime());

                    Suggestion finalSuggestion = new Suggestion(gameResponses.get(i).getSuggestionResponse().getSuggestionName(),
                            gameResponses.get(i).getSuggestionResponse().getLocation(), gameResponses.get(i).getSuggestionResponse().getId(),
                            gameResponses.get(i).getSuggestionResponse().getCreatedAt());
                    Game game = new Game(gameResponses.get(i).getGameId(), gameResponses.get(i).getGameName(),
                            eventTime, gameResponses.get(i).getEventType(), endingTime, gameResponses.get(i).getSuggestionTtl(),
                            gameResponses.get(i).getCenter(), gameResponses.get(i).getRadius(), finalSuggestion, gameResponses.get(i).getUserCount());

                    adapter.addGame(game);
                }

                if(gameResponses.size()==0){
                    emptyText.setText(R.string.empty_past_game_list);
                    list.setEmptyView(emptyText);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Error ", error.getMessage());
            }
        });
    }
}
