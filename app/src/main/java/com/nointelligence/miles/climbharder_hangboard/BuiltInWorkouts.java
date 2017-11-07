package com.nointelligence.miles.climbharder_hangboard;

import android.content.Context;

public class BuiltInWorkouts {

    Context context;

    public BuiltInWorkouts(Context c){context = c;}

    public WorkoutTable andrewFunk = new WorkoutTable("One Arm Hangs by Andrew Funk",
            new String[]{"Left arm small edge", "Rest", "Right arm small edge", "Rest",
                "Left arm medium sloper", "Rest", "Right arm medium sloper", "Rest",
                "Left arm medium edge", "Rest", "Right arm medium edge", "Rest",
                "Left arm small edge", "Rest", "Right arm small edge", "Rest",
                "Left arm large sloper", "Rest", "Right arm large sloper"},
            new String[]{"6", "3", "6", "120", "8", "3", "8", "120", "8", "3", "8", "120", "6",
                "3", "6", "120", "12", "3", "12"}
    );

    public WorkoutTable warmUp = new WorkoutTable("Warm Up by Miles Adamson",
            new String[]{"Huge Jug", "Rest", "Huge Jug", "Rest", "Huge Jug", "Rest",
            "Large Sloper", "Rest", "Large Sloper", "Rest", "Large Edge", "Rest", "Large Edge", "Rest",
            "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Huge Jug"},
            new String[]{"10", "10", "20", "10", "20", "30", "15", "15", "15", "15", "15", "15", "15", "60",
            "10", "10", "10", "10", "10", "10", "25"}
    );

    public WorkoutTable trainingBeta = new WorkoutTable("Long Beginner Routine",
            new String[]{"Huge Jug",      "Rest", "Huge Jug",      "Rest", "Huge Jug",      "Rest", "Huge Jug",      "Rest", "Huge Jug",      "Rest", "Huge Jug",      "Rest",
                         "Large Pocket",  "Rest", "Large Pocket",  "Rest", "Large Pocket",  "Rest", "Large Pocket",  "Rest", "Large Pocket",  "Rest", "Large Pocket",  "Rest",
                         "Medium Edge",   "Rest", "Medium Edge",   "Rest", "Medium Edge",   "Rest", "Medium Edge",   "Rest", "Medium Edge",   "Rest", "Medium Edge",   "Rest",
                         "Medium Pinch",  "Rest", "Medium Pinch",  "Rest", "Medium Pinch",  "Rest", "Medium Pinch",  "Rest", "Medium Pinch",  "Rest", "Medium Pinch",  "Rest",
                         "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest",
                         "Large Edge",    "Rest", "Large Edge",    "Rest", "Large Edge",    "Rest", "Large Edge",    "Rest", "Large Edge",    "Rest", "Large Edge",    "Rest",
                         "Large Pinch",   "Rest", "Large Pinch",   "Rest", "Large Pinch",   "Rest", "Large Pinch",   "Rest", "Large Pinch",   "Rest", "Large Pinch",   "Rest",
                         "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest",
                         "Large Pocket",  "Rest", "Large Pocket",  "Rest", "Large Pocket",  "Rest", "Large Pocket",  "Rest", "Large Pocket",  "Rest", "Large Pocket"},
            new String[]{"10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10", "180",
                         "10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10", "180",
                         "10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10", "180",
                         "10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10", "180",
                         "10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10", "180",
                         "10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10", "180",
                         "10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10", "180",
                         "10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10", "180",
                         "10", "5", "10", "5", "10", "5", "10", "5", "10", "5", "10"}
    );

    public WorkoutTable cruxCrush = new WorkoutTable("Long Intermediate Workout",
            new String[]{"Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest",
                         "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest",
                         "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest",

                        "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest",
                        "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest",
                        "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest",

                        "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest",
                        "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest",
                        "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest",

                        "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest",
                        "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest",
                        "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest",

                        "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest",
                        "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest",
                        "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest",

                        "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest",
                        "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest",
                        "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch"
            },
            new String[]{"7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                         "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                         "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",

                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",

                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",

                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",

                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",

                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                        "7", "3", "7", "3", "7", "3", "7", "3", "7"}
    );

    public WorkoutTable intEdge = new WorkoutTable("Intermediate Edge Routine",
            new String[]{"Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest",
                    "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest",
                    "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge", "Rest", "Medium Edge"},
            new String[]{"7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7"}
            );

    public WorkoutTable intPocket = new WorkoutTable("Intermediate Pocket Routine",
            new String[]{"Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest",
                    "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest",
                    "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket", "Rest", "Medium Pocket"},
            new String[]{"7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7"}
    );

    public WorkoutTable intPinch = new WorkoutTable("Intermediate Pinch Routine",
            new String[]{"Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest",
                    "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest",
                    "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch", "Rest", "Medium Pinch"},
            new String[]{"7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7"}
    );

    public WorkoutTable advEdge = new WorkoutTable("Advanced Edge Routine",
            new String[]{"Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest",
                    "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest",
                    "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge", "Rest", "Small Edge"},
            new String[]{"7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7"}
    );

    public WorkoutTable intSloper = new WorkoutTable("Intermediate Sloper Routine",
            new String[]{"Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest",
                    "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest",
                    "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper", "Rest", "Medium Sloper"},
            new String[]{"7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7"}
    );

    public WorkoutTable advSloper = new WorkoutTable("Advanced Sloper Routine",
            new String[]{"Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest",
                    "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest",
                    "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper", "Rest", "Small Sloper"},
            new String[]{"7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7", "3", "7", "120",
                    "7", "3", "7", "3", "7", "3", "7", "3", "7"}
    );

    // 5 reps of 5 sec on, 20 sec rest, 2 min between sets
    public WorkoutTable marc = new WorkoutTable("Squeeze Easily by Marc Eveleigh",
            new String[]{
                    "Micro Edge", "Rest", "Micro Edge", "Rest", "Micro Edge", "Rest", "Micro Edge", "Rest", "Micro Edge", "Rest",
                    "Micro Edge (Closed Crimp)", "Rest", "Micro Edge (Closed Crimp)", "Rest", "Micro Edge (Closed Crimp)", "Rest", "Micro Edge (Closed Crimp)", "Rest", "Micro Edge (Closed Crimp)", "Rest",
                    "Two Finger Edge", "Rest", "Two Finger Edge", "Rest", "Two Finger Edge", "Rest", "Two Finger Edge", "Rest", "Two Finger Edge", "Rest",
                    "Left Arm Small Edge", "Rest", "Right Arm Small Edge", "Rest", "Left Arm Small Edge", "Rest", "Right Arm Small Edge", "Rest", "Micro Edge", "Rest",
                    "Pull Up, Micro Edge", "Rest", "Pull Up, Micro Edge", "Rest", "Pull Up, Micro Edge", "Rest", "Pull Up, Micro Edge", "Rest", "Pull Up, Micro Edge"},
            new String[]{
                    "5", "20", "5", "20", "5", "20", "5", "20", "5", "20", "120",
                    "5", "20", "5", "20", "5", "20", "5", "20", "5", "20", "120",
                    "5", "20", "5", "20", "5", "20", "5", "20", "5", "20", "120",
                    "5", "20", "5", "20", "5", "20", "5", "20", "5", "20", "120",
                    "5", "20", "5", "20", "5", "20", "5", "20", "5", "20"}
    );

    WorkoutTable[] builtIns = {andrewFunk, warmUp, intSloper, intEdge, intPocket, intPinch, trainingBeta, advSloper, advEdge, cruxCrush, marc};

    // Contains all the information DatabaseHelper expects when creating a new workout
    class WorkoutTable{
        private String name;
        private String[] activities;
        private String[] durations;
        public WorkoutTable (String n, String[] a, String[] d){
            name = n;
            activities = a;
            durations = d;
        }
        public String getName() {return name;}
        public String[] getActivities() {return activities;}
        public String[] getDurations() {return durations;}
    }

    // Inserts all the default programs into the database,
    // if they don't already exist.
    public void addDefaultWorkouts() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        for (int i = 0; i < builtIns.length; i++) {
            if (builtIns[i].getDurations().length == builtIns[i].getDurations().length) {
                if (databaseHelper.insertWorkout(builtIns[i].getName())) {
                    databaseHelper.fillWorkout(builtIns[i].getName(),
                            builtIns[i].getActivities(),
                            builtIns[i].getDurations());
                }
            }
        }
    }

}
