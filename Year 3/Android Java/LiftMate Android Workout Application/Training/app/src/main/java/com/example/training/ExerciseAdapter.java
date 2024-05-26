package com.example.training;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Exercise;
import models.ExerciseWithOrder;
import models.ExerciseWorkoutConnection;
import models.Workout;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private static final String LOG = "log: ";
    private List<Exercise> exerciseList;
    private List<ExerciseWithOrder> exerciseWithOrderList; //test
    Set<String> addedExercisesSet = new HashSet<>();
    private Workout thisWorkout;  //the workout object
    private List<ExerciseWorkoutConnection> exerciseWorkoutConnectionList;  //all exerciseWorkoutConnection objects that are relevant to this workoutId.
    private Context context;
    private ImageView trophyImageView;
    MySQLiteHelper dbHelper;

    public ExerciseAdapter(List<ExerciseWorkoutConnection> exerciseWorkoutList, Workout thisWorkout, Context context){
        this.exerciseWorkoutConnectionList = exerciseWorkoutList;
        this.thisWorkout = thisWorkout;
        this.context = context;
        dbHelper = new MySQLiteHelper(context);
        exerciseWithOrderList = SortExerciseWorkouts(exerciseWorkoutConnectionList);
    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  //create ViewHolder & bind layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_list_carditem, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.ViewHolder holder, int position) {
        holder.setContainer.removeAllViews(); //chatgpt recommendation 1 to add this
        int currentPosition = holder.getAdapterPosition();
        if (currentPosition >= exerciseWithOrderList.size() || currentPosition < 0) {
            Toast.makeText(context, "Invalid index, currentposition:" + currentPosition, Toast.LENGTH_SHORT).show();
            return;
        }
        ExerciseWithOrder exerciseWithOrder = exerciseWithOrderList.get(currentPosition);

        Exercise exercise = exerciseWithOrder.getExercise();
        List<ExerciseWorkoutConnection> matchingEwcList = new ArrayList<>();
        for (ExerciseWorkoutConnection ewc : exerciseWorkoutConnectionList) {
            if (ewc.getExerciseId() == exerciseWithOrder.exercise.getExerciseId() && ewc.getDuplicateExerciseOrder() == exerciseWithOrder.getDuplicateExerciseOrder()){
                matchingEwcList.add(ewc);
                boolean showPersonalBestTrophy = dbHelper.determineIfPersonalBestInLoop(ewc);
                View setLayout = LayoutInflater.from(context).inflate(R.layout.workout_set_item, holder.setContainer, false);
                EditText weightEditText = setLayout.findViewById(R.id.weightEditText);
                EditText repsEditText = setLayout.findViewById(R.id.repsEditText);
                trophyImageView = setLayout.findViewById(R.id.prTrophyImageView);
                setLayout.setTag(ewc);
                //removesetButton has to be instantiated inside onBindViewHolder since the button is inside an "item of viewholder"
                TextView removeSetButton = setLayout.findViewById(R.id.removeSetTextViewButton);
                removeSetButton.setTag(ewc);
                removeSetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ExerciseWorkoutConnection toRemove = (ExerciseWorkoutConnection) view.getTag();
                        // Remove from database
                        MySQLiteHelper dbHelper = new MySQLiteHelper(context);
                        dbHelper.deleteExerciseWorkout(toRemove);
                        // Remove from local list
                        exerciseWorkoutConnectionList.remove(toRemove);
                        // Remove the set view from UI
                        holder.setContainer.removeView((View) view.getParent());

                        int clickCurrentPosition = holder.getAdapterPosition();
                        if (clickCurrentPosition == -1){
                            Toast.makeText(context, "int currentPosition = -1! Item no longer exists", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        notifyItemChanged(clickCurrentPosition);
                    }
                });

                weightEditText.setText(String.valueOf(ewc.getWeight()));
                repsEditText.setText(String.valueOf(ewc.getReps()));
                addTextWatcherToWeighEditText(weightEditText, ewc);
                addTextWatcherToRepsEditText(repsEditText, ewc);

                if (showPersonalBestTrophy == true){
                    trophyImageView.setVisibility(View.VISIBLE);
                }
                else{
                    trophyImageView.setVisibility(View.GONE);
                }
                holder.setContainer.addView(setLayout);
            }
        }
        holder.exerciseNameTextView.setText(exercise.getExerciseDescription());
        holder.addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View setLayout = LayoutInflater.from(context).inflate(R.layout.workout_set_item, null);
                EditText weightEditText = setLayout.findViewById(R.id.weightEditText);
                EditText repsEditText = setLayout.findViewById(R.id.repsEditText);

                int maxSetNumber = -1;
                ExerciseWorkoutConnection maxSetEwc = new ExerciseWorkoutConnection();

                for (ExerciseWorkoutConnection exerciseWorkout : exerciseWorkoutConnectionList) {
                    if (exerciseWorkout.getExerciseId() == exerciseWithOrder.exercise.getExerciseId() && exerciseWorkout.getDuplicateExerciseOrder() == exerciseWithOrder.getDuplicateExerciseOrder()) {
                        if (exerciseWorkout.getSet() > maxSetNumber) {
                            maxSetNumber = exerciseWorkout.getSet();
                            maxSetEwc = exerciseWorkout;
                        }
                    }
                }

                if (maxSetEwc != null) {
                    int nextSetNumber = maxSetNumber + 1;
                    maxSetEwc.setSet(nextSetNumber);
                    maxSetEwc.setDuplicateExerciseOrder(exerciseWithOrder.getDuplicateExerciseOrder());
                    MySQLiteHelper dbHelper = new MySQLiteHelper(context);
                    dbHelper.addExerciseWorkout(maxSetEwc);

                    // chatgpt reccomendation to solve new set disappearing when scrolling away until i back out and go into the exerciseView again
                    exerciseWorkoutConnectionList.add(maxSetEwc);
                    int clickCurrentPosition = holder.getAdapterPosition();
                    if(clickCurrentPosition == -1){
                        Toast.makeText(context, "int currentPosition = -1 in ExerciseAdapter.onBindViewHolder! Item no longer exists in the data set.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    notifyItemChanged(clickCurrentPosition);
                }

                weightEditText.setText(String.valueOf(maxSetEwc.getWeight()));
                repsEditText.setText(String.valueOf(maxSetEwc.getReps()));

                addTextWatcherToWeighEditText(weightEditText, maxSetEwc);
                addTextWatcherToRepsEditText(repsEditText, maxSetEwc);
                holder.setContainer.addView(setLayout);

            }
        });
        holder.removeExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition == -1) {
                    Toast.makeText(context, "Item no longer exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                MySQLiteHelper dbHelper = new MySQLiteHelper(context);

                List<ExerciseWorkoutConnection> itemsToRemove = new ArrayList<>();
                for (ExerciseWorkoutConnection ewc: matchingEwcList) {
                    dbHelper.deleteExerciseWorkout(ewc);
                    itemsToRemove.add(ewc);
                }

                exerciseWorkoutConnectionList.removeAll(itemsToRemove);
                if (currentPosition >= 0 && currentPosition < exerciseWithOrderList.size()) {
                    exerciseWithOrderList.remove(currentPosition);
                    Toast.makeText(context, "removed currentPosition: "+currentPosition +" from exerciseWithOrderList", Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(currentPosition);
                }
                else {
                    Toast.makeText(context, "Invalid index", Toast.LENGTH_SHORT).show();
                }

                // Update UI
                notifyItemRemoved(currentPosition);
                notifyItemRangeChanged(currentPosition, exerciseWithOrderList.size());
            }
        });

    }

    @Override
    public int getItemCount() {   ///getItemCount
    return exerciseWithOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseNameTextView;
        Button addSetButton;
        TextView removeExerciseButton;
//        Button finishWorkoutButton;
        LinearLayout setContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            addSetButton = itemView.findViewById(R.id.addSetButton);
            setContainer = itemView.findViewById(R.id.setContainer);
            removeExerciseButton = itemView.findViewById(R.id.deleteExerciseTextViewButton);
//            finishWorkoutButton = itemView.findViewById((R.id.finishWorkoutButton));
        }
    }

    //new test
    public void updateData(List<ExerciseWorkoutConnection> newExerciseWorkoutConnectionList) {
        this.exerciseWorkoutConnectionList.clear();
        notifyDataSetChanged();
        this.exerciseWorkoutConnectionList.addAll(newExerciseWorkoutConnectionList);
        this.exerciseWithOrderList = SortExerciseWorkouts(this.exerciseWorkoutConnectionList);
    }

    public List<ExerciseWithOrder> SortExerciseWorkouts(List<ExerciseWorkoutConnection> exerciseWorkoutConnectionList){
        addedExercisesSet.clear(); //chatgpt recommendation 2 to add this here
        List<ExerciseWithOrder> exerciseWithOrderList = new ArrayList<>();

        for (ExerciseWorkoutConnection ewc : exerciseWorkoutConnectionList) {
            Exercise exercise = dbHelper.getExerciseWithExerciseIdNoIsActive(ewc.getExerciseId());

            //if ewc is not a duplicate &&
            if(ewc.getDuplicateExerciseOrder() == 0 && !addedExercisesSet.contains(exercise.getExerciseId()+"_0")){
                exerciseWithOrderList.add(new ExerciseWithOrder(exercise, 0));
                addedExercisesSet.add(exercise.getExerciseId()+"_0");
            }

            if(ewc.getDuplicateExerciseOrder() != 0 && !addedExercisesSet.contains(exercise.getExerciseId()+"_"+ewc.getDuplicateExerciseOrder())){
                exerciseWithOrderList.add(new ExerciseWithOrder(exercise, ewc.getDuplicateExerciseOrder()));
                addedExercisesSet.add(exercise.getExerciseId()+"_" + ewc.getDuplicateExerciseOrder());
            }
        }
        return exerciseWithOrderList;
    }

    public void addTextWatcherToWeighEditText(EditText editWeightText, ExerciseWorkoutConnection ewc){
        editWeightText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                if(!input.isEmpty()) {
                    try {
                        int newWeight = Integer.parseInt(input);
                        ewc.setWeight(newWeight);
                        boolean isPr = dbHelper.determineIfPersonalBestTextWatcher(ewc);
                        if(isPr == true){
                            trophyImageView.setVisibility(View.VISIBLE);
                        }
                        else{
                            trophyImageView.setVisibility(View.GONE);
                        }
                        dbHelper.editExerciseWorkoutWeightValue(ewc);
                        Toast.makeText(context, "Success in editing weight value for set", Toast.LENGTH_LONG).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "error during TextWatcher:afterTextChanged for weightEditText.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void addTextWatcherToRepsEditText(EditText editRepsText, ExerciseWorkoutConnection ewc){
        editRepsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                if(!input.isEmpty()){
                    try{
                        int newReps = Integer.parseInt(editable.toString());
                        ewc.setReps(newReps);
                        dbHelper.editExerciseWorkoutRepsValue(ewc);
                        Toast.makeText(context, "Success in editing reps value for set.", Toast.LENGTH_LONG).show();
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(context, "error during TextWatcher:afterTextChanged for repsEditText.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
