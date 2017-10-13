package com.nointelligence.miles.climbharder_hangboard;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


public class WorkoutListTest {

    @Rule
    public ActivityTestRule<WorkoutList> workoutListActivityTestRule = new ActivityTestRule<WorkoutList>(WorkoutList.class);
    private WorkoutList workoutList = null;

    @Before
    public void setUp() throws Exception {
        workoutList = workoutListActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        workoutList = null;
    }

    @Test
    public void onCreate(){
        View listWorkoutList = workoutList.findViewById(R.id.listViewAllWorkouts);
        View layoutWorkoutLayout = workoutList.findViewById(R.id.layoutWorkoutList);
        View adView = workoutList.findViewById(R.id.adView);

        assertNotNull(workoutList.databaseHelper);
        assertNotNull(adView);
        assertNotNull(listWorkoutList);
        assertNotNull(layoutWorkoutLayout);
    }

    @Test
    public void checkIfBuiltIn() throws Exception {
        BuiltInWorkouts builtInWorkouts = new BuiltInWorkouts(workoutList);
        assertTrue(workoutList.checkIfBuiltIn(builtInWorkouts.andrewFunk.getName()));
        assertTrue(workoutList.checkIfBuiltIn(builtInWorkouts.cruxCrush.getName()));
        assertTrue(workoutList.checkIfBuiltIn(builtInWorkouts.warmUp.getName()));
        assertTrue(workoutList.checkIfBuiltIn(builtInWorkouts.trainingBeta.getName()));

        assertFalse(workoutList.checkIfBuiltIn("Test"));
        assertFalse(workoutList.checkIfBuiltIn("Made up Workout"));
    }

}