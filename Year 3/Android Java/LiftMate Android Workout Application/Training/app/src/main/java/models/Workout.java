package models;

import java.sql.Date;
import java.sql.Time;

public class Workout {
    private long workoutId;
    private String startTime;
    private String endTime;
    private int exercisesAmount;
    private double tonnesLifted;
    private double caloriesBurned;
    private int profileId;


    public Workout(){
    }
    //constructor for starting workout. requires startTime and Profile.
    public Workout(String startTime, int profileId){
        this.startTime = startTime;
        this.profileId = profileId;
    }

    //spare constructor for complete workout.
    public Workout(String startTime, String endTime, int exercisesAmount, double tonnesLifted, int profile, double caloriesBurned) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.exercisesAmount = exercisesAmount;
        this.tonnesLifted = tonnesLifted;
        this.profileId = profileId;
        this.caloriesBurned = caloriesBurned;
    }

    public Workout(String startTime, String endTime, int exercisesAmount, double tonnesLifted, double caloriesBurned) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.exercisesAmount = exercisesAmount;
        this.tonnesLifted = tonnesLifted;
        this.caloriesBurned = caloriesBurned;
    }


    //set and get methods.
    public void setId(long setId){
        this.workoutId = setId;
    }
    public long getId(){
        return this.workoutId;
    }

    public void setStartTime(String setStartTime) {
        this.startTime = setStartTime;
    }
    public String getStartTime() {
        return this.startTime;
    }

    public void setEndTime(String setEndTime) {
        this.endTime = setEndTime;
    }
    public String getEndTime() {
        return this.endTime;
    }

    public void setExercisesAmount(int setExercisesAmount) { this.exercisesAmount = setExercisesAmount; }
    public int getExercisesAmount() {
        return this.exercisesAmount;
    }

    public void setTonnesLifted(double setTonnesLifted) { this.tonnesLifted = setTonnesLifted; }
    public double getTonnesLifted() {
        return this.tonnesLifted;
    }

    public void setProfileId(int profileId) { this.profileId = profileId; }
    public double getProfileId() {
        return this.profileId;
    }

    public double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(double caloriesBurned) { this.caloriesBurned = caloriesBurned; }
}

