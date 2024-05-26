package models;

public class Profile {
    private long profileId;
    private String username;
    private String password;
    private String name;
    private String profileImageUrl;
    private int calorieTrackerActivated;


    public Profile() {

    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String toString() { //to get the spinners to display "Shoulders" instead of "com.example.training.ExerciseCategoryClass@...". overrides normal toString() when its an ExerciseCategoryClass object.
        return username;
    }

    public int getCalorieTrackerActivated() {
        return calorieTrackerActivated;
    }

    public void setCalorieTrackerActivated(int calorieTrackerActivated) {
        this.calorieTrackerActivated = calorieTrackerActivated;
    }
}
