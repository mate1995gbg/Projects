package com.example.training;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import models.ExerciseCategory;

public class AddNewExerciseCategoryFragment extends DialogFragment {
    private static final String TAG = "MySQLiteHelper"; // Set the TAG here
    private MySQLiteHelper dbHelper;
    // Setter method to set the dbHelper instance
    public void setDbHelper(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_add_exercisecategory, container, false);
        EditText categoryInput = view.findViewById(R.id.newExerciseCategoryEditText);

        //back-button logic
        Button backButton = view.findViewById(R.id.backToNewWorkoutBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        Button addExerciseCategoryToDbButton = view.findViewById(R.id.addExerciseCategoryBtn);
        addExerciseCategoryToDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "newExerciseCategoryEditText found: " + (categoryInput != null));

                //OBS se till att fixa så det inte går att mata in tomma saker i databasen. (if-statement med !=null på categoryInput?)
                String userInput = categoryInput.getText().toString(); //gets the user input from the EditText. EditText is instantiated in the onCreate but by the time onClick is clicked, the user has entered a value.
                if (userInput.trim().isEmpty()){
                    Toast.makeText(getContext(), "You can't add an Exercise with no name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ExerciseCategory newExerciseCategory = new ExerciseCategory(userInput); //skapa ett objekt av ExerciseCategoryClass och bind det med userInputen från EditTexten. (konstruktorn för ExerciseCategory löser det)

                // Ensure dbHelper is not null before using it
                if (dbHelper != null) {
                    boolean insertionResult = dbHelper.addExerciseCategory(newExerciseCategory); //Returns true if insertion was successful, false otherwise. check method in MySQLiteHelper
                    if (insertionResult == true){
                        dbHelper.close();
                        Bundle result = new Bundle(); //like intent. to pass data from one activity to another. Bundle is required as an argument for running setFragmentResult (check onResultFragmentListener i AddExerciseFragment).
                        getParentFragmentManager().setFragmentResult("exerciseCategoryAdded", result); //pass bundle to parent fragment
                        dismiss();
                        Toast.makeText(getActivity(), "Added exercise category: " + (newExerciseCategory.getCategoryName()), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Error during insertion of user input into database", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        return view;
    }
}
