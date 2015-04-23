package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Date;

import api.RestClient;
import api.model.SuggestionResponse;
import api.model.UserResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class CurrGame extends Activity implements OnClickListener{
    private Game currGame;
    private TextView timeWin;
    private static final String FORMAT = "%02dd %02dh %02dm %02ds";
    private RestClient restClient;
    private int votes;
    private int num_players;
    private Button btnPlayers;
    private AlertDialog.Builder builder;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr_game);

        currGame = (Game) getIntent().getSerializableExtra("currGame");
        num_players = currGame.getNumberOfMembers();

        map = ((MapFragment) this.getFragmentManager().findFragmentById(R.id.currGameMapView)).getMap();
        updateScreen(currGame);

        restClient = new RestClient();
        builder = new AlertDialog.Builder(this);

        restClient.getSuggestionInfo().getCurrSuggestion(currGame.getGameId(), new Callback<SuggestionResponse>() {
            @Override
            public void success(SuggestionResponse suggestionResponses, Response response) {
                votes = suggestionResponses.getVotes();
                TextView tv = (TextView) findViewById(R.id.num_supporters);
                tv.setText(Integer.toString(votes) + "/" + Integer.toString(num_players));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Error ", error.getMessage());
            }
        });

        Calendar testTime = currGame.getEventTime();

        TextView eventTime = (TextView) findViewById(R.id.event_time);
        eventTime.setText(testTime.getTime().toString());

        btnPlayers = (Button) findViewById(R.id.btn_players);
        btnPlayers.setOnClickListener(this);

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
                        intent.putExtra("currGame", currGame);
                        startActivityForResult(intent,1);
                    }
                });
        findViewById(R.id.btn_upvote).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (votes < num_players) {

                            //System.out.println("UPVOTE: before: "+votes);
                            //System.out.println("UPVOTE: Sugg ID: "+currGame.getCurrentSuggestion().getSuggestionId());
                            restClient.getSuggestionInfo().upvote(currGame.getGameId(), currGame.getCurrentSuggestion().getSuggestionId(), new Callback<SuggestionResponse>() {
                                @Override
                                public void success(SuggestionResponse suggestionResponses, Response response) {
                                    votes = suggestionResponses.getVotes();
                                    //System.out.println("UPVOTE after: "+votes);
                                    TextView tv = (TextView) findViewById(R.id.num_supporters);
                                    tv.setText(Integer.toString(votes) + "/" + Integer.toString(num_players));
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.i("Error ", error.getMessage());
                                    System.out.println("CURRGAME FINISHED");
                                }
                            });
                        }
                    }
                });

        timeWin = (TextView) findViewById(R.id.remaining_time_win);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date suggestionDate = new Date();   // Time when suggestion is created

        try {
            suggestionDate = sdf.parse(currGame.getCurrentSuggestion().getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar suggestionCal = Calendar.getInstance();
        suggestionCal.setTime(suggestionDate);

        long currTime = Calendar.getInstance().getTimeInMillis();  // current time
        long suggestionEndTime = suggestionCal.getTimeInMillis()+TimeUnit.MINUTES.toMillis(currGame.getSuggestionTTL());

        long diffTime = suggestionEndTime - currTime;   // remaining time until the current suggestion wins considering suggestion TTL

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
    public void onClick(View v) {

        restClient.getUserInfo().getUsers(currGame.getGameId(), new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                List<String> listItems = new ArrayList<String>();

                List<UserResponse> people = userResponse.getPlayers();

                for(int i=0; i<people.size(); i++){
                    listItems.add(people.get(i).getUserName());
                }

                final CharSequence[] players = listItems.toArray(new CharSequence[listItems.size()]);

                builder.setTitle("Players in this game");
                builder.setItems(players, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //System.out.println("requestCode "+requestCode);
        //System.out.println("resultCode "+resultCode);
        //System.out.println("number = "+data.getIntExtra("numberInvited",0));
        if (requestCode == 1) {
            if(data != null) {
                final Game updatedGame = (Game) data.getSerializableExtra("currGame");
                /*System.out.println("VETO gameID: "+updatedGame.getGameId());
                System.out.println("VETO suggID: "+updatedGame.getCurrentSuggestion().getSuggestionId());
                System.out.println("VETO suggID: "+currGame.getCurrentSuggestion().getSuggestionId());
                System.out.println("VETO suggName: "+updatedGame.getCurrentSuggestion().getName());
                System.out.println("VETO url: "+updatedGame.getCurrentSuggestion().getMobileURL());*/

                restClient.getSuggestionInfo().
                        veto(updatedGame.getGameId(), Login.getUser(),
                                currGame.getCurrentSuggestion().getSuggestionId(),
                                updatedGame.getCurrentSuggestion().getName(),
                                updatedGame.getCurrentSuggestion().getLocation_string(),
                                updatedGame.getCurrentSuggestion().getMobileURL(),
                                updatedGame.getCurrentSuggestion().getImage(),
                                updatedGame.getCurrentSuggestion().getRatingImg(),
                                new Callback<SuggestionResponse>() {
                    @Override
                    public void success(SuggestionResponse suggestionResponses, Response response) {
                        votes = suggestionResponses.getVotes();
                        //System.out.println("VETO after: "+votes);
                        TextView tv = (TextView) findViewById(R.id.num_supporters);
                        tv.setText(Integer.toString(votes) + "/" + Integer.toString(num_players));
                        updateScreen(updatedGame);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("Error ", error.getMessage());
                        System.out.println("VETO FAILED");
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateScreen(final Game game){
        LatLng latLng = getLocationFromAddress(game.getCurrentSuggestion().getLocation_string());
        Button btnCurrSuggestion = (Button) findViewById(R.id.btn_curr_suggestion);
        btnCurrSuggestion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(game.getCurrentSuggestion().getMobileURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnCurrSuggestion.setText(game.getCurrentSuggestion().toString());

        TextView addressView = (TextView) findViewById(R.id.curr_suggestion_address);
        addressView.setText(game.getCurrentSuggestion().getLocation_string());

        //System.out.println("CURRGAME sug id: "+currGame.getCurrentSuggestion().getSuggestionId());
        ImageView imageView = (ImageView) findViewById(R.id.curr_suggestion_image);
        new GetImageFromURL(imageView).execute(game.getCurrentSuggestion().getImage());

        if(map!=null){
            if(latLng != null) {
                map.clear();
                MarkerOptions eventMark = new MarkerOptions().position(latLng)
                        .title(game.getCurrentSuggestion().getName());
                map.addMarker(eventMark);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,13);
                map.moveCamera(cameraUpdate); //.animateCamera(cameraUpdate);
            }
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {
        LatLng latLng = null;
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                Toast.makeText(this, "Unable to geocode location", Toast.LENGTH_LONG).show();
                return null;
            }
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(),location.getLongitude());

            return latLng;
        } catch (IOException e) {
            Toast.makeText(this, "Unable to geocode location", Toast.LENGTH_LONG).show();
        }

        return latLng;
    }

}
