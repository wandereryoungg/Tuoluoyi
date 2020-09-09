package com.young.tuoluoyi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button btn1, btn2;
    TextView tv;
    SensorManager mSensorManager;
    Sensor gSensor;

    private static final float NS2S = 1.0f / 1000000000.0f;

    private static final float T1 = -1.0f / 1000000.0f;
    private static final float T2 = 1.0f / 1000000.0f;

    private float timestamp;
    private float[] angle = new float[3];

    private StringBuilder builder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        float T1 = new Random().nextFloat() * 2;
        float T2 = new Random().nextFloat() * 2;
        float T3 = new Random().nextFloat() * 2;

        float TT1 = new Random().nextFloat() / 5;
        float TT2 = new Random().nextFloat() / 5;
        float TT3 = new Random().nextFloat() / 5;


        float TTT1 = new Random().nextInt(2);
        float TTT2 = new Random().nextInt(2);
        float TTT3 = new Random().nextInt(2);
        if (TTT1 == 0) {
            T1 = T1 - TT1;
        } else {
            T1 = T1 + TT2;
        }
        if (TTT2 == 0) {
            T2 = T2 - TT2;
        } else {
            T2 = T2 + TT2;
        }
        if (TTT3 == 0) {
            T3 = T3 - TT3;
        } else {
            T3 = T3 + TT3;
        }


        Log.e("tuoluoyi", "T1: " + T1 + "\nT2: " + T2 + "\nT3: " + T3);


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        tv = findViewById(R.id.tv);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gSensor = mSensorManager.getDefaultSensor(4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSensorManager.registerListener(MainActivity.this, gSensor, SensorManager.SENSOR_DELAY_UI);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(builder.toString());
                mSensorManager.unregisterListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (timestamp != 0) {

            float dT = (event.timestamp - timestamp) * NS2S;
            angle[0] = event.values[0] * dT;
            angle[1] = event.values[1] * dT;
            angle[2] = event.values[2] * dT;

            float angleX = (float) Math.toDegrees(angle[0]);
            float angleY = (float) Math.toDegrees(angle[1]);
            float angleZ = (float) Math.toDegrees(angle[2]);

            tv.setText("x: " + angleX + "\ny: " + angleY + "\nz: " + angleZ);

            builder.append("x: " + angleX + "\ny: " + angleY + "\nz: " + angleZ);
        }

        timestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}