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
    public void addEvent(String name, String loc, int day, int month, int year, int hour, int min, int addTime, boolean priority,
                         String alarmType, String repeat, boolean[] days)
    {
        String date = year+"-"+month+"-"+day;
        Cursor cursor = database.rawQuery("SELECT strftime(%w,"+date+");",null);
        cursor.moveToFirst();
        int starti = cursor.getInt(0);
        cursor.close();
        for (int i=0; i<days.length; i++) {
            if(!days[i+starti]){
                continue;
            }
            if(repeat.compareTo("daily")==0) {
                cursor = database.rawQuery("SELECT strftime("+date+",'+"+(7+i)+" day');",null);
                cursor.moveToFirst();
                addHelper(name,loc,cursor.getString(0),hour,min,addTime,priority,alarmType,repeat);
                cursor.close();
                cursor = database.rawQuery("SELECT strftime("+date+",'"+(14+i)+" day');",null);
                cursor.moveToFirst();
                addHelper(name,loc,cursor.getString(0),hour,min,addTime,priority,alarmType,repeat);
                cursor.close();
            } else if(repeat.compareTo("monthly")==0) {
                cursor = database.rawQuery("SELECT strftime("+date+",'+1 month',"+"'+"+i+" day');",null);
                cursor.moveToFirst();
                addHelper(name,loc,cursor.getString(0),hour,min,addTime,priority,alarmType,repeat);
                cursor.close();
                cursor = database.rawQuery("SELECT strftime("+date+",'+2 month',"+"'+"+i+" day');",null);
                cursor.moveToFirst();
                addHelper(name,loc,cursor.getString(0),hour,min,addTime,priority,alarmType,repeat);
                cursor.close();
            }
            addHelper(name,loc,date,hour,min,addTime,priority,alarmType,repeat);
        }

    }

    private void addHelper(String name, String loc, String date, int hour, int min, int addTime, boolean priority, String alarmType, String repeat){
        ContentValues values = new ContentValues();
        values.put(DatabaseWrapper.NAME, name);
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
        Cursor cursor = database.rawQuery("Select "+DatabaseWrapper.NAME+", "+DatabaseWrapper.TIME_HOUR+", "+ DatabaseWrapper.TIME_MINS+", "+
                DatabaseWrapper.ALARM_TYPE+", "+ DatabaseWrapper.REPEAT+" from "+DatabaseWrapper.EVENTS+" where "+
                DatabaseWrapper.DATE+" = "+date+" order by "+DatabaseWrapper.TIME_HOUR+" ASC, "+DatabaseWrapper.TIME_MINS+" ASC",null);
        return cursor;
    }

    public void deleteEvent(String name) {
        database.rawQuery("Delete from "+DatabaseWrapper.EVENTS+" where "+DatabaseWrapper.NAME+" LIKE "+name,null);
    }
}
