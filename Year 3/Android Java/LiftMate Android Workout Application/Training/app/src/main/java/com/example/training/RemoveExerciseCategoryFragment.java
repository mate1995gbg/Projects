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

import models.ExerciseCategory;

public class RemoveExerciseCategoryFragment extends DialogFragment {
    private MySQLiteHelper dbHelper;
    public void setDbHelper(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    //widgets
    private Spinner exerciseCategorySpinner;
    private ArrayAdapter<ExerciseCategory> exerciseCategoryAdapter;
    List<ExerciseCategory> exerciseCategoryList;
    private int selectedCategoryId = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initializeStuff();
        View view = inflater.inflate(R.layout.dialogfragment_remove_exercisecategory, container, false);

        //spinner stuff
        exerciseCategorySpinner = view.findViewById(R.id.exerciseCategoriesToRemoveSpinner);
        exerciseCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, exerciseCategoryList);
        exerciseCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Set the layout for the dropdown
        exerciseCategorySpinner.setAdapter(exerciseCategoryAdapter); // Set the adapter to the spinner

        exerciseCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ExerciseCategory selectedExerciseCategory = (ExerciseCategory) parent.getItemAtPosition(position);
                selectedCategoryId = (int) selectedExerciseCategory.getCategoryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //back-button logic
        Button backButton = view.findViewById(R.id.backToNewWorkoutFromRemoveExerciseCategoriesBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //remove exercise button logic
        Button removeExerciseCategoryButton = view.findViewById(R.id.removeExerciseCategoryBtn);
        removeExerciseCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean deleteresult = dbHelper.deactivateExerciseCategory(selectedCategoryId);
                if (deleteresult != false){
                    dbHelper.close();
                    Bundle result = new Bundle(); //like intent. to pass data from one activity to another. Bundle is required as an argument for running setFragmentResult (check onResultFragmentListener i AddExerciseFragment).
                    getParentFragmentManager().setFragmentResult("exerciseCategoryRemoved", result); //pass bundle to parent fragment
                    dismiss();
                    Toast.makeText(getActivity(), "Removed exercise category: " + (selectedCategoryId) , Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Error during insertion of user input into database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void initializeStuff(){
        exerciseCategoryList = dbHelper.getAllExerciseCategories();
    }
}
