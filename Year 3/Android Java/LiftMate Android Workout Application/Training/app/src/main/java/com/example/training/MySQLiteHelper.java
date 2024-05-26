package com.example.training;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import models.Exercise;
import models.ExerciseCategory;
import models.ExerciseWorkoutConnection;
import models.Profile;
import models.Workout;


public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 18;
    private static MySQLiteHelper sqLiteManager;
    private static final String TAG = "MySQLiteHelper"; // Set the TAG here

    public MySQLiteHelper(Context context) {
        //Ange databasens namn i konstruktorn
        super(context, "TrainingDB", null, DATABASE_VERSION);
    }

    public static MySQLiteHelper instanceOfDatabase(Context context){
        if(sqLiteManager == null)
            sqLiteManager = new MySQLiteHelper(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL för att skapa profiltabell i databasen
        String CREATE_PROFILE_TABLE = "CREATE TABLE Profile ( " +
                "ProfileId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Username TEXT NOT NULL," +
                "Password TEXT NOT NULL," +
                "Name TEXT NOT NULL," +
                "ProfileImageUrl TEXT," +
                "CalorieTrackerActivated INTEGER DEFAULT 0," +
                "IsSelected INTEGER DEFAULT 0)";

        Log.d(TAG, "onCreate: finished profile table string.");

        String CREATE_WORKOUT_TABLE = "CREATE TABLE Workout ( " +
                "WorkoutId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "StartTime DATETIME," +
                "EndTime DATETIME," +
                "ExercisesAmount INTEGER," +
                "TonnesLifted DOUBLE," +
                "CaloriesBurned DOUBLE," +
                "ProfileId INTEGER NOT NULL," + //Define the foreign key column as NOT NULL
                "CONSTRAINT fk_profile " +
                "FOREIGN KEY (ProfileId) " +
                "REFERENCES Profile(ProfileId))";

        Log.d(TAG, "onCreate: finished workout table string.");

        String CREATE_EXERCISE_CATEGORY_TABLE = "CREATE TABLE ExerciseCategory ( " +
                "CategoryId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CategoryName TEXT NOT NULL," +
                "IsActive INTEGER DEFAULT 0," +
                "CategoryDescription TEXT, " +
                "CategoryDescription2 TEXT)";

        Log.d(TAG, "onCreate: finished exercise category string.");

        String CREATE_EXERCISE_TABLE = "CREATE TABLE Exercise ( " +
                "ExerciseId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CategoryId INTEGER NOT NULL, " +
                "ExerciseDescription TEXT, " +
                "IsActive INTEGER DEFAULT 0," + //IsActive is needed because you can't really remove old exercises & categories since you need them to properly show old workouts. If IsActive is 0 then it is deactivated and the app knows not to load the exercises with IsActive = 0 into the exercise spinners or category spinners
                "FOREIGN KEY (CategoryId) REFERENCES ExerciseCategory(CategoryId))";

        Log.d(TAG, "onCreate: finished exercise string.");

        String CREATE_EXERCISE_WORKOUT_CONNECTION_TABLE = "CREATE TABLE ExerciseWorkoutConnection ( " +
                "ExerciseWorkoutId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ExerciseId INTEGER NOT NULL," +
                "WorkoutId INTEGER NOT NULL," +
                "SetNumber INTEGER NOT NULL," +
                "Reps INTEGER NOT NULL," +
                "Weight INTEGER NOT NULL," +
                "Note TEXT," +
                "ExerciseOrder INTEGER NOT NULL," +
//                "SetOrder INTEGER NOT NULL," +
                "DuplicateExerciseOrder INTEGER NOT NULL," + //IF THIS NUMBER IS ABOVE 0 THEN A NEW EXERCISE ITEM WITH THE SAME EXERCISE ID SHOULD BE DISPLAYED IN RECYCLERVIEW (IF YOU WANT TO HAVE THE SAME EXERCISE AGAIN LATER IN A WORKOUT)
                "FOREIGN KEY (ExerciseId) REFERENCES Exercise(ExerciseId)," +
                "FOREIGN KEY (WorkoutId) REFERENCES Workout(WorkoutId))";

        String CREATE_CALORIE_TRACKER_TABLE = "CREATE TABLE CalorieTracker ( " +
                "CalorieId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Date TEXT NOT NULL, " +
                "CaloriesBurned DOUBLE NOT NULL, " +
                "ProfileId INTEGER NOT NULL, " +
                "FOREIGN KEY (ProfileId) REFERENCES Profile(ProfileId))";

        // Anropa databasen och skapa tabellen
        db.execSQL(CREATE_PROFILE_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
        db.execSQL(CREATE_EXERCISE_CATEGORY_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_EXERCISE_WORKOUT_CONNECTION_TABLE);
        db.execSQL(CREATE_CALORIE_TRACKER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS ExerciseWorkoutConnection");
        db.execSQL("DROP TABLE IF EXISTS Exercise");
        db.execSQL("DROP TABLE IF EXISTS ExerciseCategory");
        db.execSQL("DROP TABLE IF EXISTS Workout");
        db.execSQL("DROP TABLE IF EXISTS Profile");
        db.execSQL("DROP TABLE IF EXISTS CalorieTracker");
        onCreate(db);
        if (oldVersion < 20){
//            db.execSQL("ALTER TABLE Profile ADD COLUMN IsSelected INTEGER DEFAULT 0;");
            Log.d(TAG, "alter  table executed.");
        }
    }

    public List<Workout> getAllWorkoutsByProfileId(int profileId) {
        List<Workout> workouts = new LinkedList<Workout>();
        String query = "SELECT * FROM Workout WHERE ProfileId = ? ORDER BY StartTime ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = {String.valueOf(profileId)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        Workout workout = null;
        if (cursor.moveToFirst()) {
            do {
                //Skapa ett nytt objekt från klasen Note
                workout = new Workout();
                //Första kolumnen från databasen är id
                workout.setId(Integer.parseInt(cursor.getString(0)));
                //Andra kolumnen från databasen är NoteText
                workout.setStartTime(cursor.getString(1));
                workout.setEndTime(cursor.getString(2));
                workout.setExercisesAmount(cursor.getInt(3));
                workout.setTonnesLifted(cursor.getDouble(4));
                workout.setCaloriesBurned(cursor.getDouble(5));

                //Lägg till det nya objektet til listan med objekt
                workouts.add(workout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        //Returnera listan med Notes objekt
        return workouts;
    }
    public List<Workout> getAllFinishedWorkoutsByProfileId(int profileId) {
        List<Workout> workouts = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Workout WHERE ProfileId = ? AND EndTime IS NOT NULL ORDER BY StartTime ASC";

        String[] selectionArgs = {String.valueOf(profileId)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Workout workout = new Workout();
                    workout.setId(Integer.parseInt(cursor.getString(0)));
                    workout.setStartTime(cursor.getString(1));
                    workout.setEndTime(cursor.getString(2));
                    workout.setExercisesAmount(cursor.getInt(3));
                    workout.setTonnesLifted(cursor.getDouble(4));
                    workout.setCaloriesBurned(cursor.getDouble(5));
                    workouts.add(workout);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }
        return workouts;
    }

    public List<ExerciseCategory> getAllExerciseCategories() {
        List<ExerciseCategory> exerciseCategories = new LinkedList<ExerciseCategory>();

        String query = "SELECT * FROM ExerciseCategory WHERE IsActive IS 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ExerciseCategory exerciseCategory = null;
        if (cursor.moveToFirst()) {
            do {
                exerciseCategory = new ExerciseCategory();
                exerciseCategory.setCategoryId(Long.parseLong(cursor.getString(0)));
                exerciseCategory.setCategoryName(cursor.getString(1));
                exerciseCategory.setIsActive(cursor.getInt(4));
                exerciseCategories.add(exerciseCategory);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return exerciseCategories;

    }

    public boolean deactivateExerciseCategory(int categoryId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IsActive", 0);
        long result = db.update("ExerciseCategory", values, "categoryId = ?", new String[]{String.valueOf(categoryId)});
        db.close();
        return result != -1;
    }

    public boolean deactivateExercise(int exerciseId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IsActive", 0);
        long result = db.update("Exercise", values, "exerciseId = ?", new String[]{String.valueOf(exerciseId)});
        db.close();
        return result != -1;
    }

    public List<Exercise> getAllExercisesForSpecificCategory(int categoryId){
        List<Exercise> exercises = new LinkedList<Exercise>();

        String query = "SELECT * FROM Exercise WHERE IsActive IS 1 AND CategoryId = " +categoryId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Exercise exercise = null;
        if (cursor.moveToFirst()) {
            do {
                long exerciseId = cursor.getLong(cursor.getColumnIndexOrThrow("ExerciseId"));
                String exerciseDescription = cursor.getString(cursor.getColumnIndexOrThrow("ExerciseDescription"));
                int isActive = cursor.getInt(cursor.getColumnIndexOrThrow("IsActive"));
                exercise = new Exercise();
                exercise.setExerciseId(exerciseId);
                exercise.setExerciseDescription(exerciseDescription);
                exercise.setIsActive(isActive);
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return exercises;
    }

    public boolean startWorkout(int profileId){
        Workout workout = new Workout();
        workout.setStartTime(DateTimeUtil.getCurrentDateTime());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("StartTime", workout.getStartTime());
        values.put("ProfileId", profileId);

        long result = db.insert("Workout", // table
                null,
                values);

        db.close();
        return result != -1; // Return true if insertion was successful, false otherwise
    }

    public boolean hasOngoingWorkout(int profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "ProfileId = ? AND EndTime IS NULL";
        String[] selectionArgs = {String.valueOf(profileId)};
        Cursor cursor = db.query("Workout", new String[]{"WorkoutId"},
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean hasOngoingWorkout = cursor.moveToFirst();
        Log.d(TAG, "Cursor count: " + cursor.getCount());
        cursor.close();
        db.close();
        return hasOngoingWorkout;
    }

    public Workout getOngoingWorkoutByProfileId(int profileId) {
        Workout workout = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Workout WHERE EndTime IS NULL AND ProfileId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(profileId)});

        if (cursor.moveToFirst()) {
            long workoutId = cursor.getLong(cursor.getColumnIndexOrThrow("WorkoutId"));
            String startTime = cursor.getString(cursor.getColumnIndexOrThrow("StartTime"));
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow("EndTime"));
            int exercisesAmount = cursor.getInt(cursor.getColumnIndexOrThrow("ExercisesAmount"));
            double tonnesLifted = cursor.getDouble(cursor.getColumnIndexOrThrow("TonnesLifted"));
            double caloriesBurned = cursor.getDouble(cursor.getColumnIndexOrThrow("CaloriesBurned"));
            workout = new Workout();
            workout.setId(workoutId);
            workout.setStartTime(startTime);
            workout.setEndTime(endTime);
            workout.setExercisesAmount(exercisesAmount);
            workout.setTonnesLifted(tonnesLifted);
            workout.setProfileId(profileId);
            workout.setCaloriesBurned(caloriesBurned);
        }

        cursor.close();
        db.close();
        return workout;
    }

    public Workout getOngoingWorkoutByWorkoutId(int workoutId) {
        Workout workout = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Workout WHERE EndTime IS NULL AND WorkoutId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(workoutId)});

        if (cursor.moveToFirst()) {
            long workoutId2 = cursor.getLong(cursor.getColumnIndexOrThrow("WorkoutId"));
            String startTime = cursor.getString(cursor.getColumnIndexOrThrow("StartTime"));
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow("EndTime"));
            int exercisesAmount = cursor.getInt(cursor.getColumnIndexOrThrow("ExercisesAmount"));
            double tonnesLifted = cursor.getDouble(cursor.getColumnIndexOrThrow("TonnesLifted"));
            int profileId = cursor.getInt(cursor.getColumnIndexOrThrow("ProfileId"));
            double caloriesBurned = cursor.getDouble(cursor.getColumnIndexOrThrow("CaloriesBurned"));
            workout = new Workout();
            workout.setId(workoutId2);
            workout.setStartTime(startTime);
            workout.setEndTime(endTime);
            workout.setExercisesAmount(exercisesAmount);
            workout.setTonnesLifted(tonnesLifted);
            workout.setProfileId(profileId);
            workout.setCaloriesBurned(caloriesBurned);
        }

        cursor.close();
        db.close();
        return workout;
    }

    public boolean finishWorkout(){
        //get selectedProfile
        Profile profile = this.getSelectedProfile();
        //get workout object with profileId
        Workout currentWorkout = this.getOngoingWorkoutByProfileId((int) profile.getProfileId());
        List<ExerciseWorkoutConnection> ewcList = this.getAllExerciseWorkoutConnectionsForWorkout(currentWorkout.getId());
        //check if profile or currentworkout is null
        if(profile == null || currentWorkout == null) {
            // Log an error or show a user message
            return false;
        }
        if(ewcList.size() != 0){
            //set end time to workout
            String endTime = DateTimeUtil.getCurrentDateTime();
            Log.d(TAG,"(MySQLiteHelper.finishWorkout) endTime variable value: " + endTime);
            currentWorkout.setEndTime(endTime);
            Log.d(TAG,"(MySQLiteHelper.finishWorkout) set currentWorkout.setEndTime to: " + currentWorkout.getEndTime());
            //get all Ewc objects via workoutId
            List<ExerciseWorkoutConnection> ewcForThisWorkoutList = new ArrayList<>();
            ewcForThisWorkoutList = this.getAllExerciseWorkoutConnectionsForWorkout(currentWorkout.getId());
            double totalWeightLifted = 0;
            double totalWeightLiftedInTonnes = 0;
            int totalExercises = 0;

            Set<String> addedExercisesSet = new HashSet<>();

            for (ExerciseWorkoutConnection ewc: ewcForThisWorkoutList) {
                //calculate total weight
                totalWeightLifted += ewc.getWeight();
                totalWeightLiftedInTonnes = totalWeightLifted / 1000;

                //calculate total exercises
                if(ewc.getDuplicateExerciseOrder() == 0 && !addedExercisesSet.contains(ewc.getExerciseId()+"_0")){
                    addedExercisesSet.add(ewc.getExerciseId()+"_0");
                }
                if(ewc.getDuplicateExerciseOrder() != 0 && !addedExercisesSet.contains(ewc.getExerciseId()+"_"+ewc.getDuplicateExerciseOrder())){
                    addedExercisesSet.add(ewc.getExerciseId()+"_" + ewc.getDuplicateExerciseOrder());
                }
            }
            totalExercises = addedExercisesSet.size();

            currentWorkout.setExercisesAmount(totalExercises);
            currentWorkout.setTonnesLifted(totalWeightLiftedInTonnes);

            //calculate calories burned
            currentWorkout.setCaloriesBurned(returnTotalCaloriesBurnedDuringWorkout(profile.getProfileId(), currentWorkout));

            Log.d(TAG,"(MySQLiteHelper.finishWorkout) ProfileId: " + profile.getProfileId());
            Log.d(TAG,"(MySQLiteHelper.finishWorkout) WorkoutId: " + currentWorkout.getId());

            //update workout object in database where EndTime is null & profileId matches the profileid input parameter of this method
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("EndTime", currentWorkout.getEndTime());
            values.put("TonnesLifted", (double) currentWorkout.getTonnesLifted());
            values.put("ExercisesAmount", (int) currentWorkout.getExercisesAmount());
            values.put("CaloriesBurned", currentWorkout.getCaloriesBurned());
            long result = db.update("Workout", values, "ProfileId = ? AND EndTime IS NULL", new String[]{String.valueOf(profile.getProfileId())});
            db.close();
            Log.d(TAG,"(MySQLiteHelper.finishWorkout) result: " + result);
            return result != -1;
        }
        else{
            return false;
        }
    }

    public boolean addExerciseCategory(ExerciseCategory exerciseCategory){
        //set exerciseCategory to active
        exerciseCategory.setIsActive(1);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("CategoryName", exerciseCategory.getCategoryName());
        values.put("IsActive", exerciseCategory.getIsActive());
        long result = db.insert("ExerciseCategory", null, values);
        db.close();
        return result != -1; // Return true if insertion was successful, false otherwise
    }

    public boolean addExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("CategoryId", exercise.getCategoryId()); //possible to choose specific value from a category!
        values.put("ExerciseDescription", exercise.getExerciseDescription());
        values.put("IsActive", 1);

        long result = db.insert("Exercise", null, values);
        db.close();
        return result != -1; // Return true if insertion was successful, false otherwise

    }

    public boolean doesExerciseExistInWorkout(int exerciseId, int workoutId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, "exerciseId: " + exerciseId);
        Log.d(TAG, "workoutId: " + workoutId);

        boolean exerciseExists;
        String query = "SELECT * FROM ExerciseWorkoutConnection " +
                "WHERE ExerciseId = ? AND WorkoutId = ?";

        String[] selectionArgs = new String[]{String.valueOf(exerciseId), String.valueOf(workoutId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        Log.d(TAG, "Query: " + query);
        if(cursor.moveToFirst()) {
            exerciseExists = true;
        }
        else{
            exerciseExists = false;
        }
        Log.d(TAG, "(DEBUG) (dbHelper.doesExerciseExistInWorkout) boolean exerciseExists: " + exerciseExists);
        cursor.close();
        db.close();
        return exerciseExists;
    }

    public int returnMaxPlusOneOrZeroExerciseOrder(int workoutId){
        int exerciseOrderNumber = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(ExerciseOrder) FROM ExerciseWorkoutConnection " +
                "WHERE WorkoutId = " + workoutId;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            exerciseOrderNumber = cursor.getInt(0); // Get the maximum ExerciseOrder value
            cursor.close();
            db.close();
            return exerciseOrderNumber + 1;
        }
        else{
            exerciseOrderNumber = 0;
            cursor.close();
            db.close();
            return exerciseOrderNumber;
        }
    }

    public int returnMaxPlusOneDuplicateExerciseNumber(int exerciseId, int workoutId){
        int exerciseOrderNumber = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(DuplicateExerciseOrder) FROM ExerciseWorkoutConnection " +
                "WHERE ExerciseId = " + exerciseId + " AND WorkoutId = " + workoutId;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            exerciseOrderNumber = cursor.getInt(0); // Get the maximum ExerciseOrder value
        }
        cursor.close();
        db.close();
        return exerciseOrderNumber + 1;
    }

    public void addExerciseWorkout(ExerciseWorkoutConnection exerciseWorkoutConnection){
        Log.d(TAG, "(addExerciseWorkout) the income exerciseWorkoutConnection object has exerciseOrder: " + exerciseWorkoutConnection.getExerciseOrder());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ExerciseId", exerciseWorkoutConnection.getExerciseId());
        values.put("WorkoutId", exerciseWorkoutConnection.getWorkoutId());
        values.put("SetNumber", exerciseWorkoutConnection.getSet());
        values.put("Reps", exerciseWorkoutConnection.getReps());
        values.put("Weight", exerciseWorkoutConnection.getWeight());
        values.put("ExerciseOrder", exerciseWorkoutConnection.getExerciseOrder());
        values.put("DuplicateExerciseOrder", exerciseWorkoutConnection.getDuplicateExerciseOrder());

        db.insert("ExerciseWorkoutConnection", null, values);
        db.close();
    }

    public void deleteExerciseWorkout(ExerciseWorkoutConnection exerciseWorkoutConnection){
        SQLiteDatabase db = this.getWritableDatabase();

        //below is unsafe, vulnerable for SQL Injections
//        String query = "DELETE FROM ExerciseWorkoutConnection WHERE ExerciseWorkoutId = " + exerciseWorkoutConnection.getExerciseWorkoutId();
//        db.execSQL(query);

        //below is a better way!
        db.delete("ExerciseWorkoutConnection", "ExerciseWorkoutId = ?", new String[] {String.valueOf(exerciseWorkoutConnection.getExerciseWorkoutId()) });
        db.close();
    }

    ///////////////////////////////PROFILE METHODS START///////////////////////////////////////////////

    public Profile createNewProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", profile.getUsername());
        values.put("Password", profile.getPassword());
        values.put("Name", profile.getName());

        long result = db.insert("Profile", null, values);
        db.close();

        if (result == -1) {
            return null; // Return null if insertion failed
        } else {
            profile.setProfileId((int) result); // Update the profile object with the auto-generated ID
            return profile; // Return the updated profile object
        }
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new LinkedList<Profile>();

        String query = "SELECT * FROM Profile";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Profile profile = null;
        if (cursor.moveToFirst()) {
            do {
                //Skapa ett nytt objekt från klasen Note
                profile = new Profile();
                //Första kolumnen fsån databasen är id
                profile.setProfileId(Integer.parseInt(cursor.getString(0)));
                //Andra kolumnen från databasen är NoteText
                profile.setUsername(cursor.getString(1));
                profile.setPassword(cursor.getString(2));
                profile.setName(cursor.getString(3));
                profile.setProfileImageUrl(cursor.getString(4));
                profile.setCalorieTrackerActivated(cursor.getInt(5));
                //Lägg till det nya objektet til listan med objekt
                profiles.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return profiles;
    }

    public void selectProfile(int profileId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IsSelected", 1);
        db.update("Profile", values, "ProfileId = ?", new String[]{String.valueOf(profileId)});
        // set all other profiles IsSelected to 0
        ContentValues otherValues = new ContentValues();
        otherValues.put("IsSelected", 0);
        db.update("Profile", otherValues, "ProfileId != ?", new String[]{String.valueOf(profileId)});
        db.close();
    }

    public Profile getSelectedProfile() {
        Profile selectedProfile = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Profile WHERE IsSelected = 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            selectedProfile = new Profile();
            selectedProfile.setProfileId(cursor.getInt(cursor.getColumnIndexOrThrow("ProfileId")));
            selectedProfile.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("Username")));
            selectedProfile.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("Password")));
            selectedProfile.setName(cursor.getString(cursor.getColumnIndexOrThrow("Name")));
            selectedProfile.setProfileImageUrl(cursor.getString(cursor.getColumnIndexOrThrow("ProfileImageUrl")));
            selectedProfile.setCalorieTrackerActivated(cursor.getInt(cursor.getColumnIndexOrThrow("CalorieTrackerActivated")));
        }

        cursor.close();
        db.close();
        return selectedProfile;
    }

    public int updateCalorieTrackerStatusForProfile(int profileId, boolean activated) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CalorieTrackerActivated", activated ? 1 : 0);
        return db.update("Profile", values, "ProfileId = ?", new String[]{String.valueOf(profileId)});
    }

    ///////////////////////////////PROFILE METHODS END///////////////////////////////////////////////

    //NEED THIS METHOD TO GET SET,REPS, ETC TO FILL RECYCLERVIEW
    public List<ExerciseWorkoutConnection> getAllExerciseWorkoutConnectionsForWorkout(long workoutId) {
        List<ExerciseWorkoutConnection> exerciseWorkoutList = new LinkedList<ExerciseWorkoutConnection>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM ExerciseWorkoutConnection WHERE WorkoutId = " + workoutId;
        Cursor cursor = db.rawQuery(query, null);
        ExerciseWorkoutConnection exerciseWorkout = null;
        if (cursor.moveToFirst()) {
            do {
                long exerciseWorkoutId = cursor.getLong(cursor.getColumnIndexOrThrow("ExerciseWorkoutId"));
                long exerciseId = cursor.getLong(cursor.getColumnIndexOrThrow("ExerciseId"));
                long workoutId2 = cursor.getLong(cursor.getColumnIndexOrThrow("WorkoutId"));
                int exerciseSet = cursor.getInt(cursor.getColumnIndexOrThrow("SetNumber")); //was ExerciseSet before
                int exerciseOrder = cursor.getInt((cursor.getColumnIndexOrThrow("ExerciseOrder")));
                int duplicateExerciseOrder = cursor.getInt((cursor.getColumnIndexOrThrow("DuplicateExerciseOrder")));
                int reps = cursor.getInt(cursor.getColumnIndexOrThrow("Reps"));
                int weight = cursor.getInt(cursor.getColumnIndexOrThrow("Weight"));
                String note = cursor.getString(cursor.getColumnIndexOrThrow("Note"));
                exerciseWorkout = new ExerciseWorkoutConnection();
                exerciseWorkout.setExerciseWorkoutId(exerciseWorkoutId);
                exerciseWorkout.setExerciseId(exerciseId);
                exerciseWorkout.setWorkoutId(workoutId2);
                exerciseWorkout.setSet(exerciseSet);
                exerciseWorkout.setReps(reps);
                exerciseWorkout.setWeight(weight);
                exerciseWorkout.setExerciseOrder(exerciseOrder);
                exerciseWorkout.setDuplicateExerciseOrder(duplicateExerciseOrder);
                exerciseWorkout.setNote(note);
                exerciseWorkoutList.add(exerciseWorkout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return exerciseWorkoutList;
    }

    public List<ExerciseWorkoutConnection> getAllEwcForProfile(long profileId){
        List<Workout> workoutList = this.getAllWorkoutsByProfileId((int)profileId);
        List<ExerciseWorkoutConnection> exerciseWorkoutList = new LinkedList<ExerciseWorkoutConnection>();
        for (Workout workout : workoutList) {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM ExerciseWorkoutConnection WHERE WorkoutId = " + workout.getId();
            Cursor cursor = db.rawQuery(query, null);
            ExerciseWorkoutConnection exerciseWorkout = null;
            if (cursor.moveToFirst()) {
                do {
                    long exerciseWorkoutId = cursor.getLong(cursor.getColumnIndexOrThrow("ExerciseWorkoutId"));
                    long exerciseId = cursor.getLong(cursor.getColumnIndexOrThrow("ExerciseId"));
                    long workoutId2 = cursor.getLong(cursor.getColumnIndexOrThrow("WorkoutId"));
                    int exerciseSet = cursor.getInt(cursor.getColumnIndexOrThrow("SetNumber")); //was ExerciseSet before
                    int exerciseOrder = cursor.getInt((cursor.getColumnIndexOrThrow("ExerciseOrder")));
                    int duplicateExerciseOrder = cursor.getInt((cursor.getColumnIndexOrThrow("DuplicateExerciseOrder")));
                    int reps = cursor.getInt(cursor.getColumnIndexOrThrow("Reps"));
                    int weight = cursor.getInt(cursor.getColumnIndexOrThrow("Weight"));
                    String note = cursor.getString(cursor.getColumnIndexOrThrow("Note"));
                    exerciseWorkout = new ExerciseWorkoutConnection();
                    exerciseWorkout.setExerciseWorkoutId(exerciseWorkoutId);
                    exerciseWorkout.setExerciseId(exerciseId);
                    exerciseWorkout.setWorkoutId(workoutId2);
                    exerciseWorkout.setSet(exerciseSet);
                    exerciseWorkout.setReps(reps);
                    exerciseWorkout.setWeight(weight);
                    exerciseWorkout.setExerciseOrder(exerciseOrder);
                    exerciseWorkout.setDuplicateExerciseOrder(duplicateExerciseOrder);
                    exerciseWorkout.setNote(note);
                    exerciseWorkoutList.add(exerciseWorkout);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return exerciseWorkoutList;
    }

    public boolean editExerciseWorkoutWeightValue(ExerciseWorkoutConnection incomingEwc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Weight", incomingEwc.getWeight());
        String whereClause = "ExerciseId = ? AND WorkoutId = ? AND SetNumber = ? AND ExerciseOrder = ? AND DuplicateExerciseOrder = ?";
        String[] whereArgs = new String[] {
                String.valueOf(incomingEwc.getExerciseId()),
                String.valueOf(incomingEwc.getWorkoutId()),
                String.valueOf(incomingEwc.getSet()),
                String.valueOf(incomingEwc.getExerciseOrder()),
                String.valueOf(incomingEwc.getDuplicateExerciseOrder())
        };
        long result = db.update("ExerciseWorkoutConnection", values, whereClause, whereArgs);
        db.close();
        return result != -1; // Return true if insertion was successful, false otherwise
    }


    public boolean editExerciseWorkoutRepsValue(ExerciseWorkoutConnection incomingEwc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Reps", incomingEwc.getReps());

        String whereClause = "ExerciseId = ? AND WorkoutId = ? AND SetNumber = ? AND ExerciseOrder = ? AND DuplicateExerciseOrder = ?";
        String[] whereArgs = new String[] {
                String.valueOf(incomingEwc.getExerciseId()),
                String.valueOf(incomingEwc.getWorkoutId()),
                String.valueOf(incomingEwc.getSet()),
                String.valueOf(incomingEwc.getExerciseOrder()),
                String.valueOf(incomingEwc.getDuplicateExerciseOrder())
        };

        long result = db.update("ExerciseWorkoutConnection", values, whereClause, whereArgs);
        db.close();
        return result != -1; // Return true if insertion was successful, false otherwise
    }

    //NEED THIS METHOD TO GET "EXERCISENAME" FOR RECYCLERVIEW ITEMS
    public Exercise getExerciseWithExerciseId(long exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Exercise WHERE IsActive IS 1 AND ExerciseId = " + exerciseId;
        Cursor cursor = db.rawQuery(query, null);
        Exercise exercise = null;
        if (cursor.moveToFirst()) {
                long exerciseId2 = cursor.getInt(cursor.getColumnIndexOrThrow("ExerciseId"));
                long categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("CategoryId"));
                String exerciseDescription = cursor.getString(cursor.getColumnIndexOrThrow("ExerciseDescription"));
                exercise = new Exercise();
                exercise.setExerciseId(exerciseId2);
                exercise.setCategory(categoryId);
                exercise.setExerciseDescription(exerciseDescription);
            }
        cursor.close();
        db.close();
        return exercise;
    }

    public Exercise getExerciseWithExerciseIdNoIsActive(long exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Exercise WHERE ExerciseId = " + exerciseId;
        Cursor cursor = db.rawQuery(query, null);
        Exercise exercise = null;
        if (cursor.moveToFirst()) {
            long exerciseId2 = cursor.getInt(cursor.getColumnIndexOrThrow("ExerciseId"));
            long categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("CategoryId"));
            String exerciseDescription = cursor.getString(cursor.getColumnIndexOrThrow("ExerciseDescription"));
            exercise = new Exercise();
            exercise.setExerciseId(exerciseId2);
            exercise.setCategory(categoryId);
            exercise.setExerciseDescription(exerciseDescription);
        }
        cursor.close();
        db.close();
        return exercise;
    }

    //this returns all exercises, even if isActive=0. good for showing workout histories with old "removed" exercises.
    public Exercise getExerciseWithExerciseIdForHistory(long exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Exercise WHERE ExerciseId = " + exerciseId;
        Cursor cursor = db.rawQuery(query, null);
        Exercise exercise = null;
        if (cursor.moveToFirst()) {
            long exerciseId2 = cursor.getInt(cursor.getColumnIndexOrThrow("ExerciseId"));
            long categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("CategoryId"));
            String exerciseDescription = cursor.getString(cursor.getColumnIndexOrThrow("ExerciseDescription"));

            exercise = new Exercise();
            exercise.setExerciseId(exerciseId2);
            exercise.setCategory(categoryId);
            exercise.setExerciseDescription(exerciseDescription);
        }
        cursor.close();
        db.close();
        return exercise;
    }

    public ExerciseCategory getCategoryByExerciseId(long exerciseId){
        Exercise exercise = this.getExerciseWithExerciseId(exerciseId);

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM ExerciseCategory WHERE IsActive IS 1 AND CategoryId = " + exercise.getCategoryId();

        Cursor cursor = db.rawQuery(query, null);
        ExerciseCategory category = new ExerciseCategory();

        if (cursor.moveToFirst()) {
            category.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow("CategoryId")));
            category.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow("CategoryName")));
            category.setIsActive(cursor.getInt(cursor.getColumnIndexOrThrow("IsActive")));
        }
        cursor.close();
        db.close();
        return category;
    }

    public ExerciseCategory getCategoryByExerciseIdForHistory(long exerciseId){
        Exercise exercise = this.getExerciseWithExerciseIdNoIsActive(exerciseId);

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM ExerciseCategory WHERE CategoryId = " + exercise.getCategoryId();

        Cursor cursor = db.rawQuery(query, null);
        ExerciseCategory category = new ExerciseCategory();

        if (cursor.moveToFirst()) {
            category.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow("CategoryId")));
            category.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow("CategoryName")));
            category.setIsActive(cursor.getInt(cursor.getColumnIndexOrThrow("IsActive")));
        }
        cursor.close();
        db.close();
        return category;
    }


    ////////////////////////////////////////////////////calorietracker methods //////////////////////////////////////////7

    public long addCalorieRecord(String date, double caloriesBurned, int profileId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", date);
        values.put("CaloriesBurned", caloriesBurned);
        values.put("ProfileId", profileId);
        long result = db.insert("CalorieTracker", null, values);
        if (result != -1){
           Log.d(TAG, "addCalorieRecord has been run. result =" + result);
        }
        return result;
    }

    public float returnTotalCaloriesBurnedDuringWorkout(long profileId, Workout workout){
        float totalCalories = 0;

        //calculate total calories burned from lifting weight
        List<ExerciseWorkoutConnection> ewcList = getAllExerciseWorkoutConnectionsForWorkout(workout.getId());
        for (ExerciseWorkoutConnection ewc : ewcList) {
            //Calories Burned= Reps × Weight Lifted (kg) × 0.066 ((very roughly))
            float constant = 0.026f;
            totalCalories += ewc.getReps() * ewc.getWeight() * constant;
        }


        //calculate total calories burned from movement via accelerometer data entries in CaloriesTracked table
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CalorieTracker WHERE ProfileId = ? AND Date BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(profileId), workout.getStartTime(), workout.getEndTime()});

        if (cursor.moveToFirst()) {
            do {
                float caloriesBurned = cursor.getFloat(cursor.getColumnIndexOrThrow("CaloriesBurned"));
                totalCalories += caloriesBurned;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "returnTotalCaloriesBurnedDuringWorkout: " + totalCalories);
        return totalCalories;
    }
    public float returnTotalCaloriesBurnedForDay(int profileId, String dayDate) {
        Log.d(TAG, "returnTotalCaloriesBurnedForDay: dayDate string is: " + dayDate);
        float totalCalories = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        // Trim off the hours and minutes, assuming the incoming format is "yyyy-MM-dd HH:mm"
        String trimmedDayDate = dayDate.split(" ")[0];

        String dayDateStart = trimmedDayDate + " 00:00";
        String dayDateEnd = trimmedDayDate + " 23:59";

        String query = "SELECT * FROM CalorieTracker WHERE ProfileId = ? AND Date BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(profileId), dayDateStart, dayDateEnd});

        if (cursor.moveToFirst()) {
            do {
                float caloriesBurned = cursor.getFloat(cursor.getColumnIndexOrThrow("CaloriesBurned"));
                totalCalories += caloriesBurned;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(TAG, "returnTotalCaloriesBurnedForDay: totalCalories returned: " + totalCalories);
        return totalCalories;
    }

    public Map<String, Float> getTotalCaloriesBurnedForEachDay(int profileId) {
        Map<String, Float> dailyCalories = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to group records by Date and sum CaloriesBurned for each group
        String query = "SELECT substr(Date, 1, 10) as DayDate, SUM(CaloriesBurned) as TotalCalories " +
                "FROM CalorieTracker WHERE ProfileId = ? GROUP BY DayDate";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(profileId)});

        if (cursor.moveToFirst()) {
            do {
                String dayDate = cursor.getString(cursor.getColumnIndexOrThrow("DayDate"));
                float totalCalories = cursor.getFloat(cursor.getColumnIndexOrThrow("TotalCalories"));
                dailyCalories.put(dayDate, totalCalories);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dailyCalories;
    }

    public int updateCalorieRecord(int calorieId, String date, double caloriesBurned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", date);
        values.put("CaloriesBurned", caloriesBurned);
        return db.update("CalorieTracker", values, "CalorieId = ?", new String[]{String.valueOf(calorieId)});
    }

    public void deleteCalorieRecord(int calorieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CalorieTracker", "CalorieId = ?", new String[]{String.valueOf(calorieId)});
    }

public boolean determineIfPersonalBestInLoop(ExerciseWorkoutConnection ewcObject){
    double weightLiftedForIncomingEwcObject = ewcObject.getWeight() * ewcObject.getReps();
    Profile currentProfile = this.getSelectedProfile();
    List<ExerciseWorkoutConnection> allEwcsForProfile = new ArrayList<>();
    LinkedHashMap<Long, Double> mappedMaxEwcWeightToExerciseId = new LinkedHashMap<>();

    // Fetch only historical EWCs, excluding the current ewcObject if it's already in the database
    allEwcsForProfile = this.getAllEwcForProfile(currentProfile.getProfileId());

    // Filter out the current ewcObject if it's already in the database -- this is another way to delete object if found in list
    allEwcsForProfile = allEwcsForProfile.stream()
            .filter(ewc -> ewc.getExerciseWorkoutId() != ewcObject.getExerciseWorkoutId())
            .collect(Collectors.toList());

    //sort all previous Ewcs by max weight lifted and put them into LinkedHashMap
    for (ExerciseWorkoutConnection ewc : allEwcsForProfile) {
        double weightLiftedForEwcObject = ewc.getWeight() * ewc.getReps();
        Log.d(TAG, "determineIfPersonalBest: weightLiftedForEwcObject: " + weightLiftedForEwcObject);
        if (mappedMaxEwcWeightToExerciseId.containsKey(ewc.getExerciseId())){
            double weightLiftedForExercise = mappedMaxEwcWeightToExerciseId.get(ewc.getExerciseId());
            Log.d(TAG, "determineIfPersonalBest: weightLiftedForExercise: " + weightLiftedForExercise);
            if (weightLiftedForEwcObject > weightLiftedForExercise){
                Log.d(TAG, "determineIfPersonalBest: weightLiftedForEwcObject is greater than weightLiftedForExercise");
                mappedMaxEwcWeightToExerciseId.replace(ewc.getExerciseId(), weightLiftedForEwcObject);
            }
        }
        else{
            mappedMaxEwcWeightToExerciseId.put(ewc.getExerciseId(), weightLiftedForEwcObject);
        }
    }

    // Determine if incoming ewcObject is a new PR, excluding itself from comparison
    if (mappedMaxEwcWeightToExerciseId.containsKey(ewcObject.getExerciseId())){
        double weightLiftedForExercise = mappedMaxEwcWeightToExerciseId.get(ewcObject.getExerciseId());
        // The comparison logic remains the same since we have excluded the current ewcObject
        if(weightLiftedForIncomingEwcObject > weightLiftedForExercise){
            return true;
        }
        else{
            return false;
        }
    }
    else {
        // If no history, this is the first record, so it's a PR by default
        return true;
    }
}

    public boolean determineIfPersonalBest(ExerciseWorkoutConnection ewcObject) {
        Log.d(TAG, "determineIfPersonalBest: Income ewcObject weight: "+ ewcObject.getWeight());
        Log.d(TAG, "determineIfPersonalBest: Income ewcobject reps: " + ewcObject.getReps());
        double totalWeightLiftedForIncomingEwc = ewcObject.getWeight() * ewcObject.getReps();

        Profile currentProfile = this.getSelectedProfile();

        List<Workout> allWorkoutsForProfile = this.getAllWorkoutsByProfileId((int) currentProfile.getProfileId());
        Log.d(TAG, "determineIfPersonalBest: allWorkoutsForProfile size: " + allWorkoutsForProfile.size());

        LinkedHashMap<Long, ExerciseWorkoutConnection> mappedMaxEwcWeightToExerciseId = new LinkedHashMap<>();
        List<ExerciseWorkoutConnection> ewcList = new ArrayList<>();

        //get all ewc objects for profile, via workoutId:s
        for (Workout workout : allWorkoutsForProfile) {
            ewcList.addAll(this.getAllExerciseWorkoutConnectionsForWorkout(workout.getId()));
        }

        boolean isPersonalBest = false;
        Log.d(TAG, "determineIfPersonalBest: ewcList size:" +ewcList.size());
        for (ExerciseWorkoutConnection ewc : ewcList) {
            double totalWeightLiftedFromEwcList = ewc.getWeight() * ewc.getReps();
            Log.d(TAG, "determineIfPersonalBest: totalweightlifted from ewc list: " +totalWeightLiftedFromEwcList);
            long exerciseId = ewc.getExerciseId();

            // Check if a record for this exerciseId already exists in the LinkedHashMap
            ExerciseWorkoutConnection maxEwc = mappedMaxEwcWeightToExerciseId.get(exerciseId);

            if (maxEwc == null || (ewcObject.getWeight() * ewcObject.getReps()) > (maxEwc.getWeight() * maxEwc.getReps())) {
                // add ewc object to LinkedHashMap or overwrite old ewc object for exerciseId
                mappedMaxEwcWeightToExerciseId.put(exerciseId, ewcObject);
                isPersonalBest = true;
            }
        }
        Log.d(TAG, "determineIfPersonalBest: new personal best boolean is: " + isPersonalBest);
        return isPersonalBest;
    }
    public boolean determineIfPersonalBestTextWatcher(ExerciseWorkoutConnection ewcObject){ //works for TextWatcher!
        Log.d(TAG, "determineIfPersonalBest: Income ewcObject weight: "+ ewcObject.getWeight());
        Log.d(TAG, "determineIfPersonalBest: Income ewcObject reps: " + ewcObject.getReps());
        Log.d(TAG, "determineIfPersonalBest: Income ewcObject exerciseId: " + ewcObject.getExerciseId());
        double weightLiftedForIncomingEwcObject = ewcObject.getWeight() * ewcObject.getReps();
        Profile currentProfile = this.getSelectedProfile();
        List<ExerciseWorkoutConnection> allEwcsForProfile = new ArrayList<>();
        LinkedHashMap<Long, Double> mappedMaxEwcWeightToExerciseId = new LinkedHashMap<>();

        //get all ExerciseWorkoutConnections for profile
        allEwcsForProfile = this.getAllEwcForProfile(currentProfile.getProfileId());
        Log.d(TAG, "determineIfPersonalBest: allEwcsForProfile count: " + allEwcsForProfile.size());

        //sort all previous Ewcs by max weight lifted and put them into LinkedHashMap
        for (ExerciseWorkoutConnection ewc : allEwcsForProfile) {
            double weightLiftedForEwcObject = ewc.getWeight() * ewc.getReps();
            Log.d(TAG, "determineIfPersonalBest: weightLiftedForEwcObject: " + weightLiftedForEwcObject);
            if (mappedMaxEwcWeightToExerciseId.containsKey(ewc.getExerciseId())){
                double weightLiftedForExercise = mappedMaxEwcWeightToExerciseId.get(ewc.getExerciseId());
                Log.d(TAG, "determineIfPersonalBest: weightLiftedForExercise: " + weightLiftedForExercise);
                if (weightLiftedForEwcObject > weightLiftedForExercise){
                    Log.d(TAG, "determineIfPersonalBest: weightLiftedForEwcObject is greater than weightLiftedForExercise");
                    mappedMaxEwcWeightToExerciseId.replace(ewc.getExerciseId(), weightLiftedForEwcObject);
                }
            }
            else{
                mappedMaxEwcWeightToExerciseId.put(ewc.getExerciseId(), weightLiftedForEwcObject);
            }
        }

        //determine if incoming ewcObject is a new pr.
        if (mappedMaxEwcWeightToExerciseId.containsKey(ewcObject.getExerciseId())){
            Log.d(TAG, "determineIfPersonalBest: LinkedHashMap contains the ExerciseId as key!");
            double weightLiftedForExercise = mappedMaxEwcWeightToExerciseId.get(ewcObject.getExerciseId());
            Log.d(TAG, "determineIfPersonalBest: COMPARE - weightLiftedForExercise: "+weightLiftedForExercise);
            Log.d(TAG, "determineIfPersonalBest: COMPARE - weightLiftedForIncomingEwcObject: "+weightLiftedForIncomingEwcObject);
            if(weightLiftedForIncomingEwcObject > weightLiftedForExercise){
                Log.d(TAG, "determineIfPersonalBest: returned true");
                return true;
            }
            else{
                Log.d(TAG, "determineIfPersonalBest: returned false");
                return false;
            }
        }
        else{
            Log.d(TAG, "determineIfPersonalBest: LinkedHashMap DOES NOT contain the ExerciseId as key!");
            return false; //do not show trophy for first instance of ExerciseWorkoutConnection for Profile
        }
    }
}
