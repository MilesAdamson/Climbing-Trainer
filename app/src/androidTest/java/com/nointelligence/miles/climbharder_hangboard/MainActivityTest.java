package com.nointelligence.miles.climbharder_hangboard;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.Menu;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void onCreate() throws Exception {
        View ad = mainActivity.findViewById(R.id.adView);
        View background = mainActivity.findViewById(R.id.imageViewBackground);
        View layoutWorkout = mainActivity.findViewById(R.id.layoutWorkoutMainActivity);
        View layoutLogbook = mainActivity.findViewById(R.id.layoutLogbook);
        View imageBicep = mainActivity.findViewById(R.id.imageViewBicep);
        View textViewWorkouts = mainActivity.findViewById(R.id.textViewWorkouts);
        View imageLogbook = mainActivity.findViewById(R.id.imageViewLogbook);
        View textViewLogbook = mainActivity.findViewById(R.id.textViewLogbook);
        assertNotNull(ad);
        assertNotNull(background);
        assertNotNull(layoutWorkout);
        assertNotNull(layoutLogbook);
        assertNotNull(imageBicep);
        assertNotNull(textViewWorkouts);
        assertNotNull(imageLogbook);
        assertNotNull(textViewLogbook);

        // test that built in workouts were inserted correctly
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        BuiltInWorkouts builtInWorkouts = new BuiltInWorkouts(mainActivity);
        ArrayList<String>[] arrayLists = databaseHelper.selectRoutine(builtInWorkouts.andrewFunk.getName());
        assertNotEquals(arrayLists[DatabaseHelper.DURATIONS].size(), 0);
        assertNotEquals(arrayLists[DatabaseHelper.ACTIVITIES].size(), 0);
        assertNotEquals(arrayLists[DatabaseHelper.READABLES].size(), 0);
        arrayLists = databaseHelper.selectRoutine(builtInWorkouts.cruxCrush.getName());
        assertNotEquals(arrayLists[DatabaseHelper.DURATIONS].size(), 0);
        assertNotEquals(arrayLists[DatabaseHelper.ACTIVITIES].size(), 0);
        assertNotEquals(arrayLists[DatabaseHelper.READABLES].size(), 0);
        arrayLists = databaseHelper.selectRoutine(builtInWorkouts.warmUp.getName());
        assertNotEquals(arrayLists[DatabaseHelper.DURATIONS].size(), 0);
        assertNotEquals(arrayLists[DatabaseHelper.ACTIVITIES].size(), 0);
        assertNotEquals(arrayLists[DatabaseHelper.READABLES].size(), 0);
        arrayLists = databaseHelper.selectRoutine(builtInWorkouts.trainingBeta.getName());
        assertNotEquals(arrayLists[DatabaseHelper.DURATIONS].size(), 0);
        assertNotEquals(arrayLists[DatabaseHelper.ACTIVITIES].size(), 0);
        assertNotEquals(arrayLists[DatabaseHelper.READABLES].size(), 0);
    }

    @Test
    public void onResume() throws Exception {
        View ad = mainActivity.findViewById(R.id.adView);
        View background = mainActivity.findViewById(R.id.imageViewBackground);
        View layoutWorkout = mainActivity.findViewById(R.id.layoutWorkoutMainActivity);
        View layoutLogbook = mainActivity.findViewById(R.id.layoutLogbook);
        View imageBicep = mainActivity.findViewById(R.id.imageViewBicep);
        View textViewWorkouts = mainActivity.findViewById(R.id.textViewWorkouts);
        View imageLogbook = mainActivity.findViewById(R.id.imageViewLogbook);
        View textViewLogbook = mainActivity.findViewById(R.id.textViewLogbook);
        assertNotNull(ad);
        assertNotNull(background);
        assertNotNull(layoutWorkout);
        assertNotNull(layoutLogbook);
        assertNotNull(imageBicep);
        assertNotNull(textViewWorkouts);
        assertNotNull(imageLogbook);
        assertNotNull(textViewLogbook);
    }

}