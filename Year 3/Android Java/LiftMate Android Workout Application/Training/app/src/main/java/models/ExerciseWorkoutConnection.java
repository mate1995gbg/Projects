package models;

import java.io.Serializable;

public class ExerciseWorkoutConnection implements Serializable {
    private long exerciseWorkoutId;
    private long exerciseId;
    private long workoutId;
    private int exerciseSet;
    private int reps;
    private int weight;
    private String note;
    public int exerciseOrder;
//    public int SetOrder;
    public int DuplicateExerciseOrder;

    public ExerciseWorkoutConnection() {

    }

    public ExerciseWorkoutConnection(long exerciseId, long workoutId, int set, int reps, int weight, String note) {
        this.exerciseId = exerciseId;
        this.workoutId = workoutId;
        this.exerciseSet = set;
        this.reps = reps;
        this.weight = weight;
        this.note = note;
    }

    public ExerciseWorkoutConnection(long exerciseId, long workoutId, int set, int reps, int weight) {
        this.exerciseId = exerciseId;
        this.workoutId = workoutId;
        this.exerciseSet = set;
        this.reps = reps;
        this.weight = weight;
        this.note = null;
    }

    // Getter and Setter methods for exerciseId
    public long getExerciseWorkoutId() { return exerciseWorkoutId; }
    public void setExerciseWorkoutId(long exerciseWorkoutId) { this.exerciseWorkoutId = exerciseWorkoutId; }

    public long getExerciseId() { return exerciseId; }
    public void setExerciseId(long exerciseId) { this.exerciseId = exerciseId; }

    // Getter and Setter methods for workoutId
    public long getWorkoutId() { return workoutId; }
    public void setWorkoutId(long workoutId) { this.workoutId = workoutId; }

    // Getter and Setter methods for set
    public int getSet() { return exerciseSet; }
    public void setSet(int set) { this.exerciseSet = set; }

    // Getter and Setter methods for reps
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    // Getter and Setter methods for note
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public int getExerciseOrder() { return exerciseOrder; }
    public void setExerciseOrder(int exerciseOrder) { this.exerciseOrder = exerciseOrder; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

//    public int getSetOrder() { return SetOrder; }
//    public void setSetOrder(int SetOrder) { this.SetOrder = SetOrder; }

    public int getDuplicateExerciseOrder() { return DuplicateExerciseOrder; }
    public void setDuplicateExerciseOrder(int DuplicateExerciseOrder) { this.DuplicateExerciseOrder = DuplicateExerciseOrder; }
}
