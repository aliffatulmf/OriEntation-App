package com.bit.orientation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;
import java.sql.SQLSyntaxErrorException;

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

    public boolean insert(String pitch, String roll, String azimuth) {
        database = dbinit.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(dbinit.S_PITCH, pitch);
        cv.put(dbinit.S_ROLL, roll);
        cv.put(dbinit.S_AZIMUTH, azimuth);

        try {
            database.insertOrThrow(dbinit.TABLE_NAME, null, cv);
            return true;
        } catch (Exception err) {
            return false;
        }

    }

    public Cursor viewAll(String query) {
        database = dbinit.getReadableDatabase();
        if (query != null) {
            query = "select * from " + dbinit.TABLE_NAME;
        }

        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
}
