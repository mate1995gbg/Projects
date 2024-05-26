package com.example.training;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import models.ExerciseWorkoutConnection;
import models.Profile;
import models.Workout;

public class WorkoutHistoryActivity extends AppCompatActivity {
    MySQLiteHelper dbHelper = new MySQLiteHelper(this);
    List<Workout> finishedWorkoutsList;
    LinkedHashMap<String, List<ExerciseWorkoutConnection>> ewcMap = new LinkedHashMap<>();
    Profile currentProfile;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);
        getAllEwcForCurrentProfile();
        populateAndSortEwcMap();
        setupRecyclerView();

        Button backToMainButton = (Button) findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutHistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getAllEwcForCurrentProfile(){
        currentProfile = dbHelper.getSelectedProfile();
        finishedWorkoutsList = dbHelper.getAllFinishedWorkoutsByProfileId((int)currentProfile.getProfileId());
    }

    private void populateAndSortEwcMap(){
        for (Workout workout : finishedWorkoutsList) {
            List<ExerciseWorkoutConnection> ewcListForWorkoutId = dbHelper.getAllExerciseWorkoutConnectionsForWorkout(workout.getId());
            ewcMap.put(workout.getStartTime(), ewcListForWorkoutId); //set Key as starTime of workout and Value as ewclist (the entire workout with exercises and stuff

            //sort by keys (which is StartTime)
            List<String> sortedKeys = new ArrayList<>(ewcMap.keySet());
            Collections.sort(sortedKeys);
            Collections.reverse(sortedKeys);
            LinkedHashMap<String, List<ExerciseWorkoutConnection>> sortedMap = new LinkedHashMap<>();
            for (String key : sortedKeys) {
                sortedMap.put(key, ewcMap.get(key));
            }
            ewcMap = sortedMap;
        }
    }


    private void setupRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.workoutHistoryRecyclerView);
        WorkoutHistoryAdapter adapter = new WorkoutHistoryAdapter(ewcMap, finishedWorkoutsList, currentProfile, dbHelper);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onSeeWorkoutClick(){

    }

}