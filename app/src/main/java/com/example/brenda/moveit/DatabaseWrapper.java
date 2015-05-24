package com.example.brenda.moveit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Leena on 5/23/2015.
 */
public class DatabaseWrapper extends SQLiteOpenHelper{
    public static final String EVENTS = "EVENTS";
    public static final String NAME = "_description";
    public static final String LOCATION = "_location";
    public static final String DATE = "_date";
    public static final String TIME_HOUR = "_hour";
    public static final String TIME_MINS = "_mins";
    public static final String ADDITIONAL_TIME = "_addTime";
    public static final String PRIORITY = "_priority";
    public static final String ALARM_TYPE = "_alarm";
    public static final String REPEAT = "_repeat";
    private static final String DATABASE_NAME = "EVENTS.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_CREATE = "create table" + EVENTS + "(" +NAME+" text, "+ LOCATION +
            " text, " + DATE + " text, " + TIME_HOUR + " integer, " + TIME_MINS + " integer, "
            + ADDITIONAL_TIME + " integer, " + PRIORITY + " char(1), " + ALARM_TYPE + " text, " +
            REPEAT + " text );";

    public DatabaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EVENTS);
        onCreate(db);
    }
}
