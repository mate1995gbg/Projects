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

import models.Exercise;
import models.ExerciseCategory;

public class AddNewExerciseToCategoryFragment extends DialogFragment {

    private static final String TAG = "MySQLiteHelper"; // Set the TAG here
    private MySQLiteHelper dbHelper;
    // Setter method to set the dbHelper instance
    public void setDbHelper(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_add_exercise_to_category, container, false);
        EditText exerciseInput = view.findViewById(R.id.newExerciseEditText);

        Button backButton = view.findViewById(R.id.backToNewWorkoutBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Button addExerciseToCategoryButton = view.findViewById(R.id.addExerciseBtn);
        addExerciseToCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To get selected exercise category from spinner from other fragment START
                Bundle args = getArguments();
                if (args!=null) {
                    ExerciseCategory selectedExerciseCategory = args.getParcelable("selectedExerciseCategory");
                    long categoryId = selectedExerciseCategory.getCategoryId();
                    if(selectedExerciseCategory != null) {
                        //get exercise input from EditText
                        Log.d(TAG, "exerciseInput found: " + (exerciseInput != null));
                        String userInput = exerciseInput.getText().toString();
                        if (userInput.trim().isEmpty()){
                            Toast.makeText(getContext(), "You can't add a Category with no name!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Exercise newExercise = new Exercise(userInput,categoryId);

                        if (dbHelper != null) {
                            boolean insertionResult = dbHelper.addExercise(newExercise); //Returns true if insertion was successful, false otherwise. check method in MySQLiteHelper
                            if (insertionResult == true){
                                dbHelper.close();
                                Bundle result = new Bundle(); //like intent. to pass data from one activity to another. Bundle is required as an argument for running setFragmentResult (check onResultFragmentListener i AddExerciseFragment).
                                getParentFragmentManager().setFragmentResult("exerciseAdded", result); //pass bundle to parent fragment
                                dismiss();
                                Toast.makeText(getActivity(), "Added exercise: " + (newExercise.getExerciseDescription()), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(), "Error during insertion of user input into database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
        return view;
    }
}