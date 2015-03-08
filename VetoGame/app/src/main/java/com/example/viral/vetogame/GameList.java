package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import api.RestClient;
import api.model.GameResponse;
import api.model.UserResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.util.Log;

import java.util.Calendar;


public class GameList extends Activity {

    private GameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        // Configure device list.
        adapter = new GameAdapter(this);
        ListView list = (ListView) findViewById(R.id.game_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(createOnItemClickListener());
        TextView emptyText = (TextView)findViewById(R.id.emptyGamesView);
        list.setEmptyView(emptyText);

        Button button = (Button) findViewById(R.id.btn_new_game);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GameList.this,
                        NewGame.class);
                startActivityForResult(intent, 1);
            }
        });

        Button past_games_button = (Button) findViewById(R.id.btn_past_games);
        past_games_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GameList.this, PastGameList.class);
                startActivity(intent);
            }
        });

        RestClient restClient = new RestClient();

        Game newGame = new Game(null, "lunch", null, 15, "BUCKHEAD", 10);

        restClient.getGameInfo().createGame(newGame, new Callback<Game>() {
            @Override
            public void success(Game game, Response response) {
                int check = 0;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Error ", error.getMessage());
            }
        });

        restClient.getGameInfo().getGame("1", new Callback<GameResponse>() {
            @Override
            public void success(GameResponse gameResponse, Response response) {
                // success!
                Log.i("User ID ", gameResponse.getUserId());
                Log.i("Event type ", gameResponse.getEventType());
                Log.i("Radius ", Integer.toString(gameResponse.getRadius()));
                // you get the point...
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Error ", error.getMessage());
            }
        });

        restClient.getUserInfo().getUser("ABCDE", new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                // success!
                Log.i("User ID ", userResponse.getUserId());
                Log.i("User Name ", userResponse.getUserName());
                Log.i("Wins ", Integer.toString(userResponse.getWins()));
                // you get the point...
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Error ", error.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_list, menu);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(data == null){
                System.out.println("null");
            }else{
                System.out.println("not null");
                Game game = (Game) data.getSerializableExtra("gameInfo");
                adapter.addGame(game);
                Toast.makeText(getApplicationContext(), "Game Created", Toast.LENGTH_SHORT).show();
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
}
