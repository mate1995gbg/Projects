package com.example.training;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import models.Profile;

public class CalorieTrackerActivity extends AppCompatActivity {
    MySQLiteHelper dbHelper = new MySQLiteHelper(this);
    Profile currentProfile;
    Map<String, Float> dailyCalories = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorietrackeroverview);
        getAllCaloriesPerDayForCurrentProfile();
        setupRecyclerView();

        Button backToMainButton = (Button) findViewById(R.id.backToMainFromCalorieTrackerButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalorieTrackerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getAllCaloriesPerDayForCurrentProfile(){
            currentProfile = dbHelper.getSelectedProfile();
            dailyCalories = dbHelper.getTotalCaloriesBurnedForEachDay((int)currentProfile.getProfileId());
    }

    private void setupRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.calorieTrackerRecyclerView);
        CalorieTrackerAdapter adapter = new CalorieTrackerAdapter(dailyCalories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
