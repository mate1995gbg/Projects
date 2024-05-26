package com.example.training;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import models.Profile;

public class CalorieTrackerService extends Service implements SensorEventListener{
    private static final String TAG = "asd";
    private SensorManager sensorManager;
    MySQLiteHelper dbHelper;
    Profile activeProfile;
    private CalorieUpdateCallback calorieUpdateCallback;

    // These could be set dynamically
    float userWeightKg = 70;
    float metValueForWalking = 2.9f; // MET = metabolic equivalent of task
    float metValueForRunning = 8.0f;  // MET value for running
    float metValueForStationary = 1.0f;  // MET value for being stationary
    private int counter = 0;
    private String todaysDate;
    private float totalCaloriesForDay = 0;
    private float totalCalories = 0;
    private long lastUpdateTime;


    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new MySQLiteHelper(this);
        activeProfile = dbHelper.getSelectedProfile();
        todaysDate = DateTimeUtil.getCurrentDateTime(); //automatically sets todays date and time, no need to set it with further methods.
        if (activeProfile == null){
            // Log an error message
            Log.e("CalorieTrackerService", "No active profile found, stopping the service");

            // Stop the service
            stopSelf();
            return;
        }
        // Initialize your SensorManager and register listeners
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    //in order to communicate between Service and MainActivity
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        CalorieTrackerService getService() {
            return CalorieTrackerService.this;
        }
    }

    public interface CalorieUpdateCallback {
        void onCalorieUpdate(float totalCalories);
    }

    public void setCalorieUpdateCallback(CalorieUpdateCallback callback) {
        this.calorieUpdateCallback = callback;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a notification and start this service in the foreground
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        createNotificationChannel();
        startForeground(1, getNotification());
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float magnitude = (float) Math.sqrt(values[0] * values[0] + values[1] * values[1] + values[2] * values[2]);

        updateCaloriesAndCounter(magnitude);

        if (calorieUpdateCallback != null) {
            calorieUpdateCallback.onCalorieUpdate(totalCalories);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CalorieTrackerChannel";
            String description = "Channel for Calorie Tracker notification";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("notifyCaloriesTracker", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notifyCaloriesTracker")
                .setContentTitle("LiftMate - Calorie Tracker Running")
                .setContentText("Tracking calories burned...")
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setPriority(NotificationCompat.PRIORITY_LOW);
        return builder.build();
    }

    private void updateCaloriesAndCounter(float magnitude) {

        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastUpdateTime) >= 3000) { // 3000 ms = 3 s, this is in order for the accelerometer sensor to only update every 3 seconds. can change if necessary.
            float metValue;

            if (magnitude < 1.5) {
                metValue = metValueForStationary;
            } else if (magnitude >= 1.5 && magnitude < 10) {
                metValue = metValueForWalking;
            } else {
                metValue = metValueForRunning;
            }

            float timeInHours = 1 / 3600.0f;
            float caloriesBurned = metValue * userWeightKg * timeInHours;

            totalCalories += caloriesBurned;
            totalCaloriesForDay = dbHelper.returnTotalCaloriesBurnedForDay((int) activeProfile.getProfileId(), todaysDate.toString());
            Log.d(TAG, "updateCaloriesAndCounter: totalCaloriesForDay: " + totalCaloriesForDay);
            counter++;
            Toast.makeText(this, "CalorieTrackerService.updateCaloriesAndCounter has run. totalCalories: "+totalCalories + "and counter: "+ counter, Toast.LENGTH_SHORT).show();
            if(counter >= 10) {
                Toast.makeText(this, "CalorieTrackerService - counter >= 10! testing sending calories value to db.", Toast.LENGTH_SHORT).show();
                String date = DateTimeUtil.getCurrentDateTime();
                dbHelper.addCalorieRecord(date, totalCalories, (int) activeProfile.getProfileId());
                counter = 0;
                totalCalories = 0;
            }
            lastUpdateTime = currentTime;
        }
    }

    public float getTotalCaloriesForDay(){
        return totalCaloriesForDay;
    }
}
