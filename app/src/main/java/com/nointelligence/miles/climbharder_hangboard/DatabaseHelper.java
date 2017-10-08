package com.nointelligence.miles.climbharder_hangboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";

    private static final int DATABASE_VERSION = 1;

    private static final String T1 = "workouts";
    private static final String T1_C1 = "name";

    private static final String T2_C1 = "activity";
    private static final String T2_C2 = "duration";

    private static final String T3 = "logbook";
    private static final String T3_C1 = "workout";
    private static final String T3_C2 = "date";

    private static final String TEXT = " TEXT";
    private static final String KEY = " _ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
    private static final String UNIQUE = " UNIQUE";
    private static final String END = ");";

    // Indexes for the array returned by selectRoutine
    public static final int ACTIVITIES = 0;
    public static final int DURATIONS = 1;
    public static final int READABLES = 2;

    public DatabaseHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + T1 + " (" + T1_C1 + TEXT + UNIQUE + END);
        database.execSQL("CREATE TABLE " + T3 + " (" + KEY + T3_C1 + TEXT + ", " + T3_C2 + TEXT + END);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + T1);
        database.execSQL("DROP TABLE IF EXISTS " + T3);
        onCreate(database);
    }

    // Inserts a workout_editable_item into the workouts table, if a table
    // by that name doesn't already exist. If it does exist already, return false.
    public boolean insertWorkout(String name) {
        String tableNoSpaces = removeSpaces(name);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"
                + tableNoSpaces + "';)", null);

        if(cursor.getCount() == 0) {
            ContentValues myCV = new ContentValues();

            myCV.put(T1_C1, tableNoSpaces);

            long result = db.insert(T1, null, myCV);

            if (result == -1) {
                return false;
            } else {
                db.execSQL("CREATE TABLE IF NOT EXISTS "
                        + tableNoSpaces + " (" + T2_C1 + TEXT + ", " + T2_C2 + TEXT + END);
                return true;
            }
        } else {
            return false;
        }
    }

    // Returns a String ArrayList of all workout namesList
    public ArrayList<String> selectAllWorkouts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + T1_C1 + " FROM " + T1 + ";", null);
        ArrayList<String> nameList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                nameList.add(replaceUnderscores(cursor.getString(0)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return nameList;
    }

    // Deletes an entire routine table and removes it from workouts list
    public void deleteWorkout(String rowName) {
        String rowNameNoSpaces = removeSpaces(rowName);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(T1, T1_C1 + " = ?", new String[]{rowNameNoSpaces});
        db.execSQL("DROP TABLE IF EXISTS " + rowNameNoSpaces);
    }

    // Adds a list of entriesList into a workout routine, if it exists. If it doesn't,
    // return false.
    public void fillWorkout(String table, String[] names, String[] durations){
        String tableNoSpaces = removeSpaces(table);
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i = 0; i < names.length; i++){
            ContentValues myCV = new ContentValues();
            myCV.put(T2_C1, names[i]);
            myCV.put(T2_C2, durations[i]);
            db.insert(tableNoSpaces, null, myCV);
        }
    }

    // Returns the activities, durations and a human readable string for the workout.
    // If the workout doesn't exist, the lists returned are empty.
    public ArrayList<String>[] selectRoutine(String table){
        String tableNoSpaces = removeSpaces(table);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"
                + tableNoSpaces + "';)", null);
        ArrayList<String> activities = new ArrayList<>();
        ArrayList<String> durations = new ArrayList<>();
        ArrayList<String> readable = new ArrayList<>();

        // If workout exists, populate the return arrays.
        if(cursor.getCount() != 0) {
            cursor = db.rawQuery("SELECT * FROM " + tableNoSpaces + ";", null);
            if (cursor.moveToFirst()) {
                do {
                    activities.add(cursor.getString(ACTIVITIES));
                    durations.add(cursor.getString(DURATIONS));
                    readable.add(cursor.getString(ACTIVITIES) + ": " +
                            cursor.getString(DURATIONS) + "s");
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }

        return new ArrayList[]{activities, durations, readable};
    }

    // Removes spaces and puts underscores instead, since table names cannot
    // have spaces in them.
    private String removeSpaces(String input){
        String space = " ";
        String underscore = "_";
        char[] result = new char[input.length()];
        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == space.charAt(0)){
                result[i] = underscore.charAt(0);
            } else {
                result[i] = input.charAt(i);
            }
        }
        return new String(result);
    }

    // Replaces underscores with spaces again for the text user sees
    private String replaceUnderscores(String input){
        String space = " ";
        String underscore = "_";
        char[] result = new char[input.length()];
        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == underscore.charAt(0)){
                result[i] = space.charAt(0);
            } else {
                result[i] = input.charAt(i);
            }
        }
        return new String(result);
    }

    // Inserts a workout_editable_item into logbook table, which is the workout name
    // and workout date strings. Date string should be pre-formatted
    public boolean insertLogbook(String name, String date){
        String nameNoSpace = removeSpaces(name);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues myCV = new ContentValues();

        myCV.put(T3_C1, nameNoSpace);
        myCV.put(T3_C2, date);

        long result = db.insert(T3, null, myCV);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Returns the entire logbook table
    public ArrayList<String>[] selectLogbook(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + T3 + ";", null);

        ArrayList<String> tableEntires = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                keys.add(cursor.getString(0));
                names.add(replaceUnderscores(cursor.getString(1)));
                tableEntires.add(cursor.getString(2) + ":\n " +replaceUnderscores(cursor.getString(1)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return new ArrayList[] {tableEntires, keys, names};
    }

    // Deletes an entry in the logbook table based on its id
    public void deleteLogbookEntry(String key){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(T3, "_ID=?", new String[] { key });
    }
}
