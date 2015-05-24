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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;


public class EventAdd extends ActionBarActivity {

    EditText description, location, additionalTime;
    Button whenDate, whenTime, submit;
    RadioButton alarm, vibrate, priorityYes, priorityNo;
    CheckBox weekly, mondays, tuesdays, wednesdays, thursdays, fridays, saturdays, sundays, monthly;
    private String locationVal;
    private int month, date, year, hour, min, addTime;
    private boolean priority;
    private String alarmType;
    private EventOperations eventDBOperations;

    static final int DATE_PICKER_ID = 1111;
    static final int TIME_PICKER_ID = 2222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        description = (EditText) findViewById(R.id.descriptionText);
        location = (EditText) findViewById(R.id.whereLoc);
        additionalTime = (EditText) findViewById(R.id.time);
        whenDate = (Button) findViewById(R.id.changeDate);
        whenTime = (Button) findViewById(R.id.changeTime);
        priorityYes = (RadioButton) findViewById(R.id.priorityYes);
        priorityNo = (RadioButton) findViewById(R.id.priorityNo);
        alarm = (RadioButton) findViewById(R.id.alarm);
        vibrate = (RadioButton) findViewById(R.id.vibration);
        submit = (Button) findViewById(R.id.submit);

        weekly = (CheckBox) findViewById(R.id.weekly);
        mondays = (CheckBox) findViewById(R.id.mondays);
        tuesdays = (CheckBox) findViewById(R.id.tuesdays);
        wednesdays = (CheckBox) findViewById(R.id.wednesdays);
        thursdays = (CheckBox) findViewById(R.id.thursdays);
        fridays = (CheckBox) findViewById(R.id.fridays);
        saturdays = (CheckBox) findViewById(R.id.saturdays);
        sundays = (CheckBox) findViewById(R.id.sundays);
        monthly = (CheckBox) findViewById(R.id.monthly);

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
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                locationVal = location.getText().toString();
                addTime = Integer.parseInt(additionalTime.getText().toString());
                if(priorityYes.isChecked())
                {
                    priority = true;
                }
                else if(priorityNo.isChecked())
                {
                    priority = false;
                }
                if(alarm.isChecked())
                    alarmType = "alarm";
                else if(vibrate.isChecked())
                    alarmType = "vibrate";
                boolean[] flags = new boolean[7];
                flags[0] = mondays.isChecked();
                flags[1] = tuesdays.isChecked();
                flags[2] = wednesdays.isChecked();
                flags[3] = thursdays.isChecked();
                flags[4] = fridays.isChecked();
                flags[5] = saturdays.isChecked();
                flags[6] = sundays.isChecked();
                if(weekly.isChecked() == true)
                    eventDBOperations.addEvent(description.getText().toString(), locationVal, date, month, year, hour, min, addTime, priority, alarmType, "weekly", flags);
                else if(monthly.isChecked() == true)
                    eventDBOperations.addEvent(description.getText().toString(), locationVal, date, month, year, hour, min, addTime, priority, alarmType, "monthly", flags);
                else
                    eventDBOperations.addEvent(description.getText().toString(), locationVal, date, month, year, hour, min, addTime, priority, alarmType, "", flags);
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
