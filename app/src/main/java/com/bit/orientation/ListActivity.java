package com.bit.orientation;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private DBInt db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView list = (ListView) findViewById(R.id.logLine);
        ArrayList<String> arrayList = new ArrayList<>();

        db = new DBInt(this);

        try {
            db.open();
        } catch(Exception e) {
            e.printStackTrace();
        }

        Cursor cursor = db.viewAll();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                arrayList.add("ID " + cursor.getString(0) + "\nZ Axis = " + cursor.getString(1) + "\nX Axis = " + cursor.getString(2) + "\nY Axis = " + cursor.getString(3) + ",\nTime " + cursor.getString(4));
            }
        } else {
            arrayList.add("Empty");
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);
    }
}