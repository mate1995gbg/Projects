package com.example.training;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import models.Profile;

public class ProfileConfigurationActivity extends AppCompatActivity {


    private Spinner profileSpinner;
    private ArrayAdapter<Profile> profileAdapter;
    private Switch calorieTrackerSwitch;
    MySQLiteHelper dbHelper = new MySQLiteHelper(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_configuration);
        calorieTrackerSwitch = findViewById(R.id.calorieTrackerSwitch);
        //bind db data for profiles to profileSpinner
        List<Profile> allProfiles;
        allProfiles = dbHelper.getAllProfiles();
        dbHelper.close();
        if (allProfiles.isEmpty() == false){
            profileSpinner = findViewById(R.id.selectProfileSpinner);
            profileAdapter = new ArrayAdapter<>(ProfileConfigurationActivity.this, android.R.layout.simple_spinner_item, allProfiles);
            profileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Set the layout for the dropdown
            profileSpinner.setAdapter(profileAdapter); // Set the adapter to the spinner
            profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Profile selectedProfile = (Profile) adapterView.getItemAtPosition(i);
                    dbHelper.selectProfile((int) selectedProfile.getProfileId());
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
        else{
            TextView noProfilesTextView = findViewById(R.id.selectProfileTextView);
            noProfilesTextView.setText("No profiles found");
        }
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileConfigurationActivity.this, ProfileMenuActivity.class);
                startActivity(intent);
            }
        });

        setCalorieTrackerSwitch();
    }

    public void setCalorieTrackerSwitch(){
        Profile selectedProfile = dbHelper.getSelectedProfile();

        if (selectedProfile != null){
            calorieTrackerSwitch.setChecked(selectedProfile.getCalorieTrackerActivated() == 1); // Initialize the switch state based on stored value in database

            calorieTrackerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        //if the switch is enabled
                        dbHelper.updateCalorieTrackerStatusForProfile((int)selectedProfile.getProfileId(), true);
                        Intent intent = new Intent(ProfileConfigurationActivity.this, CalorieTrackerService.class);
                        startService(intent);
                    }
                    else{
                        dbHelper.updateCalorieTrackerStatusForProfile((int)selectedProfile.getProfileId(), false);
                        Intent intent = new Intent(ProfileConfigurationActivity.this, CalorieTrackerService.class);
                        stopService(intent);
                    }
                }
            });


        }
        else{
            calorieTrackerSwitch.setVisibility(View.INVISIBLE);
        }
    }
}
