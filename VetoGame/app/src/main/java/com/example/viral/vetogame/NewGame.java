package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import api.RestClient;
import api.model.GameResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewGame extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private static final String TIME_PATTERN = "hh:mm a";

    private RestClient restClient;
    private EditText textGameName;
    private String gameName;
    private Button btnEventDate;
    private Button btnEventTime;
    private Button btnLimitDate;
    private Button btnLimitTime;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private int id = -1;
    private Calendar calendar;
    private Calendar startTime; // event time
    private Calendar endTime;   // game end time
    private Suggestion tempSuggestion;

    private boolean startTimeChanged = false;
    private boolean endTimeChanged = false;

    private String gameId ="";
    private int radius = 0;
    private String center ="";
    private int suggestionTtl = 0;
    private int numberInvited = 0;
    private String userIds = "";
    private String gameType = "";
    private Spinner spinner;

    private int dayLimit = 3;
    private Suggestion suggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        spinner = (Spinner) findViewById(R.id.spin_game_topic);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_topic, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        calendar = Calendar.getInstance();
        startTime = calendar;
        endTime = calendar;
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        btnEventDate = (Button) findViewById(R.id.btn_event_date);
        btnEventTime = (Button) findViewById(R.id.btn_event_time);
        btnLimitDate = (Button) findViewById(R.id.btn_limit_date);
        btnLimitTime = (Button) findViewById(R.id.btn_limit_time);
        textGameName = (EditText)findViewById(R.id.game_name);

        findViewById(R.id.btn_invite_people).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewGame.this,
                                InvitePeople.class);
                        startActivityForResult(intent,1);

                    }
                });

        findViewById(R.id.btn_init_suggestion).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewGame.this,
                                NewSuggestion.class);
                        //MainActivity.class);
                        //InitSuggestion.class);
                        startActivityForResult(intent, 2);

                    }
                });

        if(savedInstanceState!= null){
            System.out.println("savedIntance not null");
            textGameName.setText(savedInstanceState.getString("gameName"));
            startTime = (Calendar) savedInstanceState.getSerializable("eventTime");
            gameType = savedInstanceState.getString("gameType");
            numberInvited = savedInstanceState.getInt("numberInvited");
            endTime = (Calendar) savedInstanceState.getSerializable("endTime");
            suggestion = (Suggestion) savedInstanceState.getSerializable("suggestion");
        }else{
            System.out.println("savedIntance is null");
        }

        update();
    }

    @Override
    public void onStop(){
        super.onStop();
        System.out.println("On stop " + this.getClass());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("On destroy " + this.getClass());
    }

    @Override
    public void onPause(){
        super.onPause();
        System.out.println("On pause " + this.getClass());
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("On resume " + this.getClass());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_event_date:
                showDatePickerDialog(view);
                id = 0;
                break;
            case R.id.btn_event_time:
                showTimePickerDialog(view);
                id = 1;
                break;
            case R.id.btn_limit_date:
                showDatePickerDialog(view);
                id = 2;
                break;
            case R.id.btn_limit_time:
                showTimePickerDialog(view);
                id = 3;
                break;
        }
    }

    // Date picker
    public void showDatePickerDialog(View v){
        DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
    }

    // Time picker
    public void showTimePickerDialog(View v){
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show(getFragmentManager(), "timePicker");
    }

    // -1: initialization as current time, 0: event date, 1: event time, 2: date limit, 3: time limit
    private void update() {
        Calendar currentTime = Calendar.getInstance();
        if(id==0) {
            startTime = (Calendar)calendar.clone();
            startTimeChanged = true;
            if(timeDiffInDays(currentTime,startTime) > dayLimit){
                Toast.makeText(getApplicationContext(), "Choose a time within "+dayLimit+" days",
                        Toast.LENGTH_SHORT).show();
            }else if(timeDiffInDays(currentTime,startTime)<0){//currentTime.after(startTime)){
                if(startTimeChanged) {
                    Toast.makeText(getApplicationContext(), "Choose a future time",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                btnEventDate.setText(dateFormat.format(startTime.getTime()));
            }
        }
        if(id==1) {
            startTime = (Calendar)calendar.clone();
            startTimeChanged = true;
            if(timeDifference(currentTime,startTime) == -1){
                if(startTimeChanged) {
                    Toast.makeText(getApplicationContext(), "Choose a future time",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                btnEventTime.setText(timeFormat.format(startTime.getTime()));
            }
        }
        if(id==2) {
            endTime = (Calendar)calendar.clone();
            endTimeChanged = true;
            if(timeDifference(startTime,endTime)>0 || timeDifference(currentTime,endTime)<0){
                if(endTimeChanged) {
                    Toast.makeText(getApplicationContext(), "Choose a future day, but on or before the event day",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                btnLimitDate.setText(dateFormat.format(endTime.getTime()));
            }
        }
        if(id==3) {
            endTime = (Calendar)calendar.clone();
            endTimeChanged = true;
            if(timeDifference(startTime,endTime)>=0){
                if(endTimeChanged) {
                    Toast.makeText(getApplicationContext(), "Choose a time before event time",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                btnLimitTime.setText(timeFormat.format(endTime.getTime()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_save) {
            gameType = spinner.getSelectedItem().toString();
            gameName = textGameName.getText().toString();
            tempSuggestion = new Suggestion("The Muffin Bakery");
            restClient = new RestClient();

            restClient.getGameInfo().createGame("ABMURTHY", gameType, suggestionTtl, center, radius, gameName, formatDatetime(startTime), formatDatetime(endTime), new Callback<GameResponse>() {
                @Override
                public void success(GameResponse gameResponse, Response response) {
                    gameId = gameResponse.getGameId();

                 String[] users = userIds.split(",");
                    for(int i=0; i<users.length; i++) {
                        restClient.getGameInfo().addUsers(users[i], gameId, new Callback<GameResponse>() {
                            @Override
                            public void success(GameResponse gameResponse, Response response) {
                                int check = 0;
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.i("Error ", error.getMessage());
                            }
                        });
                    }
                    Game game = new Game(gameId, gameName, startTime, gameType, endTime, suggestionTtl, center, radius, tempSuggestion,  (numberInvited+1));

                    Intent intent = new Intent(NewGame.this,GameList.class);
                    System.out.println("put game in intent");
                    intent.putExtra("gameInfo",game);
                    //startActivity(intent);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("Error ", error.getMessage());
                }
            });
        }else if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);

        update();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        update();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //System.out.println("requestCode "+requestCode);
        //System.out.println("resultCode "+resultCode);
        //System.out.println("number = "+data.getIntExtra("numberInvited",0));
        if (requestCode == 1) {
            if(data != null) {
                TextView invitePeople = (TextView) findViewById(R.id.btn_invite_people);
                numberInvited = data.getIntExtra("numberInvited", 0);
                invitePeople.setText("" + numberInvited + " people invited");
                userIds = data.getStringExtra("userIds");
            }
        }else if (requestCode == 2) {
            if(data != null) {
                suggestion = (Suggestion)data.getSerializableExtra("suggestion");
                radius = data.getIntExtra("radius",1);
                center = data.getStringExtra("center");
                Button suggestionButton = (Button) findViewById(R.id.btn_init_suggestion);
                suggestionButton.setText(suggestion.getName());

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        System.out.println("OnSave");
        savedInstanceState.putString("gameName", textGameName.getText().toString());
        savedInstanceState.putSerializable("eventTime", startTime);
        savedInstanceState.putString("gameType", gameType);
        savedInstanceState.putInt("numberInvited", numberInvited);
        savedInstanceState.putSerializable("endTime", endTime);
        savedInstanceState.putSerializable("suggestion", suggestion);
        super.onSaveInstanceState(savedInstanceState);
        System.out.println("data saved");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("OnRestore");
        textGameName.setText(savedInstanceState.getString("gameName"));
        startTime = (Calendar) savedInstanceState.getSerializable("eventTime");
        gameType = savedInstanceState.getString("gameType");
        numberInvited = savedInstanceState.getInt("numberInvited");
        endTime = (Calendar) savedInstanceState.getSerializable("endTime");

        suggestion = (Suggestion) savedInstanceState.getSerializable("suggestion");

        btnEventDate.setText(dateFormat.format(startTime.getTime()));
        btnEventTime.setText(timeFormat.format(startTime.getTime()));
        btnLimitDate.setText(dateFormat.format(endTime.getTime()));
        btnLimitTime.setText(timeFormat.format(endTime.getTime()));
        Resources res = getResources();
        String [] topics = res.getStringArray(R.array.game_topic);
        spinner.setSelection(getIndex(topics,gameType));
    }

    public int getIndex(String [] array, String value){
        for(int i=0;i<array.length;i++){
            if(array[i].equals(value)){
                return i;
            }else{
                return -1;
            }
        }
        return -1;
    }

    public int timeDiffInDays(Calendar currentTime, Calendar chosenTime)
    {
        long curTime = currentTime.getTimeInMillis();
        long choseTime = chosenTime.getTimeInMillis();
        long diffTime = choseTime - curTime;
        //System.out.println("cur time "+curTime);
        //System.out.println("cho time "+choseTime);
        //System.out.println("dif time "+diffTime);
        // divide the milliseconds by # of milliseconds in a day to get days difference
        int days = (int)( diffTime / (1000 * 60 * 60 * 24) );
        System.out.println("days "+days);
        return days;
    }

    public int timeDifference(Calendar currentTime, Calendar chosenTime)
    {
        long curTime = currentTime.getTimeInMillis();
        long choseTime = chosenTime.getTimeInMillis();
        long diffTime = choseTime - curTime;
        //System.out.println("cur time "+curTime);
        //System.out.println("cho time "+choseTime);
        //System.out.println("dif time "+diffTime);
        // divide the milliseconds by # of milliseconds in a day to get days difference
        int days = (int)( diffTime / (1000 * 60 * 60 * 24) );
        int hours = (int)( diffTime / (1000 * 60 * 60) );
        int minutes = (int)( diffTime / (1000 * 60) );
        //System.out.println("hours "+hours);
        //System.out.println("min "+minutes);

        if(days > 0){
            suggestionTtl = days * 60;
            return 1;
        }else{
            if(hours > 0){
                suggestionTtl = 30*hours/24+30;
                return 1;
            }else{
                if(minutes > 0){
                    suggestionTtl = 30*minutes/60 + 1;
                    return 1;
                }else if(minutes == 0){
                    return 0;
                }else{
                    return -1;
                }
            }
        }
    }

    // Date and time format: yyyy-mm-dd hh:mm:ss
    public String formatDatetime(Calendar calendar){
        String tmp1 = "";
        String tmp2 = "";
        int m = calendar.get(Calendar.MONTH)+1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        if(m < 10) tmp1 = "0"+m;
        else tmp1 = ""+m;

        if(d < 10) tmp2 = "0"+d;
        else tmp2 = ""+d;

        String date = calendar.get(Calendar.YEAR) + "-" + tmp1 + "-" + tmp2;

        m = calendar.get(Calendar.MINUTE);
        d = calendar.get(Calendar.SECOND);

        if(m < 10) tmp1 = "0"+m;
        else tmp1 = ""+m;

        if(d < 10) tmp2 = "0"+d;
        else tmp2 = ""+d;

        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + tmp1 + ":" + tmp2;

        return date+" "+time;
    }

}
