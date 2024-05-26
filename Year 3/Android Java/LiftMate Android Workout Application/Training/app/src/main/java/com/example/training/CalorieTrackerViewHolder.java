package com.example.training;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalorieTrackerViewHolder extends RecyclerView.ViewHolder{
    TextView calorieTrackerDateContainerTextView;
    TextView calorieTrackerCaloriesContainerTextView;



    public CalorieTrackerViewHolder(@NonNull View itemView) {
        super(itemView);
        calorieTrackerDateContainerTextView = itemView.findViewById(R.id.calorieTrackerDateContainerTextView);
        calorieTrackerCaloriesContainerTextView = itemView.findViewById(R.id.calorieTrackerCaloriesValueContainerTextView);
    }
}
