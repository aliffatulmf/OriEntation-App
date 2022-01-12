package com.bit.orientation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView myImg;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sensorBtn = findViewById(R.id.sensor_menu);
        sensorBtn.setOnClickListener(view -> toSensor());

        Button listBtn = findViewById(R.id.database_menu);
        listBtn.setOnClickListener(view -> toList());

        Button drop = findViewById(R.id.delete_record);
        drop.setOnClickListener(view -> dropRecord());
    }

    private void toSensor() {
        Intent next = new Intent(this, SensorActivity.class);
        startActivity(next);
    }

    private void toList() {
        Intent next = new Intent(this, ListActivity.class);
        startActivity(next);
    }

    private void dropRecord() {
        DBInt dbi = new DBInt(this);
        try {
            dbi.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbi.drop();
        dbi.create();
    }
}