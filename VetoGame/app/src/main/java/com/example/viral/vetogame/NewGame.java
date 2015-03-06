package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NewGame extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private static final String TIME_PATTERN = "hh:mm a";


    private EditText textGameName;
    private Button btnEventDate;
    private Button btnEventTime;
    private Button btnLimitDate;
    private Button btnLimitTime;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private int id = -1;
    private Calendar calendar;
    private Calendar startTime; // event calendar (event date, event time)
    private Calendar endTime;   // limit calendar (limit date, limit time)

    private boolean startTimeChanged = false;
    private boolean endTimeChanged = false;

    private int numberInvited = 0;
    private String gameType = "";
    private Spinner spinner;

    private int dayLimit = 3;

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
                        startActivity(intent);

                    }
                });

        update();
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
            startTime = calendar;
            startTimeChanged = true;
            if(timeDiffInDays(currentTime,startTime) > dayLimit){
                Toast.makeText(getApplicationContext(), "Choose a time within "+dayLimit+"days",
                        Toast.LENGTH_SHORT).show();
            }else if(currentTime.after(startTime)){
                if(startTimeChanged) {
                    Toast.makeText(getApplicationContext(), "Choose a future time",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                btnEventDate.setText(dateFormat.format(startTime.getTime()));
            }
        }
        if(id==1) {
            startTime = calendar;
            startTimeChanged = true;
            if(currentTime.after(startTime)){
                if(startTimeChanged) {
                    Toast.makeText(getApplicationContext(), "Choose a future time",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                btnEventTime.setText(timeFormat.format(startTime.getTime()));
            }
        }
        if(id==2) {
            endTime = calendar;
            endTimeChanged = true;
            if(currentTime.after(startTime)||startTime.after(endTime)){
                if(endTimeChanged) {
                    Toast.makeText(getApplicationContext(), "Choose a future time, but before event time",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                btnLimitDate.setText(dateFormat.format(endTime.getTime()));
            }
        }
        if(id==3) {
            endTime = calendar;
            endTimeChanged = true;
            if(currentTime.after(startTime)||endTime.after(startTime)){
                if(endTimeChanged) {
                    Toast.makeText(getApplicationContext(), "Choose a future time, but before event time",
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
            String gameName = textGameName.getText().toString();
            Suggestion tempSuggestion = new Suggestion("The Muffin Bakery");
            Game game = new Game(gameName, tempSuggestion, startTime, endTime, (numberInvited+1), gameType);
            Intent intent = new Intent(NewGame.this,GameList.class);
            System.out.println("put game in intent");
            intent.putExtra("gameInfo",game);
            //startActivity(intent);
            setResult(RESULT_OK, intent);
            finish();
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
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public int timeDiffInDays(Calendar currentTime, Calendar chosenTime)
    {
        long curTime = currentTime.getTimeInMillis();
        long choseTime = chosenTime.getTimeInMillis();
        long diffTime = choseTime - curTime;
        // divide the milliseconds by # of milliseconds in a day to get days difference
        int days = (int)( diffTime / (1000 * 60 * 60 * 24) );
        return days;
    }

}
