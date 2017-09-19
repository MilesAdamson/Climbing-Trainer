package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.LogRecord;

public class Workout extends AppCompatActivity {

    TextView textTimer, textTotalTime, textCurrent, textNext;
    double totalTime = 0;
    Button buttonStart;
    ProgressBar progressBar;
    DatabaseHelper databaseHelper;
    String name;
    int PBmax = 1000;
    int currentIndex = 0;
    int totalWorkoutTime = 0;
    int workoutLength;
    ArrayList<String> activities;
    ArrayList<String> durations;
    ArrayList<String> readables;
    boolean started = false;
    ListView workoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        textTimer = (TextView)findViewById(R.id.textViewTimer);
        progressBar = (ProgressBar)findViewById(R.id.progressBarPercent);
        progressBar.setMax(PBmax);
        progressBar.setProgress(0);


        databaseHelper = new DatabaseHelper(this);
        name = getIntent().getStringExtra("name");
        ArrayList<String>[] lists = databaseHelper.selectRoutine(name);
        activities = lists[0];
        durations = lists[1];
        workoutLength = activities.size();

        for(int i = 0; i < durations.size(); i++){
            totalWorkoutTime += Integer.parseInt(durations.get(i));
        }

        readables = lists[2];
        readables.add(0, "Initial Countdown");
        readables.add(0, "");
        readables.add(0, "");
        readables.add(0, "");
        readables.add(0, "");
        readables.add("Last one!");

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        workoutList = (ListView)findViewById(R.id.workoutList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.workout_list_item,
                R.id.textItem,
                readables);

        workoutList.setAdapter(arrayAdapter);
        workoutList.setSelection(4);
        workoutList.setClickable(false);
        workoutList.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true; // Indicates that this has been handled by you and will not be forwarded further.
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startWorkout(MenuItem item){
        if(started){
            return;
        }
        started = true;
        final int countdown = 5000; // Initial countdown length
        zeroPB();

        // Start the total timer and workout timers after the initial countdown is done
        new CountDownTimer(countdown, 25) {

            public void onTick(long millisUntilFinished) {
                updatePB(countdown, countdown - (int)millisUntilFinished);
                String timeStamp = String.format ("%.1f", (double)millisUntilFinished / 1000);
                textTimer.setText(timeStamp);
            }

            public void onFinish() {
                String timeStamp = String.format ("%.1f", (double)5000 / 1000);
                textTimer.setText(timeStamp);

                // Start total countdown timer:
                new CountDownTimer(totalWorkoutTime * 1000, 25){
                    public void onTick(long millisUntilFinished){
                        totalTime = totalWorkoutTime - millisUntilFinished / 1000;
                        formatTotalTime();
                    }
                    public void onFinish(){
                        formatTotalTime();
                    }
                }.start();

                // Start workout timer once countdown is done:
                displayWorkoutActivity();
            }
        }.start();
    }

    private void updatePB(final int max, final int current){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress((int)(PBmax*(double)current/(double)max));
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

        new CountDownTimer(len, 25) {

            public void onTick(long millisUntilFinished) {
                updatePB(len, len - (int)millisUntilFinished);
                totalTime += 25.0 / 1000;
                formatTotalTime();
                String timeStamp = String.format ("%.1f", (double)millisUntilFinished / 1000);
                textTimer.setText(timeStamp);
            }

            public void onFinish() {
                totalTime += 25.0 / 1000;
                currentIndex += 1;
                if(currentIndex == (workoutLength)){
                    started = false;
                    currentIndex = 0;
                }else{
                    displayWorkoutActivity();
                }
            }
        }.start();
    }

    private void formatTotalTime(){
        int minutes = (int)(totalTime) / 60;
        int seconds = (int)(totalTime - minutes * 60);
        final String minutesString;
        final String secondsString;

        if(minutes < 1){
            minutesString = "00";
        }else if(minutes >=1 && minutes < 10){
            minutesString = "0" + minutes;
        }else{
            minutesString = Integer.toString(minutes);
        }

        if(seconds < 10){
            secondsString = "0" + Integer.toString(seconds);
        }else{
            secondsString = Integer.toString(seconds);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //textTotalTime.setText(minutesString + ":" + secondsString);
            }
        });
    }
}
