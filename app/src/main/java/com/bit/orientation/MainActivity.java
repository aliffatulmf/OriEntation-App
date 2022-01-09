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

        myImg = findViewById(R.id.imageView);
        drawable = getResources().getDrawable(R.drawable.cc);
        myImg.setImageResource(R.drawable.arrow);

        Button sensorBtn = findViewById(R.id.sensor_menu);
        sensorBtn.setOnClickListener(view -> toSensor());

        Button listBtn = findViewById(R.id.database_menu);
        listBtn.setOnClickListener(view -> toList());
    }

    public void toSensor() {
        Intent next = new Intent(this, SensorActivity.class);
        startActivity(next);
    }

    protected void toList() {
        Intent next = new Intent(this, ListActivity.class);
        startActivity(next);
    }
}