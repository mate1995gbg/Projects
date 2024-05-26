package com.example.training;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalorieTrackerAdapter extends RecyclerView.Adapter<CalorieTrackerViewHolder>{
    Map<String, Float> dailyCalories = new HashMap<>();

    public CalorieTrackerAdapter(Map<String, Float> dailyCalories){
        this.dailyCalories = dailyCalories;
    }


    @NonNull
    @Override
    public CalorieTrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calorietrackeroverview_list_carditem, parent, false);
        return new CalorieTrackerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalorieTrackerViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(dailyCalories.keySet());
        String caloriesDate = keys.get(position);
        final float calories = dailyCalories.get(caloriesDate);
        if (calories != 0){
            holder.calorieTrackerDateContainerTextView.setText(caloriesDate);
            holder.calorieTrackerCaloriesContainerTextView.setText(String.valueOf(calories));
        }
    }

    @Override
    public int getItemCount() {
        return dailyCalories.size();
    }
}
