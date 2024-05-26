package models;

import models.Exercise;

public class ExerciseWithOrder {
    public Exercise exercise;
    private int duplicateExerciseOrder;

    public ExerciseWithOrder(){

    }

    public ExerciseWithOrder(Exercise exercise, int duplicateExerciseOrder){
        this.exercise = exercise;
        this.duplicateExerciseOrder = duplicateExerciseOrder;
    }
    public ExerciseWithOrder(int exerciseId, int duplicateExerciseOrder){
        this.exercise = new Exercise();
        this.exercise.setExerciseId(exerciseId);
        this.duplicateExerciseOrder = duplicateExerciseOrder;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getDuplicateExerciseOrder() {
        return duplicateExerciseOrder;
    }

    public void setDuplicateExerciseOrder(int duplicateExerciseOrder) {
        this.duplicateExerciseOrder = duplicateExerciseOrder;
    }
}
