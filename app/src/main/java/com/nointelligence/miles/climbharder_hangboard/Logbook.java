package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Logbook extends AppCompatActivity {

    ListView logbook;
    DatabaseHelper databaseHelper;
    ArrayList<String> entries;
    ArrayList<String> keys;
    ArrayList<String> names;

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
                alertDialog.setMessage("What would you like to do?");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "View Workout",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String name = names.get(position);
                                ArrayList<String>[] routine = databaseHelper.selectRoutine(name);
                                if(routine[0].size() == 0){
                                    Toast.makeText(getApplicationContext(), "This workout no longer exists.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Intent intent = new Intent(getApplicationContext(), EditWorkout.class);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                }
                                dialog.dismiss();
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                databaseHelper.deleteLogbookEntry(keys.get(position));
                                refreshList();
                            }
                        });

                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logbook, menu);
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

    private void refreshList(){
        entries = databaseHelper.selectLogbook()[0];
        keys = databaseHelper.selectLogbook()[1];
        names = databaseHelper.selectLogbook()[2];

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                entries);

        logbook.setAdapter(arrayAdapter);
    }

    public void help(MenuItem item){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(Logbook.this).create();
                alertDialog.setMessage("Tap an entry to view the workout, or delete the entry.");
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

    public void home(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
