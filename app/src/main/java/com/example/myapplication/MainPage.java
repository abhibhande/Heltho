package com.example.myapplication;

import static com.example.myapplication.VolleyRequest.getDailyFollowUp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.VolleyRequest;

import java.util.Vector;

public class MainPage extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean isCounterSensorAvailable;
    private Sensor stepCounter;
    TextView stepCountTextView;
    private int steps = 0;

    private static final int ACTIVITY_RECOGNITION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mainpage);



    }

    public void ReelAc(MenuItem item) {
        Intent i = new Intent(this, ReelActivity.class);
        startActivity(i);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession",Context.MODE_PRIVATE);


        TextView name=findViewById(R.id.name);
        stepCountTextView = findViewById(R.id.stepscount);
        LinearLayout specializationLayout = findViewById(R.id.doctorSpecialization);
        LinearLayout noofdays = findViewById(R.id.noofdays);
        LinearLayout medicinelist = findViewById(R.id.medicinelist);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        name.setText("Hi " + sharedPreferences.getString("name","user").toString());

        Vector<String> specializations = VolleyRequest.getDoctorCategory(this,specializationLayout);


        getDailyFollowUp(this,String.valueOf(sharedPreferences.getLong("id",1555)),noofdays,medicinelist);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int stepCount = (int) sensorEvent.values[0];
            stepCountTextView.setText(stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void initializeStepCounter() {
        if (sensorManager != null) {
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

            if (stepCounter != null) {
                isCounterSensorAvailable = true;
                sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI);
            } else {
                isCounterSensorAvailable = false;
                stepCountTextView.setText("Step Counter Sensor not available!");
                stepCountTextView.setTextSize(12);
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (isCounterSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTIVITY_RECOGNITION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeStepCounter();
            } else {
                stepCountTextView.setText("Permission Denied!");
                stepCountTextView.setTextSize(12);
            }
        }
    }
}
