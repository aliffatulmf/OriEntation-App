package com.bit.orientation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

public class DBInt {
    private DatabaseInit dbinit;
    private Context context;
    private SQLiteDatabase database;

    public DBInt(Context cnt) {
        context = cnt;
    }

    public DBInt open() throws SQLDataException {
        dbinit = new DatabaseInit(context);
        return this;
    }

    public void close() {
        dbinit.close();
    }

    public void create() {
        database = dbinit.getWritableDatabase();
        database.execSQL(dbinit.SQL_CREATE_ENTRIES);
    }

    public void drop() {
        database = dbinit.getWritableDatabase();
        database.execSQL(dbinit.SQL_DELETE_ENTRIES);
    }

    public void save(String azimuth, String pitch, String roll) {
        database = dbinit.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(dbinit.S_AZIMUTH, azimuth);
        contentValues.put(dbinit.S_PITCH, pitch);
        contentValues.put(dbinit.S_ROLL, roll);

        database.insert(dbinit.TABLE_NAME, null, contentValues);
    }

    public void flush() {
        database = dbinit.getWritableDatabase();
        database.execSQL("delete from " + dbinit.TABLE_NAME);
        database.execSQL("vacuum");
    }

    public Cursor viewAll() {
        database = dbinit.getReadableDatabase();
        String query = "select * from " + dbinit.TABLE_NAME;

        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
}
