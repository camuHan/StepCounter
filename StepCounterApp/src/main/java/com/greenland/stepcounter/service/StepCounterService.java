package com.greenland.stepcounter.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.greenland.stepcounter.stepvalue.Values;

public class StepCounterService extends Service implements SensorEventListener{

    int count = Values.Step;

    private long mLastTime;
    private float mSpeed;
    private float mLastX;
    private float mLastY;
    private float mLastZ;

    private float mX, mY, mZ;
    private static final int SHAKE_THRESHOLD = 800;
    private static final int INTERVAL_TIME	 = 100;

    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager mSensorManager;



    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        //set sensor
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerormeterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerormeterSensor != null)
            mSensorManager.registerListener(this, accelerormeterSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        if (mSensorManager != null)
            mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - mLastTime);

            if (gabOfTime > INTERVAL_TIME) {
                mLastTime = currentTime;

                mX = event.values[SensorManager.DATA_X];
                mY = event.values[SensorManager.DATA_Y];
                mZ = event.values[SensorManager.DATA_Z];

                mSpeed = Math.abs(mX + mY + mZ - mLastX - mLastY - mLastZ) / gabOfTime * 10000;

                if (mSpeed > SHAKE_THRESHOLD) {

                    Intent myFilteredResponse = new Intent("com.greenland.stepcount.serv");

                    Values.Step = count++;

                    String msg = Values.Step + "" ;
                    myFilteredResponse.putExtra("serviceData", msg);

                    sendBroadcast(myFilteredResponse);
                }
                mLastX = event.values[DATA_X];
                mLastY = event.values[DATA_Y];
                mLastZ = event.values[DATA_Z];
            }
        }
    }
}