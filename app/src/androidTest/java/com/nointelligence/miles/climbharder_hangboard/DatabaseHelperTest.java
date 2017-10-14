package com.nointelligence.miles.climbharder_hangboard;

import android.provider.ContactsContract;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseHelperTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
        mainActivity.deleteDatabase("database.db");
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void onCreate(){
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        assertNotNull(databaseHelper.getReadableDatabase());
    }

    @Test
    public void insertSelectWorkout(){
        // insert a workout called "name" and ensure the return value from db is true (success)
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        String workoutName = "name";
        assertTrue(databaseHelper.insertWorkout(workoutName));

        // add an entry in thr workout, and select it to see if it matches what was inserted
        databaseHelper.fillWorkout(workoutName, new String[]{"test"}, new String[]{"10"});
        ArrayList<String>[] result = databaseHelper.selectRoutine(workoutName);
        assertNotNull(result);
        assertTrue(result[DatabaseHelper.ACTIVITIES].get(0).equals("test"));
        assertTrue(result[DatabaseHelper.DURATIONS].get(0).equals("10"));
        assertNotNull(databaseHelper.selectAllWorkouts());

        // delete the workout from the table and see if select result is empty
        databaseHelper.deleteWorkout(workoutName);
        assertTrue(databaseHelper.selectRoutine(workoutName)[DatabaseHelper.ACTIVITIES].size() == 0);
        assertTrue(databaseHelper.selectRoutine(workoutName)[DatabaseHelper.READABLES].size() == 0);
        assertTrue(databaseHelper.selectRoutine(workoutName)[DatabaseHelper.DURATIONS].size() == 0);
    }

    @Test
    public void logbook(){
        // insert to logbook, then select and see if result is not null and correct.
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        databaseHelper.insertLogbook("name", "date");
        ArrayList<String>[] result = databaseHelper.selectLogbook();
        assertNotNull(result);
        assertTrue(result[DatabaseHelper.NAMES].get(0).equals("name"));
        assertTrue(result[DatabaseHelper.ENTRIES].get(0).equals("date:\n name"));

        // delete the above entry from logbook, and see if select result is empty
        databaseHelper.deleteLogbookEntry(result[DatabaseHelper.KEYS].get(0));
        result = databaseHelper.selectLogbook();
        assertTrue(result[0].size() == 0);
        assertTrue(result[1].size() == 0);
    }

}