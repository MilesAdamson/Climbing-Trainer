package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;

public class EditWorkout extends AppCompatActivity {

    ListView listView;
    DatabaseHelper databaseHelper;

    ArrayList<String> activities;
    ArrayList<String> durations;

    private boolean editable;
    private String workoutName;
    private String[] holdTypes;
    private String[] holdSizes;
    private String[] transSizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        workoutName = getIntent().getStringExtra("workoutName");
        editable = getIntent().getBooleanExtra("editable", true); // default value is true
        holdTypes = getResources().getStringArray(R.array.array_types);
        holdSizes = getResources().getStringArray(R.array.array_sizes);
        transSizes = getResources().getStringArray(R.array.array_transgression);

        // Load an ad into the AdMob banner view.
        // ca-app-pub-8645540572237587~1124373125
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        databaseHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listViewRoutine);
        populateListViewFromDB();
    }

    // If this workout isn't editable, do not make edit button visible
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_workout, menu);
        if (!getIntent().getBooleanExtra("editable", true)){
            menu.findItem(R.id.action_add_workout).setVisible(false);
        }
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

    // Custom adapter for the listView which has a text field and edit button
    public class WorkoutItemAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<String> list = new ArrayList<>();
        private Context context;

        public WorkoutItemAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.workout_editable_item, null);
            }

            TextView listItemText = (TextView) view.findViewById(R.id.textViewItemString);
            String itemName = list.get(position);
            listItemText.setText(itemName);

            final ImageView imageEdit = (ImageView) view.findViewById(R.id.imageViewEdit);

            // if this is a built in workout, do not show the edit button
            // users cannot change built in workouts
            if (editable) {
                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Alerts alerts = new Alerts(position);
                        alerts.entryAlert();
                        notifyDataSetChanged();
                    }
                });
            } else {
                imageEdit.setClickable(false);
                imageEdit.setVisibility(View.INVISIBLE);
            }

            return view;
        }
    }

    // Updates the listView with the data in the database, called after database has changed
    private void populateListViewFromDB(){
        ArrayList<String>[] listItems = databaseHelper.selectRoutine(workoutName);
        EditWorkout.WorkoutItemAdapter adapter =
                new EditWorkout.WorkoutItemAdapter(listItems[DatabaseHelper.READABLES], this);
        activities = listItems[DatabaseHelper.ACTIVITIES];
        durations = listItems[DatabaseHelper.DURATIONS];
        listView.setAdapter(adapter);
    }

    // Inserts a workout into the activities and durations ArrayLists
    private void insertAtPosition(String activity, String duration, int position){
        position = position + 1; // Make it below, instead of above
        ArrayList<String> tempActivities = new ArrayList<>();
        ArrayList<String> tempDurations = new ArrayList<>();

        // If there were no entriesList yet, this the the first entry
        if (activities.size() !=0) {

            for (int i = 0; i < position; i++) {
                tempActivities.add(activities.get(i));
                tempDurations.add(durations.get(i));
            }

            tempActivities.add(activity);
            tempDurations.add(duration);

            for (int i = position; i < activities.size(); i++) {
                tempActivities.add(activities.get(i));
                tempDurations.add(durations.get(i));
            }

            activities = tempActivities;
            durations = tempDurations;
        } else {
            tempActivities.add(activity);
            tempDurations.add(duration);
            activities = tempActivities;
            durations = tempDurations;
        }
    }

    // Deletes an entry from the activities and durations ArrayLists
    private void removeAtPosition(int position){
        ArrayList<String> tempActivities = new ArrayList<>();
        ArrayList<String> tempDurations = new ArrayList<>();

        for(int i = 0; i < activities.size(); i++){
            if(i != position) {
                tempActivities.add(activities.get(i));
                tempDurations.add(durations.get(i));
            }
        }

        activities = tempActivities;
        durations = tempDurations;
    }

    // Deletes the old table in the database and creates a new one with the same name,
    // then adds the activities and durations to the table
    private void saveChanges(){
        databaseHelper.deleteWorkout(workoutName);
        databaseHelper.insertWorkout(workoutName);
        String[] a = new String[activities.size()];
        String[] d = new String[durations.size()];
        a = activities.toArray(a);
        d = durations.toArray(d);
        databaseHelper.fillWorkout(workoutName, a, d);
    }

    // This class gives the user a series of alert dialogs
    // and records their inputs to create a new workout item.
    private class Alerts{

        private String hold;
        private String size;
        private String duration;
        private String type; // Rest or Hang

        private int position;

        public Alerts(int p){
            position = p;
        }

        private void setHold(String s){hold = s;}
        private void setType(String s){type = s;}
        private void setSize(String s){size = s;}
        private void setDuration(String s){duration = s;}

        public String workoutActivity;

        // builds the workout string based on whether the type is a rest or a hang
        private void buildWorkoutActivity() {
            if (type == getResources().getString(R.string.type_rest)){
                workoutActivity = type;
            } else {
                workoutActivity = size + " " + hold;
            }
        }

        // asks the user what the want to do with the item they selected
        // (delete, replace, insert below)
        public void entryAlert(){
            AlertDialog alertDialog = new AlertDialog.Builder(EditWorkout.this).create();
            alertDialog.setMessage(getString(R.string.message_entry));

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.option_insert),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            selectTypeAlert();
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.option_delete),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            removeAtPosition(position);
                            saveChanges();
                            populateListViewFromDB();
                            dialog.dismiss();
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.option_replace),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            removeAtPosition(position);
                            position -= 1; // Go back one position since one was removed
                            selectTypeAlert();
                            saveChanges();
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        // prompts the user to select a hold type
        private void selectHoldAlert(){
            final ArrayAdapter<String> adp = new ArrayAdapter<>(EditWorkout.this,
                    android.R.layout.simple_spinner_item, holdTypes);
            final Spinner sp = new Spinner(EditWorkout.this);
            sp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            sp.setAdapter(adp);
            AlertDialog.Builder builder = new AlertDialog.Builder(EditWorkout.this);
            builder.setMessage(getString(R.string.message_select_hold));

            builder.setPositiveButton(getString(R.string.option_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setHold(sp.getSelectedItem().toString());
                    selectSizeAlert();
                }
            });
            builder.setView(sp);
            builder.create().show();
        }

        // prompts the user to enter a duration in seconds
        private void selectDurationAlert(){
            AlertDialog.Builder alert = new AlertDialog.Builder(EditWorkout.this);
            alert.setTitle(getString(R.string.message_enter_duration));
            final EditText input = new EditText(EditWorkout.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setPositiveButton(getString(R.string.option_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String inString = input.getText().toString().trim();
                    if (!inString.isEmpty()) {
                        if (Integer.parseInt(inString) > 0 && Integer.parseInt(inString) <= 999) {
                            setDuration(inString);
                            buildWorkoutActivity();
                            insertAtPosition(workoutActivity, duration, position);
                            saveChanges();
                            populateListViewFromDB();
                        } else {
                            invalidInputAlert();
                        }
                    } else {
                        invalidInputAlert();
                    }
                }
            });
            alert.show();
        }

        // prompts the user to select the type of item (rest or hang)
        private void selectTypeAlert(){
            AlertDialog.Builder alert = new AlertDialog.Builder(EditWorkout.this);
            alert.setTitle(getString(R.string.message_select_type));
            alert.setPositiveButton(getString(R.string.type_hang), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setType(getString(R.string.type_hang));
                    selectHoldAlert();
                }
            });

            alert.setNeutralButton(getString(R.string.type_rest), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setType(getString(R.string.type_rest));
                    selectDurationAlert();
                }
            });
            alert.show();
        }

        // prompts the user to input a valid duration in seconds
        private void invalidInputAlert(){
            AlertDialog.Builder alert = new AlertDialog.Builder(EditWorkout.this);
            alert.setTitle(getString(R.string.error_duration));
            alert.setPositiveButton(getString(R.string.option_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectDurationAlert();
                }
            });
            alert.show();
        }

        // prompts the user to select a hold size
        private void selectSizeAlert(){
            String[] dropdown;
            // The last hold uses transgression sizes, all others use generic
            if (hold.equals(holdTypes[holdTypes.length - 1])){
                dropdown = transSizes;
            } else {
                dropdown = holdSizes;
            }

            final ArrayAdapter<String> adp = new ArrayAdapter<>(EditWorkout.this,
                    android.R.layout.simple_spinner_item, dropdown);
            final Spinner sp = new Spinner(EditWorkout.this);
            sp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            sp.setAdapter(adp);
            AlertDialog.Builder builder = new AlertDialog.Builder(EditWorkout.this);
            builder.setMessage(getString(R.string.message_select_size));

            builder.setPositiveButton(getString(R.string.option_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setSize(sp.getSelectedItem().toString());
                    selectDurationAlert();
                }
            });
            builder.setView(sp);
            builder.create().show();
        }

    }

    // Creates an alerts object which takes the user through a series of
    // alert dialogs to input a new workout item
    public void addWorkoutItem(MenuItem item){
        if (editable) {
            Alerts alerts = new Alerts(activities.size() - 1);
            alerts.selectTypeAlert();
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_cant_edit, Toast.LENGTH_LONG).show();
        }
    }

    // goes to the Workout activity with the selected workout as an intent extra string
    public void toWorkout(MenuItem item){
        if (activities.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alertDialog = new AlertDialog.Builder(EditWorkout.this).create();
                    alertDialog.setMessage(getString(R.string.message_start_question));
                    alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE,
                            getString(R.string.option_ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(),
                                            Workout.class);
                                    intent.putExtra("workoutName", workoutName);
                                    startActivity(intent);
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
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.error_empty), Toast.LENGTH_SHORT).show();
        }
    }

    // gives alert dialog with help string
    public void help(MenuItem item){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(EditWorkout.this).create();
                alertDialog.setMessage(getString(R.string.help_edit));
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

    // sends user to MainActivity
    public void home(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}