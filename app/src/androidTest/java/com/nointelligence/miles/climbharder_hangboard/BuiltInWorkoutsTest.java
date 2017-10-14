package com.nointelligence.miles.climbharder_hangboard;

import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


public class BuiltInWorkoutsTest {

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
    public void addDefaultWorkouts() throws Exception {
        // call addDefaultWorkout and select them, to ensure they were inserted correctly
        BuiltInWorkouts builtInWorkouts = new BuiltInWorkouts(mainActivity);
        assertNotNull(builtInWorkouts);
        builtInWorkouts.addDefaultWorkouts();
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        assertNotNull(databaseHelper.selectRoutine(builtInWorkouts.andrewFunk.getName())[DatabaseHelper.ACTIVITIES].size() != 0);
        assertNotNull(databaseHelper.selectRoutine(builtInWorkouts.warmUp.getName())[DatabaseHelper.ACTIVITIES].size() != 0);
        assertNotNull(databaseHelper.selectRoutine(builtInWorkouts.cruxCrush.getName())[DatabaseHelper.ACTIVITIES].size() != 0);
        assertNotNull(databaseHelper.selectRoutine(builtInWorkouts.trainingBeta.getName())[DatabaseHelper.ACTIVITIES].size() != 0);
    }

}