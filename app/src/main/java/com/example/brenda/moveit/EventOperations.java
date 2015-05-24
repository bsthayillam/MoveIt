package com.example.brenda.moveit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Leena on 5/24/2015.
 */
public class EventOperations {
    private DatabaseWrapper dbHelper;
    private String[] EVENT_TABLE_COLUMNS = { DatabaseWrapper.LOCATION, DatabaseWrapper.DATE, DatabaseWrapper.TIME_HOUR,
    DatabaseWrapper.TIME_MINS, DatabaseWrapper.ADDITIONAL_TIME, DatabaseWrapper.PRIORITY,
            DatabaseWrapper.ALARM_TYPE, DatabaseWrapper.REPEAT};
    private SQLiteDatabase database;
    public EventOperations(Context context)
    {
        dbHelper = new DatabaseWrapper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public void addEvent(String loc, int date, int hour, int min, int addTime, boolean priority, String alarmType, String repeat)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseWrapper.LOCATION, loc);
        values.put(DatabaseWrapper.DATE, date);
        values.put(DatabaseWrapper.TIME_HOUR, hour);
        values.put(DatabaseWrapper.TIME_MINS, min);
        values.put(DatabaseWrapper.ADDITIONAL_TIME, addTime);
        values.put(DatabaseWrapper.PRIORITY, priority);
        values.put(DatabaseWrapper.ALARM_TYPE, alarmType);
        values.put(DatabaseWrapper.REPEAT, repeat);
        long studId = database.insert(DatabaseWrapper.EVENTS, null, values);

    }

    public Cursor selectEvent(int date) {
        Cursor cursor = database.rawQuery("Select "+DatabaseWrapper.TIME_HOUR+", "+ DatabaseWrapper.TIME_MINS+", "+
                DatabaseWrapper.ALARM_TYPE+", "+ DatabaseWrapper.REPEAT+" from "+DatabaseWrapper.EVENTS+" where "+
                DatabaseWrapper.DATE+" = "+date+" order by "+DatabaseWrapper.TIME_HOUR+" ASC, "+DatabaseWrapper.TIME_MINS+" ASC",null);
        return cursor;
    }

    public void deleteEvent(int date, String location) {
        database.rawQuery("Delete from "+DatabaseWrapper.EVENTS+" where "+DatabaseWrapper.DATE+" = "+date+" AND "+DatabaseWrapper.LOCATION+" LIKE "+location,null);
    }
}
