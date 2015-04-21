package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import api.RestClient;
import api.model.SuggestionResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CurrGame extends Activity {
    private Game currGame;
    private TextView timeWin;
    private static final String FORMAT = "%02dd %02dh %02dm %02ds";
    private RestClient restClient;
    private int votes;
    private int num_players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr_game);

        currGame = (Game) getIntent().getSerializableExtra("currGame");
        
        Button btnCurrSuggestion = (Button) findViewById(R.id.btn_curr_suggestion);
        btnCurrSuggestion.setText(currGame.getCurrentSuggestion().toString());
        
        restClient = new RestClient();

        votes =  currGame.getCurrentSuggestion().getVotes();
        num_players = currGame.getNumberOfMembers();
        TextView tv = (TextView) findViewById(R.id.num_supporters);
        tv.setText(Integer.toString(votes)+"/"+Integer.toString(num_players));

        findViewById(R.id.btn_past_suggestions).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CurrGame.this,
                                PastSuggestion.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.btn_veto).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CurrGame.this,
                                NewSuggestion.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.btn_upvote).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (votes < num_players) {

                            restClient.getSuggestionInfo().upvote(currGame.getGameId(), currGame.getCurrentSuggestion().getSuggestionId(), new Callback<SuggestionResponse>() {
                                @Override
                                public void success(SuggestionResponse suggestionResponses, Response response) {
                                    votes = suggestionResponses.getVotes();
                                    TextView tv = (TextView) findViewById(R.id.num_supporters);
                                    tv.setText(Integer.toString(votes) + "/" + Integer.toString(num_players));
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    System.out.println("CURRGAME FINISHED");
                                }
                            });
                        }
                    }
                });

        timeWin = (TextView) findViewById(R.id.remaining_time_win);

        long startTime = Calendar.getInstance().getTimeInMillis();
        long endTime = currGame.getTimeEnding().getTimeInMillis();
        long diffTime = endTime - startTime;

        new CountDownTimer(diffTime, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                timeWin.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toDays(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.HOURS.toHours(
                                TimeUnit.MILLISECONDS.toDays(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timeWin.setText("done!");
            }
        }.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_curr_game, menu);
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
