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
    private static final String INT = " INTEGER";
    private static final String UNIQUE = " UNIQUE";
    private static final String END = ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + T1 + " (" + T1_C1 + TEXT + UNIQUE + END);

        database.execSQL("CREATE TABLE " + T3 + " ("
                + "_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + T3_C1 + TEXT + ", "
                + T3_C2 + TEXT + END);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + T1);
        database.execSQL("DROP TABLE IF EXISTS " + T3);
        onCreate(database);
    }

    // Inserts a row into the workouts table
    public boolean insertWorkout(String name) {
        String nameNoSpace = removeSpaces(name);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues myCV = new ContentValues();

        myCV.put(T1_C1, nameNoSpace);

        long result = db.insert(T1, null, myCV);

        if (result == -1) {
            return false;
        } else {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + nameNoSpace + " (" + T2_C1 + TEXT + ", " + T2_C2 + TEXT + END);
            return true;
        }
    }

    // Returns a String ArrayList of all workout names
    public ArrayList<String> selectAllWorkouts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + T1_C1 + " FROM " + T1 + ";", null);
        ArrayList<String> nameList = new ArrayList<String>();

        if(cursor.moveToFirst()) {
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

    // Adds a list of entries into a workout routine
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

    // Returns the activities, durations and a formatted string
    public ArrayList<String>[] selectRoutine(String table){
        String tableNoSpaces = removeSpaces(table);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableNoSpaces + ";", null);

        ArrayList<String> activities = new ArrayList<String>();
        ArrayList<String> durations = new ArrayList<String>();
        ArrayList<String> readable = new ArrayList<String>();

        if(cursor.moveToFirst()) {
            do {
                activities.add(cursor.getString(0));
                durations.add(cursor.getString(1));
                readable.add(cursor.getString(0) + ": " + cursor.getString(1) + "s");
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  new ArrayList[]{activities, durations, readable};
    }

    // Removes spaces and puts underscores instead. For db names
    private String removeSpaces(String input){
        String space = " ";
        String underscore = "_";
        char[] result = new char[input.length()];
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == space.charAt(0)){
                result[i] = underscore.charAt(0);
            }else{
                result[i] = input.charAt(i);
            }
        }
        return new String(result);
    }

    private String replaceUnderscores(String input){
        String space = " ";
        String underscore = "_";
        char[] result = new char[input.length()];
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == underscore.charAt(0)){
                result[i] = space.charAt(0);
            }else{
                result[i] = input.charAt(i);
            }
        }
        return new String(result);
    }

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
    public ArrayList<String> selectLogbook(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + T3 + ";", null);

        ArrayList<String> tableEntires = new ArrayList<String>();

        if(cursor.moveToFirst()) {
            do {
                tableEntires.add(cursor.getString(2) + ": " +replaceUnderscores(cursor.getString(1)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return tableEntires;
    }
}
