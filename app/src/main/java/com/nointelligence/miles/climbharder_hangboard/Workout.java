package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Workout extends AppCompatActivity {

    static final String START_TIME = "5:00";
    static final int PB_MAX = 1000; // number of progress bar ticks

    boolean started = false;
    double totalTime = 0;
    int currentIndex = 0;
    int totalWorkoutTime = 0;
    int workoutLength;
    int beepCount = 3;
    String workoutName;
    ArrayList<String> activities;
    ArrayList<String> durations;
    ArrayList<String> readables;
    TextView textTimer;
    ListView workoutList;
    CountDownTimer currentTimer;
    ProgressBar progressBar;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        textTimer = (TextView)findViewById(R.id.textViewTimer);
        textTimer.setText(START_TIME);
        progressBar = (ProgressBar)findViewById(R.id.progressBarPercent);
        progressBar.setMax(PB_MAX);
        progressBar.setProgress(0);
        databaseHelper = new DatabaseHelper(this);
        workoutName = getIntent().getStringExtra("workoutName");
        ArrayList<String>[] lists = databaseHelper.selectRoutine(workoutName);
        activities = lists[DatabaseHelper.ACTIVITIES];
        durations = lists[DatabaseHelper.DURATIONS];
        readables = lists[DatabaseHelper.READABLES];
        workoutLength = activities.size();

        for (int i = 0; i < durations.size(); i++){
            totalWorkoutTime += Integer.parseInt(durations.get(i));
        }

        currentTimer = null;

        // Add blank list items so scrolling near the top works
        readables.add(0, getString(R.string.message_initial));
        readables.add(0, "");
        readables.add(0, "");
        readables.add(0, "");
        readables.add(0, "");
        readables.add(getString(R.string.message_finished));

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        workoutList = (ListView)findViewById(R.id.workoutList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.workout_list_item,
                R.id.textItem,
                readables);

        workoutList.setAdapter(arrayAdapter);
        workoutList.setSelection(4);
        workoutList.setClickable(false);
        workoutList.setDivider(null);
        workoutList.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true; // Indicates that this has been handled
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (currentTimer != null) {
            currentTimer.cancel();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startWorkout(MenuItem item){
        // Tapping the timer button while running resets workout
        if(started){
            resetWorkout();
            return;
        }
        started = true;
        final int countdown = 5000; // Initial countdown length
        zeroPB();

        // Start the total timer and workout timers after the initial countdown is done
        currentTimer = new CountDownTimer(countdown, 25) {

            public void onTick(long millisUntilFinished) {
                updatePB(countdown, countdown - (int)millisUntilFinished);
                String timeStamp = String.format ("%.1f", (double)millisUntilFinished / 1000);
                textTimer.setText(timeStamp);
                //beep(millisUntilFinished, false);
            }

            // Start workout once initial countdown is done
            public void onFinish() {
                String timeStamp = String.format ("%.1f", (double)5000 / 1000);
                textTimer.setText(timeStamp);
               // beep(0, true);
                displayWorkoutActivity();
            }
        }.start();
    }

    private void updatePB(final int max, final int current){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress((int)(PB_MAX *(double)current/(double)max));
            }
        });
    }

    private void zeroPB(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(0);
            }
        });
    }

    private void displayWorkoutActivity() {
        // Loop through the activities and their durations
        workoutList.smoothScrollToPosition(currentIndex + 6);
        final int len = (Integer.parseInt(durations.get(currentIndex)) * 1000);

        currentTimer = new CountDownTimer(len, 25) {

            public void onTick(long millisUntilFinished) {
                updatePB(len, len - (int)millisUntilFinished);
                totalTime += 25.0 / 1000;
                String timeStamp = String.format ("%.1f", (double)millisUntilFinished / 1000);
                textTimer.setText(timeStamp);
                // Do not beep for very short activities
                if (len >= 3000) {
                    //beep(millisUntilFinished, false);
                }
            }

            public void onFinish() {
                //beep(0, true);
                totalTime += 25.0 / 1000;
                currentIndex += 1;
                if(currentIndex == workoutLength){
                    AlertDialog alertDialog = new AlertDialog.Builder(Workout.this).create();
                    alertDialog.setMessage(getString(R.string.message_save));

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.option_ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    saveWorkout();
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.option_no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    resetWorkout();
                } else {
                    displayWorkoutActivity();
                }
            }
        }.start();
    }

    private void resetWorkout(){
        started = false;
        currentIndex = 0;
        workoutList.setSelection(4);
        currentTimer.cancel();
        currentTimer = null;
        textTimer.setText(START_TIME);
        zeroPB();
    }

    // Gives a three beep countdown to the finish of a workout activity, and a final beep
    private void beep(long millisUntilFinished, boolean lastBeep){
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        currentVolume = (int)((double)currentVolume * 100.0 / 15.0);
        if (!lastBeep) {
            if (millisUntilFinished <= 3000 && beepCount == 3) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, currentVolume);
                toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 100);
                beepCount--;
            }

            if (millisUntilFinished <= 2000 && beepCount == 2) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, currentVolume);
                toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 100);
                beepCount--;
            }

            if (millisUntilFinished <= 1000 && beepCount == 1) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, currentVolume);
                toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 100);
                beepCount--;
            }
        } else {
            beepCount = 3;
            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, currentVolume);
            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 100);
        }
    }

    // Saves an entry to the logbook table with the current date in a string
    private void saveWorkout(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMM dd, yyyy");
        String date = sdf.format(new Date());
        databaseHelper.insertLogbook(workoutName, date);
    }

    // gives alert dialog with help string
    public void help(MenuItem item){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(Workout.this).create();
                alertDialog.setMessage(getString(R.string.help_workout));
                alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.option_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    // called by the menu bar, sends user to MainActivity
    public void home(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
