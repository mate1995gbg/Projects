package com.example.training;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import models.Exercise;
import models.ExerciseWorkoutConnection;
import models.Profile;
import models.Workout;

public class WorkoutActivity extends AppCompatActivity {
    //widgets
    private Button mOpenDialog;
    public Spinner mExerciseCategoryInput;
    public Spinner mChosenExerciseInput;
    private ExerciseAdapter adapter;

    //vars
    private MySQLiteHelper dbHelper = new MySQLiteHelper(this);
    private static final String TAG = "tag: ";
    public Profile thisProfile;
    public Workout thisWorkout;
    public List<Exercise> workoutExerciseList = new ArrayList<>();
    public List<ExerciseWorkoutConnection> exerciseWorkoutConnectionList = new ArrayList<>();

    //get ongoing workoutId and call getAllExerciseWorkoutConnections and getAllExercises
    public void getThings(){
        Intent intent = getIntent();
        long recievedWorkoutId = intent.getLongExtra("workoutId", -1);
        int recievedWorkoutIdInt = (int) recievedWorkoutId;

        if (recievedWorkoutId != -1){
            Log.d(TAG, "(getThings) recievedWorkoutIdInt: " + recievedWorkoutIdInt);
            thisWorkout = dbHelper.getOngoingWorkoutByWorkoutId(recievedWorkoutIdInt);

            if(thisWorkout != null){
                thisProfile = dbHelper.getSelectedProfile();
                exerciseWorkoutConnectionList = getAllExerciseWorkoutConnections(thisWorkout.getId());
                workoutExerciseList = getAllExercises(exerciseWorkoutConnectionList);
                Toast.makeText(WorkoutActivity.this, "Ran GetThings Successfully & thisworkout != null", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(WorkoutActivity.this, "Error: Workout not found", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(WorkoutActivity.this, "Error during getThings()", Toast.LENGTH_SHORT).show();
        }
    }

    //get all ewc objects in list with a workoutId
    public List<ExerciseWorkoutConnection> getAllExerciseWorkoutConnections(long workoutId){
        List<ExerciseWorkoutConnection> exercisesToPutIntoWorkoutList;
        exercisesToPutIntoWorkoutList = dbHelper.getAllExerciseWorkoutConnectionsForWorkout(workoutId);
        Log.d(TAG, "(getAllExerciseWorkoutConnections) exercisesToPutIntoWorkoutList count: " + exercisesToPutIntoWorkoutList.size());
        return exercisesToPutIntoWorkoutList;
    }

    //get all exercise objects for each ewc object in the incoming list.
    public List<Exercise> getAllExercises(List<ExerciseWorkoutConnection> exerciseWorkoutConnectionList){
        List<Exercise> outgoingExerciseList = new ArrayList<>();
        for (ExerciseWorkoutConnection exerciseWorkout : exerciseWorkoutConnectionList)  { // <---- for each ExerciseWorkoutConnectionClass in exerciseWorkoutConnectionList
            long exerciseId = exerciseWorkout.getExerciseId();
            Exercise exercise = dbHelper.getExerciseWithExerciseId(exerciseId);
            outgoingExerciseList.add(exercise);
        }
        Log.d(TAG, "(getAllExercises) getAllExerciseWorkoutConnections count: " + outgoingExerciseList.size());
        return outgoingExerciseList;
    }

    public void addExerciseToWorkout(Exercise exercise) {
        workoutExerciseList.add(exercise);
        // Update the adapter's data source here
        List<ExerciseWorkoutConnection> newExerciseWorkoutConnectionList = getAllExerciseWorkoutConnections(thisWorkout.getId());
        adapter.updateData(newExerciseWorkoutConnectionList); // replace with your updated list
        // then notify the adapter that data has changed
        adapter.notifyItemInserted(workoutExerciseList.size() - 1);
        Log.d(TAG, "addExerciseToWorkout has been run.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);
        getThings();
        Button backToMainButton = (Button) findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (thisWorkout != null){
            mOpenDialog = findViewById(R.id.addExerciseButton);
            mExerciseCategoryInput = findViewById(R.id.exerciseCategorySpinner); //chosen exercise category
            mChosenExerciseInput = findViewById(R.id.exerciseSpinner); //chosen exercise
            mOpenDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddExerciseFragment exerciseDialog = new AddExerciseFragment();
                    exerciseDialog.show(getSupportFragmentManager(), "AddExerciseDialog");
                }
            });

            RecyclerView recyclerView = findViewById(R.id.currentWorkoutRecyclerView);
            adapter = new ExerciseAdapter(exerciseWorkoutConnectionList, thisWorkout, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            Toast.makeText(WorkoutActivity.this, "Workout-object is null! Error", Toast.LENGTH_SHORT).show();
        }

        Button finishWorkoutButton = (Button) findViewById(R.id.finishWorkoutButton);
        finishWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = dbHelper.finishWorkout();
                if (result == true){
                    Intent intent = new Intent(WorkoutActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(WorkoutActivity.this, "Workout finished!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(WorkoutActivity.this, "You can't finish a workout without any exercises!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}