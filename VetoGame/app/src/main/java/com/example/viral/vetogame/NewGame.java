package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NewGame extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private static final String TIME_PATTERN = "HH:mm";

    private Button btnEventDate;
    private Button btnEventTime;
    private Button btnLimitDate;
    private Button btnLimitTime;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        Spinner spinner = (Spinner) findViewById(R.id.spin_game_topic);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_topic, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        btnEventDate = (Button) findViewById(R.id.btn_event_date);
        btnEventTime = (Button) findViewById(R.id.btn_event_time);
        btnLimitDate = (Button) findViewById(R.id.btn_limit_date);
        btnLimitTime = (Button) findViewById(R.id.btn_limit_time);

        findViewById(R.id.btn_invite_people).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewGame.this,
                                InvitePeople.class);
                        startActivity(intent);

                    }
                });

        findViewById(R.id.btn_init_suggestion).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewGame.this,
                                InitSuggestion.class);
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
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
     }

    // -1: initialization as current time, 0: event date, 1: event time, 2: date limit, 3: time limit
    private void update() {
        if(id==-1 || id==0)
            btnEventDate.setText(dateFormat.format(calendar.getTime()));
        if(id==-1 || id==1)
            btnEventTime.setText(timeFormat.format(calendar.getTime()));
        if(id==-1 || id==2)
            btnLimitDate.setText(dateFormat.format(calendar.getTime()));
        if(id==-1 || id==3)
            btnLimitTime.setText(timeFormat.format(calendar.getTime()));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
}
