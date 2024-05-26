package models;

public class CalorieTrackerObject {
    private int calorieId;
    private String date;
    private double caloriesBurned;
    private int profileId;

    public CalorieTrackerObject(){}

    public CalorieTrackerObject(int calorieId, String date, double caloriesBurned, int profileId){
        this.calorieId = calorieId;
        this.date = date;
        this.caloriesBurned = caloriesBurned;
        this.profileId = profileId;
    }

    public int getCalorieId() {
        return calorieId;
    }

    public void setCalorieId(int calorieId) {
        this.calorieId = calorieId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
