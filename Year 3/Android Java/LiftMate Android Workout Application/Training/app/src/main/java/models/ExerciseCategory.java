package models;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseCategory implements Parcelable {
    private long categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryDescription2;
    private int isActive;

//constructors

    public ExerciseCategory(){ }

    public ExerciseCategory(String categoryName){
        this.categoryName = categoryName;
    }


    //get-setters below

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryDescription2() {
        return categoryDescription2;
    }

    public void setCategoryDescription2(String categoryDescription2) {
        this.categoryDescription2 = categoryDescription2;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String toString() { //to get the spinners to display "Shoulders" instead of "com.example.training.ExerciseCategoryClass@...". overrides normal toString() when its an ExerciseCategoryClass object.
        return categoryName;
    }

    //interface stuff!!!
    //parcelable implementation
    protected ExerciseCategory(Parcel in) {
        categoryId = in.readLong();
        categoryName = in.readString();
        categoryDescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { //This method is responsible for writing the object's data to a Parcel object. The Parcel is like a container that holds the serialized data.
        dest.writeLong(categoryId);
        dest.writeString(categoryName);
        dest.writeString(categoryDescription);
    }

    @Override
    public int describeContents() { // This method typically returns 0 or Parcelable.CONTENTS_FILE_DESCRIPTOR. It's used to provide additional information about the object's contents.
        return 0;
    }

    public static final Creator<ExerciseCategory> CREATOR = new Creator<ExerciseCategory>() { //A CREATOR field: This field is a constant Parcelable.Creator that's used to create instances of your class from a Parcel. You'll define this as a static field within your class.
        @Override
        public ExerciseCategory createFromParcel(Parcel in) {
            return new ExerciseCategory(in);
        }

        @Override
        public ExerciseCategory[] newArray(int size) {
            return new ExerciseCategory[size];
        }
    };

}
