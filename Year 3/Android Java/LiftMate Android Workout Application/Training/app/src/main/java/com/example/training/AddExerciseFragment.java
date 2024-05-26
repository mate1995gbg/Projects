package com.example.training;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentResultListener;

import java.util.List;

import models.Exercise;
import models.ExerciseCategory;
import models.ExerciseWorkoutConnection;

public class AddExerciseFragment extends DialogFragment {
    private static final String TAG = "MySQLiteHelper"; // Set the TAG here
    private ArrayAdapter<Exercise> exerciseAdapter;
    private Spinner exerciseSpinner;
    private ArrayAdapter<ExerciseCategory> exerciseCategoryAdapter; //ArrayAdapter is a bridge between UI component and Data source. converts data from data source into view items that can be displayed into the UI component.
    private Spinner exerciseCategorySpinner;
    private int selectedCategoryId = -1; // Default value, indicating no category selected
    private Exercise selectedExercise;

    public void updateExerciseCategories(List<ExerciseCategory> exerciseCategories){
        exerciseCategoryAdapter.clear();
        exerciseCategoryAdapter.addAll((exerciseCategories));
        exerciseCategoryAdapter.notifyDataSetChanged();
    }

    private void updateExercises(List<Exercise> exercises){
        exerciseAdapter.clear();
        exerciseAdapter.addAll(exercises);
        exerciseAdapter.notifyDataSetChanged();
    }

    private MySQLiteHelper dbHelper;
    // Setter method to set the dbHelper instance
    public void setDbHelper(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle);
        View view = inflater.inflate(R.layout.dialogfragment_add_exercise, container, false); //bind layout to java class

        dbHelper = new MySQLiteHelper(getContext());  //Get all exercise categories from db
        List<ExerciseCategory> exerciseCategoryList = dbHelper.getAllExerciseCategories(); //put all exercisecategories from db into a list of object type <exercisecategoryclass>

        exerciseCategorySpinner = view.findViewById(R.id.exerciseCategorySpinner); // Bind/connect the exercise category spinner from the layout to a Spinner object in this java class for use.
        exerciseCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, exerciseCategoryList); // Create an ArrayAdapter to bridge UI component (simple_spinner_item) with data source (exerciseCategoryList).
        exerciseCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Set the layout for the dropdown
        exerciseCategorySpinner.setAdapter(exerciseCategoryAdapter); // Set the adapter to the spinner

        exerciseSpinner = view.findViewById(R.id.exerciseSpinner);
        exerciseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Set the layout for the dropdown
        exerciseSpinner.setAdapter(exerciseAdapter); // Set the adapter to the spinner

        exerciseCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ExerciseCategory selectedExerciseCategory = (ExerciseCategory) parent.getItemAtPosition(position);
                selectedCategoryId = (int) selectedExerciseCategory.getCategoryId();
                List<Exercise> exerciseList = dbHelper.getAllExercisesForSpecificCategory(selectedCategoryId);
                updateExercises(exerciseList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExercise = (Exercise) parent.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //onClickListener for back-button
        Button backButton = view.findViewById(R.id.backToNewWorkoutBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //onClickListener for adding a new ExerciseCategory
        Button addExerciseCategoryButton = view.findViewById(R.id.addExerciseCategoryBtn); //works as button, use this method!
        addExerciseCategoryButton.setOnClickListener(new View.OnClickListener() {
            //when button is PRESSED open fragment, set dbhelper & listener for exerciseCategoryAdded
            @Override
            public void onClick(View view) {
                AddNewExerciseCategoryFragment newFragment = new AddNewExerciseCategoryFragment();
                newFragment.setDbHelper(dbHelper);
                getParentFragmentManager().setFragmentResultListener("exerciseCategoryAdded", getViewLifecycleOwner(), new FragmentResultListener() { //Take note of RequestKey, the same requestkey is found in AddNewExerciseCategoryFragment to transport values between the two. this allows spinner in this dialogfragment to be updated as soon as the add button for new exercise category is pressed from within the other dialogFragment.
                    //listener for exerciseCategoryAdded which updated list of categories if triggered
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) { //onFragmentResult is run when it is prompted (from AddNewExerciseCategoryFragment)
                        List<ExerciseCategory> updatedCategories = dbHelper.getAllExerciseCategories();
                        updateExerciseCategories(updatedCategories);
                    }
                });
                newFragment.show(getParentFragmentManager(), "AddNewExerciseCategoryFragment");
            }
        });

        Button removeExerciseCategoryButton = view.findViewById(R.id.removeExerciseCategoryBtn);
        removeExerciseCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveExerciseCategoryFragment newRemoveExerciseCategoryFragment = new RemoveExerciseCategoryFragment();
                newRemoveExerciseCategoryFragment.setDbHelper(dbHelper);
                getParentFragmentManager().setFragmentResultListener("exerciseCategoryRemoved", getViewLifecycleOwner(), new FragmentResultListener() { //Take note of RequestKey, the same requestkey is found in AddNewExerciseCategoryFragment to transport values between the two. this allows spinner in this dialogfragment to be updated as soon as the add button for new exercise category is pressed from within the other dialogFragment.
                    //listener for exerciseCategoryAdded which updated list of categories if triggered
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) { //onFragmentResult is run when it is prompted (from AddNewExerciseCategoryFragment)
                        List<ExerciseCategory> updatedCategories = dbHelper.getAllExerciseCategories();
                        updateExerciseCategories(updatedCategories);
                        List<Exercise> updatedExercises = dbHelper.getAllExercisesForSpecificCategory(selectedCategoryId);
                        updateExercises(updatedExercises);
                    }
                });
                newRemoveExerciseCategoryFragment.show(getParentFragmentManager(), "AddNewExerciseCategoryFragment");
            }
        });

        Button removeExerciseButton = view.findViewById(R.id.removeExerciseBtn);
        removeExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveExerciseFragment newRemoveExerciseFragment = new RemoveExerciseFragment(selectedCategoryId);
                newRemoveExerciseFragment.setDbHelper(dbHelper);
                getParentFragmentManager().setFragmentResultListener("exerciseRemoved", getViewLifecycleOwner(), new FragmentResultListener() { //Take note of RequestKey, the same requestkey is found in AddNewExerciseCategoryFragment to transport values between the two. this allows spinner in this dialogfragment to be updated as soon as the add button for new exercise category is pressed from within the other dialogFragment.
                    //listener for exerciseCategoryAdded which updated list of categories if triggered
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) { //onFragmentResult is run when it is prompted (from AddNewExerciseCategoryFragment)
                        List<Exercise> updatedExercises = dbHelper.getAllExercisesForSpecificCategory(selectedCategoryId);
                        updateExercises(updatedExercises);
                    }
                });
                newRemoveExerciseFragment.show(getParentFragmentManager(), "removeExerciseFragment");
            }
        });

        //onClickListener for adding new Exercise to a category
        Button addNewExerciseToCategoryBtn = view.findViewById(R.id.addNewExerciseBtn);
        addNewExerciseToCategoryBtn.setOnClickListener(new View.OnClickListener() {
            //when button is CLICKED open fragment + send selectedExerciseCat with Bundle & set Listener for exerciseAdded
            @Override
            public void onClick(View view) {
                ExerciseCategory selectedExerciseCategory = (ExerciseCategory) exerciseCategorySpinner.getSelectedItem();
                int categoryId = (int) selectedExerciseCategory.getCategoryId();
                Bundle args = new Bundle();
                args.putParcelable("selectedExerciseCategory", selectedExerciseCategory);
                AddNewExerciseToCategoryFragment newFragment = new AddNewExerciseToCategoryFragment();
                newFragment.setArguments(args);
                newFragment.setDbHelper(dbHelper);
                newFragment.show(getParentFragmentManager(), "AddNewExerciseToCategoryFragment");
                getParentFragmentManager().setFragmentResultListener("exerciseAdded", getViewLifecycleOwner(), new FragmentResultListener() {
                    //if exerciseAdded is DETECTED, updates list of exercises if triggered
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        List<Exercise> updatedExercises = dbHelper.getAllExercisesForSpecificCategory(categoryId);
                        updateExercises(updatedExercises);

                    }
                });
            }
        });

        //onClickListener for adding the exercise to the workout
        Button addExerciseToWorkout = view.findViewById(R.id.addChosenExerciseBtn);
        addExerciseToWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedExercise != null) {
                    Log.d(TAG, "selectedExercise is found: " + (selectedExercise != null));
//                    ((WorkoutActivity) requireActivity()).addExerciseToWorkout(selectedExercise); // (1) //add selectedExercise to NewWorkoutActivity List
                    ExerciseWorkoutConnection addExerciseWorkout = new ExerciseWorkoutConnection(); //Create new EWC object
                    addExerciseWorkout.setExerciseId(selectedExercise.getExerciseId()); //set exerciseId to object from selectedExercise
                    addExerciseWorkout.setWorkoutId(((WorkoutActivity) requireActivity()).thisWorkout.getId()); //set workoutId to object from method from NewWorkoutActivity
                    boolean doesExerciseExistInWorkout = dbHelper.doesExerciseExistInWorkout((int) addExerciseWorkout.getExerciseId(), (int) addExerciseWorkout.getWorkoutId()); //check if exerciseId/WorkoutId combo exists already in EWC table

                    //if exercise doesnt exist in workout then create new exerciseWorkoutObject
                    if (!doesExerciseExistInWorkout){
                        //exerciseId / workoutId for object already set!
                        addExerciseWorkout.setSet(0);
                        addExerciseWorkout.setReps(0);
                        addExerciseWorkout.setWeight(0);
                        //get latest number for the order of things in workout ( 0 if no exercises, x + 1 if exercises exist)
                        Log.d(TAG, "(AddExerciseFragment) workoutId sent into dbHelper.returnMaxPlusOneOrZeroExerciseOrder is: " + addExerciseWorkout.getWorkoutId());
                        addExerciseWorkout.setExerciseOrder(dbHelper.returnMaxPlusOneOrZeroExerciseOrder((int) addExerciseWorkout.getWorkoutId()));
                        Log.d(TAG, "(AddExerciseFragment) object addExerciseWorkout.exerciseOrder returned from dbHelper.returnMaxPlusOneOrZeroExerciseOrder is: " + addExerciseWorkout.getExerciseOrder());

                        //if exercise does not exist in workout yet then there is no duplicate and this value can be 0.
                        addExerciseWorkout.setDuplicateExerciseOrder(0);
                        //run method to add object to database
                        dbHelper.addExerciseWorkout(addExerciseWorkout);
                        ((WorkoutActivity) requireActivity()).addExerciseToWorkout(selectedExercise); //Added here instead 1
                        Toast.makeText(getActivity(), "Added exercise with exerciseId: " +addExerciseWorkout.getExerciseId() + " and workoutId: " +addExerciseWorkout.getWorkoutId(), Toast.LENGTH_SHORT).show();

                    }
                    //IF EXERCISE ALREADY EXISTS IN WORKOUT and i want to add again, then that means we need-
                    //-to up DuplicateExerciseOrder by 1, thus giving logic for recyclerview to add another item but with same exerciseId/workoutId combo.
                    else{
                        //exerciseId / workoutId for object already set!
                        addExerciseWorkout.setDuplicateExerciseOrder(dbHelper.returnMaxPlusOneDuplicateExerciseNumber((int) addExerciseWorkout.getExerciseId(), (int) addExerciseWorkout.getWorkoutId()));
                        addExerciseWorkout.setSet(0);
                        addExerciseWorkout.setReps(0);
                        addExerciseWorkout.setWeight(0);
                        //get latest number for the order of things in workout ( 0 if no exercises, x + 1 if exercises exist)
                        addExerciseWorkout.setExerciseOrder(dbHelper.returnMaxPlusOneOrZeroExerciseOrder((int) addExerciseWorkout.getWorkoutId()));
                        //run method to add object to database
                        dbHelper.addExerciseWorkout(addExerciseWorkout);
                        ((WorkoutActivity) requireActivity()).addExerciseToWorkout(selectedExercise); //Added here instead 2
                        Toast.makeText(getActivity(), "Added duplicate exercise to EWC in db.", Toast.LENGTH_SHORT).show();
                        //((WorkoutActivity) requireActivity()).reloadExercises();
                    }
                }
                else {
                    Log.d(TAG, "No exercise selected.");
                    Toast.makeText(getActivity(), "No exercise or category selected!", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
        return view;
    }

    //manual override of DialogFragment size. could not get it to work otherwise
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}