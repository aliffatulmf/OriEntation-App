package com.bit.orientation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseInit extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "ori.db";

    static final String TABLE_NAME = "orientation";
    static final String ORI_ID = "_ID";
    static final String S_PITCH = "x_axis";
    static final String S_ROLL = "y_axis";
    static final String S_AZIMUTH = "z_axis";
    static final String ORI_TIME = "ori_time";

    static String SQL_CREATE_ENTRIES = "create table if not exists " + TABLE_NAME + " (" + ORI_ID +
            " integer primary key autoincrement," + S_PITCH + " varchar(20) not null, " + S_ROLL +
            " varchar(20) not null," + S_AZIMUTH + " varchar(20) not null," + ORI_TIME + " datetime default current_timestamp not null)";

    static String SQL_DELETE_ENTRIES = "drop table if exists " + TABLE_NAME + ";";

    public DatabaseInit(Context cnt) {
        super(cnt, DATABASE_NAME, null, DATABASE_VERSION);

        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
