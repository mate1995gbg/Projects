package com.example.training;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Exercise;
import models.ExerciseWithOrder;
import models.ExerciseWorkoutConnection;

public class OldWorkoutAdapter extends RecyclerView.Adapter<OldWorkoutViewHolder> {
    List<ExerciseWorkoutConnection> exerciseWorkoutConnectionList;
    MySQLiteHelper dbHelper;
    private List<ExerciseWithOrder> exerciseWithOrderList;
    Set<String> addedExercisesSet = new HashSet<>();
    Context context;
    private static final String TAG = "oldWorkoutAdapter"; // Set the TAG here

    public OldWorkoutAdapter(List<ExerciseWorkoutConnection> ewcList, MySQLiteHelper dbhelper, Context context){
        this.exerciseWorkoutConnectionList = ewcList;
        Log.d(TAG, "exerciseWorkoutConnectionlist size: " + exerciseWorkoutConnectionList.size());
        this.dbHelper = dbhelper;
        this.context = context;
        exerciseWithOrderList = SortExerciseWorkouts(exerciseWorkoutConnectionList);
        Log.d(TAG, "exerciseWithOrderList size: " + exerciseWithOrderList.size());
    }

    @NonNull
    @Override
    public OldWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder has run.");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.oldworkout_list_carditem, parent, false);
        return new OldWorkoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OldWorkoutViewHolder holder, int position) {
        Log.d(TAG, "onBindVewHolder has run.");
        int currentPosition = holder.getAdapterPosition();
        if (currentPosition >= exerciseWithOrderList.size() || currentPosition < 0) {
            Toast.makeText(context, "Invalid index, currentposition:" + currentPosition, Toast.LENGTH_SHORT).show();
            return;
        }
        ExerciseWithOrder exerciseWithOrder = exerciseWithOrderList.get(currentPosition);

        Exercise exercise = exerciseWithOrder.getExercise();
        List<ExerciseWorkoutConnection> matchingEwcList = new ArrayList<>();
        for (ExerciseWorkoutConnection ewc : exerciseWorkoutConnectionList) {
            if (ewc.getExerciseId() == exerciseWithOrder.exercise.getExerciseId() && ewc.getDuplicateExerciseOrder() == exerciseWithOrder.getDuplicateExerciseOrder()){
                matchingEwcList.add(ewc);
                View setLayout = LayoutInflater.from(context).inflate(R.layout.workout_set_item, holder.oldWorkoutSetContainer, false);
                EditText weightEditText = setLayout.findViewById(R.id.weightEditText);
                EditText repsEditText = setLayout.findViewById(R.id.repsEditText);

                setLayout.setTag(ewc);
                //removesetButton has to be instantiated inside onBindViewHolder since the button is inside an "item of viewholder

                weightEditText.setText(String.valueOf(ewc.getWeight()));
                repsEditText.setText(String.valueOf(ewc.getReps()));
                holder.oldWorkoutSetContainer.addView(setLayout);
            }
        }
        holder.exerciseNameTextView.setText(exercise.getExerciseDescription());
    }

    @Override
    public int getItemCount() {
        int count = exerciseWithOrderList.size();  // Replace `yourDataList` with the actual variable holding your data
        Log.d("AdapterDebug", "Item count: " + count);
        return count;
    }

    public List<ExerciseWithOrder> SortExerciseWorkouts(List<ExerciseWorkoutConnection> exerciseWorkoutConnectionList) {
        Log.d(TAG, "SortExerciseWorkouts has run.");
        addedExercisesSet.clear(); //chatgpt recommendation 2 to add this here
        List<ExerciseWithOrder> exerciseWithOrderList = new ArrayList<>();

        for (ExerciseWorkoutConnection ewc : exerciseWorkoutConnectionList) {
            Exercise exercise = dbHelper.getExerciseWithExerciseId(ewc.getExerciseId());

            //if ewc is not a duplicate &&
            if (ewc.getDuplicateExerciseOrder() == 0 && !addedExercisesSet.contains(exercise.getExerciseId() + "_0")) {
                exerciseWithOrderList.add(new ExerciseWithOrder(exercise, 0));
                addedExercisesSet.add(exercise.getExerciseId() + "_0");
            }

            if (ewc.getDuplicateExerciseOrder() != 0 && !addedExercisesSet.contains(exercise.getExerciseId() + "_" + ewc.getDuplicateExerciseOrder())) {
                exerciseWithOrderList.add(new ExerciseWithOrder(exercise, ewc.getDuplicateExerciseOrder()));
                addedExercisesSet.add(exercise.getExerciseId() + "_" + ewc.getDuplicateExerciseOrder());
            }
        }
        return exerciseWithOrderList;
    }

    public void calculateCaloriesBurned(){

    }
}
