package com.nointelligence.miles.climbharder_hangboard;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
    static final int TICK_SIZE_MS = 25; // onTick seperation in milliseconds
    static final int INDEX_START_OFFSET_LISTVIEW = 4; // start index isn't zero since list is padded at the top
    static final int INDEX_OFFSET_FIRST_ITEM_LISTVIEW = 6; // index of first workout item
    static final int INITIAL_COUNTDOWN_MS = 5000; // 5 second initial countdown is fixed

    boolean started = false;
    boolean paused = false;
    boolean inCountdown = false;
    int currentIndex = 0;
    int totalWorkoutTime = 0;
    int workoutLength;
    long msRemainingOnPause = 0;
    String workoutName;
    ArrayList<String> activities;
    ArrayList<String> durations;
    ArrayList<String> readables;
    TextView textViewTimer;
    ListView listView;
    CountDownTimer countDownTimer;
    ProgressBar progressBar;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        textViewTimer = (TextView)findViewById(R.id.textViewTimer);
        textViewTimer.setText(START_TIME);
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

        countDownTimer = null;

        // Add blank list items so scrolling near the top works smoothly
        readables.add(0, getString(R.string.message_initial));
        for (int i = 0; i < INDEX_START_OFFSET_LISTVIEW; i++) {
            readables.add(0, "");
        }
        readables.add(getString(R.string.message_finished));

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        listView = (ListView)findViewById(R.id.listViewWorkout);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.workout_list_item,
                R.id.textViewItem,
                readables);

        listView.setAdapter(arrayAdapter);
        listView.setSelection(INDEX_START_OFFSET_LISTVIEW);
        listView.setClickable(false);
        listView.setDivider(null);
        listView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
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

    // Begins the countdown timer and steps through the workout activities
    // and their corresponding durations
    public void startWorkout(MenuItem item) {

        // if they tap the watch again and are still in the initial countdown,
        // reset workout instead of asking for pause/reset
        if (inCountdown){
            inCountdown = false;
            resetWorkout();
            return;
        }

        // Tapping the timer button while running asks to reset or pause workout
        if (started && !paused) {
            AlertDialog alertDialog = new AlertDialog.Builder(Workout.this).create();
            alertDialog.setMessage(getString(R.string.message_reset));

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.option_reset),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resetWorkout();
                            dialog.dismiss();
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.option_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.option_pause),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            pauseWorkout();
                            dialog.dismiss();
                        }
                    });

            alertDialog.show();
            return;
        }

        // if not paused and hasn't started yet, do initial countdown
        if (!paused) {
            started = true;
            inCountdown = true;
            zeroPB();

            // Start the total timer and workout timers after the initial countdown is done
            countDownTimer = new CountDownTimer(INITIAL_COUNTDOWN_MS, TICK_SIZE_MS) {

                public void onTick(long millisUntilFinished) {
                    updatePB(INITIAL_COUNTDOWN_MS, INITIAL_COUNTDOWN_MS - (int) millisUntilFinished);
                    String timeStamp = String.format("%.1f", (double) millisUntilFinished / 1000);
                    textViewTimer.setText(timeStamp);
                }

                // Start workout once initial countdown is done
                public void onFinish() {
                    String timeStamp = String.format("%.1f", (double) 5000 / 1000);
                    textViewTimer.setText(timeStamp);
                    inCountdown = false;
                    displayWorkoutActivity();
                }
            }.start();
        } else {
            // it was paused, resume workout
            displayWorkoutActivity();
            paused = false;
        }
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

    // Loop through the activities and their durations, update displays.
    private void displayWorkoutActivity() {

        listView.smoothScrollToPosition(currentIndex + INDEX_OFFSET_FIRST_ITEM_LISTVIEW);
        int length;

        // if paused, resume with the ms left saved previously when it was paused
        if (!paused) {
            length = (Integer.parseInt(durations.get(currentIndex)) * 1000);
        } else {
            length = (int)msRemainingOnPause;
        }
        final int len = length;
        countDownTimer = new CountDownTimer(len, TICK_SIZE_MS) {

            public void onTick(long millisUntilFinished) {
                msRemainingOnPause = millisUntilFinished;
                updatePB(len, len - (int)millisUntilFinished);
                String timeStamp = String.format ("%.1f", (double)millisUntilFinished / 1000);
                textViewTimer.setText(timeStamp);
            }

            public void onFinish() {
                currentIndex += 1;
                if(currentIndex == workoutLength){
                    save(null);
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
        listView.setSelection(INDEX_START_OFFSET_LISTVIEW);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = null;
        textViewTimer.setText(START_TIME);
        zeroPB();
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

    // called by the menu bar, asks to save workout to logbook.
    // also has facebook share button with link to app page on google store
    // TODO change link to google play store page
    public void save(MenuItem item){
        ShareButton fbShareButton = new ShareButton(Workout.this);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://i.imgur.com/KdFuw65.png"))
                .build();
        fbShareButton.setShareContent(content);

        AlertDialog alertDialog = new AlertDialog.Builder(Workout.this).create();
        alertDialog.setMessage(getString(R.string.message_save));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.option_save),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveWorkout();
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.option_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setView(fbShareButton);
        alertDialog.show();
    }

    private void pauseWorkout(){
        countDownTimer.cancel();
        progressBar.setProgress(0);
        paused = true;
    }
}
