package com.nointelligence.miles.climbharder_hangboard;

import org.junit.Test;

import static org.junit.Assert.*;

public class VerifyInputTest {
    @Test
    public void verifyInput() throws Exception {
        String inputLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String inputUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String inputSymbols = "!@#$%^&*()_+}{|:?><";
        String inputNumbers = "1234567890";

        WorkoutList workoutList = new WorkoutList();
        assertTrue(workoutList.verifyInput(inputLowerCase));
        assertTrue(workoutList.verifyInput(inputUpperCase));
        assertFalse(workoutList.verifyInput(inputSymbols));
        assertFalse(workoutList.verifyInput(inputNumbers));
    }

}