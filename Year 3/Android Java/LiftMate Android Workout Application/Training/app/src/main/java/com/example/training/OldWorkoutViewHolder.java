package com.example.training;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OldWorkoutViewHolder extends RecyclerView.ViewHolder {
    public TextView exerciseNameTextView;
    public LinearLayout oldWorkoutSetContainer;
    public OldWorkoutViewHolder(@NonNull View itemView) {
        super(itemView);
        exerciseNameTextView = itemView.findViewById(R.id.exerciseNameTextView); // Replace with your actual ID
        oldWorkoutSetContainer = itemView.findViewById(R.id.oldWorkoutSetContainer); // Replace with your actual ID
    }
}
