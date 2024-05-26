package com.example.training;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileMenuActivity extends AppCompatActivity {

        //

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile_menu);

            Button newProfileBtn = (Button) findViewById(R.id.newprofileBtn);
            newProfileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileMenuActivity.this, CreateProfileActivity.class);
                    startActivity(intent);
                }
            });

            Button profileConfigBtn = (Button) findViewById(R.id.profilConfurationBtn);
            profileConfigBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileMenuActivity.this, ProfileConfigurationActivity.class);
                    startActivity(intent);
                }
            });

            Button backBtn = (Button) findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileMenuActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }