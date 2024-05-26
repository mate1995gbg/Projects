package com.example.training;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import models.Exercise;

public class RemoveExerciseFragment extends DialogFragment {
    private MySQLiteHelper dbHelper;
    public void setDbHelper(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public RemoveExerciseFragment(int categoryId){
        this.selectedExerciseCategoryId = categoryId;
    }

    private Spinner exerciseSpinner;
    private int selectedExerciseCategoryId;
    private ArrayAdapter<Exercise> exerciseAdapter;
    List<Exercise> exerciseList;
    private int selectedId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initializeStuff();
        View view = inflater.inflate(R.layout.dialogfragment_remove_exercise, container, false);

        //spinner stuff
        exerciseSpinner = view.findViewById(R.id.exercisesToRemoveSpinner);
        exerciseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, exerciseList);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Set the layout for the dropdown
        exerciseSpinner.setAdapter(exerciseAdapter); // Set the adapter to the spinner

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Exercise selectedExercise = (Exercise) parent.getItemAtPosition(position);
                selectedId = (int) selectedExercise.getExerciseId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //back-button logic
        Button backButton = view.findViewById(R.id.backToNewWorkoutFromRemoveExerciseBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //remove exercise button logic
        Button removeExerciseButton = view.findViewById(R.id.removeExerciseBtn);
        removeExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean deleteresult = dbHelper.deactivateExercise(selectedId);
                if (deleteresult != false){
                    dbHelper.close();
                    Bundle result = new Bundle(); //like intent. to pass data from one activity to another. Bundle is required as an argument for running setFragmentResult (check onResultFragmentListener i AddExerciseFragment).
                    getParentFragmentManager().setFragmentResult("exerciseRemoved", result); //pass bundle to parent fragment
                    dismiss();
                    Toast.makeText(getActivity(), "Removed exercise: " + (selectedId) , Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Error during insertion of user input into database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void initializeStuff(){
        exerciseList = dbHelper.getAllExercisesForSpecificCategory(selectedExerciseCategoryId);
    }
}
