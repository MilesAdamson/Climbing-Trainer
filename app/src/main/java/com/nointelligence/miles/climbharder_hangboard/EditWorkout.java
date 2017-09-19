package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;

public class EditWorkout extends AppCompatActivity {

    ListView listView;
    DatabaseHelper databaseHelper;

    ArrayList<String> activities;
    ArrayList<String> durations;

    private String workoutName;
    private final String[] holds = new String[]{"Jug", "Pinch", "Crimp", "Edge", "Sloper", "Pocket"};
    private final String[] holdSizes = new String[]{"Huge", "Large", "Medium", "Small", "Micro"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        workoutName = getIntent().getStringExtra("name");
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        databaseHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listRoutine);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                //
            }
        });

        populateListViewFromDB();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_workout, menu);
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
    public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<String> list = new ArrayList<String>();
        private Context context;

        public MyCustomAdapter(ArrayList<String> list, Context context) {
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
                view = inflater.inflate(R.layout.row, null);
            }

            TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
            listItemText.setText(list.get(position));
            final ImageView imageEdit = (ImageView) view.findViewById(R.id.image_edit);

            imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alerts alerts = new Alerts(position);
                    alerts.entryAlert();
                    notifyDataSetChanged();
                }
            });

            imageEdit.setLongClickable(true);
            imageEdit.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Find the textView which contains the string to query db with
                    TextView textView = null;
                    ViewGroup row = (ViewGroup) v.getParent();
                    for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                        View view = row.getChildAt(itemPos);
                        if (view instanceof TextView) {
                            textView = (TextView) view; //Found
                            break;
                        }
                    }
                    //
                    return true; // Do not also have onclick run
                }
            });
            return view;
        }
    }

    private void populateListViewFromDB(){
        ArrayList<String>[] listItems = databaseHelper.selectRoutine(workoutName);
        EditWorkout.MyCustomAdapter adapter = new EditWorkout.MyCustomAdapter(listItems[2], this);
        activities = listItems[0];
        durations = listItems[1];
        listView.setAdapter(adapter);
    }

    private void insertAtPosition(String activity, String duration, int position){
        position = position + 1; // Make it below, instead of above
        ArrayList<String> tempActivities = new ArrayList<>();
        ArrayList<String> tempDurations = new ArrayList<>();

        if(activities.size() !=0) {

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
        }else{
            // If there were no entries yet, this the the first entry
            tempActivities.add(activity);
            tempDurations.add(duration);
            activities = tempActivities;
            durations = tempDurations;
        }
    }

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

    private void populateListViewFromLists(){
        ArrayList<String> listItems = new ArrayList<String>();
        for(int i = 0; i < activities.size(); i++){
            listItems.add(activities.get(i) + " for " + durations.get(i) + " seconds");
        }
        EditWorkout.MyCustomAdapter adapter = new EditWorkout.MyCustomAdapter(listItems, this);
        listView.setAdapter(adapter);
    }

    private void saveChanges(){
        databaseHelper.deleteWorkout(workoutName);
        databaseHelper.insertWorkout(workoutName);
        String[] a = new String[activities.size()];
        String[] d = new String[durations.size()];
        a = activities.toArray(a);
        d = durations.toArray(d);
        databaseHelper.fillWorkout(workoutName, a, d);
    }

    private class Alerts{

        private String hold;
        private String size;
        private String duration;
        private String type;

        private int position;

        public Alerts(int p){
            position = p;
        };

        private void setHold(String s){hold = s;}
        private void setType(String s){type = s;}
        private void setSize(String s){size = s;}
        private void setDuration(String s){duration = s;}

        public String workoutActivity;

        private void buildWorkoutActivity(){
            switch (type){
                case "Rest":
                    workoutActivity = type;
                    break;
                case "Hang":
                    workoutActivity = size + " " + hold;
                    break;
            }
        }

        public void entryAlert(){
            AlertDialog alertDialog = new AlertDialog.Builder(EditWorkout.this).create();
            alertDialog.setMessage("What would you like to do?");

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Insert Below",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            selectTypeAlert();
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            removeAtPosition(position);
                            saveChanges();
                            populateListViewFromDB();
                            dialog.dismiss();
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Edit",
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

        private void selectHoldAlert(){
            final ArrayAdapter<String> adp = new ArrayAdapter<String>(EditWorkout.this,
                    android.R.layout.simple_spinner_item, holds);
            final Spinner sp = new Spinner(EditWorkout.this);
            sp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            sp.setAdapter(adp);
            AlertDialog.Builder builder = new AlertDialog.Builder(EditWorkout.this);
            builder.setMessage("Select hold type.");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setHold(sp.getSelectedItem().toString());
                    selectSizeAlert();
                }
            });
            builder.setView(sp);
            builder.create().show();
        }

        private void selectDurationAlert(){
            AlertDialog.Builder alert = new AlertDialog.Builder(EditWorkout.this);
            alert.setTitle("Enter the duration in seconds.");
            final EditText input = new EditText(EditWorkout.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String inString = input.getText().toString().trim();
                    if (!inString.isEmpty()) {
                        if (Integer.parseInt(inString) > 0 && Integer.parseInt(inString) <= 99) {
                            setDuration(inString);
                            buildWorkoutActivity();
                            insertAtPosition(workoutActivity, duration, position);
                            saveChanges();
                            populateListViewFromDB();
                        } else {
                            invalidInputAlert();
                        }
                    }else{
                        invalidInputAlert();
                    }
                }
            });
            alert.show();
        }

        private void selectTypeAlert(){
            AlertDialog.Builder alert = new AlertDialog.Builder(EditWorkout.this);
            alert.setTitle("Add rest or hang?");
            alert.setPositiveButton("Hang", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setType("Hang");
                    selectHoldAlert();
                }
            });

            alert.setNeutralButton("Rest", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setType("Rest");
                    selectDurationAlert();
                }
            });
            alert.show();
        }

        private void invalidInputAlert(){
            AlertDialog.Builder alert = new AlertDialog.Builder(EditWorkout.this);
            alert.setTitle("Duration cannot be zero, or more than 99.");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectDurationAlert();
                }
            });
            alert.show();
        }

        private void selectSizeAlert(){
            final ArrayAdapter<String> adp = new ArrayAdapter<String>(EditWorkout.this,
                    android.R.layout.simple_spinner_item, holdSizes);
            final Spinner sp = new Spinner(EditWorkout.this);
            sp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            sp.setAdapter(adp);
            AlertDialog.Builder builder = new AlertDialog.Builder(EditWorkout.this);
            builder.setMessage("Select hold size.");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

    public void addWorkoutItem(MenuItem item){
        Alerts alerts = new Alerts(activities.size() - 1);
        alerts.selectTypeAlert();
    }

}
