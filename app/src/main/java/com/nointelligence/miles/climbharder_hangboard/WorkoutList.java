package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;

public class WorkoutList extends AppCompatActivity {

    ListView listView;
    DatabaseHelper databaseHelper;
    String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        databaseHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listViewAllWorkouts);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                workoutName = (String) (listView.getItemAtPosition(position));
                askToStart();
            }
        });

        populateListViewFromDB();
    }

    @Override
    protected void onResume(){
        super.onResume();
        populateListViewFromDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_workouts, menu);
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

    // custom adapter class which has a textView and imageView,
    // the imageview has both onClick and longClickable
    public class WorkoutListAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<String> list = new ArrayList<>();
        private Context context;

        public WorkoutListAdapter(ArrayList<String> list, Context context) {
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
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.workout_editable_item, null);
            }

            TextView listItemText = (TextView) view.findViewById(R.id.textViewItemString);
            String itemName = list.get(position);
            listItemText.setText(itemName);

            // Calculate the workouts total time from its durations and display that
            TextView listItemDuration = (TextView) view.findViewById(R.id.textViewDuration);
            ArrayList<String> durations = databaseHelper.selectRoutine(itemName)[DatabaseHelper.DURATIONS];
            int totalTime = 0;
            for (int i = 0; i < durations.size(); i++){
                totalTime += Integer.parseInt(durations.get(i));
            }
            int s = totalTime % 60;
            totalTime /= 60;
            listItemDuration.setText(totalTime + " min, " + s + "s");

            // If it is built in, user can view the workout but not edit it or delete it
            // also change image to an eye, instead of edit pencil
            if (checkIfBuiltIn(itemName)){
                final ImageView imageEdit = (ImageView) view.findViewById(R.id.imageViewEdit);
                imageEdit.setImageResource(R.drawable.ic_view);
                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    // find the TextView containing the workout name and call askForEdit
                    public void onClick(View v) {
                        TextView textView = null;
                        LinearLayout linearLayout = null;
                        ViewGroup row = (ViewGroup) v.getParent();
                        for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                            View view = row.getChildAt(itemPos);
                            if (view instanceof LinearLayout) {
                                linearLayout = (LinearLayout) view;
                                textView = (TextView) linearLayout.getChildAt(0);
                            }
                        }
                        if (textView != null) {
                            askForEdit(textView.getText().toString(), false);
                            notifyDataSetChanged();
                        }
                    }
                });

                imageEdit.setLongClickable(false);

            } else {
                final ImageView imageEdit = (ImageView) view.findViewById(R.id.imageViewEdit);
                // find TextView with workout name and call askForEdit, but with editable flag true
                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = null;
                        LinearLayout linearLayout = null;
                        ViewGroup row = (ViewGroup) v.getParent();
                        for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                            View view = row.getChildAt(itemPos);
                            if (view instanceof LinearLayout) {
                                linearLayout = (LinearLayout) view;
                                textView = (TextView) linearLayout.getChildAt(0);
                            }
                        }
                        if (textView != null) {
                            askForEdit(textView.getText().toString(), true);
                            notifyDataSetChanged();
                        }
                    }
                });

                // if it's not built in, they can delete the workout with long click
                imageEdit.setLongClickable(true);
                imageEdit.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        LinearLayout linearLayout = null;
                        TextView textView = null;
                        ViewGroup row = (ViewGroup) v.getParent();
                        for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                            View view = row.getChildAt(itemPos);
                            if (view instanceof LinearLayout) {
                                linearLayout = (LinearLayout) view;
                                textView = (TextView) linearLayout.getChildAt(0);
                            }
                        }
                        if (textView != null) {
                            askForDelete(textView.getText().toString());
                        }
                        return true; // Do not also have onclick run
                    }
                });
            }
            return view;
        }
    }

    // an alert dialog which asks the user if they want to begin the workout they selected
    private void askToStart(){
        AlertDialog alertDialog = new AlertDialog.Builder(WorkoutList.this).create();
        alertDialog.setMessage(getString(R.string.message_start_question));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.option_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(databaseHelper.selectRoutine(workoutName)[0].size() > 0) {
                            Intent intent = new Intent(getApplicationContext(), Workout.class);
                            intent.putExtra("workoutName", workoutName);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.error_empty),
                                    Toast.LENGTH_SHORT).show();
                        }
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

    // an alert dialog which asks the user if they want to edit the workout they selected
    private void askForEdit(final String workoutName, final boolean editable){
        AlertDialog alertDialog = new AlertDialog.Builder(WorkoutList.this).create();
        // message is based on whether the user can edit or view the workout
        if (checkIfBuiltIn(workoutName)){
            alertDialog.setMessage(getString(R.string.message_view_workout));
        } else {
            alertDialog.setMessage(getString(R.string.message_edit_workout));
        }


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.option_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), EditWorkout.class);
                        intent.putExtra("workoutName", workoutName);
                        intent.putExtra("editable", editable);
                        startActivity(intent);
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

    // an alert dialog which asks the user if they want to delete the workout they selected
    private void askForDelete(final String name){
        AlertDialog alertDialog = new AlertDialog.Builder(WorkoutList.this).create();
        alertDialog.setMessage(getString(R.string.message_delete_workout));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.option_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.deleteWorkout(name);
                        populateListViewFromDB();
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

    // updates the listView with data in the database
    private void populateListViewFromDB(){
        ArrayList<String> listItems = databaseHelper.selectAllWorkouts();
        WorkoutListAdapter adapter = new WorkoutListAdapter(listItems, this);
        listView.setAdapter(adapter);
    }

    // alert dialog which asks the user for the new workouts name. If they enter a
    // valid workout name, they are taken to the EditWorkout activity to create it
    public void addWorkout(MenuItem item){
        AlertDialog.Builder alert = new AlertDialog.Builder(WorkoutList.this);
        alert.setTitle(getString(R.string.message_enter_name));
        final EditText editText = new EditText(WorkoutList.this);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        alert.setView(editText);
        alert.setPositiveButton(getString(R.string.option_add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputName = editText.getText().toString();

                if (inputName.trim().length() == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.error_blank),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (verifyInput(inputName)) {
                        databaseHelper.insertWorkout(inputName);
                        Intent intent = new Intent(getApplicationContext(), EditWorkout.class);
                        intent.putExtra("workoutName", inputName);
                        startActivity(intent);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        getString(R.string.error_input),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        alert.show();
    }

    // Ensure alphanumeric table namesList
    public boolean verifyInput(String input){
        char[] accepted = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '_'};
        Boolean accept;
        for (int i = 0; i < input.length(); i++){
            accept = false;
            for (int j = 0; j < accepted.length; j++){
                if (input.charAt(i) == accepted[j]){
                    accept = true; // If the char is found, accept this character
                }
            }
            if (!accept){
                return false; // If any character fails entirely, string fails
            }
        }
        return true;
    }

    // Check if the workout is a built in workout.
    // Return true if it is, and false if it isn't
    public boolean checkIfBuiltIn(String name)
    {
        String[] builtInWorkouts = getResources().getStringArray(R.array.workout_titles);
        for (int i = 0; i < builtInWorkouts.length; i++){
            if (name.equals(builtInWorkouts[i])){
                return true;
            }
        }
        return false;
    }

    // gives alert dialog with help string
    public void help(MenuItem item){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(WorkoutList.this).create();
                alertDialog.setMessage(getString(R.string.help_workout_list));
                alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE,
                        getString(R.string.option_ok),
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
