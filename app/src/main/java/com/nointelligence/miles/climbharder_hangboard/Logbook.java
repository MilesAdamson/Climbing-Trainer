package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Logbook extends AppCompatActivity {

    ListView logbook;
    DatabaseHelper databaseHelper;
    ArrayList<String> entriesList;
    ArrayList<String> keysList;
    ArrayList<String> namesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logbook);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        databaseHelper = new DatabaseHelper(this);
        logbook = (ListView)findViewById(R.id.listLogbook);
        refreshList();

        logbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long arg3) {
                AlertDialog alertDialog = new AlertDialog.Builder(Logbook.this).create();
                alertDialog.setMessage(getString(R.string.message_entry));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.option_view),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String workoutName = namesList.get(position);
                                ArrayList<String>[] routine = databaseHelper.selectRoutine(workoutName);
                                if (routine[0].size() == 0){
                                    Toast.makeText(getApplicationContext(),
                                            R.string.error_exist,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), EditWorkout.class);
                                    intent.putExtra("workoutName", workoutName);
                                    startActivity(intent);
                                }
                                dialog.dismiss();
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.option_delete),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                databaseHelper.deleteLogbookEntry(keysList.get(position));
                                refreshList();
                            }
                        });

                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logbook, menu);
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

    private void refreshList(){
        entriesList = databaseHelper.selectLogbook()[0];
        keysList = databaseHelper.selectLogbook()[1];
        namesList = databaseHelper.selectLogbook()[2];

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                entriesList);

        logbook.setAdapter(arrayAdapter);
    }

    // gives alert dialog with help string
    public void help(MenuItem item){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(Logbook.this).create();
                alertDialog.setMessage(getString(R.string.help_logbook));
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
