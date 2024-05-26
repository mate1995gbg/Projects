package com.example.training;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import models.Profile;

public class CreateProfileActivity extends AppCompatActivity {

    private static final String TAG = "TAG:"; // Set the TAG here

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private EditText profileURLEditText;
    private MySQLiteHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        MySQLiteHelper dbHelper = new MySQLiteHelper(this);
        //edittexts for binding edittexts from layout to class
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        profileURLEditText = findViewById(R.id.profileImageEditText);

        Button backBtn = (Button) findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateProfileActivity.this, ProfileMenuActivity.class);
                startActivity(intent);
            }
        });

        Button createProfileBtn = (Button) findViewById(R.id.createProfileButton);
        createProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Profile newProfile = new Profile();
                Log.d(TAG, "usernameEdtText found: " + (usernameEditText != null));
                Log.d(TAG, "usernameEdtText found: " + (usernameEditText.toString()));
                String userInputUsername = usernameEditText.getText().toString();
                newProfile.setUsername(userInputUsername);
                Log.d(TAG, "passwordEdtText found: " + (passwordEditText != null));
                Log.d(TAG, "usernameEdtText found: " + (passwordEditText.toString()));
                String userInputPass = passwordEditText.getText().toString();
                newProfile.setPassword(userInputPass);
                Log.d(TAG, "nameEdtText found: " + (nameEditText != null));
                Log.d(TAG, "nameEdtText found: " + (nameEditText.toString()));
                String userInputName = nameEditText.getText().toString();
                newProfile.setName(userInputName);
                Log.d(TAG, "profilePicURLEdtText found: " + (profileURLEditText != null));
                Log.d(TAG, "profilePicURLEdtText found: " + (profileURLEditText.toString()));
                String userInputURL = profileURLEditText.getText().toString();
                newProfile.setProfileImageUrl(userInputURL);
                if (dbHelper != null) {
                    Profile createdProfile = dbHelper.createNewProfile(newProfile);

                    if (createdProfile != null){
                        dbHelper.selectProfile((int) createdProfile.getProfileId());
                        dbHelper.close();
                        Toast.makeText(CreateProfileActivity.this, "Added and activated profile: " + (newProfile.getUsername()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(CreateProfileActivity.this, "Error running method CreateNewProfile input into database, check input values", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
