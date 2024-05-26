package com.example.geowarning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.geowarning.databinding.ActivityTextWarningBinding;
import com.example.geowarning.methods.ApiHelper;
import com.example.geowarning.model.Description;
import com.example.geowarning.model.EventDescription;
import com.example.geowarning.model.Warning;
import com.example.geowarning.model.WarningAreas;
import com.example.geowarning.model.WarningLevel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TextWarningActivity extends AppCompatActivity {

    private ActivityTextWarningBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTextWarningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ConstraintLayout constraintLayout = findViewById(R.id.warning_container);
        ConstraintSet constraintSet = new ConstraintSet();

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Active SMHI Warnings");

        ApiHelper apiHelper = new ApiHelper(this);
        apiHelper.fetchWarnings(new ApiHelper.ApiCallback<List<Warning>>() {
            @Override
            public void onSuccess(List<Warning> result) {
                int prevId = View.NO_ID;// To keep track of the previous view's ID for vertical chaining
                for (Warning warning : result) {

                    List<WarningAreas> warningAreas = warning.getWarningAreas();
                    for (WarningAreas warningArea : warningAreas) {

                        Log.d("TAG:", warningArea.getEventDescription().getSv());
                        Log.d("TAG:", warningArea.getAreaName().getSv());
                        Log.d("TAG:", warningArea.getWarningLevel().getSv());
                        Log.d("TAG:", warningArea.getApproximateStart());

                        List<Description> eventDescriptions = warningArea.getDescriptions();
                        Description firstDescription = eventDescriptions.get(0);
                        Log.d("TAG:", firstDescription.getText().getSv());

                        View cardView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_warning, constraintLayout, false);
                        TextView warningTitle = cardView.findViewById(R.id.warningTitle);
                        warningTitle.setText(warning.getEvent().getEn());
                        TextView warningLevel = cardView.findViewById(R.id.warningLevel);
                        warningLevel.setText(warningArea.getWarningLevel().getEn());

                        TextView warningArea2 = cardView.findViewById(R.id.warningArea);
                        if (warningArea.getAreaName().getEn() == null) {
                            warningArea2.setText(warningArea.getAreaName().getSv()); //sometimes english version of area name is not entered, in that case swedish name for area must be used.
                        }
                        else {
                            warningArea2.setText(warningArea.getAreaName().getEn());
                        }

                        TextView warningStartDate = cardView.findViewById(R.id.warningStartDate);
                        String input = warningArea.getApproximateStart();
                        String trimmedDate = input.substring(0, input.indexOf('T'));
                        warningStartDate.setText(trimmedDate); //string returned from warningArea.getApproximateStart needs to be trimmed in order to not have seconds stuff. originl format is UTC.
                        TextView warningDescription = cardView.findViewById(R.id.warningDescription);
                        warningDescription.setText(firstDescription.getText().getEn());

                        cardView.setId(View.generateViewId());
                        constraintLayout.addView(cardView);
                        constraintSet.clone(constraintLayout);
                        constraintSet.connect(cardView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
                        constraintSet.connect(cardView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16);

                        if (prevId == View.NO_ID) {
                            constraintSet.connect(cardView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);
                        } else {
                            constraintSet.connect(cardView.getId(), ConstraintSet.TOP, prevId, ConstraintSet.BOTTOM, 16);
                        }

                        constraintSet.applyTo(constraintLayout);
                        prevId = cardView.getId();
                    }

                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(TextWarningActivity.this, "Error using fetchWarnings method!", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}