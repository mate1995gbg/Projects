package com.example.training;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import models.Profile;
import models.Workout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    MySQLiteHelper dbHelper;
    private CalorieTrackerService myService;
    private boolean bound = false;
    private ServiceConnection serviceConnection;
    TextView calorieTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbHelper = new MySQLiteHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.newWorkoutButton);
        Button buttonHistory = findViewById(R.id.workoutHistoryButton);
        Button workoutWarning = findViewById(R.id.btnWorkoutWarning);
        Button historyWarning = findViewById(R.id.btnHistoryWarning);
        TextView randomQuoteTextView = findViewById(R.id.randomQuoteTextView);
        calorieTextView = findViewById(R.id.calorieTextView);

        RandomQuoteApiFetcher.QuoteFetcherTask quoteFetcherTask = new RandomQuoteApiFetcher.QuoteFetcherTask(randomQuoteTextView);
        quoteFetcherTask.execute();

        Profile userProfile = dbHelper.getSelectedProfile();

        //need to get workoutClass and pass it into WorkoutActivity from here so i can avoid problems with dbhelper.hasOngoingWorkout();
        if(userProfile != null){
            if(userProfile.getCalorieTrackerActivated() == 1){
                setupCalorieTrackerService();
            }
            else{
                calorieTextView.setVisibility(View.INVISIBLE);
            }

            boolean hasUnfinishedWorkout = dbHelper.hasOngoingWorkout((int) userProfile.getProfileId());
            if(hasUnfinishedWorkout == true){
                Workout ongoingWorkout = dbHelper.getOngoingWorkoutByProfileId((int) userProfile.getProfileId());
                button.setText("Continue Workout");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
                        intent.putExtra("workoutId", ongoingWorkout.getId());
                        startActivity(intent);
                    }
                });
            }
            else{
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Create an AlertDialog
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Start New Workout");
                        alertDialogBuilder.setMessage("Do you want to start a new workout?");
                        //Set positive button ("Yes") and its click listener
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean startWorkoutResult = dbHelper.startWorkout((int) userProfile.getProfileId());  //this creates new workout. need to mdoify logic in WorkoutActivity to not look for ongoing workouts otherwise a workout will always be ongoing..
                                Workout startedWorkout = dbHelper.getOngoingWorkoutByProfileId((int) userProfile.getProfileId());
                                if(startWorkoutResult == true) {
                                    Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
                                    intent.putExtra("workoutId", startedWorkout.getId());
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Error during insertion of user input into database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
            }
        }
        else{
            button.setBackgroundTintList(ContextCompat.getColorStateList(this, androidx.cardview.R.color.cardview_dark_background));
            buttonHistory.setBackgroundTintList(ContextCompat.getColorStateList(this, androidx.cardview.R.color.cardview_dark_background));
            workoutWarning.setVisibility(View.VISIBLE);
            historyWarning.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Please create and select a user profile to start workout.", Toast.LENGTH_LONG).show();
        }

        Button profileBtn = (Button) findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileMenuActivity.class);
                startActivity(intent);
            }
        });

        Button workoutHistoryBtn = (Button) findViewById(R.id.workoutHistoryButton);
        workoutHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WorkoutHistoryActivity.class);
                startActivity(intent);
            }
        });

        Button calorieTrackerOverviewBtn = (Button) findViewById(R.id.calorieTrackerOverviewButton);
        calorieTrackerOverviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalorieTrackerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (serviceConnection != null) { // Only bind if serviceConnection has been initialized
            Intent intent = new Intent(this, CalorieTrackerService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(serviceConnection);
            bound = false;
        }
    }

    public void updateCalories(float totalCalories) {
        if (bound) {  // Make sure the service is bound
            float totalCaloriesForDay = myService.getTotalCaloriesForDay();
            calorieTextView.setText(String.format("Calories burned this session: %.2f", totalCaloriesForDay));
        } else {
            calorieTextView.setText(String.format("Calories burned this session: %.2f", totalCalories));
        }
    }

    private void setupCalorieTrackerService() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                CalorieTrackerService.LocalBinder binder = (CalorieTrackerService.LocalBinder) service;
                myService = binder.getService();
                bound = true;

                myService.setCalorieUpdateCallback(new CalorieTrackerService.CalorieUpdateCallback() {
                    @Override
                    public void onCalorieUpdate(float totalCalories) {
                        updateCalories(totalCalories);
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                bound = false;
            }
        };
    }
}