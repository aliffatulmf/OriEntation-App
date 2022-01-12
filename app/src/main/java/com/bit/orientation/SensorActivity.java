package com.bit.orientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Timer timer = new Timer();

    DBInt db;

    TextView azimuth, roll, pitch;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        azimuth = findViewById(R.id.azimuth);
        roll = findViewById(R.id.roll);
        pitch = findViewById(R.id.pitch);
        imgView = findViewById(R.id.imageView);

        db = new DBInt(this);
        try {
            db.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String z = String.valueOf(azimuth.getText());
                String x = String.valueOf(pitch.getText());
                String y = String.valueOf(roll.getText());

                db.save(z, x, y);
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.length);
        }

        updateOrientationAngles();

        float rotateDegree = 0f;
        float lastRotateDegree = -(float) Math.toDegrees(orientationAngles[0]);
        if (Math.abs(rotateDegree - lastRotateDegree) > 1) {
            RotateAnimation animation = new RotateAnimation(lastRotateDegree, rotateDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setFillAfter(true);
            imgView.startAnimation(animation);
            lastRotateDegree = rotateDegree;
        }

        String zAxis = String.valueOf(Math.toDegrees(orientationAngles[0]));
        String xAxis = String.valueOf(Math.toDegrees(orientationAngles[1]));
        String yAxis = String.valueOf(Math.toDegrees(orientationAngles[2]));

        azimuth.setText(zAxis);
        pitch.setText(xAxis);
        roll.setText(yAxis);
    }

    public void updateOrientationAngles() {
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
    }

    @Override
    protected void onPause() {
        super.onPause();

        executorService.shutdownNow();
        db.close();
        sensorManager.unregisterListener(this);
    }
}