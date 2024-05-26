package com.example.training;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import models.ExerciseWorkoutConnection;

public class OldWorkoutFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private OldWorkoutAdapter adapter;
    private MySQLiteHelper dbHelper;
    private static final String TAG = "oldWorkoutFragment"; // Set the TAG here
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        Bundle args = getArguments();
        if (args != null) {
            Log.d(TAG, "args is NOT null!");
            // Assumes you used "exercise_workout_list" as your key
            List<ExerciseWorkoutConnection> exerciseWorkoutList = (List<ExerciseWorkoutConnection>) args.getSerializable("exercise_workout_list");
            dbHelper = new MySQLiteHelper(getContext());
            // Now, you can use this list to populate your RecyclerView using OldWorkoutAdapter
            adapter = new OldWorkoutAdapter(exerciseWorkoutList, dbHelper, getContext());
        }
        else{
            Log.d(TAG, "args is null!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialogfragment_oldworkout, container, false);

        recyclerView = view.findViewById(R.id.oldWorkoutRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (adapter != null) {
            Log.d(TAG, "adapter is not null!");
            recyclerView.setAdapter(adapter);
        }
        else{
            Log.d(TAG, "adapter is null!");
        }
        return view;
    }
}
