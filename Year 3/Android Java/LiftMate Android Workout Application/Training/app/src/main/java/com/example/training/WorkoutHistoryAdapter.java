package com.example.training;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import models.Exercise;
import models.ExerciseCategory;
import models.ExerciseWorkoutConnection;
import models.Profile;
import models.Workout;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryViewHolder> {
    LinkedHashMap<String, List<ExerciseWorkoutConnection>> ewcMap;
    List<Workout> finishedWorkoutsList;
    Profile currentProfile;
    MySQLiteHelper dbHelper;
    List<ExerciseWorkoutConnection> workoutData;

    public WorkoutHistoryAdapter(LinkedHashMap<String, List<ExerciseWorkoutConnection>> ewcMap, List<Workout> finishedWorkoutsList, Profile currentProfile, MySQLiteHelper dbHelper){
        this.ewcMap = ewcMap;
        this.finishedWorkoutsList = finishedWorkoutsList;
        this.currentProfile = currentProfile;
        this.dbHelper = dbHelper;
    }
    @NonNull
    @Override
    public WorkoutHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workouthistory_list_carditem, parent, false);
        return new WorkoutHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHistoryViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(ewcMap.keySet());
        String workoutDate = keys.get(position);
        final List<ExerciseWorkoutConnection> currentWorkoutData = ewcMap.get(workoutDate);
        Workout currentWorkout = findWorkoutByDate(workoutDate);
        if(currentWorkoutData != null && currentWorkout != null){
            holder.workoutDateTextView.setText(workoutDate);
            holder.workoutExercisesTextView.setText(populateExercisesTextView(currentWorkoutData));
            holder.workoutExerciseCategoriesTextView.setText(populateExerciseCategoryTextView(currentWorkoutData));
            holder.workoutWeightLiftedTextView.setText(String.valueOf(currentWorkout.getTonnesLifted()) + " KG");
            holder.workoutCaloriesBurnedTextView.setText(String.valueOf(currentWorkout.getCaloriesBurned()) + " Kcal");
            holder.seeOldWorkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OldWorkoutFragment newFragment = new OldWorkoutFragment();

                    Bundle args = new Bundle();
                    args.putSerializable("exercise_workout_list", (Serializable) currentWorkoutData);
                    newFragment.setArguments(args);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    newFragment.show(activity.getSupportFragmentManager(), "tag");
                }
            });

            holder.retrainOldWorkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean hasOngoingWorkout = dbHelper.hasOngoingWorkout((int)currentProfile.getProfileId());

                    if(hasOngoingWorkout == true){
                        Toast.makeText(view.getContext(), "You need to end your current workout before starting another!", Toast.LENGTH_SHORT).show();
                    }
                    if(hasOngoingWorkout == false){
                        dbHelper.startWorkout((int)currentProfile.getProfileId());
                        Workout ongoingWorkout = dbHelper.getOngoingWorkoutByProfileId((int)currentProfile.getProfileId());
                        for (ExerciseWorkoutConnection ewc : currentWorkoutData ) {;
                            Log.d(TAG, "ewc.exerciseWorkoutId: " + ewc.getExerciseWorkoutId());
                            ExerciseWorkoutConnection newEwc = new ExerciseWorkoutConnection();
                            newEwc.setExerciseId(ewc.getExerciseId());
                            newEwc.setWorkoutId(ongoingWorkout.getId()); //this needs to be the new workouts id, not the old ones
                            newEwc.setReps(ewc.getReps());
                            newEwc.setWeight(ewc.getWeight());
                            newEwc.setSet(ewc.getSet());
                            newEwc.setExerciseOrder(ewc.getExerciseOrder());
                            newEwc.setDuplicateExerciseOrder(ewc.getDuplicateExerciseOrder());
                            dbHelper.addExerciseWorkout(newEwc);
                        }
                        Intent intent = new Intent(view.getContext(), WorkoutActivity.class);
                        intent.putExtra("workoutId", ongoingWorkout.getId());
                        view.getContext().startActivity(intent);
                    }
                    else{
                        Toast.makeText(view.getContext(), "Something went wrong at: retrainOldWorkoutButton.onClick, hasOngoingWorkout is null or neither true/false", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return ewcMap.size();
    }

    private StringBuilder populateExercisesTextView(List<ExerciseWorkoutConnection> workoutData){
        StringBuilder exercisesStringBuilder = new StringBuilder();
        List<String> exerciseNameList = new ArrayList<>();
        for (ExerciseWorkoutConnection ewc : workoutData){
            Exercise exercise = dbHelper.getExerciseWithExerciseIdForHistory(ewc.getExerciseId());

            if(!exerciseNameList.contains(exercise.getExerciseDescription())){
                exerciseNameList.add(exercise.getExerciseDescription());
            }
        }
        for (String exerciseName : exerciseNameList){
            exercisesStringBuilder.append(exerciseName);
            exercisesStringBuilder.append(", ");
        }

        // Remove the trailing comma and space
        if (exercisesStringBuilder.length() > 0) {
            exercisesStringBuilder.setLength(exercisesStringBuilder.length() - 2);
        }

        return exercisesStringBuilder;
    }

    private StringBuilder populateExerciseCategoryTextView(List<ExerciseWorkoutConnection> workoutData){
        StringBuilder categoryStringBuilder = new StringBuilder();
        List<String> categoryNameList = new ArrayList<>();

        for (ExerciseWorkoutConnection ewc : workoutData){
            ExerciseCategory category = dbHelper.getCategoryByExerciseIdForHistory(ewc.getExerciseId());

            if(!categoryNameList.contains(category.getCategoryName())){
                categoryNameList.add(category.getCategoryName());
            }
        }

        for (String categoryName : categoryNameList){
            categoryStringBuilder.append(categoryName);
            categoryStringBuilder.append(", ");
        }

        // Remove the trailing comma and space
        if (categoryStringBuilder.length() > 0) {
            categoryStringBuilder.setLength(categoryStringBuilder.length() - 2);
        }

        return categoryStringBuilder;
    }

    private Workout findWorkoutByDate(String workoutDate){
        Log.d(TAG, "onBindViewHolder: "+workoutDate);
        // Find the matching WorkoutClass based on workoutDate
        Workout currentWorkout = null;
        for (Workout workout : finishedWorkoutsList) {
            if (workout.getStartTime().equals(workoutDate)) { // Replace getStartDate() with your actual getter
                currentWorkout = workout;
                Log.d(TAG, "currentWorkout caloriesBurned:"+currentWorkout.getCaloriesBurned());
                break;
            }
        }
        if (currentWorkout == null){
            Log.d(TAG, "returned workout is NULL");
        }
        else{
            Log.d(TAG, "returned workout is NOT NULL ");
        }
        return currentWorkout;
    }
}
