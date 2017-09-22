package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdListener;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import java.util.ArrayList;

public class WorkoutList extends AppCompatActivity {

    ListView listView;
    DatabaseHelper databaseHelper;
    String name;

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
        listView = (ListView) findViewById(R.id.listWorkouts);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                name = (String) (listView.getItemAtPosition(position));
                askForAd();
            }
        });

        populateListViewFromDB();
    }

    // Show changes made to workout list
    @Override
    protected void onResume(){
        super.onResume();
        populateListViewFromDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workouts, menu);
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
                    TextView textView = null;
                    ViewGroup row = (ViewGroup) v.getParent();
                    for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                        View view = row.getChildAt(itemPos);
                        if (view instanceof TextView) {
                            textView = (TextView) view; //Found
                            break;
                        }
                    }
                    askForEdit(textView.getText().toString());
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
                    askForDelete(textView.getText().toString());
                    return true; // Do not also have onclick run
                }
            });
            return view;
        }
    }

    private void askForAd(){
        AlertDialog alertDialog = new AlertDialog.Builder(WorkoutList.this).create();
        alertDialog.setMessage("Start this workout?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), Workout.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    private void askForEdit(final String name){
        AlertDialog alertDialog = new AlertDialog.Builder(WorkoutList.this).create();
        alertDialog.setMessage("Edit this workout?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), EditWorkout.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void askForDelete(final String name){
        AlertDialog alertDialog = new AlertDialog.Builder(WorkoutList.this).create();
        alertDialog.setMessage("Delete this workout?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.deleteWorkout(name);
                        populateListViewFromDB();
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void populateListViewFromDB(){
        ArrayList<String> listItems = databaseHelper.selectAllWorkouts();
        MyCustomAdapter adapter = new MyCustomAdapter(listItems, this);
        listView.setAdapter(adapter);
    }

    public void addWorkout(MenuItem item){
        AlertDialog.Builder alert = new AlertDialog.Builder(WorkoutList.this);
        alert.setTitle("Enter name for new workout:");
        final EditText input = new EditText(WorkoutList.this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        alert.setView(input);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();

                if(name.trim().length() == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Cannot be blank.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    if (verifyInput(name)) {
                        databaseHelper.insertWorkout(name);
                        Intent intent = new Intent(getApplicationContext(), EditWorkout.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Alpha-numeric characters only.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        alert.show();
    }

    // Ensure alphanumeric table names
    private boolean verifyInput(String input){

        char[] accepted = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                           'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                           'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                           'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                           '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '_'};
        Boolean accept;
        for(int i = 0; i < input.length(); i++){
            accept = false;
            for(int j = 0; j < accepted.length; j++){
                if(input.charAt(i) == accepted[j]){
                    accept = true; // If the char is found, accept this character
                }
            }
            if(!accept){
                return false; // If any character fails entirely, string fails
            }
        }
        return true;
    }

    public void help(MenuItem item){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(WorkoutList.this).create();
                alertDialog.setMessage("Tap the name of a workout to begin it. To create a workout, " +
                        "tap the plus button. To edit one, tap the pencil. To delete a workout, tap " +
                        "and hold the pencil.");
                alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }
}
