package models;


public class Exercise {
    private long exerciseId;
    private long categoryId;
    private String exerciseDescription;
    private int isActive;

    public Exercise(){}

    public Exercise(String exerciseDescription, long categoryId ){
        this.exerciseDescription = exerciseDescription;
        this.categoryId = categoryId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategory(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String toString() { //to get the spinners to display "Shoulders" instead of "com.example.training.ExerciseCategoryClass@...". overrides normal toString() when its an ExerciseCategoryClass object.
        return exerciseDescription;
    }
}
