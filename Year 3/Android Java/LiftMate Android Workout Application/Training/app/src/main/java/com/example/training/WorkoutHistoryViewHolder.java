package com.example.training;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutHistoryViewHolder extends RecyclerView.ViewHolder {
    TextView workoutDateTextView;
    TextView workoutExerciseCategoriesTextView;
    TextView workoutExercisesTextView;
    TextView workoutWeightLiftedTextView;
    TextView workoutCaloriesBurnedTextView;

    Button seeOldWorkoutButton;
    Button retrainOldWorkoutButton;
    public WorkoutHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        workoutDateTextView = itemView.findViewById(R.id.workoutHistoryWorkoutDateContainerTextView);
        workoutExerciseCategoriesTextView = itemView.findViewById(R.id.workoutHistoryExerciseCategoriesContainerTextView);
        workoutExercisesTextView = itemView.findViewById(R.id.workoutHistoryExercisesTrainedContainerTextView);
        workoutWeightLiftedTextView = itemView.findViewById(R.id.workoutHistoryTotalWeightLiftedContainerTextView);
        workoutCaloriesBurnedTextView = itemView.findViewById(R.id.workoutHistoryCaloriesBurnedContainerTextView);
        seeOldWorkoutButton = itemView.findViewById(R.id.seeOldWorkoutButton);
        retrainOldWorkoutButton = itemView.findViewById(R.id.retrainOldWorkoutButton);
    }
}
