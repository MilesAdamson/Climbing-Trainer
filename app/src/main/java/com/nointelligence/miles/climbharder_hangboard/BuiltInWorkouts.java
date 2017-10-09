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

    public WorkoutTable warmUp = new WorkoutTable("Slow Warm Up by Miles Adamson",

            new String[]{"Huge Jug", "Rest", "Huge Jug", "Rest", "Huge Jug", "Rest",
            "Large Sloper", "Rest", "Large Sloper", "Rest", "Large Edge", "Rest", "Large Edge"},

            new String[]{"10", "10", "20", "10", "20", "30", "15", "15", "15", "15", "15", "15", "15"}
    );

    public WorkoutTable trainingBeta = new WorkoutTable("Beginner Routine by Training Beta",

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

    public WorkoutTable cruxCrush = new WorkoutTable("Intermediate Workout by Crux Crush",
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
    public void addDefaultWorkouts(){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        if (databaseHelper.insertWorkout(andrewFunk.getName())) {
            databaseHelper.fillWorkout(andrewFunk.getName(),
                    andrewFunk.getActivities(),
                    andrewFunk.getDurations());
        }

        if (databaseHelper.insertWorkout(warmUp.getName())) {
            databaseHelper.fillWorkout(warmUp.getName(),
                    warmUp.getActivities(),
                    warmUp.getDurations());
        }

        if (databaseHelper.insertWorkout(trainingBeta.getName())){
            databaseHelper.fillWorkout(trainingBeta.getName(),
                    trainingBeta.getActivities(),
                    trainingBeta.getDurations());
        }

        if (databaseHelper.insertWorkout(cruxCrush.getName())){
            databaseHelper.fillWorkout(cruxCrush.getName(),
                    cruxCrush.getActivities(),
                    cruxCrush.getDurations());
        }

    }
}
