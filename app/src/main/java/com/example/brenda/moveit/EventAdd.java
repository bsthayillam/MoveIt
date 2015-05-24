package com.example.brenda.moveit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;


public class EventAdd extends ActionBarActivity {

    EditText location, additionalTime;
    Button whenDate, whenTime;
    Spinner repeat;
    Switch priority;
    RadioButton alarm, vibrate, priorityYes, priorityNo;
    private int month, date, year, hour, min;

    static final int DATE_PICKER_ID = 1111;
    static final int TIME_PICKER_ID = 2222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        location = (EditText) findViewById(R.id.whereLoc);
        additionalTime = (EditText) findViewById(R.id.time);
        whenDate = (Button) findViewById(R.id.changeDate);
        whenTime = (Button) findViewById(R.id.changeTime);
        priorityYes = (RadioButton) findViewById(R.id.priorityYes);
        priorityNo = (RadioButton) findViewById(R.id.priorityNo);
        alarm = (RadioButton) findViewById(R.id.alarm);
        vibrate = (RadioButton) findViewById(R.id.vibration);
        repeat = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.repeat_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeat.setAdapter(adapter);

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);

        whenDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID);
            }
        });
        whenTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(TIME_PICKER_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, pickerListenerDate, year, month,date);
            case TIME_PICKER_ID:
                return new TimePickerDialog(this, pickerListenerTime, hour, min, true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListenerDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            date   = selectedDay;
        }
    };

    private TimePickerDialog.OnTimeSetListener pickerListenerTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_add, menu);
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
